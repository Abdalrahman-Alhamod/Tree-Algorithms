import java.util.Objects;

public class Node<T extends Comparable<T>> {
    private T value; // The value stored in the node
    private Node<T> parent; // Pointer to the parent node
    private Node<T> left; // Pointer to the left child node
    private Node<T> right; // Pointer to the right child node
    private int height; // The height of the node in the tree

    /**
     * Constructs a new node with the given value.
     *
     * @param value the value to store in the node
     */
    public Node(T value) {
        Objects.requireNonNull(value, "Value cannot be null");
        this.value = value;
        parent = null;
        left = null;
        right = null;
        height = 0;
    }

    /**
     * Returns the value stored in the node.
     *
     * @return the value stored in the node
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the value stored in the node.
     *
     * @param value the value to store in the node
     */
    public void setValue(T value) {
        Objects.requireNonNull(value, "Value cannot be null");
        this.value = value;
    }

    /**
     * Returns a pointer to the parent node.
     *
     * @return a pointer to the parent node
     */
    public Node<T> getParent() {
        return parent;
    }

    /**
     * Sets the parent node.
     *
     * @param parent a pointer to the parent node
     */
    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    /**
     * Returns a pointer to the left child node.
     *
     * @return a pointer to the left child node
     */
    public Node<T> getLeft() {
        return left;
    }

    /**
     * Sets the left child node.
     *
     * @param left a pointer to the left child node
     */
    public void setLeft(Node<T> left) {
        this.left = left;
        if (left != null) {
            left.setParent(this);
        }
        updateHeight();
    }

    /**
     * Returns a pointer to the right child node.
     *
     * @return a pointer to the right child node
     */
    public Node<T> getRight() {
        return right;
    }

    /**
     * Sets the right child node.
     *
     * @param right a pointer to the right child node
     */
    public void setRight(Node<T> right) {
        this.right = right;
        if (right != null) {
            right.setParent(this);
        }
        updateHeight();
    }

    /**
     * Returns the height of the node.
     *
     * @return the height of the node
     */
    public int getHeight() {
        return height;
    }

    /**
     * Updates the height of the node based on the heights of its children.
     */
    public void updateHeight() {
        int leftHeight = (left == null) ? -1 : left.getHeight();
        int rightHeight = (right == null) ? -1 : right.getHeight();
        height = Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Gets the balance factor of the node. The balance factor is the difference
     * between the heights of the left and right subtrees.
     *
     * @return the balance factor of the node
     */
    public int getBalanceFactor() {
        int leftHeight = (left == null) ? -1 : left.height;
        int rightHeight = (right == null) ? -1 : right.height;
        return leftHeight - rightHeight;
    }
    
}