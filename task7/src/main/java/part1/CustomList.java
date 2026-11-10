package part1;

/**
 * Упорядоченная коллекция элементов с доступом по индексу.
 * Не допускает {@code null}-элементы.
 *
 * @param <E> тип элементов списка
 */
public interface CustomList<E> extends Iterable<E> {

    /**
     * Добавляет элемент в конец списка.
     *
     * @param element добавляемый элемент
     * @throws NullPointerException если {@code element} равен {@code null}
     */
    void add(E element);

    /**
     * Возвращает элемент по указанному индексу.
     *
     * @param index индекс элемента, от 0 до {@code size() - 1}
     * @return элемент на указанной позиции
     * @throws IndexOutOfBoundsException если индекс вне диапазона {@code [0, size())}
     */
    E get(int index);

    /**
     * Удаляет элемент по указанному индексу.
     * Элементы справа сдвигаются на одну позицию влево.
     *
     * @param index индекс удаляемого элемента, от 0 до {@code size() - 1}
     * @return удалённый элемент
     * @throws IndexOutOfBoundsException если индекс вне диапазона {@code [0, size())}
     */
    E remove(int index);

    /**
     * Возвращает количество элементов в списке.
     *
     * @return количество элементов
     */
    int size();

    /**
     * Проверяет, пуст ли список.
     *
     * @return {@code true}, если в списке нет элементов, иначе {@code false}
     */
    boolean isEmpty();
}