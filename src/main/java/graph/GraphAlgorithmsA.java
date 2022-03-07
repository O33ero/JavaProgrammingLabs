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
        Set<T> visited = new LinkedHashSet<>();
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

    public static <T> Set<T> bfs(Graph<T> graph, T srcVertex) throws GraphException {
        if (!graph.isContain(srcVertex)) {
            throw new GraphException("Vertex '" + srcVertex + "' does not exist");
        }
        Queue<T> queue = new LinkedList<>();
        Set<T> visited = new LinkedHashSet<>();

        queue.add(srcVertex);

        while (!queue.isEmpty()) {
            T cur = queue.poll();
            if (!visited.contains(cur)) {
                visited.add(cur);
                List<T> neighbors = graph.getOutEdges(srcVertex);
                for (T neighborVertex : neighbors) {
                    if (!visited.contains(neighborVertex)) {
                        queue.add(neighborVertex);
                    }
                }
            }
        }

        return visited;
    }

}
