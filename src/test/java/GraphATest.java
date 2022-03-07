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
}
