package map;

public class Map<K extends Comparable<K>, V> {
    RBTree<K, V> tree;

    /**
     * Конструктор по умолчанию
     */
    public Map() {
        tree = new RBTree<>();
    }

    /**
     * Конструктор копирования. Используется рекурентная реализация
     * @param other Другой {@code map}
     */
    public Map(Map<K, V> other) {
        tree = new RBTree<>(other.tree);
    }

    /**
     * Помещает новую пару ключ-значение в {@code map}
     * @param key Ключ
     * @param value Значение
     */
    public void put(K key, V value) {
        tree.insert(new Node<>(key, value, Node.Color.RED));
    }

    /**
     * Удаляет все элементы из {@code map}
     */
    public void clear() {
        tree.setRoot(new Node<>(Node.Color.BLACK)); // Delete all subtree
    }

    /**
     * Проверка {@code map} на пустоту
     * @return Результат проверки на пустоту
     */
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    /**
     * Достает из {@code map} значение соответсвующее ключу
     * @param key Ключ
     * @return Значение по ключу
     */
    public V get(K key) {
         return tree.get(key);
    }

    /**
     * Сравнение {@code map}. Используется рекуррентный подход сравнения деревьев.
     * @param o Другой {@code map}
     * @return Результат сравнения
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        Map<?, ?> map = (Map<?, ?>) o;
        return tree.equals(map.tree);
    }

    @Override
    public int hashCode() {
        return tree != null ? tree.hashCode() : 0;
    }
}
