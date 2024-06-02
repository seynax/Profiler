package fr.seynax.profiler.metric.calculator.strategy;

import fr.seynax.profiler.utils.BundleRegistry;

import java.rmi.registry.Registry;

public class StrategyRegistry implements IStrategyProvider
{
    private final BundleRegistry<IStrategy<?>, IStrategyBundle<?>> registry;

    public StrategyRegistry(BundleRegistry.IBundleFactory<IStrategyBundle<?>> bundleFactoryIn)
    {
        this.registry = new BundleRegistry<>(bundleFactoryIn);
    }

    public StrategyRegistry add(String nameIn, IStrategy<?> strategyIn)
    {
        this.registry.add(nameIn, strategyIn);

        return this;
    }

    @Override
    public IStrategy<?> firstOf(String nameIn) {
        return this.registry.firstOf(nameIn);
    }

    @Override
    public <V extends Number> IStrategy<V> firstOf(String nameIn, Class<V> typeIn) {
        var strategy = this.registry.firstOf(nameIn);
        if(strategy != null && typeIn.isAssignableFrom(strategy.getClass()))
        {
            return (IStrategy<V>) strategy;
        }

        return null;
    }
}
