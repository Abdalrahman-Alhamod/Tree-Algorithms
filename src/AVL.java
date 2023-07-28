import java.util.Stack;

/**
 * AVL Tree implementation that inherits from BST class.
 *
 * @param <T> the data type of the elements in the tree
 */
public class AVL<T extends Comparable<T>> extends BST<T> {
    /**
     * Constructs a new AVL tree with no elements.
     */
    public AVL() {
        super();
    }

    /**
     * Constructs a AVL tree with the given root node.
     *
     * @param root The root node of the AVL tree.
     */
    public AVL(Node<T> root) {
        super(root);
    }

    /**
     * Inserts an element into the AVL tree.
     *
     * @param value the value to insert into the AVL tree
     * @return true if the value was successfully inserted, false otherwise
     * @implNote This method has a time complexity of O(log(n))
     */
    @Override
    public boolean insert(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        if (useRecursiveApproach) {
            int beforeInsertSize = size;
            root = insertRecursive(root, value);
            return size != beforeInsertSize;
        } else {
            return insertIterative(root, value);
        }
    }

    /**
     * Helper method to recursively insert a value into the AVL tree.
     *
     * @param node  the root of the subtree to insert the value into
     * @param value the value to insert
     * @return the root of the updated subtree
     * @implNote This method has a time complexity of O(log(n))
     */
    private Node<T> insertRecursive(Node<T> node, T value) {
        if (node == null) {
            size++;
            return new Node<>(value);
        }
        if (value.compareTo(node.getValue()) < 0) {
            Node<T> leftChild = insertRecursive(node.getLeft(), value);
            node.setLeft(leftChild);
        } else if (value.compareTo(node.getValue()) > 0) {
            Node<T> rightChild = insertRecursive(node.getRight(), value);
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
     * Helper method to iteratively insert a value into the AVL tree.
     *
     * @param node  the root of the subtree to insert the value into
     * @param value the value to insert
     * @return the root of the updated subtree
     * @implNote This method has a time complexity of O(log(n))
     */
    public boolean insertIterative(Node<T> node, T value) {
        Node<T> newNode = new Node<>(value);
        if (node == null) {
            root = newNode;
            size++;
            return true;
        }

        Stack<Node<T>> stack = new Stack<>();
        Node<T> current = node;

        while (true) {
            stack.push(current);
            int cmp = value.compareTo(current.getValue());

            if (cmp == 0) {
                // The value already exists in the tree, so return current
                return false;
            } else if (cmp < 0) {
                if (current.getLeft() == null) {
                    current.setLeft(newNode);
                    break;
                } else {
                    current = current.getLeft();
                }
            } else {
                if (current.getRight() == null) {
                    current.setRight(newNode);
                    break;
                } else {
                    current = current.getRight();
                }
            }
        }
        size++;
        // Update heights and perform rotations
        while (!stack.isEmpty()) {
            current = stack.pop();
            current.updateHeight();
            int balanceFactor = current.getBalanceFactor();

            if (balanceFactor > 1) {
                if (value.compareTo(current.getLeft().getValue()) < 0) {
                    current = rightRotate(current);
                } else {
                    current.setLeft(leftRotate(current.getLeft()));
                    current = rightRotate(current);
                }
            } else if (balanceFactor < -1) {
                if (value.compareTo(current.getRight().getValue()) > 0) {
                    current = leftRotate(current);
                } else {
                    current.setRight(rightRotate(current.getRight()));
                    current = leftRotate(current);
                }
            }

            if (!stack.isEmpty()) {
                Node<T> parent = stack.peek();
                if (parent.getLeft() == current) {
                    parent.setLeft(current);
                } else {
                    parent.setRight(current);
                }
            } else {
                root = current;
            }
        }

        return true;
    }

    /**
     * Deletes an element from the AVL tree.
     *
     * @param value the value to delete
     * @return true if the value was successfully deleted, false otherwise
     * @implNote This method has a time complexity of O(log(n))
     */
    @Override
    public boolean delete(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        if (find(value) == null)
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
     * @implNote This method has a time complexity of O(log(n))
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
     * @implNote This method has a time complexity of O(1)
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
     * @implNote This method has a time complexity of O(1)
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