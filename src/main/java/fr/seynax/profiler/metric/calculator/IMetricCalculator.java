package fr.seynax.profiler.metric.calculator;

import java.util.Map;
import java.util.Stack;

public interface IMetricCalculator
{
	EnumMetricMethods method();

	EnumMetricSource[] sources();

	void start();

	void add(double valueIn);
	void addAll(double... valuesIn);

	void stop();

	Map<Long, Double> measures();
	Map<Long, Double> results();
}