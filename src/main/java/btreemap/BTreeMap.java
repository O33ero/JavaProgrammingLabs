package btreemap;

public class BTreeMap <K extends Comparable<K>, V>{

    private BTree<Pair<K, V>> tree;

    public BTreeMap(int treeParameter) {
        tree = new BTree<>(treeParameter);
    }

    /**
     * Добавляет новую пару, если такого ключа нет.
     * @param key Ключ
     * @param value Значение
     */
    public void put(K key, V value) {
        if (tree.search(new Pair<>(key, value)) != null) {
            return; // Not insert if exist
        }
        tree.add(new Pair<>(key, value));
    }

    /**
     * Делает замену старого значения на новое
     * @param key Ключ
     * @param newValue Новое значение
     */
    public void replace(K key, V newValue) {
        var searchResult = tree.search(new Pair<>(key, null));
        if (searchResult != null) {
            searchResult.second = newValue;
        }
    }

    /**
     * Делает замену старого значения на новое, если совпало сторое значение
     * @param key Ключ
     * @param newValue Новое значение
     */
    public void replace(K key, V oldValue, V newValue) {
        var searchResult = tree.search(new Pair<>(key, null));
        if (searchResult != null && searchResult.second.equals(oldValue)) {
            searchResult.second = newValue;
        }
    }

    /**
     * Возвращает значение по ключу
     * @param key Ключ
     * @return Значение
     */
    public V get(K key) {
        var searchResult = tree.search(new Pair<>(key, null));
        if (searchResult != null) {
            return searchResult.second;
        } else {
            return null;
        }
    }

    public int size() {
        return tree.size();
    }

}
