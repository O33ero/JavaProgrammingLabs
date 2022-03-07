package graph;

import java.util.*;

/**
 * <p>
 * Ориентированный граф. Данная реализация основывается на матрице смежности,
 * поскольку она проще в создании и работает быстрее для запросов, но занимает больше места в памяти,
 * чем реализация через список смежности. Будем считать, что быстрота алгоритмов, используемых над
 * этим графом, важнее размеров используемой памяти.
 * </p>
 *
 * <p>
 * Матрица смежности - квадратная матрица, которая хранит веса ребер.
 * Например, вес ребра из вершины A в вершину B будет хранится в ячейке [A, B] матрицы смежности,
 * где A - строка, B - столбец.
 * </p>
 *
 * <p>
 * Данная реализация не может содержать вершины с одинаковыми именами, в целях избежания коллизий
 * при добавлении/удалении ребер.
 * </p>
 *
 * @param <T> Тип данных названий вершин
 * @see <a href=www.baeldung.com/java-graphs>baeldung.com/java-graphs</a>
 */
public class Graph<T> {
    final List<T> vertexNames = new ArrayList<>(); // All vertex names
    final List<ArrayList<Integer>> adjacencyMatrix = new ArrayList<>();  // Adjacency Matrix (Матрица смежности)


    public Graph() {
        // Default constructor
        // No vertex
        // No edges
    }

    public Graph(T vertex) {
        addVertex(vertex);
    }

    public Graph(T vertex1, T vertex2) throws GraphException {
        Set<T> set = new HashSet<>(); // Need for remove duplicates
        set.add(vertex1);
        set.add(vertex2);
        if (set.size() != 2) {
            throw new GraphException("Vertexes must be different in pairs");
        } else { // parameters has duplicates
            addVertex(vertex1);
            addVertex(vertex2);
        }
    }

    public Graph(T vertex1, T vertex2, T vertex3) throws GraphException {
        Set<T> set = new HashSet<>();
        set.add(vertex1);
        set.add(vertex2);
        set.add(vertex3);
        if (set.size() != 3) { // parameters has duplicates
            throw new GraphException("Vertexes must be different in pairs");
        } else {
            addVertex(vertex1);
            addVertex(vertex2);
            addVertex(vertex3);
        }
    }

    public Graph(T vertex1, T vertex2, T vertex3, T vertex4) throws GraphException {
        Set<T> set = new HashSet<>();
        set.add(vertex1);
        set.add(vertex2);
        set.add(vertex3);
        set.add(vertex4);
        if (set.size() != 4) { // parameters has duplicates
            throw new GraphException("Vertexes must be different in pairs");
        } else {
            addVertex(vertex1);
            addVertex(vertex2);
            addVertex(vertex3);
            addVertex(vertex4);
        }
    }

    public Graph(T vertex1, T vertex2, T vertex3, T vertex4, T vertex5) throws GraphException {
        Set<T> set = new HashSet<>();
        set.add(vertex1);
        set.add(vertex2);
        set.add(vertex3);
        set.add(vertex4);
        set.add(vertex5);
        if (set.size() != 5) { // parameters has duplicates
            throw new GraphException("Vertexes must be different in pairs");
        } else {
            addVertex(vertex1);
            addVertex(vertex2);
            addVertex(vertex3);
            addVertex(vertex4);
            addVertex(vertex5);
        }
    }

    public Graph(Collection<T> collection) throws GraphException {
        Set<T> set = new HashSet<>(collection);
        if (set.size() != collection.size()) { // collection has duplicates
            throw new GraphException("Vertexes must be different in pairs");
        } else {
            for (T vertex : collection) {
                addVertex(vertex);
            }
        }
    }


    /**
     * Добавить новую вершину.
     * Если вершина с таким именем уже существует, то будет возвращено {@code false}.
     * Если такой вершины не существует, то она будет создана и возвращено {@code true}
     * @param vertexName Имя новой вершины
     * @return Результат добавления. {@code false} если такая вершина уже существует, и {@code true} - в противном случае
     */
    public boolean addVertex(T vertexName) {
        if (vertexNames.contains(vertexName)) {
            return false; // Вершина с таким названием уже существует
        } else {
            vertexNames.add(vertexName); // Добавляет новую вершину в список имен вершин

            for (List<Integer> row : adjacencyMatrix) { // Добавляет к каждой строке по одной новой ячейки
                row.add(0);
            }
            adjacencyMatrix.add(arrayListCopies(vertexNames.size())); // Добавляет новый столбец

            return true;
        }
    }

    /**
     * Добавляет новое ребро. Вес ребра принимает значение по умолчанию - 1.
     * Если какая-либо вершина не существует, то будет возвращено {@code false}.
     * Если вершины существует, то будет создано новое ребро и возвращено {@code true}
     * @param from Вершина, из которой идет ребро
     * @param to Вершина, в которое идет ребро
     * @return {@code false} если какой то из указанных вершин не существует, и {@code true} - в противном случае.
     */
    public boolean addEdge(T from, T to) {
        return addEdge(from, to, 1);
    }

