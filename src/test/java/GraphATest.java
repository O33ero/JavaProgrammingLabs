import graph.Graph;
import graph.GraphAlgorithmsA;
import graph.GraphException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class GraphATest {

    @Test
    @DisplayName("Should be true")
    void GraphA_test_0 () {
    }


    @Test
    @DisplayName("Test graph with 10 elements without edges by DFS")
    void GraphA_dfs_0 () throws GraphException {
        Graph<Integer> graph = new Graph<>();
        Set<Integer> expectedSet = new HashSet<>(); // Expected set [1] for vertex "1"
        expectedSet.add(1);

        for(int i = 1; i <= 10; i++) {
            graph.addVertex(i);
        }

        Assertions.assertEquals(expectedSet, GraphAlgorithmsA.dfs(graph, 1));
    }

    @Test
    @DisplayName("Test graph with 10 elements with 10 edges by DFS")
    void GraphA_dfs_1 () throws GraphException {
        Graph<Integer> graph = new Graph<>();
        Set<Integer> expectedSet = new HashSet<>(); // Expected set [1, 10] for vertex "1"
        expectedSet.add(1);
        expectedSet.add(10);

        for(int i = 1; i <= 10; i++) {
            graph.addVertex(i);
        }

        for(int i = 1; i <= 10; i++) {
            graph.addEdge(i, 10);
        }

        Assertions.assertEquals(expectedSet, GraphAlgorithmsA.dfs(graph, 1));
    }

    @Test
    @DisplayName("Test full-linked graph with 1000 elements by DFS")
    void GraphA_dfs_2 () throws GraphException {
        Graph<Integer> graph = new Graph<>();
        Set<Integer> expectedSet = new HashSet<>(); // Expected set [1, 2, ... 1000] for vertex "1"
        for(int i = 1; i <= 1000; i++) {
            expectedSet.add(i);
            graph.addVertex(i);
        }
        for(int i = 1; i <= 1000; i++) {
            for (int j = 1; j <= 1000; j++) {
                graph.addEdge(i, j, i + j);
            }
        }
        Assertions.assertEquals(expectedSet, GraphAlgorithmsA.dfs(graph, 1));
    }

    @Test
    @DisplayName("Test graph with 10 elements without edges by BFS")
    void GraphA_bfs_0 () throws GraphException {
        Graph<Integer> graph = new Graph<>();
        Set<Integer> expectedSet = new HashSet<>(); // Expected set [1] for vertex "1"
        expectedSet.add(1);

        for(int i = 1; i <= 10; i++) {
            graph.addVertex(i);
        }

        Assertions.assertEquals(expectedSet, GraphAlgorithmsA.bfs(graph, 1));
    }

    @Test
    @DisplayName("Test graph with 10 elements with 10 edges by BFS")
    void GraphA_bfs_1 () throws GraphException {
        Graph<Integer> graph = new Graph<>();
        Set<Integer> expectedSet = new HashSet<>(); // Expected set [1, 10] for vertex "1"
        expectedSet.add(1);
        expectedSet.add(10);

        for(int i = 1; i <= 10; i++) {
            graph.addVertex(i);
        }

        for(int i = 1; i <= 10; i++) {
            graph.addEdge(i, 10);
        }

        Assertions.assertEquals(expectedSet, GraphAlgorithmsA.bfs(graph, 1));
    }

    @Test
    @DisplayName("Test full-linked graph with 1000 elements by BFS")
    void GraphA_bfs_2 () throws GraphException {
        Graph<Integer> graph = new Graph<>();
        Set<Integer> expectedSet = new HashSet<>(); // Expected set [1, 2, ... 1000] for vertex "1"
        for(int i = 1; i <= 1000; i++) {
            expectedSet.add(i);
            graph.addVertex(i);
        }
        for(int i = 1; i <= 1000; i++) {
            for (int j = 1; j <= 1000; j++) {
                graph.addEdge(i, j, i + j);
            }
        }
        Assertions.assertEquals(expectedSet, GraphAlgorithmsA.bfs(graph, 1));
    }

    @Test
    @DisplayName("Test #1 algorithm Dijkstra's") // https://www.baeldung.com/wp-content/uploads/2017/01/step8.png
    void GraphA_dijkstra_0() throws GraphException {
        Graph<String> graph = new Graph<>();

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
        graph.addEdge("C", "E",10);
        graph.addEdge("D", "F", 1);
        graph.addEdge("D", "E", 2);
        graph.addEdge("F", "E", 5);

        Assertions.assertEquals(24, GraphAlgorithmsA.dijkstra(graph, "A", "E"));
    }

    @Test
    @DisplayName("Test #2 algorithm Dijkstra's") // https://s3.ap-south-1.amazonaws.com/s3.studytonight.com/tutorials/uploads/pictures/1627292679-103268.png
    void GraphA_dijkstra_1() throws GraphException {
        Graph<Integer> graph = new Graph<>();

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
        Graph<Integer> graph = new Graph<>();

        for(int i = 1; i <= 10; i++) {
            graph.addVertex(i);
        }

        Assertions.assertEquals(Integer.MAX_VALUE, GraphAlgorithmsA.dijkstra(graph, 1, 10));
    }

    @Test
    @DisplayName("Test algorithm Dijkstra's with full-linked graph")
    void GraphA_dijkstra_4() throws GraphException {
        Graph<Integer> graph = new Graph<>();

        for(int i = 1; i <= 100; i++) {
            graph.addVertex(i);
        }

        for(int i = 1; i <= 100; i++) {
            for (int j = 1; j <= 100; j++) {
                graph.addEdge(i, j, i + j);
            }
        }

        Assertions.assertEquals(101, GraphAlgorithmsA.dijkstra(graph, 1, 100));
    }

    @Test
    @DisplayName("Test algorithm Dijkstra's with full-linked graph with 1000 elements")
    void GraphA_dijkstra_5() throws GraphException {
        Graph<Integer> graph = new Graph<>();

        for(int i = 1; i <= 1000; i++) {
            graph.addVertex(i);
        }

        for(int i = 1; i <= 1000; i++) {
            for (int j = 1; j <= 1000; j++) {
                graph.addEdge(i, j, i + j);
            }
        }

        Assertions.assertEquals(1001, GraphAlgorithmsA.dijkstra(graph, 1, 1000));
    }


    @Test
    @DisplayName("Test algorithm Dijkstra's with full-linked graph with 5000 elements")
    void GraphA_dijkstra_6() throws GraphException {
        Graph<Integer> graph = new Graph<>();

        for(int i = 1; i <= 5000; i++) {
            graph.addVertex(i);
        }

        for(int i = 1; i <= 5000; i++) {
            for (int j = 1; j <= 5000; j++) {
                graph.addEdge(i, j, i + j);
            }
        }

        Assertions.assertEquals(5001, GraphAlgorithmsA.dijkstra(graph, 1, 5000));
    }
}
