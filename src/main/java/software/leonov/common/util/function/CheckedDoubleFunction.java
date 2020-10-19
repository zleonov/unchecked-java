package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.DoubleFunction;

/**
 * Mirror of the {@link DoubleFunction} interface whose {@code apply(double)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedDoubleFunction<R> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public R apply(final double value) throws Exception;

    /**
     * Returns a {@link DoubleFunction} which delegates to the underlying {@link CheckedDoubleFunction},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return a {@link DoubleFunction} which delegates to the underlying {@link CheckedDoubleFunction},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <R> DoubleFunction<R> evalUnchecked(final CheckedDoubleFunction<? extends R> function) {
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