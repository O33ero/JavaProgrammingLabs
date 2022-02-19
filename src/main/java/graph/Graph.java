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
    private final List<T> vertexNames = new ArrayList<>(); // All vertex names
    private final List<ArrayList<Integer>> adjacencyMatrix = new ArrayList<>();  // Adjacency Matrix (Матрица смежности)


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
