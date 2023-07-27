/**
 * AVL Tree implementation that inherits from BST class.
 *
 * @param <T> the data type of the elements in the tree
 */
public class AVL<T extends Comparable<T>> extends BST<T> {

    /**
     * Inserts an element into the AVL tree.
     *
     * @param value the value to insert into the AVL tree
     * @return true if the value was successfully inserted, false otherwise
     */
    @Override
    public boolean insert(T value) {
        root = insert(root, value);
        return true;
    }


    /**
     * Helper method to recursively insert a value into the AVL tree.
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
        } else {
            return node;
        }

        // update height of the current node
        node.updateHeight();

        // calculate the balance factor of the current node
        int balanceFactor = node.getBalanceFactor();

        // if the balance factor is greater than 1, then the tree is left-heavy
        if (balanceFactor > 1) {
            // if the left subtree is right-heavy, double rotation is required
            if (node.getLeft().getBalanceFactor() < 0) {
                node.setLeft(leftRotate(node.getLeft()));
            }
            // perform right rotation
            node = rightRotate(node);
        }
        // if the balance factor is less than -1, then the tree is right-heavy
        else if (balanceFactor < -1) {
            // if the right subtree is left-heavy, double rotation is required
            if (node.getRight().getBalanceFactor() > 0) {
                node.setRight(rightRotate(node.getRight()));
            }
            // perform left rotation
            node = leftRotate(node);
        }
        return node;
    }

    /**
     * Deletes an element from the AVL tree.
     *
     * @param value the value to delete
     * @return true if the value was successfully deleted, false otherwise
     */
    @Override
    public boolean delete(T value) {
        if (value.compareTo(root.getValue()) == 0 || find(value) == null)
            return false;
        root = delete(root, value);
        return true;
    }

    /**
     * Helper method to recursively delete a value from the AVL tree.
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
                Node<T> temp = getMin(node.getRight());
                node.setValue(temp.getValue());
                Node<T> rightChild = delete(node.getRight(), temp.getValue());
                node.setRight(rightChild);
            }
        }

        // update height of the current node
        node.updateHeight();

        // calculate the balance factor of the current node
        int balanceFactor = node.getBalanceFactor();

        // if the balance factor is greater than 1, then the tree is left-heavy
        if (balanceFactor > 1) {
            // if the left subtree is right-heavy, double rotation is required
            if (node.getLeft().getBalanceFactor() < 0) {
                node.setLeft(leftRotate(node.getLeft()));
            }
            // perform right rotation
            node = rightRotate(node);
        }
        // if the balance factor is less than -1, then the tree is right-heavy
        else if (balanceFactor < -1) {
            // if the right subtree is left-heavy, double rotation is required
            if (node.getRight().getBalanceFactor() > 0) {
                node.setRight(rightRotate(node.getRight()));
            }
            // perform left rotation
            node = leftRotate(node);
        }
        return node;
    }

    /**
     * Performs a left rotation on the given node.
     *
     * @param node the node to rotate left
     * @return the new root of the subtree
     */
    private Node<T> leftRotate(Node<T> node) {
        Node<T> newRoot = node.getRight();
        node.setRight(newRoot.getLeft());
        newRoot.setLeft(node);
        node.updateHeight();
        newRoot.updateHeight();
        return newRoot;
    }

    /**
     * Performs a right rotation on the given node.
     *
     * @param node the node to rotate right
     * @return the new root of the subtree
     */
    private Node<T> rightRotate(Node<T> node) {
        Node<T> newRoot = node.getLeft();
        node.setLeft(newRoot.getRight());
        newRoot.setRight(node);
        node.updateHeight();
        newRoot.updateHeight();
        return newRoot;
    }
}