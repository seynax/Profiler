package fr.seynax.profiler.metric.calculator.strategy;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public class ModularCalculatorStrategy<V extends Number> implements IMetricCalculatorStrategy<V> {
    private final Function<Map<Long, V>, Collection<V>> allFunction;
    private final Function<Map<Long, V>, V> minFunction;
    private final Function<Map<Long, V>, V> maxFunction;
    private final Function<Map<Long, V>, V> avgFunction;
    private final Function<Map<Long, V>, V> medianFunction;
    private final Function<Map<Long, V>, V> firstFunction;
    private final Function<Map<Long, V>, V> lastFunction;
    private final Function<Map<Long, V>, V> diffFunction;
    private final Function<Map<Long, V>, V> invDiffFunction;
    private final Function<Map<Long, V>, V> sumFunction;
    private final Function<Map<Long, V>, V> divFunction;
    private final Function<Map<Long, V>, V> mulFunction;

    public ModularCalculatorStrategy(Function<Map<Long, V>, Collection<V>> allFunction,
                                     Function<Map<Long, V>, V> minFunction,
                                     Function<Map<Long, V>, V> maxFunction,
                                     Function<Map<Long, V>, V> avgFunction,
                                     Function<Map<Long, V>, V> medianFunction,
                                     Function<Map<Long, V>, V> firstFunction,
                                     Function<Map<Long, V>, V> lastFunction,
                                     Function<Map<Long, V>, V> diffFunction,
                                     Function<Map<Long, V>, V> invDiffFunction,
                                     Function<Map<Long, V>, V> sumFunction,
                                     Function<Map<Long, V>, V> divFunction,
                                     Function<Map<Long, V>, V> mulFunction) {
        this.allFunction = allFunction;
        this.minFunction = minFunction;
        this.maxFunction = maxFunction;
        this.avgFunction = avgFunction;
        this.medianFunction = medianFunction;
        this.firstFunction = firstFunction;
        this.lastFunction = lastFunction;
        this.diffFunction = diffFunction;
        this.invDiffFunction = invDiffFunction;
        this.sumFunction = sumFunction;
        this.divFunction = divFunction;
        this.mulFunction = mulFunction;
    }

    @Override
    public Collection<V> calculateAll(Map<Long, V> measures) {
        return allFunction.apply(measures);
    }

    @Override
    public V calculateMin(Map<Long, V> measures) {
        return minFunction.apply(measures);
    }

    @Override
    public V calculateMax(Map<Long, V> measures) {
        return maxFunction.apply(measures);
    }

    @Override
    public V calculateAverage(Map<Long, V> measures) {
        return avgFunction.apply(measures);
    }

    @Override
    public V calculateMedian(Map<Long, V> measures) {
        return medianFunction.apply(measures);
    }

    @Override
    public V calculateFirst(Map<Long, V> measures) {
        return firstFunction.apply(measures);
    }

    @Override
    public V calculateLast(Map<Long, V> measures) {
        return lastFunction.apply(measures);
    }

    @Override
    public V calculateDiff(Map<Long, V> measures) {
        return diffFunction.apply(measures);
    }

    @Override
    public V calculateInvDiff(Map<Long, V> measures) {
        return invDiffFunction.apply(measures);
    }

    @Override
    public V calculateSum(Map<Long, V> measures) {
        return sumFunction.apply(measures);
    }

    @Override
    public V calculateDiv(Map<Long, V> measures) {
        return divFunction.apply(measures);
    }

    @Override
    public V calculateMul(Map<Long, V> measures) {
        return mulFunction.apply(measures);
    }
}