     /**
     * Добавляет новое ребро. Вес ребра принимает значение из параметра {@code weight}.
     * Если какая-либо вершина не существует, то будет возвращено {@code false}.
     * Если вершины существует, то будет создано новое ребро и возвращено {@code true}
     * @param from Вершина, из которой идет ребро
     * @param to Вершина, в которое идет ребро
     * @return {@code false} если какой то из указанных вершин не существует, и {@code true} - в противном случае.
     */
    public boolean addEdge(T from, T to, int weight) {
        int indexFrom;
        int indexTo;
        if (!vertexNames.contains(from) || !vertexNames.contains(to)) {
            return false;
        } else {
            indexFrom = vertexNames.indexOf(from);
            indexTo = vertexNames.indexOf(to);
        }

        setEdge(indexFrom, indexTo, weight);
        return true;
    }

    private void setEdge(int indexFrom, int indexTo, int value) {
        adjacencyMatrix.get(indexFrom).set(indexTo, value);
    }


    /**
     * Удаляет ребро.
     * @param from Вершина, из которой идет ребро
     * @param to Вершина, в которое идет ребро
     */
    public void removeEdge(T from, T to) {
        int indexFrom;
        int indexTo;
        if (!vertexNames.contains(from) || !vertexNames.contains(to)) {
            return;
        } else {
            indexFrom = vertexNames.indexOf(from);
            indexTo = vertexNames.indexOf(to);
        }

        setEdge(indexFrom, indexTo, 0);
    }

    /**
     * Удаляет вершину по имени
     * @param vertex Вершина
     */
    public void removeVertex(T vertex) {
        int indexVertex;
        if (!vertexNames.contains(vertex)) {
            return;
        } else {
            indexVertex = vertexNames.indexOf(vertex);
        }

        adjacencyMatrix.remove(indexVertex);
        for (List<Integer> row : adjacencyMatrix) {
            row.remove(indexVertex);
        }
        vertexNames.remove(indexVertex);
    }

    /**
     * Возвращает количество вершина
     * @return Количество вершин
     */
    public int vertexCount() {
        return vertexNames.size();
    }

    /**
     * Возвращает количество ненулевых ребер
     * @return Количество ребер
     */
    public int edgeCount() {
        int count = 0;
        for (List<Integer> row : adjacencyMatrix) {
            for (int value : row) {
                if (value != 0) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Возвращает список вершин, которые входят в вершину {@code vertex}.
     * @param vertex Вершина, относительно которое идет поиск
     * @return Список вершин, входящих в вершину
     * @throws GraphException указанной вершины не существует
     */
    public List<T> getInEdges(T vertex) throws GraphException {
        if (!vertexNames.contains(vertex)) {
            throw new GraphException("Vertex " + vertex + " does not exist");
        }

        int indexVertex = vertexNames.indexOf(vertex);
        List<T> inEdges = new ArrayList<>();
        for (int i = 0; i < vertexNames.size(); i++) {
            if (adjacencyMatrix.get(i).get(indexVertex) != 0) {
                inEdges.add(vertexNames.get(i));
            }
        }

        return inEdges;
    }

    /**
     * Возвращает список вершин, которые выходят из вершины {@code vertex}
     * @param vertex Вершина, относительно которой идет поиск
     * @return Список вершин, выходят из вершины
     * @throws GraphException указанная вершина не найдена
     */
    public List<T> getOutEdges(T vertex) throws GraphException {
        if (!vertexNames.contains((vertex))) {
            throw new GraphException("Vertex " + vertex + " does not exist");
        }

        int vertexIndex = vertexNames.indexOf(vertex);
        List<T> outEdges = new ArrayList<>();
        for (int i = 0; i < vertexNames.size(); i++) {
            if (adjacencyMatrix.get(vertexIndex).get(i) != 0) {
                outEdges.add(vertexNames.get(i));
            }
        }

        return outEdges;
    }
    /**
     * Проверка, что граф имеет вершину {@code vertex}
     * @param vertex Вершина
     * @return Результат проверки
     */
    public boolean isContain(T vertex) {
        return vertexNames.contains(vertex);
    }

    /**
     * Проверка, что граф содержит хотя бы один отрицательные вес на ребрах.
     * @return Результат проверки
     */
    public boolean containNegativeEdge() {
        for (List<Integer> row : adjacencyMatrix) {
            long countNegativeWeight = row.stream().filter(x -> x < 0).count();
            if (countNegativeWeight != 0) {
                return false;
            }
        }
        return true;
    }

    public int getWeight(T srcVertex, T destVertex) {
        return adjacencyMatrix.get(vertexNames.indexOf(srcVertex)).get(vertexNames.indexOf(destVertex));
    }

    public List<Edge<T>> getAllEdges() {
        List<Edge<T>> result = new ArrayList<>();

        for (int i = 0; i < vertexNames.size(); i++) {
            for (int j = 0; j < vertexNames.size(); j++) {
                if (adjacencyMatrix.get(i).get(j) != 0) {
                    result.add(new Edge<>(vertexNames.get(i), vertexNames.get(j), adjacencyMatrix.get(i).get(j)));
                }
            }
        }

        return result;
    }

    private ArrayList<Integer> arrayListCopies(int n) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(0);
        }
        return result;
    }

    private String stringAdjacencyMatrix() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("   ").append(vertexNames).append("\n");
        for (int i = 0; i < vertexNames.size(); i++) {
            stringBuilder.append(vertexNames.get(i)).append(": ").append(adjacencyMatrix.get(i)).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return stringAdjacencyMatrix();
    }
}
