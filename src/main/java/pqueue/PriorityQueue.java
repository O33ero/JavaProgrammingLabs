package pqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PriorityQueue<E extends Comparable<E>> {
    private final List<E> heap;

    public PriorityQueue() {
        heap = new ArrayList<>();
    }

    public PriorityQueue(int initialCapacity) {
        heap = new ArrayList<>(initialCapacity);
    }

    @SuppressWarnings("unchecked")
    public PriorityQueue(Class<? extends List> listImplementation) {
        Objects.requireNonNull(listImplementation);
        try {
            heap = listImplementation.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Failed to create new instance of " + listImplementation.getName() + " Message: " + ex);
        }
    }

    @SuppressWarnings("unchecked")
    public PriorityQueue(int initialCapacity, Class<? extends List> listImplementation) {
        Objects.requireNonNull(listImplementation);
        try {
            heap = listImplementation.getDeclaredConstructor(Integer.TYPE).newInstance(initialCapacity);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Failed to create new instance of " + listImplementation.getName() + " Message: " + ex);
        }
    }

    /**
     * Проверяет очередь на пустоту
     *
     * @return Возвращает True, если очередь пуста, и False - в противном случае.
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Очищает очередь, удаляя все элементы
     */
    public void clear() {
        heap.clear();
    }

    /**
     * Возвращает размер очереди
     *
     * @return Размер очереди
     */
    public int getSize() {
        return heap.size();
    }

    /**
     * Добавляет новый элемент в очередь.
     *
     * @param element новый элемент
     */
    public void push(E element) {
        Objects.requireNonNull(element);
        heap.add(element); // Add new element in heap
        shiftUp(heap.size() - 1); // Shift it to up
    }

    /**
     * Извлекает следующий элемент из очереди.
     *
     * @return Следующий элемент из очереди
     */
    public E pop() {
        if (heap.isEmpty()) {
            return null;
        }

        E maxElement = heap.get(0); // Get max element (root)

        if (heap.size() > 1) { // If 2 or more elements
            heap.set(0, heap.get(heap.size() - 1)); // Upping last element to root of heap
            heap.remove(heap.size() - 1); // Removing last element
            shiftDown(0); // Shift root to down
        } else { // Only one element
            heap.remove(0);
        }

        return maxElement; // return max value
    }

    /**
     * Возвращает, но не извлекает следующий элемент очереди
     *
     * @return Следующий элемент очереди
     */
    public E front() {
        if (heap.isEmpty()) {
            return null;
        } else {
            return heap.get(0);
        }
    }

    /**
     * Удаляет элемент по значению
     *
     * @param element Элемент для удаления
     */
    public void remove(E element) {
        int deletionIndex = heap.indexOf(element);
        if (deletionIndex == -1) {
            return; // Element not found
        }
        if (deletionIndex == heap.size() - 1) {
            heap.remove(element); // Delete last element
        } else {
            E lastElement = heap.get(heap.size() - 1); // Last element
            boolean removeResult = heap.remove(element); // Remove
            if (removeResult) {
                heap.add(deletionIndex, lastElement); // Push last element to position of deleted element
                heap.remove(heap.size() - 1);
                if (heap.size() > 2) {
                    shiftDown(deletionIndex); // Shift down it element
                }
            }
        }
    }

    private void swap(int i, int j) {
        E temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    private int getParent(int i) {
        return (i - 1) / 2;
    }

    private int getLeftChild(int i) {
        return 2 * i + 1;
    }

    private int getRightChild(int i) {
        return 2 * i + 2;
    }

    /**
     * Проверяет родительский корень на соответствие условию, что корень должен быть не меньше своих потомков.
     * В случае если условие нарушено, выбирается самый большой элемент из потомков и меняется местами с родителем.
     * Рекурсивно повторяется для нового родителя (бывшего самого большого потомка).
     *
     * @param parent индекс родительского элемента
     */
    private void shiftDown(int parent) {
        while (true) {
            int leftChild = getLeftChild(parent);
            int rightChild = getRightChild(parent);
            int largest = parent;

            if (leftChild < heap.size() && heap.get(leftChild).compareTo(heap.get(parent)) > 0) {
                largest = leftChild;
            }
            if (rightChild < heap.size() && heap.get(rightChild).compareTo(heap.get(parent)) > 0) {
                largest = rightChild;
            }

            if (largest != parent) {
                swap(parent, largest);
                parent = largest;
            } else {
                break;
            }
        }
    }

    /**
     * Поднимает потомка выше, если потомок больше чем родитель.
     * Повторяется рекурсивно, пока потомок больше родителя.
     *
     * @param i индекс потомка
     */
    private void shiftUp(int i) {
        while (i > 0) {
            int parent = getParent(i);
            if (heap.get(i).compareTo(heap.get(parent)) <= 0) {
                return;
            }
            swap(i, parent);
            i = parent;
        }
    }
}
