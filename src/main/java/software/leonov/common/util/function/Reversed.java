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

import java.io.Serializable;
import java.util.Objects;

class Reversed<T> implements CheckedComparator<T>, Serializable {

    private static final long serialVersionUID = 2209364064239230798L;

    private final CheckedComparator<T> comparator;

    Reversed(final CheckedComparator<T> comparator) {
        Objects.requireNonNull(comparator, "comparator == null");
        this.comparator = comparator;
    }

    @Override
    public int compare(final T left, final T right) throws Exception {
        return comparator.compare(right, left);
    }

    @Override
    public CheckedComparator<T> reversed() {
        return comparator;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        final Reversed<?> other = (Reversed<?>) obj;

        return comparator.equals(other.comparator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comparator);
    }

}
