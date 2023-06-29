import java.util.function.Consumer;

public interface Tree<T extends Comparable<T>> {
    /**
     * Checks if the tree contains a node with the given value.
     *
     * @param value the value to search for
     * @return true if the tree contains a node with the given value, false otherwise
     */
    boolean contains(T value);

    /**
     * Finds the node with the given value in the tree.
     *
     * @param value the value to search for
     * @return the node with the given value, or null if not found
     */
    Node<T> find(T value);

    /**
     * Counts the number of nodes in the tree with the given value.
     *
     * @param value the value to count
     * @return the number of nodes with the given value
     */
    int count(T value);

    /**
     * Traverses the tree in pre-order and performs an action on each node.
     *
     * @param action the action to perform on each node
     */
    void preOrder(Consumer<T> action);

    /**
     * Traverses the tree in-order and performs an action on each node.
     *
     * @param action the action to perform on each node
     */
    void inOrder(Consumer<T> action);

    /**
     * Traverses the tree in post-order and performs an action on each node.
     *
     * @param action the action to perform on each node
     */
    void postOrder(Consumer<T> action);

    /**
     * Returns the depth of the tree.
     *
     * @return the depth of the tree
     */
    int getDepth();

    /**
     * Prints the tree to the console.
     */
    void printTree();

    /**
     * Searches for the node with the given value in the tree.
     *
     * @param value the value to search for
     * @return the node with the given value, or null if not found
     */
    Node<T> search(T value);

    /**
     * Returns the minimum node in the tree.
     *
     * @return the minimum node in the tree, or null if the tree is empty
     */
    Node<T> getMin();

    /**
     * Returns the maximum node in the tree.
     *
     * @return the maximum node in the tree, or null if the tree is empty
     */
    Node<T> getMax();

    /**
     * Returns the successor node of the given node.
     *
     * @param node the node to find the successor of
     * @return the successor node of the given node, or null if the node has no successor
     */
    Node<T> getSuccessor(Node<T> node);

    /**
     * Returns the predecessor node of the given node.
     *
     * @param node the node to find the predecessor of
     * @return the predecessor node of the given node, or null if the node has no predecessor
     */
    Node<T> getPredecessor(Node<T> node);

    /**
     * Inserts the given value into the tree.
     *
     * @param value the value to insert
     * @return true if the value was inserted successfully, false if the value already exists in the tree
     */
    boolean insert(T value);

    /**
     * Deletes the node with the given value from the tree.
     *
     * @param value the value to delete
     * @return true if the node was deleted successfully, false if the node was not found in the tree
     */
    boolean delete(T value);
}