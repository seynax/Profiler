package fr.seynax.profiler.metric.calculator.strategy;

public interface IStrategyProvider
{
    IStrategy<?> firstOf(String nameIn);
    <V extends Number> IStrategy<V> firstOf(String nameIn, Class<V> typeIn);
}
