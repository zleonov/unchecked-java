package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.LongSupplier;

/**
 * Mirror of the {@link LongSupplier} interface whose {@code getAsLong()} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedLongSupplier {

    /**
     * Returns a long result.
     *
     * @return a long result
     * @throws Exception if an error occurs
     */
    public long getAsLong() throws Exception;

    /**
     * Returns a {@link LongSupplier} which delegates to the underlying {@link CheckedLongSupplier},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param supplier the underlying checked long-supplier
     * @return a {@link LongSupplier} which delegates to the underlying {@link CheckedLongSupplier},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static LongSupplier unchecked(final CheckedLongSupplier supplier) {
        Objects.requireNonNull(supplier, "supplier == null");
        return () -> {
            try {
                return supplier.getAsLong();
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

    /**
     * Returns the result of calling {@link #getAsLong()} on the specified {@code CheckedLongSupplier}
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param supplier the specified checked supplier
     * @return the result of calling {@link #getAsLong()} on the specified {@code CheckedSupplier}
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static long uncheckedGet(final CheckedLongSupplier supplier) {
        Objects.requireNonNull(supplier, "supplier == null");
        return unchecked(supplier).getAsLong();
    }

}
