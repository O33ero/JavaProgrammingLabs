import graph.Graph;
import graph.GraphAlgorithmsB;
import graph.GraphException;
import graph.MatrixGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class GraphBTest {

    @Test
    @DisplayName("Should be true")
    void GraphB_test_0() {
    }

    @Test
    @DisplayName("Tarjan algorithm. Simple test")
    void GraphB_tarjan_0() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();

        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);

        graph.addEdge(0, 2);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);

        List<Integer> expect = new ArrayList<>();
        expect.add(0);
        expect.add(1);
        expect.add(2);
        expect.add(3);
        expect.add(4);

        Assertions.assertEquals(expect, GraphAlgorithmsB.tarjan(graph));
    }


    @Test
    @DisplayName("Tarjan algorithm. Simple test in shuffle order")
    void GraphB_tarjan_1() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();
        graph.addVertex(3);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(4);
        graph.addVertex(2);

        graph.addEdge(0, 2);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);

        List<Integer> expect = new ArrayList<>();
        expect.add(0);
        expect.add(1);
        expect.add(2);
        expect.add(3);
        expect.add(4);

        Assertions.assertEquals(expect, GraphAlgorithmsB.tarjan(graph));
    }

    @Test
    @DisplayName("Tarjan algorithm. Test with loop")
    void GraphB_tarjan_2() {
        Graph<Integer> graph = new MatrixGraph<>();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);

        graph.addEdge(0, 2);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);

        graph.addEdge(1, 1); // loop

        Assertions.assertThrows(GraphException.class, () -> GraphAlgorithmsB.tarjan(graph));
    }
}
