package graph;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphAlgorithmsA {

    private GraphAlgorithmsA() {}

    public static <T> Set<T> dfs (Graph<T> graph, T srcVertex) throws GraphException {
        return dfs(graph, srcVertex, new HashSet<>());
    }
    private static <T> Set<T> dfs(Graph<T> graph, T srcVertex, Set<T> visited) throws GraphException {
        if (!graph.isContain(srcVertex)) {
            throw new GraphException("Vertex '" + srcVertex + "' does not exist");
        }
        if (visited.contains(srcVertex)) {
            return visited;
        }

        visited.add(srcVertex);
        List<T> neighbors = graph.getOutEdges(srcVertex);
        for (T neighborVertex : neighbors) {
            if (!visited.contains(neighborVertex)) {
                dfs(graph, neighborVertex, visited);
            }
        }

        return visited;
    }

}
