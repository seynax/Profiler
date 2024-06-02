package fr.seynax.profiler.metric.calculator.strategy;

import java.util.Collection;
import java.util.List;

public interface IStrategyBundle<V extends Number>
{
    IStrategy<V> get(String nameIn);
}
