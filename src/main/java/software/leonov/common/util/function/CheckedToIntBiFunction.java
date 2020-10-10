package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.ToIntBiFunction;

/**
 * Mirror of the {@link ToIntBiFunction} interface whose {@code applyAsInt(T, U)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedToIntBiFunction<T, U> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public int applyAsInt(T t, U u) throws Exception;

    /**
     * Returns a {@link ToIntBiFunction} which delegates to the underlying {@link CheckedToIntBiFunction},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return a {@link ToIntBiFunction} which delegates to the underlying {@link CheckedToIntBiFunction},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T, U> ToIntBiFunction<T, U> unchecked(final CheckedToIntBiFunction<? super T, ? super U> function) {
        Objects.requireNonNull(function, "function == null");
        return (t, u) -> {
            try {
                return function.applyAsInt(t, u);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}
