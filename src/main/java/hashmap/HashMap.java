package hashmap;

import java.util.Objects;

public class HashMap<K, V> {
    private final int DEFAULT_CAPACITY = 16;
    private final float DEFAULT_LOAD_FACTOR = 2.0f;

    // Уровень загружености
    private float loadFactor = DEFAULT_LOAD_FACTOR;
    // Размер таблицы (Кол-во списков)
    private int capacity = DEFAULT_CAPACITY;
    // Количество элементов в таблице (Общее количество элементов по всем спискам)
    private int countOfElements = 0;

    // Таблица
    private Node<K, V>[] table;



    public HashMap() {
        init(DEFAULT_LOAD_FACTOR, DEFAULT_CAPACITY);
    }

    public HashMap(float loadFactor) {
        init(loadFactor, DEFAULT_CAPACITY);
    }

    public HashMap(int capacity) {
        init(DEFAULT_LOAD_FACTOR, capacity);
    }

    public HashMap(float loadFactor, int capacity) {
        init(loadFactor, capacity);
    }

    /**
     * Инициализация {@code hashMap}
     * @param loadFactor Допустимый уровень загружености
     * @param capacity Начальный размер таблицы
     */
    private void init(float loadFactor, int capacity) {
        this.loadFactor = loadFactor;
        this.capacity = capacity;
        table = (Node<K, V>[]) new Node[this.capacity];
    }

    /**
     * Возвращает индекс в таблице на основе хеш-кода ключа
     * @param key Ключ
     * @return Индекс
     */
    private int indexOf(K key) {
        return key.hashCode() % (capacity - 1);
    }

    /**
     * Вставляет узел {@code newNode} в таблицу {@code table} в связанные списко на позиции {@code index}.
     * Если флаг {@code swapIfExist} установлен в {@code true}, то заменяет старое значение при совпадении ключа, и не заменяет при {@code false}.
     * @param newNode Новый узел
     * @param index Индекс
     * @param swapIfExist Сменить, если уже существует
     * @param table Таблица
     * @return Возвращает количество добавленных новых узлов в таблицу (0 или 1)
     */
    private int putNodeByIndex(Node<K, V> newNode, int index,
                                boolean swapIfExist, Node<K, V>[] table) {
        if (table[index] == null) {
            // Create new list and add new node
            table[index] = newNode;
        } else {
            Node<K, V> tempNode = table[index];
            do {
                if ((tempNode.getHash() == newNode.getHash() &&
                        (tempNode.getKey().equals(newNode.getKey())))) {
                    // Rewrite old value to new
                    if (swapIfExist) {
                        tempNode.setValue(newNode.getValue());
                    }
                    return 0;
                } else {
                    if (tempNode.getNext() != null) {
                        tempNode = tempNode.getNext();
                    }
                }
            } while (tempNode.getNext() != null);

            // Add new node to linked list
            tempNode.setNext(newNode);
        }
        return 1;
    }

    /**
     * Возвращает узел, которые находится в таблице на позиции {@code index}. Может вернутся {@code null}.
     * @param index Индекс
     * @return Узел или {@code null}, если на позиции {@code index} нет узла
     */
    private Node<K, V> getNodeByIndex(int index) {
        return table[index];
    }

    /**
     * Ребалансировка словаря, если уровень загружености выше установленного.
     */
    private void rebalanced() {
        float actualLoadFactor = getActualLoad();
        if (actualLoadFactor < loadFactor) {
            return;
        }

        capacity = 2 * capacity + 1;
        Node<K, V>[] newTable = (Node<K, V>[]) new Node[capacity];
        int addedNodesCount = 0;
        for (int i = 0; i < table.length && addedNodesCount < countOfElements; i++) {
            if (table[i] == null) {
                continue;
            }

            Node<K, V> node = table[i];
            while (node != null) {
                int index = indexOf(node.getKey());
                putNodeByIndex(node, index, true, newTable);
                addedNodesCount++;
                node = node.getNext();
            }
        }


        table = newTable;
    }


    /**
     * Добавляет ключ-значение в словарь.
     * Если такой ключ уже существует, то перезаписывает старое значение новым.
     * @param key Ключ
     * @param value Значение
     */
    public void put(K key, V value) {
        Objects.requireNonNull(key);
        int index = indexOf(key);
        Node<K, V> node = new Node<>(key.hashCode(), key, value);
        countOfElements += putNodeByIndex(node, index, true, table);
        rebalanced();
    }

    /**
     * Добавляет ключ-значение в словарь.
     * Если такого ключа не существует, то старое значение не меняется.
     * @param key Ключ
     * @param value Значение
     */
    public void putIfNotExist(K key, V value) {
        Objects.requireNonNull(key);
        int index = indexOf(key);
        Node<K, V> node = new Node<>(key.hashCode(), key, value);
        countOfElements += putNodeByIndex(node, index, false, table);
        rebalanced();
    }

    /**
     * Возвращает значение по ключу
     * @param key Ключ
     * @return Значение
     */
    public V get(K key) {
        int index = indexOf(key);
        Node<K, V> node = getNodeByIndex(index);

        while (node != null) {
            if (node.getKey().equals(key)) {
                return node.getValue();
            } else {
                node = node.getNext();
            }
        }

        throw new HashMapException("Key " + key + " does not exist");
    }

    public V getOrNull(K key) {
        try {
            return get(key);
        } catch (HashMapException ex) {
            return null;
        }
    }

    public float getActualLoad() {
        return ((float) countOfElements) / capacity;
    }

    public float getInitLoad() {
        return loadFactor;
    }

    public int getSize() {
        return countOfElements;
    }

    public void setLoadFactor(float loadFactor) {
        this.loadFactor = loadFactor;
    }

}
