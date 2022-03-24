package graph;

import java.util.Objects;

/**
 * Реализация объекта "Ребро". У ребра есть вершина источник {@code src} и вершина назначения {@code dest}, а также вес {@code weight}.
 * Этот объект нужен для некоторых алгоритмов.
 * @param <T> Тип данных имен узлов
 */
public class Edge<T> implements Comparable<Edge<T>> {
    T src;
    T dest;
    int weight;

    public Edge(T from, T to, int weight) {
        this.src = from;
        this.dest = to;
        this.weight = weight;
    }

    /**
     * Ребро считается меньше другого ребра, если ее вес меньше чем у сравниваемого ребра
     * @param o Сравниваемое ребро
     * @return Результат сравнения (см. {@link Comparable})
     */
    @Override
    public int compareTo(Edge<T> o) {
        return this.weight - o.weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge<?> edge = (Edge<?>) o;

        if (weight != edge.weight) return false;
        if (!Objects.equals(src, edge.src)) return false;
        return Objects.equals(dest, edge.dest);
    }

    @Override
    public int hashCode() {
        int result = src != null ? src.hashCode() : 0;
        result = 31 * result + (dest != null ? dest.hashCode() : 0);
        result = 31 * result + weight;
        return result;
    }

    @Override
    public String toString() {
        return "(" + src + ")" +
                " -- " + weight + " -->" +
                " (" + dest + ")";
    }
}
