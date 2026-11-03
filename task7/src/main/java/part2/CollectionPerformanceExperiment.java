package part2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CollectionPerformanceExperiment {

    // константы, можно поменять, если хочется повысить точность
    private static final int N = 10_000;
    private static final int WARMUP_RUNS = 3;
    private static final int MEASURE_RUNS = 7;


    private static long checksum = 0;

    private static final String[] NAMES = {
            "Добавление в конец",
            "Добавление в начало",
            "Вставка в середину",
            "Доступ по индексу",
            "Удаление из начала",
            "Удаление с конца"
    };

    // красивый вывод с одинаковыми по ширине столбцами
    public static void main(String[] args) {
        System.out.printf("%-22s %10s %16s %16s%n",
                "Операция", "Элементов", "ArrayList, мкс", "LinkedList, мкс");

        for (int op = 0; op < NAMES.length; op++) {
            long arrayTime = measure(op, false);
            long linkedTime = measure(op, true);
            System.out.printf("%-22s %10d %16d %16d%n",
                    NAMES[op], N, arrayTime / 1000, linkedTime / 1000);
        }
        System.out.println("Контрольная сумма: " + checksum);
    }

    private static List<Integer> newList(boolean linked) {
        if (linked) {
            return new LinkedList<>();
        }
        return new ArrayList<>();
    }

    private static void fill(List<Integer> list) {
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
    }


    // первые несколько вызовов не идут в подсчет, так как JIT должен разогнатся (оптимизировать код)
    private static long measure(int op, boolean linked) {
        for (int i = 0; i < WARMUP_RUNS; i++) {
            run(op, linked);
        }
        long[] times = new long[MEASURE_RUNS];
        for (int i = 0; i < MEASURE_RUNS; i++) {
            times[i] = run(op, linked);
        }
        Arrays.sort(times);
        return times[MEASURE_RUNS / 2];
    }

    private static long run(int op, boolean linked) {
        switch (op) {
            case 0: return addToEnd(newList(linked));
            case 1: return addToStart(newList(linked));
            case 2: return insertInMiddle(newList(linked));
            case 3: return accessByIndex(newList(linked));
            case 4: return removeFromStart(newList(linked));
            case 5: return removeFromEnd(newList(linked));
            default: return 0;
        }
    }

    private static long addToEnd(List<Integer> list) {
        long start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
        long worktime = System.nanoTime() - start;

        checksum += list.size();
        return worktime;
    }

    private static long addToStart(List<Integer> list) {
        long start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            list.add(0, i);
        }
        long elapsed = System.nanoTime() - start;

        checksum += list.size();
        return elapsed;
    }

    private static long insertInMiddle(List<Integer> list) {
        fill(list); // подготовка, не в замере

        long start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            list.add(list.size() / 2, i);
        }
        long elapsed = System.nanoTime() - start;

        checksum += list.size();
        return elapsed;
    }

    private static long accessByIndex(List<Integer> list) {
        fill(list);

        long sum = 0;
        long start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            sum += list.get(i);
        }
        long elapsed = System.nanoTime() - start;

        checksum += sum;
        return elapsed;
    }

    private static long removeFromStart(List<Integer> list) {
        fill(list);

        long sum = 0;
        long start = System.nanoTime();
        while (!list.isEmpty()) {
            sum += list.remove(0);
        }
        long elapsed = System.nanoTime() - start;

        checksum += sum;
        return elapsed;
    }

    private static long removeFromEnd(List<Integer> list) {
        fill(list);

        long sum = 0;
        long start = System.nanoTime();
        while (!list.isEmpty()) {
            sum += list.remove(list.size() - 1);
        }
        long elapsed = System.nanoTime() - start;

        checksum += sum;
        return elapsed;
    }

}
