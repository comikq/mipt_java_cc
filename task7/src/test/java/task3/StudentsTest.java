package task3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import part3.Student;
import part3.Students;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static part3.Students.findStudentsByGradeRange;
import static part3.Students.getTopNStudentsById;

class StudentsTest {

    Student s1, s2, s3, s4, s5;
    Map<Integer, Student> studentsHashMap;
    NavigableMap<Integer, Student> studentsTreeMap;

    @BeforeEach
    void setUp() {
        s1 = new Student(1, "Michelangelo", 0.0);
        s2 = new Student(2, "Donatello", 4.0);
        s3 = new Student(3, "Raphael", 5.0);
        s4 = new Student(4, "Leonardo", 7.5);
        s5 = new Student(5, "April", 10.0);

        studentsHashMap = new HashMap<>();
        studentsTreeMap = new TreeMap<>(Comparator.reverseOrder());

        for (Student s : List.of(s3, s5, s1, s4, s2)) {
            studentsHashMap.put(s.id(), s);
            studentsTreeMap.put(s.id(), s);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "4.5, 8.0, 2",   // часть студентов
            "0.0, 10.0, 5",  // все
            "8.0, 9.0, 0",   // никого
            "4.0, 4.5, 1",   // оценка равна minGrade
            "7.0, 7.5, 1",   // оценка равна maxGrade
            "5.0, 5.0, 1",   // minGrade == maxGrade
            "0.0, 0.0, 1",   // нижний край шкалы
            "10.0, 10.0, 1"  // верхний край шкалы
    })
    void findInRange_validRange_returnsExpectedCount(double min, double max, int expected) {
        assertEquals(expected, findStudentsByGradeRange(studentsHashMap, min, max).size());
    }

    @ParameterizedTest
    @CsvSource({"6.0, 5.0", "-0.1, 5.0", "1.0, -0.1", "1.0, 10.1", "10.1, 11.0", "NaN, 5.0", "1.0, NaN"})
    void findInRange_invalidArgs_throwsIAE(double min, double max) {
        assertThrows(IllegalArgumentException.class,
                () -> findStudentsByGradeRange(studentsHashMap, min, max));
    }
    @Test
    void findInRange_emptyMap_returnsEmpty() {
        assertTrue(findStudentsByGradeRange(new HashMap<>(), 0.0, 10.0).isEmpty());
    }

    @Test
    void findInRange_treeMap_returnsExpectedCount() {
        assertEquals(2, findStudentsByGradeRange(studentsTreeMap, 4.0, 5.0).size());
    }

    @Test
    void findInRange_anyCall_mapUnchangedAndResultIndependent() {
        Map<Integer, Student> copy = new HashMap<>(studentsHashMap);

        List<Student> result = findStudentsByGradeRange(studentsHashMap, 0.0, 10.0);
        assertDoesNotThrow(() -> result.add(new Student(99, "Тест", 1.0)));
        result.clear();

        assertEquals(copy, studentsHashMap);
    }

    @Test
    void topN_nLessThanSize_returnsLargestIdsDesc() {
        assertEquals(List.of(s5, s4), getTopNStudentsById(studentsTreeMap, 2));
    }

    @ParameterizedTest
    @CsvSource({"0, 0", "1, 1", "5, 5", "100, 5"})
    void topN_validN_returnsExpectedCount(int n, int expected) {
        assertEquals(expected, getTopNStudentsById(studentsTreeMap, n).size());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -100})
    void topN_negativeN_throwsIllegalArgumentEsception(int n) {
        assertThrows(IllegalArgumentException.class,
                () -> getTopNStudentsById(studentsTreeMap, n));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 3})
    void topN_emptyMap_returnsEmpty(int n) {
        NavigableMap<Integer, Student> empty = new TreeMap<>(Comparator.reverseOrder());
        assertTrue(getTopNStudentsById(empty, n).isEmpty());
    }

    @Test
    void topN_anyCall_mapUnchangedAndResultIndependent() {
        Map<Integer, Student> copy = new TreeMap<>(studentsTreeMap);

        List<Student> result = getTopNStudentsById(studentsTreeMap, 3);
        assertDoesNotThrow(() -> result.add(new Student(99, "Test", 1.0)));
        result.clear();

        assertEquals(copy, studentsTreeMap);
    }
}
