import graph.Graph;
import graph.GraphAlgorithmsA;
import graph.GraphException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GraphATest {

    @Test
    @DisplayName("Should be true")
    void GraphA_test_0 () {
    }

    @Test
    @DisplayName("Test dfs")
    void GraphA_dfs_0 () throws GraphException {
        Graph<Integer> graph = new Graph<>();
        for(int i = 1; i <= 3000; i++) {
            graph.addVertex(i);
        }

        for(int i = 1; i <= 3000; i++) {
            for (int j = 1; j <= 3000; j++) {
                graph.addEdge(i, j, i + j);
            }
        }

        Assertions.assertTrue(GraphAlgorithmsA.dfs(graph, 1, 3000));
    }
}
