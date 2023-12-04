package software.leonov.common.util.function;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static software.leonov.common.util.function.CheckedComparator.evalUnchecked;
import static software.leonov.common.util.function.CheckedComparator.naturalOrder;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NaturalOrderingTest {

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
    void test_naturalOrder_strings() throws Exception {
        final List<String> actual = newArrayList("b", "a", "d", "c");

        actual.sort(evalUnchecked(naturalOrder()));

        assertThat(actual).isEqualTo(newArrayList("a", "b", "c", "d"));
    }

    @Test
    void test_naturalOrder_strings_reversed() throws Exception {
        final List<String> actual = newArrayList("b", "a", "d", "c");

        actual.sort(evalUnchecked(CheckedComparator.<String>naturalOrder().reversed()));

        assertThat(actual).isEqualTo(newArrayList("d", "c", "b", "a"));
    }

    @Test
    void test_naturalOrder_ints() throws Exception {
        final List<Integer> actual = newArrayList(3, 7, 25, -6, 8, 5);

        actual.sort(evalUnchecked(naturalOrder()));

        assertThat(actual).isEqualTo(newArrayList(-6, 3, 5, 7, 8, 25));
    }
    
    @Test
    void test_naturalOrder_ints_reversed() throws Exception {
        final List<Integer> actual = newArrayList(3, 7, 25, -6, 8, 5);

        actual.sort(evalUnchecked(CheckedComparator.<Integer>naturalOrder().reversed()));

        assertThat(actual).isEqualTo(newArrayList(25, 8, 7, 5, 3, -6));
    }

    @Test
    void test_naturalOrder_ints_npe() throws Exception {
        final List<Integer> actual = newArrayList(3, 7, 25, null, -6, 8, 5);

        assertThrows(NullPointerException.class, () -> actual.sort(evalUnchecked(naturalOrder())));
    }

}
