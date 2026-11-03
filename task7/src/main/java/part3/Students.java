package part3;

import java.util.*;

/**
 * Класс, реализующий методы из задания
 */
public class Students {
    /**
     * Метод, возвращающий список студентов, отметки которых находятся в границах от {@code minGrade} до {@code maxGrade}
     *
     * @param students Map студентов
     * @param minGrade Нижняя грань отметок
     * @param maxGrade Верхняя грань отметок
     * @return список учеников, подходящих под критерии
     */
    public static List<Student> findStudentsByGradeRange(Map<Integer, Student> students, double minGrade, double maxGrade) {
        if (minGrade > maxGrade) {
            throw new IllegalArgumentException("min must be <= max");
        } else if (minGrade < 0 || maxGrade < 0 || minGrade > 10 || maxGrade > 10) {
            throw new IllegalArgumentException("grade must be in [0, 10]");
        } else if (Double.isNaN(minGrade) || Double.isNaN(maxGrade)) {
            throw new IllegalArgumentException("grade must be not NaN");
        }
        List<Student> studentsList = new ArrayList<>();

        for (Student s : students.values()) {
            if (s.grade() <= maxGrade && s.grade() >= minGrade) {
                studentsList.add(s);
            }
        }
        return studentsList;
    }

    /**
     * Метод, выводящий топ N студентов по Идентификатору
     * @param students Map студентов
     * @param n
     * @return Список студентов, подходящих под критерии
     */
    public static List<Student> getTopNStudentsById(NavigableMap<Integer, Student> students, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be > 0");
        }
        List<Student> studentList = new ArrayList<>();
        int i = 0;
        for (Student s : students.values()) {
            if (studentList.size() == n) { break; }
            studentList.add(s);
        }
        return studentList;
    }
}
