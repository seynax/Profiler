package fr.seynax.profiler;

import fr.seynax.profiler.metric.calculator.EnumMetricMethods;
import fr.seynax.profiler.metric.calculator.EnumMetricSource;
import fr.seynax.profiler.metric.calculator.MetricCalculator;
import fr.seynax.profiler.metric.calculator.strategy.IMetricCalculatorStrategy;
import fr.seynax.profiler.metric.calculator.strategy.primitives.ByteCalculatorStrategy;
import fr.seynax.profiler.metric.calculator.strategy.primitives.FloatCalculatorStrategy;
import fr.seynax.profiler.metric.calculator.strategy.primitives.ShortCalculatorStrategy;
import fr.seynax.profiler.utils.IMeasurer;

public class Main {
	public static void main(String[] argsIn) throws InterruptedException {
		// Example with FloatCalculatorStrategy
		IMeasurer<Float> measurer = () -> (float) System.nanoTime(); // Implement your measurer
		IMetricCalculatorStrategy<Float> floatStrategy = new FloatCalculatorStrategy();
		MetricCalculator<Float> floatMetricCalculator = new MetricCalculator<>(measurer, EnumMetricMethods.DIFF, floatStrategy, EnumMetricSource.START, EnumMetricSource.END);

		floatMetricCalculator.start();
		// Simulate some data collection
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		floatMetricCalculator.stop();

		for (var result : floatMetricCalculator.results().values()) {
			float resultInSeconds = result / 1_000_000_000f;
			System.out.println(resultInSeconds + " seconds");
		}

		// Example with ByteCalculatorStrategy
		IMeasurer<Byte> byteMeasurer = () -> (byte) 4; // Implement your measurer
		IMetricCalculatorStrategy<Byte> byteStrategy = new ByteCalculatorStrategy();
		MetricCalculator<Byte> byteMetricCalculator = new MetricCalculator<>(byteMeasurer, EnumMetricMethods.AVG, byteStrategy, EnumMetricSource.START, EnumMetricSource.END);

		byteMetricCalculator.start();
		// Simulate some data collection
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		byteMetricCalculator.stop();

		for (var result : byteMetricCalculator.results().values()) {
			double resultInSeconds = result / 1_000_000_000.0;
			System.out.println(resultInSeconds + " seconds");
		}

		// Example with ShortCalculatorStrategy
		IMeasurer<Short> shortMeasurer = () -> (short) 4; // Implement your measurer
		IMetricCalculatorStrategy<Short> shortStrategy = new ShortCalculatorStrategy();
		MetricCalculator<Short> shortMetricCalculator = new MetricCalculator<>(shortMeasurer, EnumMetricMethods.AVG, shortStrategy, EnumMetricSource.START, EnumMetricSource.END);

		shortMetricCalculator.start();
		// Simulate some data collection
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		shortMetricCalculator.stop();

		for (var result : shortMetricCalculator.results().values()) {
			double resultInSeconds = result / 1_000_000_000.0;
			System.out.println(resultInSeconds + " seconds");
		}
	}
}