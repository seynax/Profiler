package fr.seynax.profiler;

public class Main
{
	public static void main(String[] argsIn) throws InterruptedException
	{
		// Measurer = function who return a value

		// MetricCalculator = function who use a measurer to calculate a metric :
		// - at start or end
		// - start - end
		// - end - start
		// - repeated at interval between start and end

		// TimedValue : timed value
		// HistoricData : stack of TimedValue
		// Metric : named data who contains :
		// - average
		// - min
		// - max
		// - sum
		// - last
		// - historic
	}
}