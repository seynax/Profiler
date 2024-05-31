package fr.seynax.profiler.metric.calculator;

import java.util.Stack;

public interface IMetricCalculator
{
	Stack<EnumMetricSource> metricSources();

	EnumMetricMethods metricMethod();

	void start();

	void stop();
}