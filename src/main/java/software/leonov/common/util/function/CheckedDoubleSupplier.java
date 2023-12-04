/*
 * Copyright (C) 2023 Zhenya Leonov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
     * {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param supplier the underlying checked double-supplier
     * @return a {@link DoubleSupplier} which delegates to the underlying {@link CheckedDoubleSupplier},
     *         {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked
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
     * {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param supplier the specified checked supplier
     * @return the result of calling {@link #getAsDouble()} on the specified {@code CheckedSupplier}
     *         {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static double uncheckedGet(final CheckedDoubleSupplier supplier) {
        Objects.requireNonNull(supplier, "supplier == null");
        return evalUnchecked(supplier).getAsDouble();
    }

}
