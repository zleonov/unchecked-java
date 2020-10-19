package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.LongToDoubleFunction;

/**
 * Mirror of the {@link LongToDoubleFunction} interface whose {@code applyAsDouble(long)} method can throw a checked
 * exception.
 */
@FunctionalInterface
public interface CheckedLongToDoubleFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public double applyAsDouble(final long value) throws Exception;

    /**
     * Returns a {@link LongToDoubleFunction} which delegates to the underlying {@link CheckedLongToDoubleFunction},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return a {@link LongToDoubleFunction} which delegates to the underlying {@link CheckedLongToDoubleFunction},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static LongToDoubleFunction evalUnchecked(final CheckedLongToDoubleFunction function) {
        Objects.requireNonNull(function, "function == null");
        return d -> {
            try {
                return function.applyAsDouble(d);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}