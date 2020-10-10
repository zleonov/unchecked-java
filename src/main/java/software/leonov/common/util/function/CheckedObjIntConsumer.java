package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.ObjIntConsumer;

/**
 * Mirror of the {@link ObjIntConsumer} interface whose {@code accept(T, int)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedObjIntConsumer<T> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t     the first argument
     * @param value the second argument
     * @throws Exception if an error occurs
     */
    public void accept(final T t, final int value) throws Exception;

    /**
     * Returns an {@link ObjIntConsumer} which delegates to the underlying {@link CheckedObjIntConsumer},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param consumer the underlying checked consumer
     * @return an {@link ObjIntConsumer} which delegates to the underlying {@link CheckedObjIntConsumer},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T> ObjIntConsumer<T> unchecked(final CheckedObjIntConsumer<? super T> consumer) {
        Objects.requireNonNull(consumer, "consumer == null");
        return (t, i) -> {
            try {
                consumer.accept(t, i);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }
}
