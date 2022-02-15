package btreemap;


import java.util.Arrays;
import java.util.Comparator;

public class Node<T extends Comparable<T>> {

        private T[] keyList = null;
        private int keysSize = 0;
        private Node<T>[] childList = null;
        private int childrenSize = 0;
        private Comparator<Node<T>> comparator = new Comparator<Node<T>>() {
            @Override
            public int compare(Node<T> arg0, Node<T> arg1) {
                return arg0.getKey(0).compareTo(arg1.getKey(0));
            }
        };

        protected Node<T> parent = null;

        public Node(Node<T> parent, int tParameter) {
            this.parent = parent;
            this.keyList = (T[]) new Comparable[2 * tParameter + 1];
            this.keysSize = 0;
            this.childList = new Node[2 * tParameter + 2];
            this.childrenSize = 0;
        }

        public T getKey(int index) {
            return keyList[index];
        }

        public void addKey(T value) {
            keyList[keysSize++] = value;
            Arrays.sort(keyList, 0, keysSize);
        }


        public int numberOfKeys() {
            return keysSize;
        }

        public int indexOf(T value) {
            for (int i = 0; i < keysSize; i++) {
                if (keyList[i].equals(value))
                    return i;
            }
            return -1;
        }

        public Node<T> getChild(int index) {
            if (index >= childrenSize)
                return null;
            return childList[index];
        }


        public boolean addChild(Node<T> child) {
            child.parent = this;
            childList[childrenSize++] = child;
            Arrays.sort(childList, 0, childrenSize, comparator);
            return true;
        }

        public boolean removeChild(Node<T> child) {
            boolean found = false;
            if (childrenSize == 0)
                return found;
            for (int i = 0; i < childrenSize; i++) {
                if (childList[i].equals(child)) {
                    found = true;
                } else if (found) {
                    // shift the rest of the keys down
                    childList[i - 1] = childList[i];
                }
            }
            if (found) {
                childrenSize--;
                childList[childrenSize] = null;
            }
            return found;
        }


        public int numberOfChildren() {
            return childrenSize;
        }



}
