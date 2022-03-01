package map;

import java.util.Objects;

/**
 * Узлы красно-черного дерева
 * @param <T> Тип данных, хранимых в дереве
 */
class Node <T extends Comparable<T>>{

    /**
     * Цвет узлов в реализации красно-черного дерева.
     */
    enum Color {
        RED,
        BLACK
    }

    T data;
    Color color = Color.BLACK;
    Node<T> parent = null;
    Node<T> leftChild = null;
    Node<T> rightChild = null;

    public Node(T data, Color color) {
        this.data = data;
        this.color = color;
    }


    public Node (Node<T> other, Color color)  {
        this.data = other.data;
        this.color = color;
        if (other.leftChild != null) {
            this.leftChild = new Node<>(other.leftChild, other.leftChild.color);
        }
        if (other.rightChild != null) {
            this.rightChild = new Node<>(other.rightChild, other.rightChild.color);
        }
    }

    /**
     * Рекуррентное сравнение узлов и их потомков.
     * @param a Первый узел
     * @param b Второй узел
     * @return Результат сравнения первого и второго узла
     */
    public static boolean compareNodes(Node<?> a, Node<?> b) {
        if (a == null && b == null) {
            return true;
        }
        if (a != null && b == null) {
            return false;
        }
        if (a == null) {
            return false;
        }

        if (!Objects.equals(a.data, b.data)) {
            return false;
        } else {
            return compareNodes(a.leftChild, b.leftChild) && compareNodes(a.rightChild, b.rightChild);
        }
    }

    @Override
    public String toString() {
        return "[" + data + ", " + ((color == Color.RED) ? "RED" : "BLACK")  +"]";
    }
}
