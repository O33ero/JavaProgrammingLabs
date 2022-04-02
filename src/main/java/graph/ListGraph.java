package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Реализация графа через списки смежности.
 * @param <T> Тип данных имен вершин
 */
public class ListGraph<T> extends Graph<T>{
    private final List<T> vertexNames = new ArrayList<>();
    private final List<ArrayList<Node<T>>> adjacencyList = new ArrayList<>();

    private static class Node<T> {
        public Node(T vertexName, int weight) {
            this.name = vertexName;
            this.weight = weight;
        }
        T name;
        int weight;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node<?> node = (Node<?>) o;

            if (weight != node.weight) return false;
            return Objects.equals(name, node.name);
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + weight;
            return result;
        }

        @Override
        public String toString() {
            return  "{" +
                    name +
                    " : " +
                    weight +
                    '}';
        }
    }

    public ListGraph() {
        // Default constructor
    }

    public ListGraph(Graph<T> graph) {
        for(T vertex : graph.getVertexNames()) {
            this.addVertex(vertex);
        }

        for(Edge<T> edge : graph.getAllEdges()) {
            this.addEdge(edge.src, edge.dest, edge.weight);
        }
    }


    @Override
    public boolean addVertex(T vertexName) {
        if(vertexNames.contains(vertexName)) {
            return false;
        }
        vertexNames.add(vertexName);
        adjacencyList.add(new ArrayList<>());

        return true;
    }

    @Override
    public void addEdge(T from, T to) {
        addEdge(from, to, 1);
    }

    @Override
    public void addEdge(T from, T to, int weight) {
        if (!vertexNames.contains(from)) {
            return;
        }
        if (!vertexNames.contains(to)) {
            return;
        }
        int fromIndex = vertexNames.indexOf(from);
        adjacencyList.get(fromIndex).add(new Node<>(to, weight));
    }

    @Override
    public void removeEdge(T from, T to) {
        int fromIndex = vertexNames.indexOf(from);
        List<Node<T>> adjList = adjacencyList.get(fromIndex);
        adjacencyList.get(fromIndex).remove(getNodeIndex(adjList, to));
    }

    @Override
    public void removeVertex(T vertex) {
        int vertexIndex = vertexNames.indexOf(vertex);
        for(List<Node<T>> list : adjacencyList) {
            int index = getNodeIndex(list, vertex);
            if (index != -1) {
                list.remove(index);
            }
        }
        adjacencyList.remove(vertexIndex);
        vertexNames.remove(vertexIndex);
    }

    @Override
    public int vertexCount() {
        return vertexNames.size();
    }

    @Override
    public int edgeCount() {
        return adjacencyList.stream().map(ArrayList::size).reduce(0, Integer::sum);
    }

    @Override
    public List<T> getInEdges(T vertex) {
        List<T> result = new ArrayList<>();

        for(List<Node<T>> list : adjacencyList) {
            int index = getNodeIndex(list, vertex);
            if (index != -1) {
                result.add(list.get(index).name);
            }
        }
        return result;
    }

    @Override
    public List<T> getOutEdges(T vertex) {
        List<T> result = new ArrayList<>();
        for (Node<T> node : adjacencyList.get(vertexNames.indexOf(vertex))) {
            result.add(node.name);
        }
        return result;
    }

    @Override
    public boolean isContain(T vertex) {
        return vertexNames.contains(vertex);
    }

    @Override
    public boolean containNegativeEdge() {
        for (List<Node<T>> list : adjacencyList) {
            for(Node<T> node : list) {
                if (node.weight < 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getWeight(T from, T to) {
        List<Node<T>> vertexList = adjacencyList.get(vertexNames.indexOf(from));
        int nodeIndex = getNodeIndex(vertexList, to);
        return nodeIndex != -1 ? vertexList.get(nodeIndex).weight : 0;
    }

    @Override
    public List<Edge<T>> getAllEdges() {
        List<Edge<T>> result = new ArrayList<>();
        for(int i = 0; i < adjacencyList.size(); i++) {
            T root = vertexNames.get(i);
            List<Node<T>> vertexList = adjacencyList.get(i);
            for(Node<T> node : vertexList) {
                result.add(new Edge<>(root, node.name, node.weight));
            }
        }

        return result;
    }

    @Override
    public List<Edge<T>> getEdges(T vertex) {
        List<Edge<T>> result = new ArrayList<>();

        int indexVertex = vertexNames.indexOf(vertex);
        List<Node<T>> vertexList = adjacencyList.get(indexVertex);
        for (Node<T> node : vertexList) {
            result.add(new Edge<>(vertex, node.name, node.weight));
        }
        return result;
    }

    @Override
    public List<T> getVertexNames() {
        return vertexNames;
    }

    @Override
    public Graph<T> getNewInstance() {
        return new ListGraph<>();
    }

    @Override
    public String toString() {
        return stringListGraph();
    }

    private String stringListGraph() {
        StringBuilder strBuilder = new StringBuilder();
        for(int i = 0; i < adjacencyList.size(); i++) {
            T root = vertexNames.get(i);
            List<Node<T>> vertexList = adjacencyList.get(i);
            strBuilder.append(root).append(" -> ").append(vertexList).append("\n");
        }
        return strBuilder.toString();
    }

    private int getNodeIndex(List<Node<T>> list, T vertex) {
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).name.equals(vertex)) {
                return i;
            }
        }
        return -1;
    }
}
