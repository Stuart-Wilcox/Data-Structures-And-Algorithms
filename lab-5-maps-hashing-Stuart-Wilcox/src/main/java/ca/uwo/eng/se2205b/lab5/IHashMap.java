package ca.uwo.eng.se2205b.lab5;

import java.util.Map;

/**
 * Defines extra methods that are useful for a Hash Map
 */
public interface IHashMap<K, V> extends Map<K, V> {
    
    
    /**
     * The threshold for the load factor for the hashmap to decide whether or not to expand.
     * @return Load factor
     */
    double loadFactorThreshold();
    
    /**
     * The current load factor of the HashMap, {@code n / N}
     * @return Current load factor
     */
    double loadFactor();
    
    /**
     * Current capacity of the HashMap, this is <strong>not</strong> the size. This must be a Prime Number
     *
     * @return Current capacity
     *
     * @see #size()
     */
    int capacity();
}
