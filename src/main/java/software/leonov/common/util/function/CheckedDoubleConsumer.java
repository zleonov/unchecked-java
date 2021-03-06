package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.DoubleConsumer;

/**
 * Mirror of the {@link DoubleConsumer} interface whose {@code accept(double)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedDoubleConsumer {

    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     * @throws Exception if an error occurs
     */
    public void accept(double value) throws Exception;

    /**
     * Returns a composed {@code CheckedDoubleConsumer} that performs, in sequence, this operation followed by the
     * {@code after} operation. If performing either operation throws an exception, it is relayed to the caller of the
     * composed operation. If performing {@code this} operation throws an exception, the {@code after} operation will not be
     * performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code CheckedDoubleConsumer} that performs in sequence this operation followed by the
     *         {@code after} operation
     */
    public default CheckedDoubleConsumer andThen(final CheckedDoubleConsumer after) {
        Objects.requireNonNull(after, "after == null");
        return t -> {
            accept(t);
            after.accept(t);
        };
    }

    /**
     * Returns a {@link DoubleConsumer} which delegates to the underlying {@link CheckedDoubleConsumer},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param consumer the underlying checked consumer
     * @return a {@link DoubleConsumer} which delegates to the underlying {@link CheckedDoubleConsumer},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static DoubleConsumer evalUnchecked(final CheckedDoubleConsumer consumer) {
        Objects.requireNonNull(consumer, "consumer == null");
        return t -> {
            try {
                consumer.accept(t);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}