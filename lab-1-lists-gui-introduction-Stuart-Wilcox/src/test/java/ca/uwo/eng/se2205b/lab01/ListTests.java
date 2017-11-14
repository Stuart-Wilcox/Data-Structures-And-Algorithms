package ca.uwo.eng.se2205b.lab01;

import com.google.common.collect.testing.ListTestSuiteBuilder;
import com.google.common.collect.testing.TestStringListGenerator;
import com.google.common.collect.testing.features.CollectionFeature;
import com.google.common.collect.testing.features.CollectionSize;
import com.google.common.collect.testing.features.ListFeature;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.Arrays;
import java.util.List;

/**
 * Test Case for testing the implementation of a {@link List}.
 *
 * This was taken/modified from https://www.klittlepage.com/2014/01/08/testing-collections-with-guava-testlib-and-junit-4/
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ListTests.LinkedListTests.class,
        //ListTests.ArrayListTests.class
})
public class ListTests {

    /**
     * Test your implementation of a doubly linked list.
     */
   public static class LinkedListTests {

        public static TestSuite suite() {
            /**
             * This "Builder" pattern: https://en.wikipedia.org/wiki/Builder_pattern, creates a Test Suite that will
             * run your List implementation and test that it matches the contracts in the List interface.
             */
            return ListTestSuiteBuilder
                    // The create method is called with an array of elements
                    // that should populate the collection.
                    .using(new TestStringListGenerator() {

                        @Override
                        protected List<String> create(String[] elements) {
                            return new MyLinkedList<>(Arrays.asList(elements));
                        }

                    })
                    .named("LinkedList tests")
                    // The following defines the features of lists we care about
                    .withFeatures(
                            ListFeature.GENERAL_PURPOSE,
                            ListFeature.SUPPORTS_ADD_WITH_INDEX,
                            ListFeature.SUPPORTS_REMOVE_WITH_INDEX,
                            ListFeature.SUPPORTS_SET,
                            CollectionFeature.SUPPORTS_ADD,
                            CollectionFeature.SUPPORTS_REMOVE,
                            CollectionFeature.ALLOWS_NULL_VALUES,
                            CollectionFeature.GENERAL_PURPOSE,
                            CollectionSize.ANY
                    ).createTestSuite();
        }
    }

    /**
     * Test the ArrayList implementation
     */
//    public static class ArrayListTests {
//
//        /**
//         * The method responsible for returning the {@link TestSuite} generated
//         * by one of the {@link com.google.common.collect.testing.FeatureSpecificTestSuiteBuilder} classes.
//         * This method must be public static method with no arguments named
//         * "suite()".
//         *
//         * @return An instance of {@link TestSuite} for collection testing.
//         */
//        public static TestSuite suite() {
//            /**
//             * guava-testlib has a host of test suite builders available,
//             * all descending from {@link FeatureSpecificTestSuiteBuilder}.
//             * The
//             * {@link FeatureSpecificTestSuiteBuilder#usingGenerator(Object)}
//             * is the main entry point in that collections are tested by
//             * implementing {@link TestCollectionGenerator} and providing an
//             * instance of the interface to the test suite builder via the
//             * #usingGenerator(Object) method. Most of suite builder classes
//             * provide a convenience method such as
//             * {@link ListTestSuiteBuilder.using()} that streamline the process
//             * of creating a builder.
//             *
//             */
//            return ListTestSuiteBuilder
//                    // The create method is called with an array of elements
//                    // that should populate the collection.
//                    .using(new TestStringListGenerator() {
//
//                        @Override
//                        protected List<String> create(String[] elements) {
//                            return new MyArrayList<>(Arrays.asList(elements));
//                        }
//
//
//                    })
//                    // You can optionally give a name to your test suite. This
//                    // name is used by JUnit and other tools during report
//                    // generation.
//                    .named("ArrayList tests")
//                    // Guava has a host of "features" in the
//                    // com.google.common.collect.testing.features package. Use
//                    // them to specify how the collection should behave, and
//                    // what operations are supported.
//                    .withFeatures(
//                            ListFeature.GENERAL_PURPOSE,
//                            ListFeature.SUPPORTS_ADD_WITH_INDEX,
//                            ListFeature.SUPPORTS_REMOVE_WITH_INDEX,
//                            ListFeature.SUPPORTS_SET,
//                            CollectionFeature.SUPPORTS_ADD,
//                            CollectionFeature.SUPPORTS_REMOVE,
//                            CollectionFeature.ALLOWS_NULL_VALUES,
//                            CollectionFeature.GENERAL_PURPOSE,
//                            CollectionSize.ANY
//                    ).createTestSuite();
//        }
//    }

}
