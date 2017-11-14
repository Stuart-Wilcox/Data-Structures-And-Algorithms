package ca.uwo.eng.se2205b.lab5;

import ca.uwo.eng.se2205b.lab5.model.MyHashMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static org.junit.Assert.*;

/**
 * Marking tests for Lab 4
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        MarkingTests.Capacity.class,
        MarkingTests.Contains.class,
        MarkingTests.EntrySet.class,
        MarkingTests.Equals.class,
        MarkingTests.Get.class,
        MarkingTests.LoadFactor.class,
        MarkingTests.LoadFactorThreshold.class,
        MarkingTests.Put.class,
        MarkingTests.Size.class,
})
public class MarkingTests {

    private static class Base {
        MyHashMap<String, String> underTest = new MyHashMap<>();
    }

    public static class Capacity extends Base {

        @Test
        public void isPrime() {
            assertTrue("Capacity must be a prime number", MarkingTests.isPrime(underTest.capacity()));
        }
    }


    public static class Size extends Base {

        @Test
        public void empty() {
            assertEquals(0, underTest.size());

        }

        @Test
        public void single() {
            underTest.put("a", "b");
            assertEquals(1, underTest.size());
        }

        @Test
        public void multi() {
            underTest.put("a", "b");
            underTest.put("c", "b");
            underTest.put("b", "b");
            assertEquals(3, underTest.size());
        }
    }

    public static class Equals extends Base {

        @Test
        public void sameInstance() {
            assertTrue(underTest.equals(underTest));
        }

        @Test
        public void bothEmpty() {
            assertTrue("Two maps should be equal", underTest.equals(new HashMap<>()));
        }

        @Test
        public void singleElement() {
            underTest.put("a", "b");

            HashMap<String, String> hash = new HashMap<>();
            hash.put("a", "b");
            assertTrue("Single element", underTest.equals(hash));
        }

        @Test
        public void multiElement() {
            underTest.put("a", "b");
            underTest.put("c", "d");

            HashMap<String, String> hash = new HashMap<>();
            hash.put("c", "d");
            hash.put("a", "b");
            assertTrue("Single element", underTest.equals(hash));
        }
    }


    public static class Put extends Base {

        @Test
        public void intoEmpty() {
            assertNull("New key returns null", underTest.put("Bob", "Burgers"));
            assertEquals("Invalid size", 1, underTest.size());
        }

        @Test
        public void nullKey() {
            try {
                underTest.put(null, "bad");
                fail("Should have thrown NPE or IllegalArgumentException");
            } catch (NullPointerException | IllegalArgumentException e) {
                // no fail! :)
            }
        }

        @Test
        public void replaceKey() {
            assertNull("New key returns null", underTest.put("Bob", "Burgers"));
            assertEquals("Old value was not returned", "Burgers", underTest.put("Bob", "Something Else"));
        }
    }

    public static class Get extends Base {

        @Test
        public void intoEmpty() {
            assertNull("Did not return null from empty map", underTest.get("Bob"));
        }

        @Test
        public void nullKey() {
            try {
                underTest.get(null);
                fail("Should have thrown NPE or IllegalArgumentException");
            } catch (NullPointerException | IllegalArgumentException e) {
                // no fail! :)
            }
        }

        @Test
        public void wrongType() {
            try {
                assertNull(underTest.get(2));

                underTest.put("2", "bob");
                assertNull(underTest.get(2));
            } catch (ClassCastException | IllegalArgumentException e) {
                // acceptable
            }
        }

        @Test
        public void afterRemovals() {
            assertFalse(underTest.containsKey("2"));

            for (int i = 2; i < 5; ++i) {
                String s = Integer.toString(i);
                underTest.put(s, s);
                assertEquals("Can not find just inserted entry", s, underTest.get(s));
            }

            underTest.remove("3");
            assertNull("Found removed key: 3", underTest.get("3"));

            for (int i = 2; i < 5; ++i) {
                if (i == 3) continue;

                String s = Integer.toString(i);
                assertEquals("Can not previously inserted entry", s, underTest.get(s));
            }
        }
    }

    public static class Contains extends Base {

        @Test
        public void empty() {
            assertFalse("Did not return null from empty map", underTest.containsKey("Bob"));
            assertFalse("Did not return null from empty map", underTest.containsValue("Bob"));
        }

        @Test
        public void nullKey() {
            try {
                assertFalse("Should not contain null key", underTest.containsKey(null));
                fail("Should have thrown NPE or IllegalArgumentException");
            } catch (NullPointerException | IllegalArgumentException e) {
                // no fail! :)
            }
        }

        @Test
        public void nullValue() {
            assertFalse("null is not in the map", underTest.containsValue(null));
            underTest.put("bob", null);
            assertTrue("should return true", underTest.containsValue(null));
        }


        @Test
        public void wrongType_key() {
            try {
                assertFalse(underTest.containsKey(2));

                underTest.put("2", "bob");
                assertFalse(underTest.containsKey(2));
            } catch (ClassCastException | IllegalArgumentException e) {
                // acceptable to throw, or not (hashing doesn't need casts..)
            }
        }

        @Test
        public void wrongType_value() {
            assertFalse(underTest.containsValue(2));

            underTest.put("2", "2");
            assertFalse(underTest.containsValue(2));
        }

        @Test
        public void afterRemovals() {
            assertFalse(underTest.containsKey("2"));

            for (int i = 2; i < 5; ++i) {
                String s = Integer.toString(i);
                underTest.put(s, s);
                assertTrue("Can not find just inserted entry", underTest.containsKey(s));
            }

            underTest.remove("3");
            assertFalse("Found removed key: 3", underTest.containsKey("3"));

            for (int i = 2; i < 5; ++i) {
                if (i == 3) continue;

                String s = Integer.toString(i);
                assertTrue("Can not previously inserted entry", underTest.containsKey(s));
            }
        }
    }

    public static class EntrySet extends Base {

        List<String> keys = Arrays.asList("a", "b", "c", "d");

        public EntrySet() {
            for (String key: keys) {
                underTest.put(key, "a");
            }
        }

        @Test
        public void present() {

            Set<Map.Entry<String, String>> entries = underTest.entrySet();
            Iterator<Map.Entry<String, String>> eit = entries.iterator();
            Iterator<String> lit = keys.iterator();

            while (lit.hasNext() && eit.hasNext()) {
                assertEquals("Keys are in the wrong order", lit.next(), eit.next().getKey());
            }
        }

        @Test
        public void remove() {
            Set<Map.Entry<String, String>> entries = underTest.entrySet();
            Iterator<Map.Entry<String, String>> it = entries.iterator();

            it.next();
            Map.Entry<String, String> e1 = it.next();

            it.remove();

            assertFalse("Key is still found in map", underTest.containsKey(e1.getKey()));
        }
    }

    public static class LoadFactorThreshold extends Base {


        @Test
        public void ctor_default() {
            MyHashMap<String, Integer> ut = new MyHashMap<>();
            assertEquals("Incorrect default load factor threshold", 0.3, ut.loadFactorThreshold(), 0.001);
        }


        @Test
        public void ctor() {
            MyHashMap<String, Integer> ut = new MyHashMap<>(0.8);

            assertEquals("Incorrect default load factor threshold", 0.8, ut.loadFactorThreshold(), 0.001);
        }

        @Test
        public void elements() {
            MyHashMap<String, Integer> ut = new MyHashMap<>(0.8);

            assertNull(ut.put("2", 4));
            assertEquals("Incorrect default load factor threshold", 0.8, ut.loadFactorThreshold(), 0.001);
            assertNull(ut.put("4", 4));
            assertEquals("Incorrect default load factor threshold", 0.8, ut.loadFactorThreshold(), 0.001);
            assertNull(ut.put("5", 4));
            assertEquals("Incorrect default load factor threshold", 0.8, ut.loadFactorThreshold(), 0.001);

            assertEquals(4, ut.remove("2").intValue());
            assertEquals("Incorrect default load factor threshold", 0.8, ut.loadFactorThreshold(), 0.001);
        }
    }

    public static class LoadFactor extends Base {

        private static final PrimeFactory primeFactory = new PrimeFactory(10000);

        private int primeAfter(int gte) {
            Iterator<Integer> primes = primeFactory.iterator();

            int p = primes.next();
            while (p < gte && primes.hasNext()) {
                p = primes.next();
            }

            assertTrue("Could not get a prime greater than or equal to " + gte, p >= gte);

            return p;
        }

        private void checkLoadFactor(String message, int numElems) {
            assertEquals(message,
                    1.0 * numElems / underTest.capacity(), underTest.loadFactor(), 0.000001);
        }


        @Test
        public void empty() {
            checkLoadFactor("Empty loadFactor should be zero", 0);
        }

        @Test
        public void single() {
            underTest.put("a", "b");
            checkLoadFactor("Single element load factor incorrect", 1);
        }

        @Test
        public void checkGrowth() {
            int originalCapacity = underTest.capacity();
            int nextCapacity = primeAfter(underTest.capacity() * 2);
            int toAdd = (int)Math.ceil(originalCapacity * underTest.loadFactorThreshold());

            for (int i = 0; i < toAdd; ++i) {
                assertNull(underTest.put(Integer.toString(i), "A"));
                checkLoadFactor("LoadFactor did not change", i+1);
            }

            assertEquals("Capacity should not have changed", originalCapacity, underTest.capacity());
            underTest.put(Integer.toString(toAdd), "A");
            checkLoadFactor("LoadFactor did not change", toAdd+1);

            assertEquals("Incorrect growth capacity", nextCapacity, underTest.capacity());
        }
    }

    private static class PrimeFactory implements Iterable<Integer> {

        private final boolean[] primes;

        public PrimeFactory(int maxValue) {
            checkArgument(maxValue >= 2, "maxValue < 2");

            this.primes = new boolean[maxValue];

            primes[0] = true;
            primes[1] = true;

            boolean foundPrime;
            int prime = 2;
            do {

                // "mark" all of the multiples of prime, skip prime * 1
                for (int i = prime*2; i < primes.length; i += prime) {
                    primes[i] = true;
                }

                // find the next prime
                foundPrime = false;
                for (int i = prime+1; !foundPrime && i < primes.length; ++i) {
                    if (!primes[i]) {
                        prime = i;
                        foundPrime = true;
                    }
                }

                // If we didn't find a prime, we ran up to the max value
            } while (foundPrime);
        }

        @Override
        public Iterator<Integer> iterator() {
            return new PrimeIterator();
        }

        /**
         * Implementation of {@link Iterator} that returns palindrome primes.
         */
        private class PrimeIterator implements Iterator<Integer> {

            /**
             * The current value
             */
            private int currentPrime;
            private int nextPrime;

            PrimeIterator() {
                currentPrime = 2;
                nextPrime = 3;
            }

            @Override
            public boolean hasNext() {
                return nextPrime < primes.length;
            }

            @Override
            public Integer next() {
                if (currentPrime >= primes.length) {
                    throw new ArrayIndexOutOfBoundsException(currentPrime);
                }

                currentPrime = nextPrime;

                nextPrime += 1; // look for the next prime
                while (nextPrime < primes.length && primes[nextPrime]) {
                    nextPrime++;
                }

                return currentPrime;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("");
            }
        }


    }

    public static boolean isPrime(int n) {
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

}
