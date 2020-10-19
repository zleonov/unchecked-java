package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.LongFunction;

/**
 * Mirror of the {@link LongFunction} interface whose {@code apply(long)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedLongFunction<R> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public R apply(final long value) throws Exception;

    /**
     * Returns a {@link LongFunction} which delegates to the underlying {@link CheckedLongFunction},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return a {@link LongFunction} which delegates to the underlying {@link CheckedLongFunction},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <R> LongFunction<R> evalUnchecked(final CheckedLongFunction<? extends R> function) {
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