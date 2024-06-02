package fr.seynax.profiler.metric.calculator.strategy;

import java.util.Collection;
import java.util.Map;

public interface IMetricCalculatorStrategy<V extends Number> {
    Collection<V> calculateAll(Map<Long, V> measures);
    V calculateMin(Map<Long, V> measures);
    V calculateMax(Map<Long, V> measures);
    V calculateAverage(Map<Long, V> measures);
    V calculateMedian(Map<Long, V> measures);
    V calculateFirst(Map<Long, V> measures);
    V calculateLast(Map<Long, V> measures);
    V calculateDiff(Map<Long, V> measures);
    V calculateInvDiff(Map<Long, V> measures);
    V calculateSum(Map<Long, V> measures);
    V calculateDiv(Map<Long, V> measures);
    V calculateMul(Map<Long, V> measures);
}
