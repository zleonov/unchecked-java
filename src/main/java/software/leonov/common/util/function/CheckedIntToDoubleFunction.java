package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.IntToDoubleFunction;

/**
 * Mirror of the {@link IntToDoubleFunction} interface whose {@code applyAsDouble(int)} method can throw a checked
 * exception.
 */
@FunctionalInterface
public interface CheckedIntToDoubleFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public double applyAsDouble(final int value) throws Exception;

    /**
     * Returns an {@link IntToDoubleFunction} which delegates to the underlying {@link CheckedIntToDoubleFunction},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return an {@link IntToDoubleFunction} which delegates to the underlying {@link CheckedIntToDoubleFunction},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static IntToDoubleFunction unchecked(final CheckedIntToDoubleFunction function) {
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