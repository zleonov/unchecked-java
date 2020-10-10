package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.DoubleToLongFunction;

/**
 * Mirror of the {@link DoubleToLongFunction} interface whose {@code applyAsLong(double)} method can throw a checked
 * exception.
 */
@FunctionalInterface
public interface CheckedDoubleToLongFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public long applyAsLong(final double value) throws Exception;

    /**
     * Returns a {@link DoubleToLongFunction} which delegates to the underlying {@link CheckedDoubleToLongFunction},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return a {@link DoubleToLongFunction} which delegates to the underlying {@link CheckedDoubleToLongFunction},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static DoubleToLongFunction unchecked(final CheckedDoubleToLongFunction function) {
        Objects.requireNonNull(function, "function == null");
        return d -> {
            try {
                return function.applyAsLong(d);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}