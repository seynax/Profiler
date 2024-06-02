package fr.seynax.profiler.metric.calculator.strategy.primitives;

import fr.seynax.profiler.metric.calculator.strategy.IMetricCalculatorStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ByteCalculatorStrategy implements IMetricCalculatorStrategy<Byte> {
    @Override
    public Collection<Byte> calculateAll(Map<Long, Byte> measures) {
        var values = new ArrayList<Byte>();
        for(var measure : measures.values())
        {
            values.add(measure);
        }
        return values;
    }

    @Override
    public Byte calculateMin(Map<Long, Byte> measures) {
        return measures.values().stream().min(Byte::compareTo).orElse((byte) 0);
    }

    @Override
    public Byte calculateMax(Map<Long, Byte> measures) {
        return measures.values().stream().max(Byte::compareTo).orElse((byte) 0);
    }

    @Override
    public Byte calculateAverage(Map<Long, Byte> measures) {
        int sum = measures.values().stream().mapToInt(Byte::intValue).sum();
        return (byte) (sum / measures.size());
    }

    @Override
    public Byte calculateMedian(Map<Long, Byte> measures) {
        var sorted = measures.values().stream().sorted().toArray(Byte[]::new);
        int size = sorted.length;
        if (size % 2 == 0) {
            return (byte) ((sorted[size / 2 - 1] + sorted[size / 2]) / 2);
        } else {
            return sorted[size / 2];
        }
    }

    @Override
    public Byte calculateFirst(Map<Long, Byte> measures) {
        return measures.values().stream().findFirst().orElse((byte) 0);
    }

    @Override
    public Byte calculateLast(Map<Long, Byte> measures) {
        return measures.values().stream().reduce((first, second) -> second).orElse((byte) 0);
    }

    @Override
    public Byte calculateDiff(Map<Long, Byte> measures) {
        byte diff = 0;
        byte last = 0;
        for (var value : measures.values()) {
            diff = (byte) (value - last);
            last = value;
        }
        return diff;
    }

    @Override
    public Byte calculateInvDiff(Map<Long, Byte> measures) {
        byte diff = 0;
        byte last = 0;
        int i = 0;
        for (var value : measures.values()) {
            if (i > 0) {
                diff = (byte) (last - value);
            }
            last = value;
            i++;
        }
        return diff;
    }

    @Override
    public Byte calculateSum(Map<Long, Byte> measures) {
        int sum = measures.values().stream().mapToInt(Byte::intValue).sum();
        return (byte) sum;
    }

    @Override
    public Byte calculateDiv(Map<Long, Byte> measures) {
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
        return (byte) div;
    }

    @Override
    public Byte calculateMul(Map<Long, Byte> measures) {
        int mul = 1;
        for (var value : measures.values()) {
            mul *= value;
        }
        return (byte) mul;
    }
}
