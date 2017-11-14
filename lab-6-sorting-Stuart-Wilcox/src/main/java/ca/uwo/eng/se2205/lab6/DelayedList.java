package ca.uwo.eng.se2205.lab6;

import com.google.common.collect.ImmutableList;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Creates a {@link java.util.List} that is artifically delayed by {@link #waitTime}. This class wraps an
 * {@link ArrayList} and adds artificial delays.
 */
@ParametersAreNonnullByDefault
public abstract class DelayedList<E> extends AbstractList<E> implements Delayed {

    /**
     * A "c-style" typedef to make the syntax easier to read.
     */
    public static abstract class Factory implements Delayed {

        private final Delayed.Time waitTime;

        // Private so no one else can instantiate
        private Factory(Delayed.Time time) {
            this.waitTime = checkNotNull(time, "time == null");
        }

        public <E> DelayedList<E> create(Collection<? extends E> initialElements) {
            return DelayedList.create(waitTime, initialElements);
        }

        @Override
        public Time getTime() {
            return waitTime;
        }
    }

    private long operationsPerformed;
    private final ArrayList<E> backingList;

    private final Delayed.Time waitTime;

    private DelayedList(Delayed.Time time, Collection<? extends E> initialElements) {
        this.backingList = new ArrayList<>(initialElements);
        this.waitTime = checkNotNull(time, "time == null");
        operationsPerformed = 0;
    }

    /**
     * Creates a new {@code DelayedList} element
     * @param time The time to delay all access methods
     * @param initialElements The initial elements within the List -- this operation operates immediately and does not
     *                        get slowed by {@param time}.
     * @param <E> The type stored in the {@code List}
     * @return New non-{@code null} {@code List}.
     */
    public static <E> DelayedList<E> create(Delayed.Time time, Collection<? extends E> initialElements) {
        return new DelayedList<E>(time, initialElements) { };
    }

    /**
     * Creates a factory to create {@code DelayedList} values.
     * @param time Time for all produced values
     * @return Non-{@code null} factory.
     */
    public static Factory createFactory(Delayed.Time time) {
        return new Factory(time) {};
    }

    /**
     * Get all of the Factory functions defined by {@link Delayed.Time}.
     * @return Non-{@code null} list of elements.
     */
    public static ImmutableList<Factory> getAllFactories() {
        List<Factory> all = Stream.of(Delayed.Time.values())
                .map(DelayedList::createFactory) // Need to specify <E> otherwise the compiler drops it to ?
                .collect(Collectors.toList());

        return ImmutableList.copyOf(all);
    }

    @Override
    public Time getTime() {
        return waitTime;
    }

    @Override
    public int size() {
        return backingList.size();
    }

    @Override
    public E set(int index, E element) {
        sleep();
        operationsPerformed++;
        return backingList.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        sleep();
        operationsPerformed++;
        backingList.add(index, element);
    }

    @Override
    public E remove(int index) {
        sleep();
        operationsPerformed++;
        return backingList.remove(index);
    }

    @Override
    public E get(int index) {
        sleep();
        operationsPerformed++;
        return backingList.get(index);
    }

    /**
     * Get the number of operations that have been performed on this List
     * @return number of operations
     */
    public long getOperationsPerformed() {
        return operationsPerformed;
    }

    public DelayedList<E> subList(int p1, int p2){
        DelayedList<E> returnList = create(waitTime, new ArrayList<E>());
        for(int i = p1; i< p2; i++){
            returnList.add(this.get(i));
        }
        return returnList;
    }

}
