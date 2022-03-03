package graph;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphAlgorithmsA {

    public static <T> boolean dfs (Graph<T> graph, T srcVertex, T destVertex) throws GraphException {
        return dfs(graph, srcVertex, destVertex, new HashSet<T>());
    }
    private static <T> boolean dfs(Graph<T> graph, T srcVertex, T destVertex, Set<T> visited) throws GraphException {
        if (!graph.isContain(srcVertex)) {
            throw new GraphException("Vertex '" + srcVertex + "' does not exist");
        }
        if (!graph.isContain(destVertex)) {
            throw new GraphException("Vertex '" + srcVertex + "' does not exist");
        }

        if (srcVertex.equals(destVertex)) {
            return true;
        }
        if (visited.contains(srcVertex)) {
            return true;
        }

        visited.add(srcVertex);
        List<T> neighbors = graph.getOutEdges(srcVertex);
        for (T neighborVertex : neighbors) {
            if (!visited.contains(neighborVertex)) {
                boolean reached = dfs(graph, neighborVertex, destVertex, visited);
                if (reached) {
                    return true;
                }
            }
        }

        return false;
    }

}
