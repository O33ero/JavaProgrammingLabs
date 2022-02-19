package map;

import java.util.*;

class RBTree<T extends Comparable<T>> {
    private Node<T> root;
    private Comparator<T> comparator = Comparable::compareTo;

    /** Constructors **/
    public RBTree() {
        root = null;
        comparator = Comparable::compareTo;
    }

    public RBTree(T value) {
        root = null;
    }

    public RBTree(RBTree<T> other) {
        comparator = other.comparator;
        if (other.root == null) {
            root = null;
        } else {
            root = new Node<>(other.root.data, Node.Color.BLACK);
            if (other.root.leftChild != null) {
                root.leftChild = new Node<>(other.root.leftChild, other.root.leftChild.color);
            }
            if (other.root.rightChild != null) {
                root.rightChild = new Node<>(other.root.rightChild, other.root.rightChild.color);
            }
        }
    }

    public RBTree(Comparator<T> comparator) {
        root = null;
        this.comparator = comparator;
    }

    public RBTree(T value, Comparator<T> comparator) {
        root = new Node<>(value, Node.Color.BLACK);
        this.comparator = comparator;
    }

    public RBTree(RBTree<T> other, Comparator<T> comparator) {
        this.comparator = comparator;
        root = new Node<>(other.root.data, Node.Color.BLACK);
        if (other.root.leftChild != null) {
            root.leftChild = new Node<>(other.root.leftChild, other.root.leftChild.color);
        }
        if (other.root.rightChild != null) {
            root.rightChild = new Node<>(other.root.rightChild, other.root.rightChild.color);
        }
    }

    /** Package-private **/
    void setRoot(Node<T> root) {
        this.root = root;
    }

    /* Public funcs */

    /**
     * Проверяет на пустоту
     * @return Результат проверки на пустоту
     */
    public boolean isEmpty() {
        return root.data == null;
    }

    /**
     * Вставляет новую пару в дерево
     * @param value Новая пара
     */
    public void insert(T value) {
        insert(new Node<>(value, Node.Color.RED));
    }

    /**
     * Добавляет новый элемент в дерево
     * @param insertionNode Новый узел
     */
    private void insert(Node<T> insertionNode) {
        if (root == null) { // Tree is empty, new key-value pair is new root
            root = new Node<>(insertionNode.data, Node.Color.BLACK);
        }

        Node<T> cur = root;
        while (true) {
            if (comparator.compare(insertionNode.data, cur.data) < 0) { // Checking left child
                if (cur.leftChild == null) {
                    insertionNode.parent = cur;
                    cur.leftChild = insertionNode; // Left child is NULL, we found place for new key-value pair
                    rbBalance(cur.leftChild); // Balancing tree with Red-Black rules
                    break;
                } else {
                    cur = cur.leftChild; // New parent is left child
                    continue;
                }
            }

            if (comparator.compare(insertionNode.data, cur.data) > 0) { // Checking right child
                if (cur.rightChild == null) {
                    insertionNode.parent = cur;
                    cur.rightChild = insertionNode; // Right child is NULL, we found place for new key-value pair
                    rbBalance(cur.rightChild); // Balancing tree with Red-Black rules
                    break;
                } else {
                    cur = cur.rightChild; // New parent is right child
                    continue;
                }
            } else {
                return; // Duplicates not inserting
            }
        }
    }

    public T get(T value) {
        Node<T> cur = root;

        while (cur != null) {
            if (cur.data.equals(value)) {
                return cur.data;
            }

            if (comparator.compare(value, cur.data) < 0) {
                cur = cur.leftChild;
            } else  {
                cur = cur.rightChild;
            }
        }

        return null;
    }


