package ca.uwo.eng.se2205.lab6;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Runs a set of tasks concurrently and will block until all complete.
 */
@ParametersAreNonnullByDefault
public final class BatchRunner {

    private static final int NUMBER_OF_PROCS = Runtime.getRuntime().availableProcessors();
    private final Executor exec;

    private final List<Runnable> toRun;

    /**
     * Create a new Batch Runner instance
     */
    public BatchRunner() {
        exec = Executors.newFixedThreadPool(NUMBER_OF_PROCS);
        toRun = new ArrayList<>(NUMBER_OF_PROCS);
    }


    /**
     * Enqueues the {@code Runnable} to run next time {@link #run()} is called.
     *
     * @param toRun Runnable to run
     *
     * @see #enqueueAll(Collection)
     */
    public synchronized void enqueue(Runnable toRun) {
        this.toRun.add(checkNotNull(toRun, "toRun == null"));
    }

    /**
     * Enqueues a collection of Runnables
     * @param all Collection of Runnables
     *
     * @see #enqueue(Runnable)
     */
    public synchronized void enqueueAll(Collection<? extends Runnable> all) {
        all.forEach(this::enqueue);
    }

    /**
     * Synchronously runs all of the tasks within the Runner. This will wait until all of the tasks complete
     * before returning.
     */
    public synchronized void run() {
        System.out.println("Running via " + toRun.size() + " tasks across " + NUMBER_OF_PROCS + " threads.");

        CountDownLatch latch = new CountDownLatch(toRun.size());

        for (Runnable r : toRun) {
            exec.execute(() -> {
                r.run();
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new IllegalStateException("Waiting interrupted", e);
        }

        System.out.println("Completed batch commands.");

        toRun.clear();
    }


}
