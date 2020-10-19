package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.ObjLongConsumer;

/**
 * Mirror of the {@link ObjLongConsumer} interface whose {@code accept(T, long)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedObjLongConsumer<T> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t     the first argument
     * @param value the second argument
     * @throws Exception if an error occurs
     */
    public void accept(final T t, final long value) throws Exception;

    /**
     * Returns an {@link ObjLongConsumer} which delegates to the underlying {@link CheckedObjLongConsumer},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param consumer the underlying checked consumer
     * @return an {@link ObjLongConsumer} which delegates to the underlying {@link CheckedObjLongConsumer},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T> ObjLongConsumer<T> evalUnchecked(final CheckedObjLongConsumer<? super T> consumer) {
        Objects.requireNonNull(consumer, "consumer == null");
        return (t, l) -> {
            try {
                consumer.accept(t, l);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }
}