    /**
     * Ребалансировка дерева относительно узла
     * @param node Узел относительной ребалансировки
     */
    private void rbBalance(Node<T> node) { // Балансировка КЧД: https://www.happycoders.eu/algorithms/red-black-tree-java/
        // Случай #1: Новый элемент - корень => Новый элемент должен быть черным
        if (node == root) {
            node.color = (Node.Color.BLACK);
            return;
        }

        // Родитель черный => балансировка не требуется
        Node<T> parent = node.parent;
        if (parent.color == Node.Color.BLACK) {
            return;
        }

        // Случай #2: Родитель нового элемента - корень => Родитель должен быть черным
        Node<T> grandparent = getGrandparent(node);
        if (grandparent == null) {
            parent.color = (Node.Color.BLACK);
            return;
        }

        // Случай #3: Дядя (node.parent.parent.otherChild) и родитель - красные => Изменить цвет родителя, дедушки (node.parent.parent) и дяди (node.parent.parent.otherChild)
        Node<T> uncle = getUncle(node);
        if (uncle != null && uncle.color == Node.Color.RED) {
            parent.color = (Node.Color.BLACK);
            grandparent.color = (Node.Color.RED);
            uncle.color = (Node.Color.BLACK);

            rbBalance(grandparent);
        } else if (parent == grandparent.leftChild) { // Родитель левый потомок от дедушки
            // Случай #4: Дядя черный и новый элемент является левым-правым потомком от дедушки.
            if (node == parent.rightChild) {
                leftRotate(parent);
                parent = node;
            }

            // Случай #5: Дядя черный и новый элемент является левым-левым потомком от дедушки
            rightRotate(grandparent);
            parent.color = (Node.Color.BLACK);
            grandparent.color = (Node.Color.RED);
        } else { // Родитель правый потомок от дедушки
            // Случай #4: Дядя черный и новый элемент является правым-левым потомком от дедушки
            if (node == parent.leftChild) {
                rightRotate(parent);
                parent = node;
            }

            // Случай #5: Дядя черный и новый элемент правый-правый потомок от дедушки
            leftRotate(grandparent);
            parent.color = (Node.Color.BLACK);
            grandparent.color = (Node.Color.RED);
        }


    }

    /**
     * Поворот дерева влево относительно узла
     * @param node Поворот относительно узла
     */
    private void leftRotate(Node<T> node) {
        Node<T> parent = node.parent;
        Node<T> rightChild = node.rightChild;
        node.rightChild = rightChild.leftChild;
        if (rightChild.leftChild != null) {
            rightChild.leftChild.parent = node;
        }
        rightChild.leftChild = node;
        node.parent = rightChild;

        if (parent == null) {
            root = rightChild;
        } else if (parent.leftChild == node) {
            parent.leftChild = rightChild;
        } else if (parent.rightChild == node) {
            parent.rightChild = rightChild;
        } else {
            return;
        }

        if (rightChild != null) {
            rightChild.parent = parent;
        }
    }

    /**
     * Поворот дерева влево относительно узла
     * @param node Поворот относительно узла
     */
    private void rightRotate(Node<T> node) {
        Node<T> parent = node.parent;
        Node<T> leftChild = node.leftChild;
        node.leftChild = leftChild.rightChild;
        if (leftChild.rightChild != null) {
            leftChild.rightChild.parent = node;
        }
        leftChild.rightChild = node;
        node.parent = leftChild;

        if (parent == null) {
            root = leftChild;
        } else if (parent.leftChild == node) {
            parent.leftChild = leftChild;
        } else if (parent.rightChild == node) {
            parent.rightChild = leftChild;
        } else {
            return;
        }

        if (leftChild != null) {
            leftChild.parent = parent;
        }
    }

    /**
     * Возвращает 'Дедушку' (родителя родителя) для узла
     * @param node Относительный узел
     * @return Дедушка относительно узла
     */
    private Node<T> getGrandparent(Node<T> node) {
        if (node.parent == null) {
            return null;
        }
        return node.parent.parent;
    }

    /**
     * Возвращает 'Дядю' (другой потомок родителя родителя) для узла
     * @param node Относительный узел
     * @return Дядя относительно узла
     */
    private Node<T> getUncle(Node<T> node) {
        Node<T> grandparent = node.parent.parent;
        if (grandparent.leftChild == node.parent) {
            return grandparent.rightChild;
        } else if (grandparent.rightChild == node.parent) {
            return grandparent.leftChild;
        } else {
            return null;
        }
    }


    /** Перегрузки **/

    /**
     * Сравнение деревьев. Перебирая все узлы используя рекуррентный подход
     * @param other Сравниваемое дерево
     * @return Резултат сравнения
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;
        if (comparator != null && !comparator.equals(((RBTree<?>) other).comparator)) {
                return false;
        }

        return Node.compareNodes(this.root, ((RBTree<?>) other).root);
    }

    @Override
    public int hashCode() {
        int result = root != null ? root.hashCode() : 0;
        result = 31 * result + (comparator != null ? comparator.hashCode() : 0);
        return result;
    }
}