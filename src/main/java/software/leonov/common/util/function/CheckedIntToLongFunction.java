package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.IntToLongFunction;

/**
 * Mirror of the {@link IntToLongFunction} interface whose {@code applyAsLong(int)} method can throw a checked
 * exception.
 */
@FunctionalInterface
public interface CheckedIntToLongFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public long applyAsLong(final int value) throws Exception;

    /**
     * Returns an {@link IntToLongFunction} which delegates to the underlying {@link CheckedIntToLongFunction},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return an {@link IntToLongFunction} which delegates to the underlying {@link CheckedIntToLongFunction},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static IntToLongFunction unchecked(final CheckedIntToLongFunction function) {
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