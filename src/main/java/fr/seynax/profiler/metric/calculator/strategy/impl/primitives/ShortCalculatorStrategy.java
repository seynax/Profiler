package fr.seynax.profiler.metric.calculator.strategy.impl.primitives;

import fr.seynax.profiler.metric.calculator.strategy.impl.IMetricCalculatorStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ShortCalculatorStrategy implements IMetricCalculatorStrategy<Short> {
    @Override
    public Collection<Short> calculateAll(Map<Long, Short> measures) {
        var values = new ArrayList<Short>();
        for(var measure : measures.values())
        {
            values.add(measure);
        }
        return values;
    }

    @Override
    public Short calculateMin(Map<Long, Short> measures) {
        return measures.values().stream().min(Short::compareTo).orElse((short) 0);
    }

    @Override
    public Short calculateMax(Map<Long, Short> measures) {
        return measures.values().stream().max(Short::compareTo).orElse((short) 0);
    }

    @Override
    public Short calculateAverage(Map<Long, Short> measures) {
        int sum = measures.values().stream().mapToInt(Short::intValue).sum();
        return (short) (sum / measures.size());
    }

    @Override
    public Short calculateMedian(Map<Long, Short> measures) {
        var sorted = measures.values().stream().sorted().toArray(Short[]::new);
        int size = sorted.length;
        if (size % 2 == 0) {
            return (short) ((sorted[size / 2 - 1] + sorted[size / 2]) / 2);
        } else {
            return sorted[size / 2];
        }
    }

    @Override
    public Short calculateFirst(Map<Long, Short> measures) {
        return measures.values().stream().findFirst().orElse((short) 0);
    }

    @Override
    public Short calculateLast(Map<Long, Short> measures) {
        return measures.values().stream().reduce((first, second) -> second).orElse((short) 0);
    }

    @Override
    public Short calculateDiff(Map<Long, Short> measures) {
        short diff = 0;
        short last = 0;
        for (var value : measures.values()) {
            diff = (short) (value - last);
            last = value;
        }
        return diff;
    }

    @Override
    public Short calculateInvDiff(Map<Long, Short> measures) {
        short diff = 0;
        short last = 0;
        int i = 0;
        for (var value : measures.values()) {
            if (i > 0) {
                diff = (short) (last - value);
            }
            last = value;
            i++;
        }
        return diff;
    }

    @Override
    public Short calculateSum(Map<Long, Short> measures) {
        int sum = measures.values().stream().mapToInt(Short::intValue).sum();
        return (short) sum;
    }

    @Override
    public Short calculateDiv(Map<Long, Short> measures) {
        int div = 1;
        int i = 0;
        for (var value : measures.values()) {
            if (i == 0) {
                div = value;
            } else {
                div /= value;
            }
            i++;
        }
        return (short) div;
    }

    @Override
    public Short calculateMul(Map<Long, Short> measures) {
        int mul = 1;
        for (var value : measures.values()) {
            mul *= value;
        }
        return (short) mul;
    }
}
