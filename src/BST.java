/**
 * Binary Search Tree implementation that inherits from BinaryTree class.
 */
public class BST<T extends Comparable<T>> extends BinaryTree<T> {

    /**
     * Inserts an element into the BST.
     *
     * @param value the value to insert into the BST
     */
    @Override
    public boolean insert(T value) {
        root = insert(root, value);
        return true;
    }

    /**
     * Helper method to recursively insert a value into the BST.
     *
     * @param node  the root of the subtree to insert the value into
     * @param value the value to insert
     * @return the root of the updated subtree
     */
    private Node<T> insert(Node<T> node, T value) {
        if (node == null) {
            return new Node<>(value);
        }
        if (value.compareTo(node.getValue()) < 0) {
            Node<T> leftChild = insert(node.getLeft(), value);
            node.setLeft(leftChild);
        } else if (value.compareTo(node.getValue()) > 0) {
            Node<T> rightChild = insert(node.getRight(), value);
            node.setRight(rightChild);
        }
        node.updateHeight();
        return node;
    }

    /**
     * Searches the tree for a node with the specified value and returns it.
     *
     * @param value the value to search for
     * @return the node with the specified value, or null if it is not found
     */
    @Override
    public Node<T> search(T value) {
        return search(root, value);
    }

    /**
     * Helper method to recursively search for a value in the BST.
     *
     * @param node  the root of the subtree to search in
     * @param value the value to search for
     * @return the node with the specified value, or null if it is not found
     */
    private Node<T> search(Node<T> node, T value) {
        if (node == null) {
            return null;
        }
        if (value.compareTo(node.getValue()) < 0) {
            return search(node.getLeft(), value);
        } else if (value.compareTo(node.getValue()) > 0) {
            return search(node.getRight(), value);
        } else {
            return node;
        }
    }

    /**
     * Deletes an element from the BST.
     *
     * @param value the value to delete
     */
    @Override
    public boolean delete(T value) {
        if (value.compareTo(root.getValue()) == 0 || search(value) == null)
            return false;
        root = delete(root, value);
        return true;
    }

    /**
     * Helper method to recursively delete a value from the BST.
     *
     * @param node  the root of the subtree to delete the value from
     * @param value the value to delete
     * @return the root of the updated subtree
     */
    private Node<T> delete(Node<T> node, T value) {
        if (node == null) {
            return null;
        }
        if (value.compareTo(node.getValue()) < 0) {
            Node<T> leftChild = delete(node.getLeft(), value);
            node.setLeft(leftChild);
        } else if (value.compareTo(node.getValue()) > 0) {
            Node<T> rightChild = delete(node.getRight(), value);
            node.setRight(rightChild);
        } else {
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            } else if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else {
                Node<T> temp = findMin(node.getRight());
                node.setValue(temp.getValue());
                Node<T> rightChild = delete(node.getRight(), temp.getValue());
                node.setRight(rightChild);
            }
        }
        node.updateHeight();
        return node;
    }

    /**
     * Finds the minimum value in a subtree.
     *
     * @param node the root of the subtree to find the minimum value in
     * @return the minimum value in the subtree
     */
    protected Node<T> findMin(Node<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    /**
     * Finds the maximum value in a subtree.
     *
     * @param node the root of the subtree to find the maximum value in
     * @return the maximum value in the subtree
     */
    private Node<T> findMax(Node<T> node) {
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }

    /**
     * Returns the node with the minimum value in the tree.
     *
     * @return the node with the minimum value in the tree, or null if the tree is empty
     */
    @Override
    public Node<T> getMin() {
        Node<T> current = root;
        while (current != null && current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    /**
     * Returns the node with the maximum value in the tree.
     *
     * @return the node with the maximum value in the tree, or null if the tree is empty
     */
    @Override
    public Node<T> getMax() {
        Node<T> current = root;
        while (current != null && current.getRight() != null) {
            current = current.getRight();
        }
        return current;
    }

    /**
     * Checks if a dead end exists in the BST.
     *
     * @return true if a dead end exists, false otherwise
     */
    public boolean deadEnd() {
        return deadEnd(root, null, null);
        /*if (root.getValue() instanceof Integer) {
            return deadEnd(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
        } else if (root.getValue() instanceof Character) {
            return deadEnd(root, 'a', 'z');
        } else if (root.getValue() instanceof String) {
            return deadEnd(root, "apple", "zebra");
        } else if (root.getValue() instanceof Float) {
            return deadEnd(root, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }*/
    }

    /**
     * Helper method to recursively check for a dead end in the BST.
     *
     * @param node the root of the subtree to check
     * @param min  the minimum value allowed in the subtree (exclusive)
     * @param max  the maximum value allowed in the subtree (exclusive)
     * @return true if a dead end exists in the subtree, false otherwise
     */
    private boolean deadEnd(Node<T> node, T min, T max) {
        if (node == null) {
            return false;
        }
        T value = node.getValue();
        if ((min != null && value.compareTo(min) <= 0) && (max != null && value.compareTo(max) >= 0)) {
            return true;
        }
        return deadEnd(node.getLeft(), min, value) || deadEnd(node.getRight(), value, max);
    }

    /**
     * Finds the lowest common ancestor of two nodes with the specified values in the BST.
     *
     * @param value1 the value of the first node
     * @param value2 the value of the second node
     * @return the lowest common ancestor of the two nodes, or null if one or both values are not in the BST
     */
    public Node<T> LCA(T value1, T value2) {
        return LCA(root, value1, value2);
    }

    /**
     * Helper method to recursively find the lowest common ancestor of two nodes with the specified values in the BST.
     *
     * @param node   the root of the subtree to search in
     * @param value1 the value of the first node
     * @param value2 the value of the second node
     * @return the lowest common ancestor of the two nodes, or null if one or both values are not in the BST
     */
    private Node<T> LCA(Node<T> node, T value1, T value2) {
        if (node == null) {
            return null;
        }
        if (node.getValue().compareTo(value1) > 0 && node.getValue().compareTo(value2) > 0) {
            return LCA(node.getLeft(), value1, value2);
        }
        if (node.getValue().compareTo(value1) < 0 && node.getValue().compareTo(value2) < 0) {
            return LCA(node.getRight(), value1, value2);
        }
        return node;
    }

    /**
     * Calculates the distance between two nodes with the specified values in the BST.
     *
     * @param value1 the value of the first node
     * @param value2 the value of the second node
     * @return the distance between the two nodes, or -1 if one or both values are not in the BST
     */
    public int calcDistance(T value1, T value2) {
        Node<T> lcaNode = LCA(value1, value2);
        if (lcaNode == null) {
            return -1;
        }
        int distance1 = calcDistanceBetween(lcaNode, value1);
        int distance2 = calcDistanceBetween(lcaNode, value2);
        return distance1 + distance2;
    }

    /**
     * Calculates the distance between a node with the specified value and a given node in the BST.
     *
     * @param node  the node to start the distance calculation from
     * @param value the value of the node to calculate the distance to
     * @return the distance between the two nodes
     */
    private int calcDistanceBetween(Node<T> node, T value) {
        if (node == null) {
            return -1;
        }
        int distance = -1;
        if (node.getValue().equals(value)) {
            distance = 0;
        } else if (node.getValue().compareTo(value) > 0) {
            distance = 1 + calcDistanceBetween(node.getLeft(), value);
        } else {
            distance = 1 + calcDistanceBetween(node.getRight(), value);
        }
        return distance;
    }


}