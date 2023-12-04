package software.leonov.common.util.function;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.truth.Truth.assertThat;
import static software.leonov.common.util.function.CheckedComparator.evalUnchecked;
import static software.leonov.common.util.function.CheckedComparator.naturalOrder;
import static software.leonov.common.util.function.CheckedComparator.nullsFirst;
import static software.leonov.common.util.function.CheckedComparator.nullsLast;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NullsTest {

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void test_nullsFirst_naturalOrder() throws Exception {
        final List<String> actual = newArrayList("b", "a", null, "d", "c", null);

        actual.sort(evalUnchecked(nullsFirst(naturalOrder())));

        assertThat(actual).isEqualTo(newArrayList(null, null, "a", "b", "c", "d"));
    }

    @Test
    void test_nullsFirst_naturalOrder_reversed() throws Exception {
        final List<String> actual = newArrayList("b", "a", null, "d", "c", null);

        actual.sort(evalUnchecked(nullsFirst(CheckedComparator.<String>naturalOrder()).reversed()));

        assertThat(actual).isEqualTo(newArrayList("d", "c", "b", "a", null, null));
    }

    @Test
    void test_nullsLast_naturalOrder() throws Exception {
        final List<String> actual = newArrayList("b", "a", null, "d", "c", null);

        actual.sort(evalUnchecked(nullsLast(naturalOrder())));

        assertThat(actual).isEqualTo(newArrayList("a", "b", "c", "d", null, null));
    }

    @Test
    void test_nullsLast_naturalOrder_reversed() throws Exception {
        final List<String> actual = newArrayList("b", "a", null, "d", "c", null);

        actual.sort(evalUnchecked(nullsLast(CheckedComparator.<String>naturalOrder()).reversed()));

        assertThat(actual).isEqualTo(newArrayList(null, null, "d", "c", "b", "a"));
    }

    @Test
    void test_nullsFirst_null() throws Exception {
        final List<String> actual = newArrayList("b", "a", null, "d", "c", null);

        actual.sort(evalUnchecked(nullsFirst(null)));

        assertThat(actual).isEqualTo(newArrayList(null, null, "b", "a", "d", "c"));
    }

    @Test
    void test_nullsFirst_last() throws Exception {
        final List<String> actual = newArrayList("b", "a", null, "d", "c", null);

        actual.sort(evalUnchecked(nullsLast(null)));

        assertThat(actual).isEqualTo(newArrayList("b", "a", "d", "c", null, null));
    }

}
