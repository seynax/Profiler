package fr.seynax.profiler.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

public class BundleRegistry<V, B>
{
    private final IBundleFactory<B> bundleFactory;
    private final Map<String, Stack<V>> values;
    private final Map<String, B> bundles;

    private @Setter @Getter B defaultBundle;

    public BundleRegistry(IBundleFactory<B> bundleFactoryIn)
    {
        this.bundleFactory = bundleFactoryIn;

        this.values = new LinkedHashMap<>();
        this.bundles = new LinkedHashMap<>();
    }

    public final BundleRegistry add(String nameIn, V valueIn)
    {
        var stack = this.values.get(valueIn);
        if (stack == null)
        {
            stack = new Stack<V>();
            this.values.put(nameIn, stack);
        }
        stack.add(valueIn);
        return this;
    }

    public final V firstOf(String nameIn)
    {
        var values = this.values.get(nameIn);

        return values.firstElement();
    }

    public final B createBundle(String nameIn)
    {
        var bundle = this.bundleFactory.create();

        this.bundles.put(nameIn, bundle);

        return bundle;
    }

    public final static class Values<V>
    {
        private final Map<String, V> values;

        public Values()
        {
            this.values = new LinkedHashMap<>();
        }

        public Values<V> add(String nameIn, V valueIn)
        {
            this.values.put(nameIn, valueIn);

            return this;
        }

        public V get(String nameIn)
        {
            return this.values.get(nameIn);
        }

        public V get(int indexIn)
        {
            if(indexIn >= this.values.size())
            {
                return null;
            }

            int i = 0;
            for(var value : this.values.values())
            {
                if(i == indexIn)
                {
                    return value;
                }

                i ++;
            }

            return null;
        }
        public V first()
        {
            return this.get(0);
        }

        public V fallback(int indexIn)
        {
            return this.get(indexIn + 1);
        }
    }

    public interface IBundleFactory<B>
    {
        B create();
    }
}