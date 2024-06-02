package fr.seynax.profiler;

import fr.seynax.profiler.metric.calculator.EnumMetricMethods;
import fr.seynax.profiler.metric.calculator.EnumMetricSource;
import fr.seynax.profiler.metric.calculator.MetricCalculator;

public class Main
{
	public static void main(String[] argsIn) throws InterruptedException
	{
		// TimedValue : timed value
		// HistoricData : stack of TimedValue
		// Metric : named data who contains :
		// - average
		// - min
		// - max
		// - sum
		// - last
		// - historic

		var metricCalculator = new MetricCalculator(() -> System.nanoTime(), EnumMetricMethods.DIFF, EnumMetricSource.START, EnumMetricSource.END);
		metricCalculator.start();
		Thread.sleep(2_000);
		metricCalculator.stop();
		for(var entry : metricCalculator.measures().entrySet())
		{
			var time = entry.getKey();
			var value = entry.getValue();

			System.out.println(time + ": " + (value) + " nanoseconds");
		}

		for(var result : metricCalculator.results().values())
		{
			System.out.println((result / 1_000_000_000L) + " seconds");
		}
	}
}