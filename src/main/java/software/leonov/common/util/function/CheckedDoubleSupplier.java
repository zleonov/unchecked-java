package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.DoubleSupplier;

/**
 * Mirror of the {@link DoubleSupplier} interface whose {@code getAsDouble()} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedDoubleSupplier {

    /**
     * Returns a double result.
     *
     * @return a double result
     * @throws Exception if an error occurs
     */
    public double getAsDouble() throws Exception;

    /**
     * Returns a {@link DoubleSupplier} which delegates to the underlying {@link CheckedDoubleSupplier},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param supplier the underlying checked double-supplier
     * @return a {@link DoubleSupplier} which delegates to the underlying {@link CheckedDoubleSupplier},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static DoubleSupplier evalUnchecked(final CheckedDoubleSupplier supplier) {
        Objects.requireNonNull(supplier, "supplier == null");
        return () -> {
            try {
                return supplier.getAsDouble();
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

    /**
     * Returns the result of calling {@link #getAsDouble()} on the specified {@code CheckedDoubleSupplier}
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param supplier the specified checked supplier
     * @return the result of calling {@link #getAsDouble()} on the specified {@code CheckedSupplier}
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static double uncheckedGet(final CheckedDoubleSupplier supplier) {
        Objects.requireNonNull(supplier, "supplier == null");
        return evalUnchecked(supplier).getAsDouble();
    }

}
