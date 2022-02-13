import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pqueue.PriorityQueue;

import java.util.ArrayList;
import java.util.LinkedList;

class PriorityQueueTest {

    @Test
    @DisplayName("Should be true")
    void PriorityQueue_Success_0() {
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
    void PriorityQueue_Constructors_2() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new PriorityQueue<>(10, LinkedList.class)
        );
    }

    @Test
    @DisplayName("Test 'isEmpty' function")
    void PriorityQueue_isEmpty_0() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Assertions.assertTrue(pq.isEmpty());
        pq.push(1);
        Assertions.assertFalse(pq.isEmpty());
    }

    @Test
    @DisplayName("Test 'getSize' function")
    void PriorityQueue_getSize_0() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Assertions.assertEquals(0, pq.getSize());
        pq.push(1);
        Assertions.assertEquals(1, pq.getSize());
    }

    @Test
    @DisplayName("Test 'clear' function")
    void PriorityQueue_clear_0() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.push(1);
        Assertions.assertEquals(1, pq.getSize());
        pq.clear();
        Assertions.assertEquals(0, pq.getSize());
    }

    @Test
    @DisplayName("Adding numbers in reverse order.")
    void PriorityQueue_Push_0() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.push(1);
        pq.push(2);
        pq.push(3);
        pq.push(4);
        pq.push(5);
        pq.push(6);

        Assertions.assertEquals(6, pq.pop());
    }

    @Test
    @DisplayName("Test with 30.000.000 elements. Value from 0 to 100.")
    void PriorityQueue_Push_1() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int max = 0;
        for (int i = 0; i < 30000000; i++) {
            int now = Utils.getRandom(0, 100);
            pq.push(now);
            if (now > max) {
                max = now;
            }
        }
        Assertions.assertEquals(max, pq.pop());
    }

    @Test
    @DisplayName("Test with 100.000.000 elements. Value full random.")
    void PriorityQueue_Push_2() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int max = 0;
        for (int i = 0; i < 100000000; i++) {
            int now = Utils.getRandom();
            pq.push(now);
            if (now > max) {
                max = now;
            }
        }
        Assertions.assertEquals(max, pq.pop());
    }

    @Test
    @DisplayName("Test with 50.000 elements. Value from 0 to 100. Implementation with LinkedList.")
    void PriorityQueue_Push_3() {
        PriorityQueue<Integer> pq = new PriorityQueue<>(LinkedList.class);
        int max = 0;
        for (int i = 0; i < 50000; i++) {
            int now = Utils.getRandom(0, 100);
            pq.push(now);
            if (now > max) {
                max = now;
            }
        }
        Assertions.assertEquals(max, pq.pop());
    }

    @Test
    @DisplayName("Test with 100.000 elements. Value full random. Implementation with LinkedList.")
    void PriorityQueue_Push_4() {
        PriorityQueue<Integer> pq = new PriorityQueue<>(LinkedList.class);
        int max = 0;
        for (int i = 0; i < 100000; i++) {
            int now = Utils.getRandom();
            pq.push(now);
            if (now > max) {
                max = now;
            }
        }
        Assertions.assertEquals(max, pq.pop());
    }
}
