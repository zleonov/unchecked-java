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
     * {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param supplier the underlying checked long-supplier
     * @return a {@link LongSupplier} which delegates to the underlying {@link CheckedLongSupplier},
     *         {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked
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
     * {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param supplier the specified checked supplier
     * @return the result of calling {@link #getAsLong()} on the specified {@code CheckedSupplier}
     *         {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static long uncheckedGet(final CheckedLongSupplier supplier) {
        Objects.requireNonNull(supplier, "supplier == null");
        return unchecked(supplier).getAsLong();
    }

}
