import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

/**
 * A binary tree implementation of the Tree interface.
 *
 * @param <T> the type of elements stored in the tree
 */
public class BinaryTree<T extends Comparable<T>> implements Tree<T> {

    protected Node<T> root; // The root node of the tree
    protected int size; // The number of nodes in the tree

    /**
     * Constructs a new binary tree with no elements.
     */
    public BinaryTree() {
        root = null;
        size = 0;
    }

    @Override
    public boolean contains(T value) {
        return find(value) != null;
    }

    /**
     * Finds the node with the specified value in the tree.
     *
     * @param value the value to search for
     * @return the node with the specified value, or null if it is not found
     */
    @Override
    public Node<T> find(T value) {
        return find(root, value);
    }

    /**
     * Recursive helper method for finding the node with the specified value in the tree.
     *
     * @param node  the root node of the current subtree
     * @param value the value to search for
     * @return the node with the specified value in the subtree, or null if it is not found
     */
    private Node<T> find(Node<T> node, T value) {
        if (node == null) {
            return null;
        } else if (node.getValue().equals(value)) {
            return node;
        } else {
            Node<T> left = find(node.getLeft(), value);
            Node<T> right = find(node.getRight(), value);
            if (left != null) {
                return left;
            } else if (right != null) {
                return right;
            } else {
                return null;
            }
        }
    }

    /**
     * Counts the number of occurrences of the specified value in the tree.
     *
     * @param value the value to count
     * @return the number of occurrences of the value in the tree
     */
    @Override
    public int count(T value) {
        return count(root, value);
    }

    /**
     * Recursive helper method for counting the number of occurrences of the specified value in the tree.
     *
     * @param node  the root node of the current subtree
     * @param value the value to count
     * @return the number of occurrences of the value in the subtree
     */
    private int count(Node<T> node, T value) {
        if (node == null) {
            return 0;
        } else {
            int count = 0;
            if (node.getValue().equals(value)) {
                count++;
            }
            count += count(node.getLeft(), value);
            count += count(node.getRight(), value);
            return count;
        }
    }

    /**
     * Performs a pre-order traversal of the tree and applies the specified action to each node.
     *
     * @param action the action to apply to each node
     */
    @Override
    public void preOrder(Consumer<T> action) {
        preOrder(root, action);
    }

    private void preOrder(Node<T> node, Consumer<T> action) {
        if (node != null) {
            action.accept(node.getValue());
            preOrder(node.getLeft(), action);
            preOrder(node.getRight(), action);
        }
    }

    /**
     * Performs an in-order traversal of the tree and applies the specified action to each node.
     *
     * @param action the action to apply to each node
     */
    @Override
    public void inOrder(Consumer<T> action) {
        inOrder(root, action);
    }

    private void inOrder(Node<T> node, Consumer<T> action) {
        if (node != null) {
            inOrder(node.getLeft(), action);
            action.accept(node.getValue());
            inOrder(node.getRight(), action);
        }
    }

    /**
     * Performs a post-order traversal of the tree and applies the specified action to each node.
     *
     * @param action the action to apply to each node
     */
    @Override
    public void postOrder(Consumer<T> action) {
        postOrder(root, action);
    }

    private void postOrder(Node<T> node, Consumer<T> action) {
        if (node != null) {
            postOrder(node.getLeft(), action);
            postOrder(node.getRight(), action);
            action.accept(node.getValue());
        }
    }

    /**
     * Returns the maximum depth of the tree.
     *
     * @return the maximum depth of the tree
     */
    @Override
    public int getDepth() {
        return getDepth(root);
    }

    private int getDepth(Node<T> node) {
        if (node == null) {
            return -1;
        }
        int leftDepth = getDepth(node.getLeft());
        int rightDepth = getDepth(node.getRight());
        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * Prints a graphical representation of the tree to standard output.
     */
    @Override
    public void printTree() {
        printTree(root, 0);
    }

    private void printTree(Node<T> node, int indent) {
        if (node != null) {
            printTree(node.getRight(), indent + 4);
            System.out.printf("%" + indent + "s%s%n", "", node.getValue());
            printTree(node.getLeft(), indent + 4);
        }
    }

    /**
     * Searches the tree for a node with the specified value and returns it.
     *
     * @param value the value to search for
     * @return the node with the specified value, or null if it is not found
     */
    @Override
    public Node<T> search(T value) {
        Stack<Node<T>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node<T> node = stack.pop();
            if (node.getValue().equals(value)) {
                return node;
            } else {
                if (node.getLeft() != null) {
                    stack.push(node.getLeft());
                }
                if (node.getRight() != null) {
                    stack.push(node.getRight());
                }
            }
        }
        return null;
    }


