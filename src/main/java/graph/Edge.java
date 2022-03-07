package graph;

import java.util.Objects;

public class Edge<T> implements Comparable<Edge<T>> {
    T src;
    T dest;
    int weight;

    Edge(T from, T to, int weight) {
        this.src = from;
        this.dest = to;
        this.weight = weight;
    }

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
