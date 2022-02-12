import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pqueue.PriorityQueue;

import java.util.ArrayList;
import java.util.LinkedList;

class PriorityQueueTest {

    @Test
    @DisplayName("Should be true")
    void PriorityQueue_Success_0 () {
    }

    @Test
    @DisplayName("No parameters constructor and constructor with initialCapacity parameter")
    void PriorityQueue_Constructors_0() {
        PriorityQueue<Integer> pq0 = new PriorityQueue<>();
        PriorityQueue<Integer> pq1 = new PriorityQueue<>(100);
    }

    @Test
    @DisplayName("Constructors with listImplementation as parameter")
    void PriorityQueue_Constructors_1() {
        PriorityQueue<Integer> pq0 = new PriorityQueue<>(ArrayList.class);
        PriorityQueue<Integer> pq1 = new PriorityQueue<>(LinkedList.class);
    }

    @Test
    @DisplayName("LinkedList has not constructor with initialCapacity parameter, expected ")
    void ProrityQueue_Constructors_2() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new PriorityQueue<>(10, LinkedList.class)
            );
    }

}
