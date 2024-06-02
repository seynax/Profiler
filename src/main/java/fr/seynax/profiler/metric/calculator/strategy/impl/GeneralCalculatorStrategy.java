package fr.seynax.profiler.metric.calculator.strategy.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class GeneralCalculatorStrategy<V extends Number> implements IMetricCalculatorStrategy<V> {
    @Override
    public Collection<V> calculateAll(Map<Long, V> measures) {
        var values = new ArrayList<V>();
        for(var measure : measures.values())
        {
            values.add(measure);
        }
        return values;
    }

    @Override
    public V calculateMin(Map<Long, V> measures) {
        return measures.values().stream().min(this::compareNumbers).orElse(null);
    }

    @Override
    public V calculateMax(Map<Long, V> measures) {
        return measures.values().stream().max(this::compareNumbers).orElse(null);
    }

    @Override
    public V calculateAverage(Map<Long, V> measures) {
        double sum = measures.values().stream().mapToDouble(Number::doubleValue).sum();
        return fromDouble(sum / measures.size());
    }

    @Override
    public V calculateMedian(Map<Long, V> measures) {
        var sorted = measures.values().stream().sorted(this::compareNumbers).toArray(Number[]::new);
        int size = sorted.length;
        if (size % 2 == 0) {
            return fromDouble((sorted[size / 2 - 1].doubleValue() + sorted[size / 2].doubleValue()) / 2.0);
        } else {
            return fromDouble(sorted[size / 2].doubleValue());
        }
    }

    @Override
    public V calculateFirst(Map<Long, V> measures) {
        return measures.values().stream().findFirst().orElse(null);
    }

    @Override
    public V calculateLast(Map<Long, V> measures) {
        return measures.values().stream().reduce((first, second) -> second).orElse(null);
    }

    @Override
    public V calculateDiff(Map<Long, V> measures) {
        double diff = 0.0;
        double last = 0.0;
        for (var value : measures.values()) {
            diff = value.doubleValue() - last;
            last = value.doubleValue();
        }
        return fromDouble(diff);
    }

    @Override
    public V calculateInvDiff(Map<Long, V> measures) {
        double diff = 0.0;
        double last = 0.0;
        int i = 0;
        for (var value : measures.values()) {
            if (i > 0) {
                diff = last - value.doubleValue();
            }
            last = value.doubleValue();
            i++;
        }
        return fromDouble(diff);
    }

    @Override
    public V calculateSum(Map<Long, V> measures) {
        double sum = measures.values().stream().mapToDouble(Number::doubleValue).sum();
        return fromDouble(sum);
    }

    @Override
    public V calculateDiv(Map<Long, V> measures) {
        double div = 1.0;
        int i = 0;
        for (var value : measures.values()) {
            if (i == 0) {
                div = value.doubleValue();
            } else {
                div /= value.doubleValue();
            }
            i++;
        }
        return fromDouble(div);
    }

    @Override
    public V calculateMul(Map<Long, V> measures) {
        double mul = 1.0;
        for (var value : measures.values()) {
            mul *= value.doubleValue();
        }
        return fromDouble(mul);
    }

    private int compareNumbers(Number n1, Number n2) {
        return Double.compare(n1.doubleValue(), n2.doubleValue());
    }

    private V fromDouble(double value) {
        return (V) Double.valueOf(value);
    }
}