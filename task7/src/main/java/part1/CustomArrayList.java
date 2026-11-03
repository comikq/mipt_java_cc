package part1;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * Список на основе динамического массива. Не допускает {@code null},
 * расширяется с коэффициентом 1.5.
 *
 * @param <E> тип элементов списка
 */
public class CustomArrayList<E> implements CustomList<E> {

    private Object[] elements;
    private int size = 0;

    /**
     * Инициализирует part1.CustomArrayList с default-значением {@code capacity} (10)
     */
    public CustomArrayList() {
        this.elements = new Object[10];
    }

    /**
     * Инициализирует part1.CustomArrayList с пользовательским значением capacity
     *
     * @param capacity пользовательское значение {@code capacity}
     */
    public CustomArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException();
        }
        this.elements = new Object[capacity];
    }

    /**
     * Добавляет элемент в конец списка
     *
     * @param element добавляемый элемент
     * @throws NullPointerException если {@code element} равен {@code null}
     */
    @Override
    public void add(E element) {
        if (element == null) {
            throw new NullPointerException();
        }

        if (elements.length == size) {
            if (elements.length <= 1) {
                elements = Arrays.copyOf(elements, elements.length + 1);
            } else {
                elements = Arrays.copyOf(elements, (int) (elements.length * 1.5));
            }
        }
        elements[size++] = element;
    }

    /**
     * Возвращает элемент из списка по индексу
     *
     * @param index индекс возвращаемого элемента
     * @return элемент списка, соответствующий индексу
     * @throws IndexOutOfBoundsException если индекс вне диапазона {@code [0, size())}
     */
    @Override
    public E get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else {
            return (E) elements[index];
        }
    }

    /**
     * Удаляет элемент списка по индексу, сдвигает элементы справа на ячейку влево
     *
     * @param index индекс удаляемого элемента
     * @return удаленный элемент
     * @throws IndexOutOfBoundsException если индекс вне диапазона {@code [0, size())}
     */
    @Override
    public E remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else {
            E removed = (E) elements[index];
            System.arraycopy(elements, index + 1, elements, index, size - index - 1);
            elements[--size] = null;
            return removed;
        }
    }

    /**
     * Возвращает количество элементов в списке
     *
     * @return количество элементов в списке
     */
    @Override
    public int size() { return size; }

    /**
     * Возвращает true, если список пустой, иначе false
     *
     * @return {@code true}, если список пустой, иначе {@code false}
     */
    @Override
    public boolean isEmpty() { return size == 0; }

    /**
     * Возвращает итератор по элементам списка в порядке их следования,
     * от индекса 0 до {@code size - 1}
     *
     * @return итератор по элементам списка
     * */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) elements[cursor++];
            }
        };
    }
}
