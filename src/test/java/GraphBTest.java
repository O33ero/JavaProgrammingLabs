import graph.Graph;
import graph.GraphAlgorithmsB;
import graph.GraphException;
import graph.MatrixGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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


    @Test
    @DisplayName("Fleury’s Algorithm. Test: https://graphonline.ru/?graph=mUImtGrPpVRmTsjO")
    void GraphB_fleury_0() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();
        graph.addVertex(5);
        graph.addVertex(2);
        graph.addVertex(4);
        graph.addVertex(1);
        graph.addVertex(3);

        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 1);

        // 1 -> 2 -> 3 -> 4 -> 5
        System.out.println("Expect: 5 -> 1 -> 2 -> 3 -> 4 -> 5");
        System.out.println("Actual: " + GraphAlgorithmsB.fleury(graph));
        System.out.println();
    }

    @Test
    @DisplayName("Fleury’s Algorithm. Test: https://graphonline.ru/?graph=DyKsursxSUDeLVUk")
    void GraphB_fleury_1() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);


        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 1);

        graph.addEdge(2, 5);

        // 2 -> 3 -> 4 -> 5 -> 1 -> 2 -> 5
        System.out.println("Expect: 2 -> 3 -> 4 -> 5 -> 1 -> 2 -> 5");
        System.out.println("Actual: " + GraphAlgorithmsB.fleury(graph));
        System.out.println();
    }

    @Test
    @DisplayName("Fleury’s Algorithm. Test: https://graphonline.ru/?graph=LcOCAFkkXFGlpPnD")
    void GraphB_fleury_2() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();
        graph.addVertex(8);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(7);
        graph.addVertex(6);

        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 7);
        graph.addEdge(7, 8);
        graph.addEdge(8, 6);
        graph.addEdge(6, 1);
        graph.addEdge(6, 5);
        graph.addEdge(5, 1);

        // 6 -> 1 -> 2 -> 3 -> 4 -> 7 -> 8 -> 6 -> 5 -> 1
        System.out.println("Expect: 6 -> 1 -> 2 -> 3 -> 4 -> 7 -> 8 -> 6 -> 5 -> 1");
        System.out.println("Actual: " + GraphAlgorithmsB.fleury(graph));
        System.out.println();
    }

    @Test
    @DisplayName("Fleury’s Algorithm. Test: https://graphonline.ru/?graph=dzbrMsVGZbXrCtni")
    void GraphB_fleury_3() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);
        graph.addVertex(9);
        graph.addVertex(10);
        graph.addVertex(11);

        graph.addEdge(1, 2);
        graph.addEdge(1, 9);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(4, 7);
        graph.addEdge(5, 1);
        graph.addEdge(5, 7);
        graph.addEdge(6, 1);
        graph.addEdge(6, 5);
        graph.addEdge(7, 8);
        graph.addEdge(7, 10);
        graph.addEdge(8, 6);
        graph.addEdge(8, 11);
        graph.addEdge(9, 6);
        graph.addEdge(10, 4);
        graph.addEdge(10, 8);
        graph.addEdge(11, 10);

        System.out.println("Expect: 1 -> 2 -> 3 -> 4 -> 5 -> 7 -> 8 -> 11 -> 10 -> 4 -> 7 -> 10 -> 8 -> 6 -> 5 -> 1 -> 9 -> 6 -> 1");
        System.out.println("Actual: " + GraphAlgorithmsB.fleury(graph));
        System.out.println();
    }

    @Test
    @DisplayName("Fleury’s Algorithm. Test: https://graphonline.ru/?graph=edkbLGQCMWSFEOTW")
    void GraphB_fleury_4() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();
        graph.addVertex(5);
        graph.addVertex(4);
        graph.addVertex(3);
        graph.addVertex(2);
        graph.addVertex(1);

        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);

        // 1 -> 2 -> 3 -> 4 -> 5
        System.out.println("Expect: 1 -> 2 -> 3 -> 4 -> 5");
        System.out.println("Actual: " + GraphAlgorithmsB.fleury(graph));
        System.out.println();
    }

    @Test
    @DisplayName("Fleury’s Algorithm. Expect exception")
    void GraphB_fleury_5() {
        Graph<Integer> graph = new MatrixGraph<>();
        graph.addVertex(5);
        graph.addVertex(4);
        graph.addVertex(3);
        graph.addVertex(2);
        graph.addVertex(1);

        Assertions.assertThrows(GraphException.class, () -> GraphAlgorithmsB.fleury(graph));
    }


    @Test
    @DisplayName("FindEulerCycle Algorithm. Test: https://graphonline.ru/?graph=xCFoolzKXJeXyLBf")
    void GraphB_findEulerCycle_0() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);

        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 1);

        // 1 -> 2 -> 3 -> 4 -> 5
        System.out.println("Expect: 1 -> 2 -> 3 -> 4 -> 5");
        System.out.println("Actual: " + GraphAlgorithmsB.findEulerCycle(graph));
        System.out.println();
    }

    @Test
    @DisplayName("FindEulerCycle Algorithm. Test: https://graphonline.ru/?graph=dzbrMsVGZbXrCtni")
    void GraphB_findEulerCycle_1() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);
        graph.addVertex(9);
        graph.addVertex(10);
        graph.addVertex(11);

        graph.addEdge(1, 2);
        graph.addEdge(1, 9);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(4, 7);
        graph.addEdge(5, 1);
        graph.addEdge(5, 7);
        graph.addEdge(6, 1);
        graph.addEdge(6, 5);
        graph.addEdge(7, 8);
        graph.addEdge(7, 10);
        graph.addEdge(8, 6);
        graph.addEdge(8, 11);
        graph.addEdge(9, 6);
        graph.addEdge(10, 4);
        graph.addEdge(10, 8);
        graph.addEdge(11, 10);

        System.out.println("Expect: 1 -> 2 -> 3 -> 4 -> 5 -> 1 -> 9 -> 6 -> 5 -> 7 -> 8 -> 11 -> 10 -> 4 -> 7 -> 10 -> 8 -> 6 -> 1");
        System.out.println("Actual: " + GraphAlgorithmsB.findEulerCycle(graph));
        System.out.println();
    }

    @Test
    @DisplayName("FindEulerCycle Algorithm. Test: https://graphonline.ru/?graph=PhCmnmPynClnyoLA")
    void GraphB_findEulerCycle_3() {
        Graph<Integer> graph = new MatrixGraph<>();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);

        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 1);

        graph.addEdge(2, 5);

        // Graph does not contain euler graph
        Assertions.assertThrows(GraphException.class, () ->
                GraphAlgorithmsB.findEulerCycle(graph)
        );
    }

    @Test
    @DisplayName("Kosaraju's algorithm. Test: https://graphonline.ru/?graph=aaKwNXXLQeruYVjg")
    void GraphB_kosaraju_0() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();

        // #3 component
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);

        // #1 component
        graph.addVertex(3);

        // #2 component
        graph.addVertex(4);


        graph.addEdge(0, 2);
        graph.addEdge(2, 1);
        graph.addEdge(1, 0);
        graph.addEdge(0, 3);
        graph.addEdge(3, 4);

        // [0, 1, 2] [3] [4]
        List<ArrayList<Integer>> expect = new ArrayList<>();
        expect.add(new ArrayList<>(Arrays.asList(0, 1, 2)));
        expect.add(new ArrayList<>(Arrays.asList(3)));
        expect.add(new ArrayList<>(Arrays.asList(4)));
        Assertions.assertEquals(expect, GraphAlgorithmsB.kosaraju(graph));
    }

    @Test
    @DisplayName("Kosaraju's algorithm. Test: https://graphonline.ru/?graph=oOaXQYPliayGIdqz")
    void GraphB_kosaraju_1() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();

        // #1 component
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        // #2 component
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);

        // #3 component
        graph.addVertex(7);

        // #4 component
        graph.addVertex(8);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 0);
        graph.addEdge(2, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 6);
        graph.addEdge(6, 4);
        graph.addEdge(7, 6);
        graph.addEdge(7, 8);

        // [0, 1, 2] [4, 5, 7] [7] [8]
        List<ArrayList<Integer>> expect = new ArrayList<>();
        expect.add(new ArrayList<>(Arrays.asList(0, 3, 2, 1)));
        expect.add(new ArrayList<>(Arrays.asList(4, 6, 5)));
        expect.add(new ArrayList<>(Arrays.asList(7)));
        expect.add(new ArrayList<>(Arrays.asList(8)));
        Assertions.assertEquals(expect, GraphAlgorithmsB.kosaraju(graph));
    }

    @Test
    @DisplayName("Kosaraju's algorithm. Test: https://graphonline.ru/?graph=wyWGzrZAUafkKSJy")
    void GraphB_kosaraju_2() throws GraphException {
        Graph<Integer> graph = new MatrixGraph<>();

        // #1 component
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);

        // #2 component
        graph.addVertex(3);

        // #3 component
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);

        // #4 component
        graph.addVertex(7);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);
        graph.addEdge(2, 3);
        graph.addEdge(4, 3);
        graph.addEdge(4, 7);
        graph.addEdge(4, 5);
        graph.addEdge(6, 4);
        graph.addEdge(5, 6);
        graph.addEdge(6, 7);

        // [0, 1, 2] [3] [4, 5, 6] [7]
        List<ArrayList<Integer>> expect = new ArrayList<>();
        expect.add(new ArrayList<>(Arrays.asList(4, 6, 5)));
        expect.add(new ArrayList<>(Arrays.asList(0, 2, 1)));
        expect.add(new ArrayList<>(Arrays.asList(7)));
        expect.add(new ArrayList<>(Arrays.asList(3)));
        Assertions.assertEquals(expect, GraphAlgorithmsB.kosaraju(graph));
    }
}
