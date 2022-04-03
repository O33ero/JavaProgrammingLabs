package graph;


import java.util.*;

public class GraphAlgorithmsA {

    private GraphAlgorithmsA() {
    }

    public static <T> List<T> dfs(final Graph<T> graph, T srcVertex) throws GraphException {
        Objects.requireNonNull(graph);
        Objects.requireNonNull(srcVertex);
        if (!graph.isContain(srcVertex)) {
            throw new GraphException("Vertex '" + srcVertex + "' does not exist");
        }

        Deque<T> stack = new ArrayDeque<>();
        List<T> visited = new ArrayList<>();
        stack.push(srcVertex);
        while (!stack.isEmpty()) {
            T cur = stack.pop();
            if (!visited.contains(cur)) {
                visited.add(cur);

                List<T> neighbors = graph.getOutEdges(cur);
                for (T neighborVertex : neighbors) {
                    if (!visited.contains(neighborVertex)) {
                        stack.add(neighborVertex);
                    }
                }
            }
        }

        return visited;
    }

    public static <T> Set<T> bfs(final Graph<T> graph, T srcVertex) throws GraphException {
        Objects.requireNonNull(graph);
        Objects.requireNonNull(srcVertex);
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
     *
     * @param graph     Граф
     * @param srcVertex Исходная вершина
     * @param <T>       Тип данных, обозначающих имя вершины
     * @return Карта с весами кратчайших путей
     * @throws GraphException Граф не должен содержать отрицательных весов, вершина {@code srcVertex} должна принадлежать графу.
     */
    public static <T> Map<T, Integer> dijkstra(final Graph<T> graph, T srcVertex) throws GraphException {
        Objects.requireNonNull(graph);
        Objects.requireNonNull(srcVertex);

        if (!graph.isContain(srcVertex)) {
            throw new GraphException("Vertex '" + srcVertex + "' does not exist");
        }
        if (graph.containNegativeEdge()) {
            throw new GraphException("Graph contain negative weight and algorithm Dijkstra cannot be used");
        }

        Map<T, Integer> shortestPathWeight = new HashMap<>(); // Minimal weight of path from src to vertex
        for (T vertex : graph.getVertexNames()) {
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
                if ((oldWeightNeighbor == Integer.MAX_VALUE) || // Weight == +INF
                        (oldWeightNeighbor > newWeightNeighbor + shortestPathWeight.get(cur))) { // newWeight < oldWeight
                    shortestPathWeight.replace(vertexNeighbor, newWeightNeighbor + weightCur);
                }

                if (!visited.contains(vertexNeighbor) && !unvisited.contains(vertexNeighbor)) { // vertexNeighbor has been visited or will be visited soon
                    unvisited.add(vertexNeighbor);
                }
            }
        }

        for(Map.Entry<T, Integer> key : shortestPathWeight.entrySet()) {
            if (shortestPathWeight.get(key.getKey()) == Integer.MAX_VALUE) {
                shortestPathWeight.put(key.getKey(), -1);
            }
        }
        return shortestPathWeight;
    }

    /**
     * Алгоритм Дейкстры. Возвращает значение веса кратчайшего путу от {@code srcVertex}
     * до {@code destVertex}. В случае отсутствия пути от {@code srcVertex} до {@code destVertex},
     * значение будет равно {@code Integer.MAX_VALUE}.
     *
     * @param graph      Граф
     * @param srcVertex  Исходная вершина
     * @param destVertex Конечная вершина
     * @param <T>        Тип данных, обозначающих имя вершины
     * @return Значение веса кратчайшего пути от {@code srcVertex} до {@code destVertex} или -1, если пути не существует
     * @throws GraphException Граф не должен содержать отрицательных весов, вершины {@code srcVertex} и {@code destVertex} должны принадлежать графу.
     */
    public static <T> int dijkstra(final Graph<T> graph, T srcVertex, T destVertex) throws GraphException {
        Objects.requireNonNull(graph);
        Objects.requireNonNull(srcVertex);
        Objects.requireNonNull(destVertex);
        if (!graph.isContain(srcVertex)) {
            throw new GraphException("Vertex '" + srcVertex + "' does not exist");
        }
        if (!graph.isContain(destVertex)) {
            throw new GraphException("Vertex '" + destVertex + "' does not exist");
        }
        if (graph.containNegativeEdge()) {
            throw new GraphException("Graph contain negative weight and algorithm Dijkstra cannot be used");
        }

        return dijkstra(graph, srcVertex).get(destVertex);
    }

