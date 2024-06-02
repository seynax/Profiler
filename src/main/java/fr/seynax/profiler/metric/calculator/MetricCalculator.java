package fr.seynax.profiler.metric.calculator;

import fr.seynax.profiler.metric.calculator.EnumMetricMethods;
import fr.seynax.profiler.metric.calculator.EnumMetricSource;
import fr.seynax.profiler.metric.calculator.IMetricCalculator;
import fr.seynax.profiler.metric.calculator.strategy.IMetricCalculatorStrategy;
import fr.seynax.profiler.utils.IMeasurer;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

public class MetricCalculator<V extends Number> implements IMetricCalculator<V> {
    private final IMeasurer<V> measurer;
    private final @Getter EnumMetricMethods method;
    private final @Getter EnumMetricSource[] sources;
    private final @Getter Map<Long, V> measures;
    private final @Getter Map<Long, V> results;
    private @Getter @Setter long interval;
    private boolean isRunning;
    private boolean hasBeenStopped;
    private Thread thread;
    private final IMetricCalculatorStrategy<V> strategy;

    public MetricCalculator(IMeasurer<V> measurerIn, EnumMetricMethods methodIn, IMetricCalculatorStrategy<V> strategyIn, EnumMetricSource... sourcesIn) {
        this.measurer = measurerIn;
        this.method = methodIn;
        this.strategy = strategyIn;
        this.sources = new EnumMetricSource[sourcesIn.length];
        for (int i = 0; i < sourcesIn.length; i++) {
            this.sources[i] = sourcesIn[i];
        }
        this.measures = new LinkedHashMap<>();
        this.results = new LinkedHashMap<>();
    }

    public final boolean containsSource(EnumMetricSource sourceIn) {
        for (var source : this.sources) {
            if (source.equals(sourceIn)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void start() {
        if (containsSource(EnumMetricSource.START)) {
            this.measures.put(System.nanoTime(), this.measurer.measure());
        }

        this.isRunning = true;
        this.hasBeenStopped = false;
        if (containsSource(EnumMetricSource.INTERVAL)) {
            this.thread = new Thread(() -> {
                while (this.isRunning) {
                    if (this.interval > 0) {
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
            this.thread.start();
        }
    }

    @Override
    public void add(V valueIn) {
        if (!containsSource(EnumMetricSource.MANUAL)) {
            throw new IllegalArgumentException("[ERROR] MetricCalculator : cannot add value manually." +
                    "MANUAL EnumMetricSource is not indicated.");
        }

        this.measures.put(System.nanoTime(), valueIn);
    }

    @Override
    public void addAll(V... valuesIn) {
        if (!containsSource(EnumMetricSource.MANUAL)) {
            throw new IllegalArgumentException("[ERROR] MetricCalculator : cannot add value manually." +
                    "MANUAL EnumMetricSource is not indicated.");
        }

        for (var value : valuesIn) {
            this.measures.put(System.nanoTime(), value);
        }
    }

    public final V get(int indexIn) {
        int i = 0;
        for (var value : this.measures.values()) {
            if (i == indexIn) {
                return value;
            }
            i++;
        }
        throw new RuntimeException("[ERROR] MetricCalculator : " + indexIn + " index not found in measures !");
    }

    @Override
    public void stop() {
        var stopTime = System.nanoTime();
        this.isRunning = false;
        if (containsSource(EnumMetricSource.INTERVAL)) {
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

        if (containsSource(EnumMetricSource.END)) {
            this.measures.put(System.nanoTime(), this.measurer.measure());
        }

        switch (this.method) {
            case ALL:
                for (var entry : this.measures.entrySet()) {
                    var time = entry.getKey();
                    var value = entry.getValue();
                    this.results.put(time, value);
                }
                break;
            case MIN:
                this.results.put(stopTime, strategy.calculateMin(this.measures));
                break;
            case MAX:
                this.results.put(stopTime, strategy.calculateMax(this.measures));
                break;
            case AVG:
                this.results.put(stopTime, strategy.calculateAverage(this.measures));
                break;
            case MEDIAN:
                this.results.put(stopTime, strategy.calculateMedian(this.measures));
                break;
            case FIRST:
                this.results.put(stopTime, strategy.calculateFirst(this.measures));
                break;
            case LAST:
                this.results.put(stopTime, strategy.calculateLast(this.measures));
                break;
            case DIFF:
                this.results.put(stopTime, strategy.calculateDiff(this.measures));
                break;
            case INV_DIFF:
                this.results.put(stopTime, strategy.calculateInvDiff(this.measures));
                break;
            case SUM:
                this.results.put(stopTime, strategy.calculateSum(this.measures));
                break;
            case DIV:
                this.results.put(stopTime, strategy.calculateDiv(this.measures));
                break;
            case MUL:
                this.results.put(stopTime, strategy.calculateMul(this.measures));
                break;
        }
    }
}