package pqueue;

import java.util.ArrayList;
import java.util.List;

public class PriorityQueue<E extends Comparable<E>> {
    private List<E> heap;

    public PriorityQueue() {
        heap = new ArrayList<>();
    }

    public PriorityQueue(int initialCapacity) {
        heap = new ArrayList<>(initialCapacity);
    }

    @SuppressWarnings("unchecked")
    public PriorityQueue(Class<? extends List> listImplementation) {
        try {
            heap = listImplementation.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Failed to create new instance of " + listImplementation.getName() + " Message: " + ex);
        }
    }

    @SuppressWarnings("unchecked")
    public PriorityQueue(int initialCapacity, Class<? extends List> listImplementation) {
        try {
            heap = listImplementation.getDeclaredConstructor(Integer.TYPE).newInstance(initialCapacity);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Failed to create new instance of " + listImplementation.getName() + " Message: " + ex);
        }
    }


}
