package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;

/**
 * Mirror of the {@link Runnable} interface whose {@code run()} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedRunnable {

    /**
     * The general contract of the method {@code run()} is that it may take any action whatsoever.
     * 
     * @throws Exception if an error occurs
     */
    public void run() throws Exception;

    /**
     * Returns a {@link Runnable} which delegates to the underlying {@link CheckedRunnable},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param runnable the underlying checked runnable
     * @return a {@link Runnable} which delegates to the underlying {@link CheckedRunnable},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static Runnable evalUnchecked(final CheckedRunnable runnable) {
        Objects.requireNonNull(runnable, "runnable == null");
        return () -> {
            try {
                runnable.run();
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}