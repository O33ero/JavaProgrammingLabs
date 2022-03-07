package graph;


import java.util.*;

public class GraphAlgorithmsA {

    private GraphAlgorithmsA() {
    }

    public static <T> Set<T> dfs(Graph<T> graph, T srcVertex) throws GraphException {
        if (!graph.isContain(srcVertex)) {
            throw new GraphException("Vertex '" + srcVertex + "' does not exist");
        }

        Stack<T> stack = new Stack<>();
        Set<T> visited = new HashSet<>();
        stack.push(srcVertex);
        while (!stack.isEmpty()) {
            T cur = stack.pop();
            if (!visited.contains(cur)) {
                visited.add(cur);

                List<T> neighbors = graph.getOutEdges(srcVertex);
                for (T neighborVertex : neighbors) {
                    if (!visited.contains(neighborVertex)) {
                        stack.add(neighborVertex);
                    }
                }
            }
        }

        return visited;
    }

}
