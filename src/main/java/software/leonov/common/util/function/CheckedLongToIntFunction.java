package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.LongToIntFunction;

/**
 * Mirror of the {@link LongToIntFunction} interface whose {@code applyAsInt(long)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedLongToIntFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public int applyAsInt(final long value) throws Exception;

    /**
     * Returns a {@link LongToIntFunction} which delegates to the underlying {@link CheckedLongToIntFunction},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return a {@link LongToIntFunction} which delegates to the underlying {@link CheckedLongToIntFunction},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static LongToIntFunction unchecked(final CheckedLongToIntFunction function) {
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