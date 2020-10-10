package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.IntFunction;

/**
 * Mirror of the {@link IntFunction} interface whose {@code apply(int)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedIntFunction<R> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public R apply(final int value) throws Exception;

    /**
     * Returns an {@link IntFunction} which delegates to the underlying {@link CheckedIntFunction},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return an {@link IntFunction} which delegates to the underlying {@link CheckedIntFunction},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <R> IntFunction<R> unchecked(final CheckedIntFunction<? extends R> function) {
        Objects.requireNonNull(function, "function == null");
        return d -> {
            try {
                return function.apply(d);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}