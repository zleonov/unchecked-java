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
import java.util.function.BooleanSupplier;

/**
 * Mirror of the {@link BooleanSupplier} interface whose {@code getAsBoolean()} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedBooleanSupplier {

    /**
     * Returns a boolean result.
     * 
     * @return a boolean result
     * @throws Exception if an error occurs
     */
    public boolean getAsBoolean() throws Exception;

    /**
     * Returns a {@link BooleanSupplier} which delegates to the underlying {@link CheckedBooleanSupplier},
     * {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param supplier the underlying checked supplier of boolean values
     * @return a {@link BooleanSupplier} which delegates to the underlying {@link CheckedBooleanSupplier},
     *         {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static BooleanSupplier unchecked(final CheckedBooleanSupplier supplier) {
        Objects.requireNonNull(supplier, "supplier == null");
        return () -> {
            try {
                return supplier.getAsBoolean();
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

    /**
     * Returns the result of calling {@link #getAsBoolean()} on the specified {@code CheckedBooleanSupplier}
     * {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param supplier the specified checked supplier of boolean values
     * @return the result of calling {@link #getAsBoolean()} on the specified {@code CheckedBooleanSupplier}
     *         {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static boolean uncheckedGet(final CheckedBooleanSupplier supplier) {
        Objects.requireNonNull(supplier, "supplier == null");
        return unchecked(supplier).getAsBoolean();
    }

}
