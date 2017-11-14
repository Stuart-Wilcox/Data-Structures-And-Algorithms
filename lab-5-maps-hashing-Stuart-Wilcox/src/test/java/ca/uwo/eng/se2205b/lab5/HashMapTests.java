package ca.uwo.eng.se2205b.lab5;

import ca.uwo.eng.se2205b.lab5.model.MyHashMap;
import com.google.common.collect.testing.MapTestSuiteBuilder;
import com.google.common.collect.testing.TestStringMapGenerator;
import com.google.common.collect.testing.features.CollectionFeature;
import com.google.common.collect.testing.features.CollectionSize;
import com.google.common.collect.testing.features.MapFeature;
import junit.framework.Test;

import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * HashMap testing
 */
public class HashMapTests {

    /**
     * Check if a number is a prime
     * @param n
     * @return True if prime
     */
    public static boolean isPrime(int n) {
        checkArgument(n > 0, "n must be positive");
        if (n <= 1) {
            return false;
        } else if (n <= 3) {
            return true;
        } else if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        
        int i = 5;
        while (i * i <= n) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
            
            i += 6;
        }
        
        return true;
    }

    public static Test suite() {
        return MapTestSuiteBuilder.using(new TestStringMapGenerator() {
                    @Override
                    protected Map<String, String> create(Map.Entry<String, String>[] entries) {
                        MyHashMap<String, String> map = new MyHashMap<>(0.5);
                        for (Map.Entry<String, String> e : entries) {
                            map.put(e.getKey(), e.getValue());
                        }

                        return map;
                    }
                }).named(HashMapTests.class.getSimpleName())
                .withFeatures(
                        CollectionSize.ANY,
                        CollectionFeature.NON_STANDARD_TOSTRING,
                        MapFeature.GENERAL_PURPOSE,
                        MapFeature.ALLOWS_NULL_VALUES,
                        MapFeature.SUPPORTS_PUT,
                        MapFeature.SUPPORTS_REMOVE
                ).createTestSuite();
    }


    @org.junit.Test
    public void loadFactor() {
        
    }

}
