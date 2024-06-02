package fr.seynax.profiler.metric.calculator;

import fr.seynax.profiler.utils.IMeasurer;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

public class MetricCalculator implements IMetricCalculator
{
    private final IMeasurer measurer;
    private final @Getter EnumMetricMethods method;
    private final @Getter EnumMetricSource[] sources;
    private final @Getter Map<Long, Double> measures;
    private final @Getter Map<Long, Double> results;
    private @Getter @Setter long interval;
    private boolean isRunning;
    private boolean hasBeenStopped;
    private Thread thread;

    public MetricCalculator(IMeasurer measurerIn, EnumMetricMethods methodIn, EnumMetricSource... sourcesIn)
    {
        this.measurer = measurerIn;
        this.method = methodIn;
        this.sources = new EnumMetricSource[sourcesIn.length];
        for(int i = 0; i < sourcesIn.length; i ++)
        {
            this.sources[i] = sourcesIn[i];
        }
        this.measures = new LinkedHashMap<>();
        this.results = new LinkedHashMap<>();
    }

    public final boolean containsSource(EnumMetricSource sourceIn)
    {
        for(var source : this.sources)
        {
            if(source.equals(sourceIn))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public void start()
    {
        if (containsSource(EnumMetricSource.START))
        {
            this.measures.put(System.nanoTime(), this.measurer.measure());
        }

        this.isRunning = true;
        this.hasBeenStopped = false;
        if(containsSource(EnumMetricSource.INTERVAL))
        {
            this.thread = new Thread(() -> {
               while(this.isRunning)
               {
                   if(this.interval > 0) {
                       try {
                           Thread.sleep(this.interval);
                       } catch (InterruptedException e) {
                           throw new RuntimeException(e);
                       }

                       synchronized (this.measures) {
                           this.measures.put(System.nanoTime(), this.measurer.measure());
                       }
                   }
               }

               this.hasBeenStopped = true;
            });
        }
    }

    @Override
    public void add(double valueIn)
    {
        if(!containsSource(EnumMetricSource.MANUAL))
        {
            throw new IllegalArgumentException("[ERROR] MetricCalculator : cannot add value manually." +
                    "MANUAL EnumMetricSource is not indicated.");
        }

        this.measures.put(System.nanoTime(), valueIn);
    }

    @Override
    public void addAll(double... valuesIn)
    {
        if(!containsSource(EnumMetricSource.MANUAL))
        {
            throw new IllegalArgumentException("[ERROR] MetricCalculator : cannot add value manually." +
                    "MANUAL EnumMetricSource is not indicated.");
        }

        for(var value : valuesIn)
        {
            this.measures.put(System.nanoTime(), value);
        }
    }

    public final double get(int indexIn)
    {
        int i = 0;
        for(var value : this.measures.values())
        {
            if(i == indexIn)
            {
                return value;
            }
            i ++;
        }
        throw new RuntimeException("[ERROR] MetricCalculator : " + indexIn + " index not found in measures !");
    }

    @Override
    public void stop()
    {
        var stopTime = System.nanoTime();
        this.isRunning = false;
        if(containsSource(EnumMetricSource.INTERVAL)) {
            try {
                this.thread.join();
                var start = System.nanoTime();
                while (!this.hasBeenStopped && System.nanoTime() - start <= 1_000_000_0L) {
                    Thread.sleep(5);
                }
                this.thread.interrupt();
                this.hasBeenStopped = true;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            this.hasBeenStopped = true;
        }

        if (containsSource(EnumMetricSource.END))
        {
            this.measures.put(System.nanoTime(), this.measurer.measure());
        }

        switch(this.method)
        {
            case ALL:
                for(var entry : this.measures.entrySet())
                {
                    var time = entry.getKey();
                    var value = entry.getValue();
                    this.results.put(time, value);
                }
                break;
            case MIN:
                var min = Double.POSITIVE_INFINITY;
                for(var value : this.measures.values())
                {
                    if(value < min)
                    {
                        min = value;
                    }
                }
                this.results.put(stopTime, min);
                break;
            case MAX:
                var max = Double.NEGATIVE_INFINITY;
                for(var value : this.measures.values())
                {
                    if(value > max)
                    {
                        max = value;
                    }
                }
                this.results.put(stopTime, max);
                break;
            case AVG:
                var average = 0.0D;
                var count = 0;
                for(var value : this.measures.values())
                {
                    average += value;
                    count ++;
                }
                average /= count;
                this.results.put(stopTime, average);
                break;
            case MEDIAN:
                var middle = this.measures.size();
                var lower = (int) Math.floor(middle);
                var upper = (int) Math.ceil(middle);
                    var median = this.get(lower);
                if(lower != upper)
                {
                    median += this.get(upper);
                    median /= 2.0D;
                }
                this.results.put(stopTime, median);
                break;
            case FIRST:
                this.results.put(stopTime, this.get(0));
                break;
            case LAST:
                this.results.put(stopTime, this.get(this.measures.size() - 1));
                break;
            case DIFF:
                var diff = 0.0D;
                var last = 0.0D;
                for(var value : this.measures.values())
                {
                    diff = value - last;
                    last = diff;
                }
                this.results.put(stopTime, diff);
                break;
            case INV_DIFF:
                diff = 0.0D;
                last = 0.0D;
                var i = 0;
                for(var value : this.measures.values())
                {
                    if(i > 0)
                    {
                        diff = last - value;
                    }
                    last = diff;
                    i ++;
                }
                this.results.put(stopTime, diff);
                break;
            case  SUM:
                var sum = 0.0D;
                for(var value : this.measures.values())
                {
                    sum += value;
                }
                this.results.put(stopTime, sum);
                break;
            case DIV:
                i = 0;
                var div = 0.0D;
                for(var value : this.measures.values())
                {
                    if(i == 0)
                    {
                        div = value;
                    }
                    else
                    {
                        div /= value;
                    }
                    i ++;
                }
                this.results.put(stopTime, div);
                break;
            case MUL:
                var mul = 0.0D;
                for(var value : this.measures.values())
                {
                    mul *= value;
                }
                this.results.put(stopTime, mul);
                break;
        }
    }
}
