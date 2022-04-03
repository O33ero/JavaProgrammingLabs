package graph;

import java.util.*;

/**
 * Интерфейся для ориентированных графов. Функций, описанных ниже, достаточно для реализации всех алгоритмов на графах.
 * @param <T> Тип данных имен вершин
 */
public abstract class Graph<T> {
    final List<T> vertexNames = new ArrayList<>(); // All vertex names

    /**
     * Добавляет новую вершину
     *
     * @param vertexName Имя вершины
     * @return Результат добавления
     */
    public abstract boolean addVertex(T vertexName);

    /**
     * Добавляет новое ребро с весом 1
     *
     * @param from Из
     * @param to В
     */
    public abstract void addEdge(T from, T to);

    /**
     * Добавляет новое ребро с весом {@code weight}
     * @param from Из
     * @param to В
     * @param weight Вес
     */
    public abstract void addEdge(T from, T to, int weight);

    /**
     * Удаляет ребро
     * @param from Из
     * @param to В
     */
    public abstract void removeEdge(T from, T to);

    /**
     * Удаляет вершину
     * @param vertex Имя вершины
     */
    public abstract void removeVertex(T vertex);

    /**
     * Возвращает количество вершин
     * @return Количество вершин
     */
    public int vertexCount() {
        return vertexNames.size();
    }

    /**
     * Возвращает количество ребер
     * @return Количество ребер
     */
    public abstract int edgeCount();

    /**
     * Возвращает список входящих ребер
     * @param vertex Имя вершины
     * @return Список имен вершин
     */
    public abstract List<T> getInEdges(T vertex) throws GraphException;

    /**
     * Возвращает список исходящих вершин
     * @param vertex Имя вершины
     * @return Список имен вершин
     */
    public abstract List<T> getOutEdges(T vertex) throws GraphException;

    /**
     * Проверяет граф на наличие вершины
     * @param vertex Имя вершины
     * @return Результат проверки
     */
    public boolean isContain(T vertex) {
        return vertexNames.contains(vertex);
    }

    /**
     * Проверяет граф на наличие ребер с отрицательным весом
     * @return Результат проверки
     */
    public abstract boolean containNegativeEdge();

    /**
     * Возвращает вес ребра или 0, если такого ребра нет
     * @param from Из
     * @param to В
     * @return Вес ребра {@code from} -> {@code to}
     */
    public abstract int getWeight(T from, T to);

    /**
     * Возвращает список всех ребер
     * @return Список всех ребер
     */
    public abstract List<Edge<T>> getAllEdges();

    /**
     * Возвращает список вершин, для которых {@code vertex} является истоком.
     * @param vertex Имя вершины
     * @return Список ребер
     */
    public abstract List<Edge<T>> getEdges(T vertex);

    /**
     * Возвращает ребро, для котрого определен источник и конец
     * @param src Истчник
     * @param dest Конец
     * @return Ребро из источника в конец или null, если такой вершины не существует
     */
   public Edge<T> getEdge(T src, T dest) {
       for(Edge<T> edge : getEdges(src)) {
           if (edge.src.equals(src) && edge.dest.equals(dest)) {
               return edge;
           }
       }
       return null;
   }

    /**
     * Возвращает список всех имен вершин в графе
     * @return Список имен вершин
     */
    public List<T> getVertexNames() {
        return vertexNames;
    }

    /**
     * Возвращает новый объект, того же типа
     * @return Новый объект
     */
    public abstract Graph<T> getNewInstance();

    /**
     * Красивый вывод графа
     * @return Строка для вывода графа
     */
    @Override
    public abstract String toString();

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Graph<T> that = (Graph<T>) o;

        HashSet<Edge<T>> a = new HashSet<>(this.getAllEdges());
        HashSet<Edge<T>> b = new HashSet<>(that.getAllEdges());
        return Objects.equals(a, b);
    }
}
