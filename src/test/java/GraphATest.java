import graph.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class GraphATest {

    @Test
    @DisplayName("Should be true")
    void GraphA_test_0() {
    }


    @Test
    @DisplayName("Equals test")
    void GraphA_equals_0() {
        Graph<Integer> graphA = new MatrixGraph<>();
        graphA.addVertex(1);
        graphA.addVertex(2);
        graphA.addEdge(1, 2, 10);


        Graph<Integer> graphB = new MatrixGraph<>();
        graphB.addVertex(1);
        graphB.addVertex(2);
        graphB.addEdge(1, 2, 10);

        Assertions.assertTrue(graphA.equals(graphB));
    }

    @Test
    @DisplayName("Test graph with 10 elements without edges by DFS")
    void GraphA_dfs_0() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();
        Set<Integer> expectedSet = new HashSet<>(); // Expected set [1] for vertex "1"
        expectedSet.add(1);

        for (int i = 1; i <= 10; i++) {
            graph.addVertex(i);
        }

        Assertions.assertEquals(expectedSet, new HashSet<>(GraphAlgorithmsA.dfs(graph, 1)));
    }

    @Test
    @DisplayName("Test graph with 10 elements with 10 edges by DFS")
    void GraphA_dfs_1() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();
        Set<Integer> expectedSet = new HashSet<>(); // Expected set [1, 10] for vertex "1"
        expectedSet.add(1);
        expectedSet.add(10);

        for (int i = 1; i <= 10; i++) {
            graph.addVertex(i);
        }

        for (int i = 1; i <= 10; i++) {
            graph.addEdge(i, 10);
        }

        Assertions.assertEquals(expectedSet, new HashSet<>(GraphAlgorithmsA.dfs(graph, 1)));
    }

    @Test
    @DisplayName("Test full-linked graph with 1000 elements by DFS")
    void GraphA_dfs_2() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();
        Set<Integer> expectedSet = new HashSet<>(); // Expected set [1, 2, ... 1000] for vertex "1"
        for (int i = 1; i <= 1000; i++) {
            expectedSet.add(i);
            graph.addVertex(i);
        }
        for (int i = 1; i <= 1000; i++) {
            for (int j = 1; j <= 1000; j++) {
                graph.addEdge(i, j, i + j);
            }
        }
        Assertions.assertEquals(expectedSet, new HashSet<>(GraphAlgorithmsA.dfs(graph, 1)));
    }

    @Test
    @DisplayName("Test graph with 10 elements without edges by BFS")
    void GraphA_bfs_0() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();
        Set<Integer> expectedSet = new HashSet<>(); // Expected set [1] for vertex "1"
        expectedSet.add(1);

        for (int i = 1; i <= 10; i++) {
            graph.addVertex(i);
        }

        Assertions.assertEquals(expectedSet, GraphAlgorithmsA.bfs(graph, 1));
    }

    @Test
    @DisplayName("Test graph with 10 elements with 10 edges by BFS")
    void GraphA_bfs_1() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();
        Set<Integer> expectedSet = new HashSet<>(); // Expected set [1, 10] for vertex "1"
        expectedSet.add(1);
        expectedSet.add(10);

        for (int i = 1; i <= 10; i++) {
            graph.addVertex(i);
        }

        for (int i = 1; i <= 10; i++) {
            graph.addEdge(i, 10);
        }

        Assertions.assertEquals(expectedSet, GraphAlgorithmsA.bfs(graph, 1));
    }

    @Test
    @DisplayName("Test full-linked graph with 1000 elements by BFS")
    void GraphA_bfs_2() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();
        Set<Integer> expectedSet = new HashSet<>(); // Expected set [1, 2, ... 1000] for vertex "1"
        for (int i = 1; i <= 1000; i++) {
            expectedSet.add(i);
            graph.addVertex(i);
        }
        for (int i = 1; i <= 1000; i++) {
            for (int j = 1; j <= 1000; j++) {
                graph.addEdge(i, j, i + j);
            }
        }
        Assertions.assertEquals(expectedSet, GraphAlgorithmsA.bfs(graph, 1));
    }

    @Test
    @DisplayName("Test #1 algorithm Dijkstra's")
        // https://www.baeldung.com/wp-content/uploads/2017/01/step8.png
    void GraphA_dijkstra_0() throws GraphException {
        Graph<String> graph = new MatrixGraph<>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");

        graph.addEdge("A", "B", 10);
        graph.addEdge("A", "C", 15);
        graph.addEdge("B", "F", 15);
        graph.addEdge("B", "D", 12);
        graph.addEdge("C", "E", 10);
        graph.addEdge("D", "F", 1);
        graph.addEdge("D", "E", 2);
        graph.addEdge("F", "E", 5);

        Assertions.assertEquals(24, GraphAlgorithmsA.dijkstra(graph, "A", "E"));
    }

    @Test
    @DisplayName("Test #2 algorithm Dijkstra's")
        // https://s3.ap-south-1.amazonaws.com/s3.studytonight.com/tutorials/uploads/pictures/1627292679-103268.png
    void GraphA_dijkstra_1() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();

        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);

        graph.addEdge(0, 4, 21);
        graph.addEdge(0, 3, 18);
        graph.addEdge(1, 2, 7);
        graph.addEdge(1, 4, 6);
        graph.addEdge(2, 1, 7);
        graph.addEdge(3, 0, 18);
        graph.addEdge(3, 4, 11);
        graph.addEdge(3, 5, 7);
        graph.addEdge(4, 0, 21);
        graph.addEdge(4, 1, 6);
        graph.addEdge(4, 3, 11);
        graph.addEdge(4, 5, 10);
        graph.addEdge(5, 4, 10);
        graph.addEdge(5, 3, 7);


        Assertions.assertEquals(34, GraphAlgorithmsA.dijkstra(graph, 0, 2));
    }

    @Test
    @DisplayName("Test algorithm Dijkstra's with non-linked graph")
    void GraphA_dijkstra_3() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();

        for (int i = 1; i <= 10; i++) {
            graph.addVertex(i);
        }

        Assertions.assertEquals(-1, GraphAlgorithmsA.dijkstra(graph, 1, 10));
    }

    @Test
    @DisplayName("Test algorithm Dijkstra's with full-linked graph")
    void GraphA_dijkstra_4() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();

        for (int i = 1; i <= 100; i++) {
            graph.addVertex(i);
        }

        for (int i = 1; i <= 100; i++) {
            for (int j = 1; j <= 100; j++) {
                graph.addEdge(i, j, i + j);
            }
        }

        Assertions.assertEquals(101, GraphAlgorithmsA.dijkstra(graph, 1, 100));
    }

    @Test
    @DisplayName("Test algorithm Dijkstra's with full-linked graph with 1000 elements")
    void GraphA_dijkstra_5() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();

        for (int i = 1; i <= 1000; i++) {
            graph.addVertex(i);
        }

        for (int i = 1; i <= 1000; i++) {
            for (int j = 1; j <= 1000; j++) {
                graph.addEdge(i, j, i + j);
            }
        }

        Assertions.assertEquals(1001, GraphAlgorithmsA.dijkstra(graph, 1, 1000));
    }


    @Test
    @DisplayName("Test algorithm Dijkstra's with full-linked graph with 2000 elements")
    void GraphA_dijkstra_6() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();

        for (int i = 1; i <= 2000; i++) {
            graph.addVertex(i);
        }

        for (int i = 1; i <= 2000; i++) {
            for (int j = 1; j <= 2000; j++) {
                graph.addEdge(i, j, i + j);
            }
        }

        Assertions.assertEquals(2001, GraphAlgorithmsA.dijkstra(graph, 1, 2000));
    }


    @Test
    @DisplayName("Test #1 algorithm Kruskal's")
    void GraphA_kruskal_0() {
        Graph<Integer> graph = new ListGraph<>();

        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);

        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 7, 8);
        graph.addEdge(1, 0, 4);
        graph.addEdge(1, 7, 11);
        graph.addEdge(1, 2, 8);
        graph.addEdge(2, 1, 8);
        graph.addEdge(2, 8, 2);
        graph.addEdge(2, 5, 4);
        graph.addEdge(2, 3, 7);
        graph.addEdge(3, 2, 7);
        graph.addEdge(3, 5, 14);
        graph.addEdge(3, 4, 9);
        graph.addEdge(4, 3, 9);
        graph.addEdge(4, 5, 10);
        graph.addEdge(5, 4, 10);
        graph.addEdge(5, 3, 14);
        graph.addEdge(5, 2, 4);
        graph.addEdge(5, 6, 2);
        graph.addEdge(6, 5, 2);
        graph.addEdge(6, 8, 6);
        graph.addEdge(6, 7, 1);
        graph.addEdge(7, 6, 1);
        graph.addEdge(7, 8, 7);
        graph.addEdge(7, 1, 11);
        graph.addEdge(7, 0, 8);
        graph.addEdge(8, 2, 2);
        graph.addEdge(8, 6, 6);
        graph.addEdge(8, 7, 7);

        Graph<Integer> expected = new ListGraph<>();
        expected.addVertex(0);
        expected.addVertex(1);
        expected.addVertex(2);
        expected.addVertex(3);
        expected.addVertex(4);
        expected.addVertex(5);
        expected.addVertex(6);
        expected.addVertex(7);
        expected.addVertex(8);

        expected.addEdge(0, 1, 4);
        expected.addEdge(0, 7, 8);
        expected.addEdge(2, 8, 2);
        expected.addEdge(2, 5, 4);
        expected.addEdge(2, 3, 7);
        expected.addEdge(3, 4, 9);
        expected.addEdge(5, 6, 2);
        expected.addEdge(6, 7, 1);

        Assertions.assertTrue(GraphAlgorithmsA.kruskal(graph).equals(expected));
        System.out.println(GraphAlgorithmsA.kruskal(graph));
    }

    @Test
    @DisplayName("Test #1 algorithm Floyd-Warshall's")
    void GraphA_floydWarshall_0() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();

        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(0, 1, 5);
        graph.addEdge(1, 2, 3);
        graph.addEdge(2, 3, 1);
        graph.addEdge(0,3, 10);

        List<Edge<Integer>> expected = new ArrayList<>();
        expected.add(new Edge<>(0, 1, 5));
        expected.add(new Edge<>(0, 2, 8));
        expected.add(new Edge<>(0, 3, 9));
        expected.add(new Edge<>(1, 2, 3));
        expected.add(new Edge<>(1, 3, 4));
        expected.add(new Edge<>(2, 3, 1));

        Assertions.assertEquals(expected, GraphAlgorithmsA.floydWarshall(graph));
    }
}
