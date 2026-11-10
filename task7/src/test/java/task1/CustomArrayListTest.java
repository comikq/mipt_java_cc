package task1;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import part1.CustomArrayList;
import part1.CustomList;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CustomArrayListTest {

    private CustomList<Integer> list;

    private void fillList(int count) {
        for (int i = 0; i < count; i++) {
            list.add(i);
        }
    }

    @BeforeEach
    void setUp() {
        list = new CustomArrayList<>();
    }

    @Test
    void newList_isEmpty() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void add_singleElement_isStoredAtIndexZero() {
        list.add(10);

        assertEquals(1, list.size());
        assertEquals(10, list.get(0));
    }

    @Test
    void add_elements_preservesOrder() {
        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void add_moreThanInitialCapacity_keepsAllElements() {
        fillList(20);
        assertEquals(20, list.size());
        for (int i = 0; i < 20; i++) {
            assertEquals(i, list.get(i));
        }
    }

    @Test
    void get_firstMiddleAndLastIndex_returnsCorrectElements() {
        fillList(9);
        assertEquals(0, list.get(0));
        assertEquals(4, list.get(4));
        assertEquals(8, list.get(8));
    }

    @ParameterizedTest
    @ValueSource(ints = {-5, -1, 10, 15})
    void get_invalidIndex_throwsIndexOutOfBounds(int index) {
        fillList(10);

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(index));
    }

    @ParameterizedTest
    @ValueSource(ints = {-5, -1, 10, 15})
    void remove_invalidIndex_throwsIndexOutOfBounds(int index) {
        fillList(10);

        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(index));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 4, 9})
    void remove_firstMiddleLast_returnRemovedElement(int index) {
        fillList(10);

        assertEquals(index, list.remove(index));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 4, 9})
    void remove_element_decreasesSizeAndShiftsTail(int index) {
        fillList(10);

        list.remove(index);

        assertEquals(9, list.size());
        for (int i = index; i < list.size(); i++) {
            assertEquals(i + 1, list.get(i));
        }
    }

    @Test
    void add_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> list.add(null));
    }

    @Test
    void iterator_traversesElementsInListOrder() {
        fillList(5);

        Iterator<Integer> iterator = list.iterator();

        for (int i = 0; i < 5; i++) {
            assertTrue(iterator.hasNext());
            assertEquals(i, iterator.next());
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    void next_afterLastElement_throwsNoSuchElementException() {
        fillList(3);

        Iterator<Integer> iterator = list.iterator();
        for (int i = 0; i < 3; i++) {
            iterator.next();
        }

        assertThrows(NoSuchElementException.class, iterator::next);
    }
}
