package graph;

import java.util.*;

public class GraphAlgorithmsB {
    private GraphAlgorithmsB() {
    }

    /**
     * Алгоритм Тарьяна для топологической сортровки.
     * Топологический порядок вершин - такой порядок, при котором ребра вершин направлены строго в одном направлении.
     * То есть в списке вершин [a, b, c] может существовать ребро (a, b), (a, c) или (b, c), но точно не существует ребра (b, a), (c, a), или (c, b).
     *
     * @param graph Граф
     * @param <T>   Тип данных имен вершин
     * @return Списко в топологическом порядке.
     * @throws GraphException Исключение, если в графе найден цикл.
     */
    public static <T> List<T> tarjan(final Graph<T> graph) throws GraphException {
        Objects.requireNonNull(graph);
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


    /**
     * Алгоритм Флёри для поиска эйлерового пути.
     * P.S. Очень слабая реализация. Нужно больше оптимизации в функции вычисления возможности удаления ребра (определения моста),
     * поскольку с очень большими графами приходится пересчитывать вершины при каждом удалении, что очень-очень медлено и бесполезно
     * в большинстве случаев. Но, к сажалению, ничего лучше для ориентированного графа придумать не удалось.
     *
     * @param graph Граф
     * @param <T>   Тип данных имен вершин
     * @return Списко ребер в порядке их обхода
     * @throws GraphException Если граф не может иметь эйлеровый цикл и эйлеровый путь.
     */
    public static <T> List<Edge<T>> fleury(final Graph<T> graph) throws GraphException {
        Objects.requireNonNull(graph);
        Graph<T> graphOther = new MatrixGraph<>(graph);

        T startVertex = checkGraphForEuler(graph, false);

        List<Edge<T>> result = new ArrayList<>();
        helperFleury(startVertex, startVertex, graphOther, result);
        return result;
    }

    /**
     * Реккурсивный обход и удаление ребер
     *
     * @param vertex      Вершина
     * @param startVertex Начальная вершина, с которой начался алгоритм
     * @param graph       Граф
     * @param result      Резултирующий массив
     * @param <T>         Тип данных имен вершин
     * @throws GraphException Не может быть брошено
     */
    private static <T> void helperFleury(T vertex, T startVertex, Graph<T> graph, List<Edge<T>> result) throws GraphException {

        for (T neighbor : graph.getVertexNames()) {
            if (graph.getEdge(vertex, neighbor) == null) {
                continue;
            }
            // Если попался старт пути (цикла), то пытаемся пойти в другую сторону
            if (neighbor.equals(startVertex) && (graph.getOutEdges(vertex).size() > 1)) {
                continue;
            }

            if (canBeRemoved(vertex, neighbor, graph)) {
                result.add(graph.getEdge(vertex, neighbor));
                graph.removeEdge(vertex, neighbor);
                helperFleury(neighbor, startVertex, graph, result);
                break;
            }
        }
    }

    /**
     * Определяет можно ли удалить ребро (src, dest) и после этого обойти все ребра начиная с вершины dest
     *
     * @param src   Исходная вершина
     * @param dest  Конечная вершина
     * @param graph Граф
     * @param <T>   Тип данных имен вершин
     * @return Результат проверки
     * @throws GraphException Не может быть брошено
     */
    private static <T> boolean canBeRemoved(T src, T dest, final Graph<T> graph) throws GraphException {

        if (graph.getOutEdges(src).size() == 1) {
            return true;
        }

        int allEdgesCount = graph.getAllEdges().size();
        int newEdgesCount = 0;

        Deque<Edge<T>> stack = new ArrayDeque<>();
        Set<Edge<T>> visited = new HashSet<>();
        Edge<T> tempEdge = graph.getEdge(src, dest);
        graph.removeEdge(src, dest);
        stack.add(tempEdge);


        while (!stack.isEmpty()) {
            Edge<T> edge = stack.pop();
            visited.add(edge);
            for (Edge<T> e : graph.getEdges(edge.dest)) {
                if (!visited.contains(e) && !stack.contains(e)) {
                    stack.add(e);
                }
            }
            newEdgesCount++;
        }
        graph.addEdge(tempEdge.src, tempEdge.dest, tempEdge.weight);

        return allEdgesCount == newEdgesCount;
    }

    /**
     * Поиск эйлеровго цикла через объединение циклов.
     *
     * @param graph Граф
     * @param <T>   Тип данных имен вершин
     * @return Список ребер в порядке обхода эйлерового цикла
     * @throws GraphException Не может быть брошено
     */
    public static <T> List<Edge<T>> findEulerCycle(final Graph<T> graph) throws GraphException {
        Objects.requireNonNull(graph);
        Graph<T> graphOther = new MatrixGraph<>(graph);

        T startVertex = checkGraphForEuler(graphOther, true);

        List<T> result = new ArrayList<>();
        Deque<T> stack = new ArrayDeque<>();
        stack.push(startVertex);

        while (!stack.isEmpty()) {
            T v = stack.peek();
            if (graphOther.getOutEdges(v).isEmpty()) {
                result.add(v);
                stack.pop();
            } else {
                Edge<T> edge = graphOther.getEdges(v).get(0);
                graphOther.removeEdge(edge.src, edge.dest);
                stack.push(edge.dest);
            }
        }

        Collections.reverse(result);

        // convert list of vertexes to list of edges
        List<Edge<T>> edgeResult = new ArrayList<>();
        for (int i = 0; i < result.size() - 1; i++) {
            edgeResult.add(
                    graph.getEdge(result.get(i), result.get(i + 1))
            );
        }


        return edgeResult;
    }

    /**
     * Проверка графа на эйлеровость. Проверяет граф на существование эйлерового цикла или эйлерового пути
     *
     * @param graph   Граф
     * @param isCycle Обязательно должен существовать эйлеровый цикл
     * @param <T>     Тип данных имен вершин
     * @return Имя вершины с которой должен начинатся эйлеровый путь (цикл)
     * @throws GraphException Будет выброшено, если граф не эйлеровый
     * @see <a href="https://ru.wikipedia.org/wiki/Эйлеров_цикл">Условия существований эйлерового цикла и пути</a>
     */
    private static <T> T checkGraphForEuler(final Graph<T> graph, boolean isCycle) throws GraphException {
        // Проверка графа по критерию существования эйлерова пути для ориентированного графа
        // https://ru.wikipedia.org/wiki/Эйлеров_цикл
        int degreeP1 = 0; // inDegree = outDegree + 1
        int degreeM1 = 0; // inDegree = outDegree - 1
        T startVertex = null;
        for (T vertex : graph.getVertexNames()) {
            if (graph.getInEdges(vertex).size() == graph.getOutEdges(vertex).size()) {
                continue;
            }
            if (degreeM1 == 0 && graph.getInEdges(vertex).size() == graph.getOutEdges(vertex).size() - 1) {
                degreeM1++;
                startVertex = vertex;
                continue;
            }
            if (degreeP1 == 0 && graph.getInEdges(vertex).size() == graph.getOutEdges(vertex).size() + 1) {
                degreeP1++;
                continue;
            }
            throw new GraphException("Euler path does not exist. Algorithm cannot be used for this graph.");
        }
        if (degreeM1 != degreeP1) {
            throw new GraphException("Euler path does not exist. Algorithm cannot be used for this graph.");
        }

        if (isCycle && degreeM1 == 1) {
            throw new GraphException("Euler circuit does not exist. Algorithm cannot be used for this graph.");
        }

        // Если degreeM1 == degreeP1 == 1, то существует эйлеров путь (не цикл)
        // Если degreeM1 == degreeP1 == 0, то существует эйлеров цикл, поэтому нужно проверить граф на сильно связаность
        // Чтобы граф был сильно связным, необходимо чтобы для любых s и t существовал ориентированный путь (s, t) и (t, s)
        // Как хорошо, что можно воспользоватся алгоритмом Дейкстры, написанный в прошлой лабе :)))
        if (degreeM1 == 0) {
            for (T vertex : graph.getVertexNames()) {
                Map<T, Integer> shortestMap = GraphAlgorithmsA.dijkstra(graph, vertex);
                long existedPathCount = shortestMap.values().stream().filter(x -> x != -1).count();
                if (existedPathCount != graph.getVertexNames().size()) {
                    throw new GraphException("Euler circuit does not exist. Algorithm cannot be used for this graph.");
                }
            }
        }
        if (startVertex == null) {
            startVertex = graph.getVertexNames().get(0);
        }

        return startVertex;
    }


    /**
     * Алгоритм  Косарайю. Алгоритм для нахождения сильных компонет связности.
     *
     * @param graph Граф
     * @param <T>   Тип данных имен вершин
     * @return Список объединений, где каждое объединение - список вершин одной компоненты связности
     * @throws GraphException Не может быть выбрешено
     */
    public static <T> List<List<T>> kosaraju(final Graph<T> graph) throws GraphException {
        Objects.requireNonNull(graph);
        List<List<T>> result = new ArrayList<>();

        // Non-recursive algorithm
        Deque<T> dfsStack = new ArrayDeque<>();
        Deque<T> resultStack = new ArrayDeque<>();
        Deque<T> unvisited = new ArrayDeque<>(graph.getVertexNames());
        dfsStack.add(graph.getVertexNames().get(0));
        while (!dfsStack.isEmpty() || !unvisited.isEmpty()) {
            T vertex;
            if (dfsStack.isEmpty()) {
                vertex = unvisited.peek();
                dfsStack.add(vertex);
            } else {
                vertex = dfsStack.peek();
            }
            unvisited.remove(vertex);

            boolean existUnvisitedNeighbor = false;
            for (T neighbor : graph.getOutEdges(vertex)) {
                if (unvisited.contains(neighbor)) {
                    dfsStack.add(neighbor);
                    existUnvisitedNeighbor = true;
                    break;
                }
            }
            if (existUnvisitedNeighbor) {
                continue;
            }

            dfsStack.pop();
            resultStack.add(vertex);
        }

//        // Recursive algorithm
//        for (T vertex : graph.getVertexNames()) {
//            dfsExtended(vertex, graph, visited, stack);
//        }


        Graph<T> transposedGraph = graph.getTransposedGraph();
        Set<T> visited = new HashSet<>();
        while (!resultStack.isEmpty()) {
            T vertex = resultStack.pop();
            if (!visited.contains(vertex)) {
                List<T> componentList = GraphAlgorithmsA.dfs(transposedGraph, vertex);
                componentList.removeAll(visited);
                visited.addAll(componentList);
                result.add(componentList);
            }
        }

        result.sort((x, y) -> Integer.compare(y.size(), x.size()));
        return result;
    }

    /**
     * Измененый DFS алгоритм, заполняющий {@code stack} в порядке "выхода" из вершины при обходе.
     * Реккурсивное решение.
     *
     * @param vertex  Вершина
     * @param graph   Граф
     * @param visited Множество, посещенных вершин
     * @param stack   Результирующий стек
     * @param <T>     Тип данных имен вершин
     * @throws GraphException Не может быть брошено
     */
    private static <T> void dfsExtended(T vertex, Graph<T> graph, Set<T> visited, Deque<T> stack) throws GraphException {
        if (!visited.contains(vertex)) {
            visited.add(vertex);
            for (T neighbor : graph.getOutEdges(vertex)) {
                dfsExtended(neighbor, graph, visited, stack);
            }
            stack.add(vertex);
        }
    }


}