    /**
     * Returns the node with the minimum value in the tree.
     *
     * @return the node with the minimum value in the tree, or null if the tree is empty
     */
    @Override
    public Node<T> getMin() {
        if (root == null) {
            return null;
        }
        return getMin(root);
    }

    /**
     * Recursive helper method for finding the node with the minimum value in the tree.
     *
     * @param node the root node of the current subtree
     * @return the node with the minimum value in the subtree, or null if the subtree is empty
     */
    private Node<T> getMin(Node<T> node) {
        if (node == null) {
            return null;
        } else {
            Node<T> left = getMin(node.getLeft());
            Node<T> right = getMin(node.getRight());
            Node<T> min = node;
            if (left != null && left.getValue().compareTo(min.getValue()) < 0) {
                min = left;
            }
            if (right != null && right.getValue().compareTo(min.getValue()) < 0) {
                min = right;
            }
            return min;
        }
    }

    /**
     * Returns the node with the maximum value in the tree.
     *
     * @return the node with the maximum value in the tree, or null if the tree is empty
     */
    @Override
    public Node<T> getMax() {
        if (root == null) {
            return null;
        }
        return getMax(root);
    }

    /**
     * Recursive helper method for finding the node with the maximum value in the tree.
     *
     * @param node the root node of the current subtree
     * @return the node with the maximum value in the subtree, or null if the subtree is empty
     */
    private Node<T> getMax(Node<T> node) {
        if (node == null) {
            return null;
        } else {
            Node<T> left = getMax(node.getLeft());
            Node<T> right = getMax(node.getRight());
            Node<T> max = node;
            if (left != null && left.getValue().compareTo(max.getValue()) > 0) {
                max = left;
            }
            if (right != null && right.getValue().compareTo(max.getValue()) > 0) {
                max = right;
            }
            return max;
        }
    }

    /**
     * Inserts a new node with the specified value into the tree.
     *
     * @param value the value to insert
     * @return true if the value was inserted, or false if it already exists in the tree
     */
    @Override
    public boolean insert(T value) {
        Node<T> newNode = new Node<>(value);

        // If the tree is empty, set the root node to be the new node
        if (root == null) {
            root = newNode;
            size++;
            return true;
        }

        // Traverse the tree to find the correct position to insert the new node
        Node<T> current = root;
        while (true) {
            int cmp = current.getValue().compareTo(value);
            if (cmp == 0) {
                // The value already exists in the tree, so return false
                return false;
            } else if (cmp > 0) {
                // The value is less than the current node's value, so move to the left subtree
                if (current.getLeft() == null) {
                    current.setLeft(newNode);
                    break;
                } else {
                    current = current.getLeft();
                }
            } else {
                // The value is greater than the current node's value, so move to the right subtree
                if (current.getRight() == null) {
                    current.setRight(newNode);
                    break;
                } else {
                    current = current.getRight();
                }
            }
        }

        // Increment the size of the tree and return true
        size++;
        return true;
    }

    /**
     * Removes the node with the specified value from the tree.
     *
     * @param value the value to remove
     * @return true if the node was found and removed, false otherwise
     */
    @Override
    public boolean delete(T value) {
        Node<T> parent = null;
        Node<T> current = root;
        while (current != null) {
            int cmp = current.getValue().compareTo(value);
            if (cmp == 0) {
                // The node has been found, so remove it
                if (current.getLeft() == null && current.getRight() == null) {
                    // Case 1: node has no children
                    if (parent == null) {
                        root = null;
                    } else if (parent.getLeft() == current) {
                        parent.setLeft(null);
                    } else {
                        parent.setRight(null);
                    }
                } else if (current.getLeft() == null) {
                    // Case 2: node has one child (right)
                    if (parent == null) {
                        root = current.getRight();
                    } else if (parent.getLeft() == current) {
                        parent.setLeft(current.getRight());
                    } else {
                        parent.setRight(current.getRight());
                    }
                } else if (current.getRight() == null) {
                    // Case 2: node has one child (left)
                    if (parent == null) {
                        root = current.getLeft();
                    } else if (parent.getLeft() == current) {
                        parent.setLeft(current.getLeft());
                    } else {
                        parent.setRight(current.getLeft());
                    }
                } else {
                    // Case 3: node has two children
                    Node<T> successor = getSuccessor(current);
                    current.setValue(successor.getValue());
                    delete(successor.getValue());
                }
                size--;
                return true;
            } else if (cmp > 0) {
                // The value to be deleted is less than the value at the current node, so move to the left subtree
                parent = current;
                current = current.getLeft();
            } else {
                // The value to be deleted is greater than the value at the current node, so move to the right subtree
                parent = current;
                current = current.getRight();
            }
        }
        // The value to be deleted was not found in the tree
        return false;
    }

