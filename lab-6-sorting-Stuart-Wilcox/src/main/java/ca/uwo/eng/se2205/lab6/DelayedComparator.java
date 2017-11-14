package ca.uwo.eng.se2205.lab6;

import com.google.common.collect.ImmutableList;

import javax.annotation.ParametersAreNonnullByDefault;
import java.time.Duration;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Peforms comparisons but adds an artificial Delay.
 */
@ParametersAreNonnullByDefault
public abstract class DelayedComparator<E> implements Comparator<E>, Delayed {

    /**
     * A "c-style" typedef to make the syntax easier to read.
     */
    public static abstract class Factory implements Delayed {

        private final Delayed.Time waitTime;

        // Private so no one else can instantiate
        private Factory(Delayed.Time time) {
            this.waitTime = checkNotNull(time, "time == null");
        }

        public <E> DelayedComparator<E> create(Comparator<E> comparator) {
            return DelayedComparator.create(comparator, waitTime);
        }

        @Override
        public Time getTime() {
            return waitTime;
        }
    }

    private final Delayed.Time waitTime;

    private final Comparator<E> wrapped;
    private int comparisonsPerformed;

    private DelayedComparator(Delayed.Time time, Comparator<E> comparator) {
        this.waitTime = checkNotNull(time, "time == null");
        this.wrapped = checkNotNull(comparator, "comparator == null");
        comparisonsPerformed = 0;
    }

    @Override
    public final Duration getDelay() {
        return waitTime.getDelay();
    }

    @Override
    public Time getTime() {
        return waitTime;
    }

    /**
     * Creates a factory for creating multiple {@link DelayedComparator} with the same
     * {@link ca.uwo.eng.se2205.lab6.Delayed.Time}.
     *
     * @param amount Time to wait
     * @return New factory
     */
    public static DelayedComparator.Factory createFactory(Delayed.Time amount) {
        return new Factory(amount) { };
    }

    /**
     * Creates a {@link DelayedComparator} that waits for {@code amount} and performs the comparison in
     * {@code comparator}.
     *
     * @param comparator The wrapped comparison
     * @param amount How long to wait
     * @param <E> Type of elements
     * @return Non-{@code null} comparator that is delayed
     */
    public static <E> DelayedComparator<E> create(Comparator<E> comparator, Delayed.Time amount) {
        return new DelayedComparator<E>(amount, comparator) { };
    }

    /**
     * Creates {@link Factory} for each value in the {@link ca.uwo.eng.se2205.lab6.Delayed.Time} enumeration.
     *
     * @return Non-{@code null} List of values
     */
    public static ImmutableList<DelayedComparator.Factory> getAllFactories() {
        return ImmutableList.copyOf(Stream.of(Delayed.Time.values())
                .map(DelayedComparator::createFactory)
                .collect(Collectors.toList()));
    }

    @Override
    public synchronized int compare(E lhs, E rhs) {
        sleep();
        comparisonsPerformed++;
        return wrapped.compare(lhs, rhs);
    }

    /**
     * Get the number of times the comparison was performed.
     * @return Number of comparisons performed.
     */
    public int getComparisonsPerformed() {
        return comparisonsPerformed;
    }
}
