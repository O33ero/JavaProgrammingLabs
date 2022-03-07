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

    /**
     * Алгоритм Дейкстры. Возвращает {@code map} содержащую веса кратчайших путей от {@code srcVertex}
     * до всех остальных вершин. В случае отсутствия пути от {@code srcVertex} до какой либо вершины,
     * значение в {@code map} будет равно {@code Integer.MAX_VALUE}.
     * @param graph Граф
     * @param srcVertex Исходная вершина
     * @param <T> Тип данных, обозначающих имя вершины
     * @return Карта с весами кратчайших путей
     * @throws GraphException Граф не должен содержать отрицательных весов, вершина {@code srcVertex} должна принадлежать графу.
     */
    public static <T> Map<T, Integer> dijkstra(Graph<T> graph, T srcVertex) throws GraphException {
        Objects.requireNonNull(graph);
        Objects.requireNonNull(srcVertex);

        if (!graph.isContain(srcVertex)) {
            throw new GraphException("Vertex '" + srcVertex + "' does not exist");
        }
        if (!graph.containNegativeEdge()) {
            throw new GraphException("Graph contain negative weight and algorithm Dijkstra cannot be used");
        }

        Map<T, Integer> shortestPathWeight = new HashMap<>(); // Minimal weight of path from src to vertex
        for (T vertex : graph.vertexNames) {
            if (vertex.equals(srcVertex)) {
                shortestPathWeight.put(vertex, 0);
            } else {
                shortestPathWeight.put(vertex, Integer.MAX_VALUE); // MAX_VALUE instead of positive infinity
            }
        }

        Queue<T> unvisited = new LinkedList<>();
        Set<T> visited = new HashSet<>();
        unvisited.add(srcVertex);
        while (!unvisited.isEmpty()) {
            T cur = unvisited.poll();
            visited.add(cur);

            int weightCur = shortestPathWeight.get(cur);
            List<T> neighbors = graph.getOutEdges(cur);
            for (T vertexNeighbor : neighbors) {
                if (vertexNeighbor.equals(cur)) { // skip self loop, because path is always longer with loop than without one
                    continue;
                }

                int oldWeightNeighbor = shortestPathWeight.get(vertexNeighbor); // Wight to vertex or +INF (MAX_VALUE)
                int newWeightNeighbor = graph.getWeight(cur, vertexNeighbor); // This edge always exist
                if (    (oldWeightNeighbor == Integer.MAX_VALUE) || // Weight == +INF
                        (oldWeightNeighbor > newWeightNeighbor + shortestPathWeight.get(cur))) { // newWeight < oldWeight
                    shortestPathWeight.replace(vertexNeighbor, newWeightNeighbor + weightCur);
                }

                if (!visited.contains(vertexNeighbor) && !unvisited.contains(vertexNeighbor)) { // vertexNeighbor has been visited or will be visited soon
                    unvisited.add(vertexNeighbor);
                }
            }
        }

        return shortestPathWeight;
    }

    /**
     * Алгоритм Дейкстры. Возвращает значение веса кратчайшего путу от {@code srcVertex}
     * до {@code destVertex}. В случае отсутствия пути от {@code srcVertex} до {@code destVertex},
     * значение будет равно {@code Integer.MAX_VALUE}.
     * @param graph Граф
     * @param srcVertex Исходная вершина
     * @param destVertex Конечная вершина
     * @param <T> Тип данных, обозначающих имя вершины
     * @return Значение веса кратчайшего пути от {@code srcVertex} до {@code destVertex}
     * @throws GraphException Граф не должен содержать отрицательных весов, вершины {@code srcVertex} и {@code destVertex} должны принадлежать графу.
     */
    public static <T> int dijkstra(Graph<T> graph, T srcVertex, T destVertex) throws GraphException {
        Objects.requireNonNull(graph);
        Objects.requireNonNull(srcVertex);
        Objects.requireNonNull(destVertex);
        if (!graph.isContain(srcVertex)) {
            throw new GraphException("Vertex '" + srcVertex + "' does not exist");
        }
        if (!graph.isContain(destVertex)) {
            throw new GraphException("Vertex '" + destVertex + "' does not exist");
        }
        if (!graph.containNegativeEdge()) {
            throw new GraphException("Graph contain negative weight and algorithm Dijkstra cannot be used");
        }

        return dijkstra(graph, srcVertex).get(destVertex);
    }

    public static <T> Graph<T> kruskal(Graph<T> graph) {
        Graph<T> spanningGraph = new Graph<>();

        // Create list of edges in non-decreasing order
        List<Edge<T>> sortedAllEdges = graph.getAllEdges();
        Collections.sort(sortedAllEdges);

        // Create non-linked graph with duplicate names
        for (T vertex : graph.vertexNames) {
            spanningGraph.addVertex(vertex);
        }

        // List of sets
        List<Set<T>> setList = new ArrayList<>();


        int takenEdges = 0;
        int countVertexes = graph.vertexCount();
        Iterator<Edge<T>> iter = sortedAllEdges.listIterator();

        while (iter.hasNext() && takenEdges < countVertexes - 1) {
            Edge<T> nextEdge = iter.next();

            int srcIndex = kruskalFind(nextEdge.src, setList);
            int destIndex = kruskalFind(nextEdge.dest, setList);

            if (srcIndex != destIndex) { // src and dest include in different sets
                Set<T> newUnionSet = new HashSet<>();

                // Get or create srcSet
                Set<T> srcSet;
                if (srcIndex != -1) {
                    srcSet = setList.get(srcIndex);
                } else {
                    srcSet = new HashSet<>();
                    srcSet.add(nextEdge.src);
                }

                // Get or create destSet
                Set<T> destSet;
                if (destIndex != -1) {
                    destSet = setList.get(destIndex);
                } else {
                    destSet = new HashSet<>();
                    destSet.add(nextEdge.dest);
                }

                // Union two sets
                newUnionSet.addAll(srcSet);
                newUnionSet.addAll(destSet);

                // Remove old sets
                if (srcIndex != -1) {
                    setList.remove(srcSet);
                }
                if (destIndex != -1) {
                    setList.remove(destSet);
                }

                // Add new union set
                setList.add(newUnionSet);

                // Add new edge
                spanningGraph.addEdge(nextEdge.src, nextEdge.dest, nextEdge.weight);
                takenEdges++;
            } else if (srcIndex == -1 && destIndex == -1) { // src and dest not include in anyone set
                // Create new set
                Set<T> newSet = new HashSet<>();
                newSet.add(nextEdge.src);
                newSet.add(nextEdge.dest);
                // Add to setList
                setList.add(newSet);

                spanningGraph.addEdge(nextEdge.src, nextEdge.dest, nextEdge.weight);
                takenEdges++;
            } // else skip edge and go to next
        }

        return spanningGraph;
    }

    private static <T> int kruskalFind(T waypoint, List<Set<T>> setList) {
        for (int i = 0; i < setList.size(); i++) {
            if (setList.get(i).contains(waypoint)) {
                return i;
            }
        }
        return -1;
    }

}
