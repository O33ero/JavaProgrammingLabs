package graph;

import java.util.*;

public class GraphAlgorithmsB {
    private GraphAlgorithmsB() {
    }

    /**
     * Алгоритм Тарьяна для топологической сортровки.
     * Топологический порядок вершин - такой порядок, при котором ребра вершин направлены строго в одном направлении.
     * То есть в списке вершин [a, b, c] может существовать ребро (a, b), (a, c) или (b, c), но точно не существует ребра (b, a), (c, a), или (c, b).
     * @param graph Граф
     * @param <T> Тип данных имен вершин
     * @return Списко в топологическом порядке.
     * @throws GraphException Исключение, если в графе найден цикл.
     */
    public static <T> List<T> tarjan(Graph<T> graph) throws GraphException {
        Set<T> grayVertex = new HashSet<>();
        List<T> blackVertex = new ArrayList<>();

        for (T vertex : graph.getVertexNames()) {
            helperTarjan(vertex, graph, blackVertex, grayVertex);
        }

        Collections.reverse(blackVertex);
        return blackVertex;
    }

    private static <T> void helperTarjan(T vertex, Graph<T> graph, List<T> blackVertex, Set<T> grayVertex) throws GraphException {
        if (blackVertex.contains(vertex)) {
            return;
        }
        if (grayVertex.contains(vertex)) {
            throw new GraphException("Detected loop. Algorithm cannot be used for this graph.");
        }

        grayVertex.add(vertex);
        List<T> neighbors = graph.getOutEdges(vertex);
        for (T neighbor : neighbors) {
            helperTarjan(neighbor, graph, blackVertex, grayVertex);
        }
        grayVertex.remove(vertex);
        blackVertex.add(vertex);
    }
}
