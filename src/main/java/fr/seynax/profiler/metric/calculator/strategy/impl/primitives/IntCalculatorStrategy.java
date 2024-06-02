package fr.seynax.profiler.metric.calculator.strategy.impl.primitives;

import fr.seynax.profiler.metric.calculator.strategy.impl.IMetricCalculatorStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class IntCalculatorStrategy implements IMetricCalculatorStrategy<Integer> {
    @Override
    public Collection<Integer> calculateAll(Map<Long, Integer> measures) {
        var values = new ArrayList<Integer>();
        for(var measure : measures.values())
        {
            values.add(measure);
        }
        return values;
    }

    @Override
    public Integer calculateMin(Map<Long, Integer> measures) {
        return measures.values().stream().min(Integer::compareTo).orElse(0);
    }

    @Override
    public Integer calculateMax(Map<Long, Integer> measures) {
        return measures.values().stream().max(Integer::compareTo).orElse(0);
    }

    @Override
    public Integer calculateAverage(Map<Long, Integer> measures) {
        int sum = measures.values().stream().mapToInt(Integer::intValue).sum();
        return sum / measures.size();
    }

    @Override
    public Integer calculateMedian(Map<Long, Integer> measures) {
        var sorted = measures.values().stream().sorted().toArray(Integer[]::new);
        int size = sorted.length;
        if (size % 2 == 0) {
            return (sorted[size / 2 - 1] + sorted[size / 2]) / 2;
        } else {
            return sorted[size / 2];
        }
    }

    @Override
    public Integer calculateFirst(Map<Long, Integer> measures) {
        return measures.values().stream().findFirst().orElse(0);
    }

    @Override
    public Integer calculateLast(Map<Long, Integer> measures) {
        return measures.values().stream().reduce((first, second) -> second).orElse(0);
    }

    @Override
    public Integer calculateDiff(Map<Long, Integer> measures) {
        int diff = 0;
        int last = 0;
        for (var value : measures.values()) {
            diff = value - last;
            last = value;
        }
        return diff;
    }

    @Override
    public Integer calculateInvDiff(Map<Long, Integer> measures) {
        int diff = 0;
        int last = 0;
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
    public Integer calculateSum(Map<Long, Integer> measures) {
        return measures.values().stream().mapToInt(Integer::intValue).sum();
    }

    @Override
    public Integer calculateDiv(Map<Long, Integer> measures) {
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
        return div;
    }

    @Override
    public Integer calculateMul(Map<Long, Integer> measures) {
        int mul = 1;
        for (var value : measures.values()) {
            mul *= value;
        }
        return mul;
    }
}