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
import java.util.function.IntSupplier;

/**
 * Mirror of the {@link IntSupplier} interface whose {@code getAsInt()} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedIntSupplier {

    /**
     * Returns an integer result.
     *
     * @return an integer result
     * @throws Exception if an error occurs
     */
    public int getAsInt() throws Exception;

    /**
     * Returns an {@link IntSupplier} which delegates to the underlying {@link CheckedIntSupplier},
     * {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param supplier the underlying checked int-supplier
     * @return an {@link IntSupplier} which delegates to the underlying {@link CheckedIntSupplier},
     *         {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static IntSupplier evalUnchecked(final CheckedIntSupplier supplier) {
        Objects.requireNonNull(supplier, "supplier == null");
        return () -> {
            try {
                return supplier.getAsInt();
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

    /**
     * Returns the result of calling {@link #getAsInt()} on the specified {@code CheckedIntSupplier}
     * {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param supplier the specified checked supplier
     * @return the result of calling {@link #getAsInt()} on the specified {@code CheckedSupplier}
     *         {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static int uncheckedGet(final CheckedIntSupplier supplier) {
        Objects.requireNonNull(supplier, "supplier == null");
        return evalUnchecked(supplier).getAsInt();
    }

}
