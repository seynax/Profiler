package fr.seynax.profiler;

import fr.seynax.profiler.metric.calculator.EnumMetricSource;
import fr.seynax.profiler.metric.calculator.MetricCalculator;
import fr.seynax.profiler.metric.calculator.strategy.IStrategy;
import fr.seynax.profiler.metric.calculator.strategy.StrategyRegistry;
import fr.seynax.profiler.metric.calculator.strategy.impl.IMetricCalculatorStrategy;
import fr.seynax.profiler.metric.calculator.strategy.impl.primitives.ByteCalculatorStrategy;
import fr.seynax.profiler.metric.calculator.strategy.impl.primitives.FloatCalculatorStrategy;
import fr.seynax.profiler.metric.calculator.strategy.impl.primitives.ShortCalculatorStrategy;
import fr.seynax.profiler.utils.IMeasurer;

import java.util.Collection;
import java.util.List;

public class Main {
	public static void main(String[] argsIn) throws InterruptedException {
		// Example with FloatCalculatorStrategy
		IMeasurer<Float> measurer = () -> (float) System.nanoTime(); // Implement your measurer
		var strategyProvider = new StrategyRegistry(() -> {
			return null;
		});
		strategyProvider.add("DIFF", new IStrategy<Float>() {
			@Override
			public void calculate(List<Float> resultsIn, Collection<Float> measuresIn) {

			}
		})
		MetricCalculator<Float> floatMetricCalculator = new MetricCalculator<>(measurer, "DIFF", strategyProvider, EnumMetricSource.START, EnumMetricSource.END);

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
	}
}