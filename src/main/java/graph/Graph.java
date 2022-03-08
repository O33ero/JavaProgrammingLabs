package graph;

import java.util.List;

public interface Graph<T> {

    /**
     * Добавляет новую вершину
     *
     * @param vertexName Имя вершины
     * @return Результат добавления
     */
    boolean addVertex(T vertexName);

    /**
     * Добавляет новое ребро с весом 1
     *
     * @param from Из
     * @param to В
     * @return Результат добавления
     */
    boolean addEdge(T from, T to);

    /**
     * Добавляет новое ребро с весом {@code weight}
     * @param from Из
     * @param to В
     * @param weight Вес
     * @return Результат добавления
     */
    boolean addEdge(T from, T to, int weight);

    /**
     * Удаляет ребро
     * @param from Из
     * @param to В
     */
    void removeEdge(T from, T to);

    /**
     * Удаляет вершину
     * @param vertex Имя вершины
     */
    void removeVertex(T vertex);

    /**
     * Возвращает количество вершин
     * @return Количество вершин
     */
    int vertexCount();

    /**
     * Возвращает количество ребер
     * @return Количество ребер
     */
    int edgeCount();

    /**
     * Возвращает список входящих ребер
     * @param vertex Имя вершины
     * @return Список имен вершин
     */
    List<T> getInEdges(T vertex) throws GraphException;

    /**
     * Возвращает список исходящих вершин
     * @param vertex Имя вершины
     * @return Список имен вершин
     */
    List<T> getOutEdges(T vertex) throws GraphException;

    /**
     * Проверяет граф на наличие вершины
     * @param vertex Имя вершины
     * @return Результат проверки
     */
    boolean isContain(T vertex);

    /**
     * Проверяет граф на наличие ребер с отрицательным весом
     * @return Результат проверки
     */
    boolean containNegativeEdge();

    /**
     * Возвращает вес ребра или 0, если такого ребра нет
     * @param from Из
     * @param to В
     * @return Вес ребра {@code from} -> {@code to}
     */
    int getWeight(T from, T to);

    /**
     * Возвращает список всех ребер
     * @return Список всех ребер
     */
    List<Edge<T>> getAllEdges();

    /**
     * Возвращает список всех имен вершин в графе
     * @return Список имен вершин
     */
    List<T> getVertexNames();

    /**
     * Возвращает новый объект, того же типа
     * @return Новый объект
     */
    Graph<T> getNewInstance();

    /**
     * Красивый вывод графа
     * @return Строка для вывода графа
     */
    String toString();
}
