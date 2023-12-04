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

class ReversedNaturalOrdering<T> implements CheckedComparator<Comparable<Object>>, Serializable {

    private static final long serialVersionUID = -9063120991425892492L;

    private static final ReversedNaturalOrdering<Comparable<Object>> INSTANCE = new ReversedNaturalOrdering<>();

    static ReversedNaturalOrdering<Comparable<Object>> getInstance() {
        return INSTANCE;
    }

    private ReversedNaturalOrdering() {
    }

    @Override
    public int compare(final Comparable<Object> left, final Comparable<Object> right) throws Exception {
        Objects.requireNonNull(left, "left == null");
        Objects.requireNonNull(right, "right == null");
        return right.compareTo(left);
    }

    // singleton
    private Object readResolve() {
        return ReversedNaturalOrdering.getInstance();
    }

    @Override
    public CheckedComparator<Comparable<Object>> reversed() {
        return NaturalOrdering.getInstance();
    }

}