    /**
     * Finds the successor of the specified node in the tree.
     *
     * @param node the node to find the successor of
     * @return the successor of the node
     */
    public Node<T> getSuccessor(Node<T> node) {
        if (node == null) {
            return null;
        }
        if (node.getRight() != null) {
            // If the node has a right subtree, the successor is the node with the minimum value in that subtree
            Node<T> current = node.getRight();
            while (current.getLeft() != null) {
                current = current.getLeft();
            }
            return current;
        } else {
            // If the node does not have a right subtree, we need to go up the tree until we find the first ancestor that is a left child of its parent
            Node<T> current = node;
            Node<T> parent = node.getParent();
            while (parent != null && current == parent.getRight()) {
                current = parent;
                parent = parent.getParent();
            }
            return parent;
        }
    }

    /**
     * Finds the predecessor of the specified node in the tree.
     *
     * @param node the node to find the predecessor of
     * @return the predecessor of the node
     */
    public Node<T> getPredecessor(Node<T> node) {
        if (node == null) {
            return null;
        }
        if (node.getLeft() != null) {
            // If the node has a left subtree, the predecessor is the node with the maximum value in that subtree
            Node<T> current = node.getLeft();
            while (current.getRight() != null) {
                current = current.getRight();
            }
            return current;
        } else {
            // If the node does not have a left subtree, we need to go up the tree until we find the first ancestor that is a right child of its parent
            Node<T> current = node;
            Node<T> parent = node.getParent();
            while (parent != null && current == parent.getLeft()) {
                current = parent;
                parent = parent.getParent();
            }
            return parent;
        }
    }

    /**
     * Returns the number of nodes in the tree.
     *
     * @return the number of nodes in the tree
     */
    public int size() {
        return countNodes(root);
    }