    /**
     * Алгоритм Крускала. Алгоритм поиска минимального остовного графа.
     * @param graph Граф
     * @param <T> Тип данных вершин
     * @return Минимальный оставной граф
     */
    public static <T> Graph<T> kruskal(final Graph<T> graph) {
        Objects.requireNonNull(graph);
        Graph<T> spanningGraph = graph.getNewInstance();

        // Create list of edges in non-decreasing order
        List<Edge<T>> sortedAllEdges = graph.getAllEdges();
        Collections.sort(sortedAllEdges);

        // Create non-linked graph with duplicate names
        for (T vertex : graph.getVertexNames()) {
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

    /**
     * Алгоритм Прима. Алгоритм поиска минимального остовного графа.
     * @param graph Граф
     * @param <T> Тип данных имен вершин
     * @return Минимальный оставной граф
     */
    public static <T> Graph<T> prim(final Graph<T> graph) {
        Objects.requireNonNull(graph);
        Graph<T> spanningGraph = graph.getNewInstance();
        for(T vertex : graph.getVertexNames()) {
            spanningGraph.addVertex(vertex);
        }

        List<T> unvisited = new LinkedList<>(graph.getVertexNames());

        PriorityQueue<Edge<T>> sortedEdges = new PriorityQueue<>();

        T cur = unvisited.iterator().next(); // get random vertex
        sortedEdges.addAll(graph.getEdges(cur)); // get all edges
        unvisited.remove(cur);
        while (!unvisited.isEmpty()){
            while(!sortedEdges.isEmpty()) {
                Edge<T> minimalEdge = sortedEdges.poll();
                if (unvisited.contains(minimalEdge.dest)) { // if new edge creating loop, it will be dropped and selected next edge
                    spanningGraph.addEdge(minimalEdge.src, minimalEdge.dest, minimalEdge.weight);
                    cur = minimalEdge.dest;
                    break;
                }
            }
            sortedEdges.addAll(graph.getEdges(cur)); // get all edges
            unvisited.remove(cur);
        }

        return spanningGraph;
    }

    /**
     * Алгоритм Флойда-Уоршелла. Алгоритм нахождения длин кратчайших путей между всеми парами вершин во взвешенном ориентированном графе
     * @param graph Граф
     * @param <T> Тип данных имен вершин
     * @return Список длин кратчайших путей между всеми парами вершин.
     * @throws GraphException Граф имеет отрицательные веса
     */
    public static <T> List<Edge<T>> floydWarshall(final Graph<T> graph) throws GraphException {
        Objects.requireNonNull(graph);
        if (graph.containNegativeEdge()) {
            throw new GraphException("Graph cannot contain negative edge");
        }


        // Create matrix
        List<ArrayList<Integer>> matrix = new ArrayList<>();
        List<T> vertexNames = graph.getVertexNames();
        for(int i = 0; i < vertexNames.size(); i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for(int j = 0; j < vertexNames.size(); j++) {
                row.add(Integer.MAX_VALUE);
            }

            matrix.add(row);
        }

        // Initial edges
        List<Edge<T>> edgeList = graph.getAllEdges();
        for (Edge<T> edge : edgeList) {
            int indexSrc = vertexNames.indexOf(edge.src);
            int indexDest = vertexNames.indexOf(edge.dest);
            matrix.get(indexSrc).set(indexDest, edge.weight);
        }

        // Initial positive infinitive
        for (List<Integer> row : matrix) {
            for(int i = 0; i < row.size(); i++) {
                if (row.get(i) == 0) {
                    row.set(i, Integer.MAX_VALUE);
                }
            }
        }


        // Algorithm body
        for(int k = 0; k < vertexNames.size(); k++) {
            for(int i = 0; i < vertexNames.size(); i++) {
                for(int j = 0; j < vertexNames.size(); j++) {
                    int oldValue = matrix.get(i).get(j);
                    int newValue = matrix.get(i).get(k) + matrix.get(k).get(j);

                    if(newValue < 0) { // More than maxValue
                        continue;
                    }

                    if (newValue < oldValue) {
                        matrix.get(i).set(j, matrix.get(i).get(k) + matrix.get(k).get(j));
                    }
                }
            }
        }

        // Represent result
        List<Edge<T>> result = new ArrayList<>();
        for(int i = 0; i < matrix.size(); i++) {
            for(int j = 0; j < matrix.size(); j++) {
                int value = matrix.get(i).get(j);
                if (value != Integer.MAX_VALUE) {
                    result.add(
                            new Edge<>(vertexNames.get(i), vertexNames.get(j), value)
                    );
                }
            }
        }
        return result;
    }
}
