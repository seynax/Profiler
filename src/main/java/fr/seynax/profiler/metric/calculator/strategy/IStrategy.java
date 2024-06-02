package fr.seynax.profiler.metric.calculator.strategy;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public interface IStrategy<V extends Number>
{
    void calculate(List<V> resultsIn, Collection<V> measuresIn);
}
