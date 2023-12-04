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

class Nulls<T> implements CheckedComparator<T>, Serializable {

    private static final long serialVersionUID = 7459251984407887411L;

    private final boolean              nullsFirst;
    private final CheckedComparator<T> comparator; // can be null

    static <T> Nulls<T> first(final CheckedComparator<? super T> comparator) {
        return new Nulls<>(true, comparator);
    }

    static <T> Nulls<T> last(final CheckedComparator<? super T> comparator) {
        return new Nulls<>(false, comparator);
    }

    @SuppressWarnings("unchecked")
    private Nulls(final boolean nullsFirst, final CheckedComparator<? super T> comparator) {
        this.nullsFirst = nullsFirst;
        this.comparator = (CheckedComparator<T>) comparator;
    }

    @Override
    public int compare(final T left, final T right) throws Exception {
        if (left == null) {
            if (right == null)
                return 0;
            else {
                if (nullsFirst)
                    return -1;
                else
                    return 1;
            }
        } else if (right == null) {
            if (nullsFirst)
                return 1;
            else
                return -1;
        } else {
            if (comparator == null)
                return 0;
            else
                return comparator.compare(left, right);
        }
    }

    @Override
    public CheckedComparator<T> thenComparing(final CheckedComparator<? super T> other) {
        Objects.requireNonNull(other, "other == null");
        return new Nulls<>(nullsFirst, comparator == null ? other : comparator.thenComparing(other));
    }

    @Override
    public CheckedComparator<T> reversed() {
        return new Nulls<>(!nullsFirst, comparator == null ? null : comparator.reversed());
    }
}
