package map;

import java.util.Objects;

class Node <K extends Comparable<K>, V>{

    /**
     * Цвет узлов в реализации красно-черного дерева.
     */
    enum Color {
        RED,
        BLACK
    }

    Pair<K, V> data;
    Color color = Color.BLACK;
    Node<K, V> parent = null;
    Node<K, V> leftChild = null;
    Node<K, V> rightChild = null;

    public Node(K key, V value, Color color) {
        this.data = new Pair<>(key, value);
        this.color = color;
    }

    public Node(Color color) {
        this.data = null;
        this.color = color;
    }

    public Node(Pair<K, V> data, Color color) {
        this.data = data;
        this.color = color;
    }

    public Node (Node<K, V> other, Color color)  {
        this.data = new Pair<>(other.data.first, other.data.second);
        this.color = color;
        if (other.leftChild != null) {
            this.leftChild = new Node<>(other.leftChild, other.leftChild.color);
        }
        if (other.rightChild != null) {
            this.rightChild = new Node<>(other.rightChild, other.rightChild.color);
        }
    }

    /**
     * Рекурентное сравнение узлов и их потомков.
     * @param a Первый узел
     * @param b Второй узел
     * @return Результат сравнения первого и второго узла
     */
    public static boolean compareNodes(Node<?, ?> a, Node<?, ?> b) {
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
        return "[" + data.first + ", " + ((color == Color.RED) ? "RED" : "BLACK")  +"]";
    }
}
