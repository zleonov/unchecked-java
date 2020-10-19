package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.ToIntFunction;

/**
 * Mirror of the {@link ToIntFunction} interface whose {@code applyAsInt(T)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedToIntFunction<T> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public int applyAsInt(final T t) throws Exception;

    /**
     * Returns a {@link ToIntFunction} which delegates to the underlying {@link CheckedToIntFunction},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return a {@link ToIntFunction} which delegates to the underlying {@link CheckedToIntFunction},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T> ToIntFunction<T> evalUnchecked(final CheckedToIntFunction<? super T> function) {
        Objects.requireNonNull(function, "function == null");
        return t -> {
            try {
                return function.applyAsInt(t);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}