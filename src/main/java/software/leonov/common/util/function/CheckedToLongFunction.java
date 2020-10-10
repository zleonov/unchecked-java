package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.ToLongFunction;

/**
 * Mirror of the {@link ToLongFunction} interface whose {@code applyAsLong(T)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedToLongFunction<T> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public long applyAsLong(final T t) throws Exception;

    /**
     * Returns a {@link ToLongFunction} which delegates to the underlying {@link CheckedToLongFunction},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return a {@link ToLongFunction} which delegates to the underlying {@link CheckedToLongFunction},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T> ToLongFunction<T> unchecked(final CheckedToLongFunction<? super T> function) {
        Objects.requireNonNull(function, "function == null");
        return t -> {
            try {
                return function.applyAsLong(t);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}