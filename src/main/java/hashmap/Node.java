package hashmap;

import java.util.Objects;

public class Node<K, V> {
    private int hash;
    private K key;
    private V value;
    private Node<K, V> next;

    Node(int hash, K key, V value, Node<K,V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    Node(int hash, K key, V value) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = null;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public int getHash() {
        return hash;
    }

    public Node<K, V> getNext() {
        return next;
    }

    public void setNext(Node<K, V> next) {
        this.next = next;
    }

    public void setValue(V newValue) {
        this.value = newValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node<?, ?> node = (Node<?, ?>) o;

        return Objects.equals(key, node.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return "[" + key + " : " + value + "]";
    }
}
