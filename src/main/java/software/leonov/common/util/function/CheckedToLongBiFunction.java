package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.ToLongBiFunction;

/**
 * Mirror of the {@link ToLongBiFunction} interface whose {@code applyAsLong(T, U)} method can throw a checked
 * exception.
 */
@FunctionalInterface
public interface CheckedToLongBiFunction<T, U> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public long applyAsLong(T t, U u) throws Exception;

    /**
     * Returns a {@link ToLongBiFunction} which delegates to the underlying {@link CheckedToLongBiFunction},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return a {@link ToLongBiFunction} which delegates to the underlying {@link CheckedToLongBiFunction},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T, U> ToLongBiFunction<T, U> unchecked(final CheckedToLongBiFunction<? super T, ? super U> function) {
        Objects.requireNonNull(function, "function == null");
        return (t, u) -> {
            try {
                return function.applyAsLong(t, u);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}
