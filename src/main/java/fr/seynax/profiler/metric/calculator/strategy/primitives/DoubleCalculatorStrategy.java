package fr.seynax.profiler.metric.calculator.strategy.primitives;

import fr.seynax.profiler.metric.calculator.strategy.IMetricCalculatorStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class DoubleCalculatorStrategy implements IMetricCalculatorStrategy<Double> {
    @Override
    public Collection<Double> calculateAll(Map<Long, Double> measures) {
        var values = new ArrayList<Double>();
        for(var measure : measures.values())
        {
            values.add(measure);
        }
        return values;
    }

    @Override
    public Double calculateMin(Map<Long, Double> measures) {
        return measures.values().stream().min(Double::compareTo).orElse(0.0);
    }

    @Override
    public Double calculateMax(Map<Long, Double> measures) {
        return measures.values().stream().max(Double::compareTo).orElse(0.0);
    }

    @Override
    public Double calculateAverage(Map<Long, Double> measures) {
        double sum = measures.values().stream().mapToDouble(Double::doubleValue).sum();
        return sum / measures.size();
    }

    @Override
    public Double calculateMedian(Map<Long, Double> measures) {
        var sorted = measures.values().stream().sorted().toArray(Double[]::new);
        int size = sorted.length;
        if (size % 2 == 0) {
            return (sorted[size / 2 - 1] + sorted[size / 2]) / 2.0;
        } else {
            return sorted[size / 2];
        }
    }

    @Override
    public Double calculateFirst(Map<Long, Double> measures) {
        return measures.values().stream().findFirst().orElse(0.0);
    }

    @Override
    public Double calculateLast(Map<Long, Double> measures) {
        return measures.values().stream().reduce((first, second) -> second).orElse(0.0);
    }

    @Override
    public Double calculateDiff(Map<Long, Double> measures) {
        double diff = 0.0;
        double last = 0.0;
        for (var value : measures.values()) {
            diff = value - last;
            last = value;
        }
        return diff;
    }

    @Override
    public Double calculateInvDiff(Map<Long, Double> measures) {
        double diff = 0.0;
        double last = 0.0;
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
    public Double calculateSum(Map<Long, Double> measures) {
        return measures.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    @Override
    public Double calculateDiv(Map<Long, Double> measures) {
        double div = 1.0;
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
    public Double calculateMul(Map<Long, Double> measures) {
        double mul = 1.0;
        for (var value : measures.values()) {
            mul *= value;
        }
        return mul;
    }
}