    /**
     * Counts the number of nodes in the subtree rooted at the given node.
     *
     * @param node the root of the subtree
     * @return the number of nodes in the subtree
     */
    private int countNodes(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.getLeft()) + countNodes(node.getRight());
    }

    /**
     * Returns true if the binary tree is strict, i.e., every node in the tree has either two children or no children.
     * Returns false otherwise.
     *
     * @return true if the binary tree is strict, false otherwise
     */
    public boolean isStrict() {
        return isStrict(root);
    }

    /**
     * Returns true if the binary tree rooted at the specified node is strict, i.e., every node in the subtree rooted at
     * the node has either two children or no children. Returns false otherwise.
     *
     * @param node the root of the subtree to check
     * @return true if the subtree is strict, false otherwise
     */
    private boolean isStrict(Node<T> node) {
        if (node == null) {
            // An empty tree is trivially strict
            return true;
        } else if (node.getLeft() == null && node.getRight() == null) {
            // A leaf node is also strict
            return true;
        } else if (node.getLeft() != null && node.getRight() != null) {
            // A node with two children is strict if both its children are strict
            return isStrict(node.getLeft()) && isStrict(node.getRight());
        } else {
            // A node with only one child is not strict
            return false;
        }
    }


    /**
     * Returns true if the binary tree is complete, i.e., all levels of the tree are completely filled except possibly
     * the last level, which is filled from left to right. Returns false otherwise.
     *
     * @return true if the binary tree is complete, false otherwise
     */
    public boolean isComplete() {
        return isComplete(root, 0, size);
    }

    /**
     * Returns true if the binary tree rooted at the specified node is complete, i.e., all levels of the subtree rooted
     * at the node are completely filled except possibly the last level, which is filled from left to right. Returns
     * false otherwise.
     *
     * @param node    the root of the subtree to check
     * @param index   the index of the node in the tree, starting from 0 for the root
     * @param maxsize the maximum number of nodes that can be in the subtree, including the root
     * @return true if the subtree is complete, false otherwise
     */
    private boolean isComplete(Node<T> node, int index, int maxsize) {
        if (node == null) {
            // An empty tree is trivially complete
            return true;
        }
        if (index >= maxsize) {
            // The node is beyond the maximum allowed index, so the tree is not complete
            return false;
        }
        // Recursively check the left and right subtrees
        return isComplete(node.getLeft(), 2 * index + 1, maxsize)
                && isComplete(node.getRight(), 2 * index + 2, maxsize);
    }

    /**
     * Checks whether the binary tree is a full binary tree.
     *
     * @return true if the binary tree is a full binary tree, false otherwise
     */
    public boolean isFull() {
        return isFull(root);
    }

    private boolean isFull(Node<T> node) {
        if (node == null) {
            return true;
        }
        if (node.getLeft() == null && node.getRight() == null) {
            return true;
        }
        if (node.getLeft() != null && node.getRight() != null) {
            return isFull(node.getLeft()) && isFull(node.getRight());
        }
        return false;
    }

    /**
     * Returns the maximum height of the tree.
     *
     * @return the maximum height of the tree
     */
    public int getMaxHeight() {
        return size() - 1;
    }

    /**
     * Returns the minimum height of the tree.
     *
     * @return the minimum height of the tree
     */
    public int getMinHeight() {
        return (int) Math.floor(Math.log(size() + 1) / Math.log(2));
    }

    /**
     * Returns the minimum number of nodes in a binary tree of the same height as the tree.
     *
     * @return the minimum number of nodes in a binary tree of the same height as the tree
     */
    public int getMinNodes() {
        return (int) Math.pow(2, getMinHeight()) - 1;
    }

    /**
     * Returns the number of nodes in the complete binary tree rooted at the specified node.
     *
     * @param <TP> the type of the value stored in the node
     * @param node the root of the tree
     * @return the number of nodes in the tree
     */
    public <TP extends Comparable<T>> int countNodesCompleteBT(Node<T> node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = getLeftHeight(node);
        int rightHeight = getRightHeight(node);
        if (leftHeight == rightHeight) {
            // The tree is full and complete
            return (1 << (leftHeight + 1)) - 1;
        } else {
            // The tree is not full and complete
            int leftCount = countNodesCompleteBT(node.getLeft());
            int rightCount = countNodesCompleteBT(node.getRight());
            return 1 + leftCount + rightCount;
        }
    }

    /**
     * Returns the height of the left subtree of the given node.
     *
     * @param <TP> the type of the value stored in the node
     * @param node the root of the subtree
     * @return the height of the subtree
     */
    private <TP extends Comparable<T>> int getLeftHeight(Node<T> node) {
        int height = 0;
        while (node.getLeft() != null) {
            height++;
            node = node.getLeft();
        }
        return height;
    }

    /**
     * Returns the height of the right subtree of the given node.
     *
     * @param <TP> the type of the value stored in the node
     * @param node the root of the subtree
     * @return the height of the subtree
     */
    private <TP extends Comparable<T>> int getRightHeight(Node<T> node) {
        int height = 0;
        while (node.getRight() != null) {
            height++;
            node = node.getRight();
        }
        return height;
    }

    /**
     * Returns the number of leaves (i.e., nodes with no children) in the tree.
     *
     * @return the number of leaves in the tree
     */
    public int countLeaves() {
        return countLeaves(root);
    }

    private int countLeaves(Node<T> node) {
        if (node == null) {
            return 0;
        } else if (node.getLeft() == null && node.getRight() == null) {
            return 1;
        } else {
            return countLeaves(node.getLeft()) + countLeaves(node.getRight());
        }
    }

    /**
     * Returns the number of non-leaf nodes (i.e., nodes with at least one child) in the tree.
     *
     * @return the number of non-leaf nodes in the tree
     */
    public int countNonLeaves() {
        return countNonLeaves(root);
    }

    private int countNonLeaves(Node<T> node) {
        if (node == null || (node.getLeft() == null && node.getRight() == null)) {
            return 0;
        } else {
            return 1 + countNonLeaves(node.getLeft()) + countNonLeaves(node.getRight());
        }
    }

    /**
     * Checks if the tree is a binary search tree.
     *
     * @return true if the tree is a binary search tree, false otherwise
     */
    public boolean isBST() {
        if (root == null) {
            return true;
        } else {
            return isBST(root, null, null);
        }
        /*if (root.getValue() instanceof Integer) {
            return isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
        } else if (root.getValue() instanceof Character) {
            return isBST(root, 'a', 'z');
        } else if (root.getValue() instanceof String) {
            return isBST(root, "apple", "zebra");
        } else if (root.getValue() instanceof Float) {
            return isBST(root, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }*/
    }

    /**
     * Recursively checks if the subtree rooted at the specified node is a binary search tree.
     *
     * @param node the root of the subtree to check
     * @param min  the minimum value that nodes in the subtree can have
     * @param max  the maximum value that nodes in the subtree can have
     * @return true if the subtree is a binary search tree, false otherwise
     */
    private boolean isBST(Node<T> node, T min, T max) {
        if (node == null) {
            return true;
        }
        if ((min != null && node.getValue().compareTo(min) <= 0) ||
                (max != null && node.getValue().compareTo(max) >= 0)) {
            return false;
        }
        return isBST(node.getLeft(), min, node.getValue()) && isBST(node.getRight(), node.getValue(), max);
    }

    /**
     * Prints a graphical representation of the binary tree to standard output.
     * The tree is printed in a top-down fashion, with each level of the tree
     * indented by 4 spaces and nodes printed in the order right, root, left.
     *
     * @param root the root node of the tree to print
     */
    public String printTree(Node<T> root) {
        // Clear the console before printing the tree
//        try {
//            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

        // Get the maximum depth of the tree
        int maxLevel = getDepth(root) + 1;

        // Create a StringBuilder to store the string representation of the tree
        StringBuilder sb = new StringBuilder();

        // Print the nodes of the tree in a vertical format
        printNodeInternal(Collections.singletonList(root), 1, maxLevel, sb);

        // Returning  the StringBuilder to the toString function
        return sb.toString();
    }

    /**
     * Helper method that prints the nodes of the binary tree to a StringBuilder
     * in a vertical format.
     *
     * @param nodes    the list of nodes to print
     * @param level    the current level of the tree
     * @param maxLevel the maximum level of the tree
     * @param sb       the StringBuilder used to store the string representation of the tree
     */
    private static <T extends Comparable<T>> void printNodeInternal(List<Node<T>> nodes, int level, int maxLevel, StringBuilder sb) {
        // Stop printing if the list of nodes is empty or all elements are null
        if (nodes.isEmpty() || isAllElementsNull(nodes))
            return;

        // Calculate the number of edge lines and spaces for this level of the tree
        int floor = maxLevel - level;
        int edgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        // Add the necessary first spaces to the StringBuilder
        appendWhitespaces(firstSpaces, sb);

        // Create a new list to store the child nodes for the next level of the tree
        List<Node<T>> newNodes = new ArrayList<Node<T>>();

        // Iterate through the nodes in the current level
        for (Node<T> node : nodes) {
            if (node != null) {
                // Append the value of the node to the StringBuilder
                sb.append(node.getValue());

                // Add the child nodes of the current node to the new list
                newNodes.add(node.getLeft());
                newNodes.add(node.getRight());
            } else {
                // If the current node is null, add two null nodes and a space to the new list and append a space to the StringBuilder
                newNodes.add(null);
                newNodes.add(null);
                sb.append(" ");
            }

            // Add the necessary between spaces to the StringBuilder
            appendWhitespaces(betweenSpaces, sb);
        }

        // Append a newline character to the StringBuilder
        sb.append("\n");

        // Iterate through the edge lines for this level of the tree
        for (int i = 1; i <= edgeLines; i++) {
            // Iterate through the nodes in the current level
            for (int j = 0; j < nodes.size(); j++) {
                // Add the necessary spaces before the edge symbol
                appendWhitespaces(firstSpaces - i, sb);

                // Print the left edge symbol if there is a left child node, otherwise add a space
                if (nodes.get(j) == null) {
                    appendWhitespaces(edgeLines + edgeLines + i + 1, sb);
                    continue;
                }
                if (nodes.get(j).getLeft() != null)
                    sb.append("/");
                else
                    appendWhitespaces(1, sb);

                // Add the necessary spaces between the edge symbols
                appendWhitespaces(i + i - 1, sb);

                // Print the right edge symbol if there is a right child node, otherwise add a space
                if (nodes.get(j).getRight() != null)
                    sb.append("\\");
                else
                    appendWhitespaces(1, sb);

                // Add the necessary spaces after the edge symbol
                appendWhitespaces(edgeLines + edgeLines - i, sb);
            }

            // Append a newline character to the StringBuilder
            sb.append("\n");
        }

        // Recursively print the child nodes for the next level of the tree
        printNodeInternal(newNodes, level + 1, maxLevel, sb);
    }

    /**
     * Appends the specified number of spaces to the StringBuilder.
     *
     * @param count the number ofspaces to append
     * @param sb    the StringBuilder to append the spaces to
     */
    private static void appendWhitespaces(int count, StringBuilder sb) {
        for (int i = 0; i < count; i++) {
            sb.append(" ");
        }
    }

    /**
     * Checks if all the elements in the specified list are null.
     *
     * @param list the list to check
     * @return true if all elements in the list are null, false otherwise
     */
    private static boolean isAllElementsNull(List<?> list) {
        for (Object object : list) {
            if (object != null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString(){
        return printTree(root);
    }

}