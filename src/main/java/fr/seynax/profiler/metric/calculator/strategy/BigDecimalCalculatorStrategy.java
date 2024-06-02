package fr.seynax.profiler.metric.calculator.strategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class BigDecimalCalculatorStrategy implements IMetricCalculatorStrategy<BigDecimal> {
    @Override
    public Collection<BigDecimal> calculateAll(Map<Long, BigDecimal> measures) {
        var values = new ArrayList<BigDecimal>();
        for(var measure : measures.values())
        {
            values.add(measure);
        }
        return values;
    }

    @Override
    public BigDecimal calculateMin(Map<Long, BigDecimal> measures) {
        return measures.values().stream().min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal calculateMax(Map<Long, BigDecimal> measures) {
        return measures.values().stream().max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal calculateAverage(Map<Long, BigDecimal> measures) {
        BigDecimal sum = measures.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(BigDecimal.valueOf(measures.size()), BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal calculateMedian(Map<Long, BigDecimal> measures) {
        var sorted = measures.values().stream().sorted().collect(Collectors.toList());
        int size = sorted.size();
        if (size % 2 == 0) {
            return (sorted.get(size / 2 - 1).add(sorted.get(size / 2))).divide(BigDecimal.valueOf(2), BigDecimal.ROUND_HALF_UP);
        } else {
            return sorted.get(size / 2);
        }
    }

    @Override
    public BigDecimal calculateFirst(Map<Long, BigDecimal> measures) {
        return measures.values().stream().findFirst().orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal calculateLast(Map<Long, BigDecimal> measures) {
        return measures.values().stream().reduce((first, second) -> second).orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal calculateDiff(Map<Long, BigDecimal> measures) {
        BigDecimal diff = BigDecimal.ZERO;
        BigDecimal last = BigDecimal.ZERO;
        for (var value : measures.values()) {
            diff = value.subtract(last);
            last = value;
        }
        return diff;
    }

    @Override
    public BigDecimal calculateInvDiff(Map<Long, BigDecimal> measures) {
        BigDecimal diff = BigDecimal.ZERO;
        BigDecimal last = BigDecimal.ZERO;
        int i = 0;
        for (var value : measures.values()) {
            if (i > 0) {
                diff = last.subtract(value);
            }
            last = value;
            i++;
        }
        return diff;
    }

    @Override
    public BigDecimal calculateSum(Map<Long, BigDecimal> measures) {
        return measures.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateDiv(Map<Long, BigDecimal> measures) {
        BigDecimal div = null;
        int i = 0;
        for (var value : measures.values()) {
            if (i == 0) {
                div = value;
            } else {
                div = div.divide(value, BigDecimal.ROUND_HALF_UP);
            }
            i++;
        }
        return div;
    }

    @Override
    public BigDecimal calculateMul(Map<Long, BigDecimal> measures) {
        BigDecimal mul = BigDecimal.ONE;
        for (var value : measures.values()) {
            mul = mul.multiply(value);
        }
        return mul;
    }
}