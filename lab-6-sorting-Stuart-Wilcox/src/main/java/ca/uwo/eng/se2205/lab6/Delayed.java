package ca.uwo.eng.se2205.lab6;

import java.time.Duration;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Denotes an instance that is currently being delayed.
 */
public interface Delayed {

    /**
     * Denotes the specific timing used for the lab.
     */
    enum Time implements Delayed {
        Slow(30),
        Normal(15),
        Fast(0);

        private final Duration waitTime;

        Time(long millis) {
            checkArgument(millis >= 0, "millis < 0");

            this.waitTime = Duration.ofMillis(millis);
        }

        @Override
        public Duration getDelay() {
            return waitTime;
        }

        @Override
        public Time getTime() {
            return this;
        }
    }

    /**
     * Get the delay in use.
     * @return The waiting duration
     */
    default Duration getDelay() {
        return getTime().getDelay();
    }

    /**
     * Get the delay in use.
     * @return The waiting duration
     */
    Time getTime();

    /**
     * Sleeps the current thread by {@link #getDelay()}.
     */
    default void sleep() {
        Duration waitTime = getDelay();
        try {
            Thread.sleep(waitTime.toMillis());
        } catch (InterruptedException ie) {
            throw new IllegalStateException("Could not sleep, interrupted", ie);
        }
    }
}
