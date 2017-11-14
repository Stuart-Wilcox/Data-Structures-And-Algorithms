package ca.uwo.eng.se2205b.lab4;

import com.google.common.collect.testing.MapTestSuiteBuilder;
import com.google.common.collect.testing.TestStringMapGenerator;
import com.google.common.collect.testing.features.CollectionFeature;
import com.google.common.collect.testing.features.CollectionSize;
import com.google.common.collect.testing.features.MapFeature;
import junit.framework.Test;

import java.util.Map;

/**
 * Test Map implementation
 */
public class MapTests {

    public static Test suite() {
        return MapTestSuiteBuilder.using(new TestStringMapGenerator() {
                    @Override
                    protected Map<String, String> create(Map.Entry<String, String>[] entries) {
                        AVLTreeMap<String, String> map = new AVLTreeMap<>();
                        for (Map.Entry<String, String> e : entries) {
                            map.put(e.getKey(), e.getValue());
                        }

                        return map;
                    }
                }).named(MapTests.class.getSimpleName())
                .withFeatures(
                        CollectionSize.ANY,
                        CollectionFeature.NON_STANDARD_TOSTRING,
                        MapFeature.GENERAL_PURPOSE,
                        MapFeature.ALLOWS_NULL_VALUES,
                        MapFeature.SUPPORTS_PUT,
                        MapFeature.SUPPORTS_REMOVE
                ).createTestSuite();
    }

    //@org.junit.Test
    //public void otherTests() {
        // I recommend writing more tests in thie class that will do less.. daunting tests based on what you think will
        // work.

        // Not everything will be tested, for example, using a comparator rather than Comparable<> elements
    //}

}
