package fr.seynax.profiler.metric.calculator.strategy.impl.primitives;

import fr.seynax.profiler.metric.calculator.strategy.impl.IMetricCalculatorStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class FloatCalculatorStrategy implements IMetricCalculatorStrategy<Float> {
    @Override
    public Collection<Float> calculateAll(Map<Long, Float> measures) {
        var values = new ArrayList<Float>();
        for(var measure : measures.values())
        {
            values.add(measure);
        }
        return values;
    }

    @Override
    public Float calculateMin(Map<Long, Float> measures) {
        return measures.values().stream().min(Float::compareTo).orElse(0.0f);
    }

    @Override
    public Float calculateMax(Map<Long, Float> measures) {
        return measures.values().stream().max(Float::compareTo).orElse(0.0f);
    }

    @Override
    public Float calculateAverage(Map<Long, Float> measures) {
        double sum = measures.values().stream().mapToDouble(Float::doubleValue).sum();
        return (float) (sum / measures.size());
    }

    @Override
    public Float calculateMedian(Map<Long, Float> measures) {
        var sorted = measures.values().stream().sorted().toArray(Float[]::new);
        int size = sorted.length;
        if (size % 2 == 0) {
            return (sorted[size / 2 - 1] + sorted[size / 2]) / 2.0f;
        } else {
            return sorted[size / 2];
        }
    }

    @Override
    public Float calculateFirst(Map<Long, Float> measures) {
        return measures.values().stream().findFirst().orElse(0.0f);
    }

    @Override
    public Float calculateLast(Map<Long, Float> measures) {
        return measures.values().stream().reduce((first, second) -> second).orElse(0.0f);
    }

    @Override
    public Float calculateDiff(Map<Long, Float> measures) {
        float diff = 0.0f;
        float last = 0.0f;
        for (var value : measures.values()) {
            diff = value - last;
            last = value;
        }
        return diff;
    }

    @Override
    public Float calculateInvDiff(Map<Long, Float> measures) {
        float diff = 0.0f;
        float last = 0.0f;
        int i = 0;
        for (var value : measures.values()) {
            if (i > 0) {
                diff = last - value;
            }
            last = value;
            i++;
        }
        return diff;
    }

    @Override
    public Float calculateSum(Map<Long, Float> measures) {
        return (float) measures.values().stream().mapToDouble(Float::doubleValue).sum();
    }

    @Override
    public Float calculateDiv(Map<Long, Float> measures) {
        float div = 1.0f;
        int i = 0;
        for (var value : measures.values()) {
            if (i == 0) {
                div = value;
            } else {
                div /= value;
            }
            i++;
        }
        return div;
    }

    @Override
    public Float calculateMul(Map<Long, Float> measures) {
        float mul = 1.0f;
        for (var value : measures.values()) {
            mul *= value;
        }
        return mul;
    }
}
