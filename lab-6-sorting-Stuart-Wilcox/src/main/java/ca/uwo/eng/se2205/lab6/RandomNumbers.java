package ca.uwo.eng.se2205.lab6;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates "random" numbers by reading from a resource file -- these are always in a consistently random order.
 */
public abstract class RandomNumbers {

    // no construct
    private RandomNumbers() { }

    /**
     * Get a list of consistent numbers of length {@param count}
     * @param count Number of random integers to read
     * @return List containing integers &gt; 0
     */
    public static List<Integer> get(int count) {
        try (InputStream in = RandomNumbers.class.getResourceAsStream("/numbers.txt");
             Reader ir = new InputStreamReader(in);
             BufferedReader reader = new BufferedReader(ir)) {

            ArrayList<Integer> output = new ArrayList<>(count);
            String line;
            while ((line = reader.readLine()) != null && output.size() < count) {
                List<Integer> nums = Splitter.on(CharMatcher.whitespace())
                        .splitToList(line)
                        .stream()
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

                Iterator<Integer> it = nums.iterator();
                while (it.hasNext() && output.size() < count) {
                    output.add(it.next());
                }
            }

            output.trimToSize();

            return output;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
