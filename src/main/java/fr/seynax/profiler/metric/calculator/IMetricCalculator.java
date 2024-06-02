package fr.seynax.profiler.metric.calculator;

import java.util.Map;
import java.util.Stack;

public interface IMetricCalculator<V extends Number>
{
	String method();

	EnumMetricSource[] sources();

	void start();

	void add(V valueIn);
	void addAll(V... valuesIn);

	void stop();

	Map<Long, V> measures();
	Map<Long, V> results();
}