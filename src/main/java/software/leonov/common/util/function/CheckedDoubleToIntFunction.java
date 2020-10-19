package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.DoubleToIntFunction;

/**
 * Mirror of the {@link DoubleToIntFunction} interface whose {@code applyAsInt(double)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedDoubleToIntFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public int applyAsInt(final double value) throws Exception;

    /**
     * Returns a {@link DoubleToIntFunction} which delegates to the underlying {@link CheckedDoubleToIntFunction},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return a {@link DoubleToIntFunction} which delegates to the underlying {@link CheckedDoubleToIntFunction},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static DoubleToIntFunction evalUnchecked(final CheckedDoubleToIntFunction function) {
        Objects.requireNonNull(function, "function == null");
        return d -> {
            try {
                return function.applyAsInt(d);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}