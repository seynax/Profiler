package fr.seynax.profiler.metric.calculator.strategy.primitives;

import fr.seynax.profiler.metric.calculator.strategy.IMetricCalculatorStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class LongCalculatorStrategy implements IMetricCalculatorStrategy<Long> {
    @Override
    public Collection<Long> calculateAll(Map<Long, Long> measures) {
        var values = new ArrayList<Long>();
        for(var measure : measures.values())
        {
            values.add(measure);
        }
        return values;
    }

    @Override
    public Long calculateMin(Map<Long, Long> measures) {
        return measures.values().stream().min(Long::compareTo).orElse(0L);
    }

    @Override
    public Long calculateMax(Map<Long, Long> measures) {
        return measures.values().stream().max(Long::compareTo).orElse(0L);
    }

    @Override
    public Long calculateAverage(Map<Long, Long> measures) {
        long sum = measures.values().stream().mapToLong(Long::longValue).sum();
        return sum / measures.size();
    }

    @Override
    public Long calculateMedian(Map<Long, Long> measures) {
        var sorted = measures.values().stream().sorted().toArray(Long[]::new);
        int size = sorted.length;
        if (size % 2 == 0) {
            return (sorted[size / 2 - 1] + sorted[size / 2]) / 2L;
        } else {
            return sorted[size / 2];
        }
    }

    @Override
    public Long calculateFirst(Map<Long, Long> measures) {
        return measures.values().stream().findFirst().orElse(0L);
    }

    @Override
    public Long calculateLast(Map<Long, Long> measures) {
        return measures.values().stream().reduce((first, second) -> second).orElse(0L);
    }

    @Override
    public Long calculateDiff(Map<Long, Long> measures) {
        long diff = 0L;
        long last = 0L;
        for (var value : measures.values()) {
            diff = value - last;
            last = value;
        }
        return diff;
    }

    @Override
    public Long calculateInvDiff(Map<Long, Long> measures) {
        long diff = 0L;
        long last = 0L;
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
    public Long calculateSum(Map<Long, Long> measures) {
        return measures.values().stream().mapToLong(Long::longValue).sum();
    }

    @Override
    public Long calculateDiv(Map<Long, Long> measures) {
        long div = 1L;
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
    public Long calculateMul(Map<Long, Long> measures) {
        long mul = 1L;
        for (var value : measures.values()) {
            mul *= value;
        }
        return mul;
    }
}