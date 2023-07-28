import java.util.*;
import java.util.function.Consumer;

/**
 * A binary tree implementation of the Tree interface.
 *
 * @param <T> the type of elements stored in the tree
 */
public abstract class BinaryTree<T extends Comparable<T>> implements Tree<T> {

    /**
     * The root node of the tree
     */
    protected Node<T> root;
    /**
     * The number of nodes in the tree
     */
    protected int size;

    /**
     * A boolean flag indicating whether to use the recursive approach or not.
     * <p>
     * When this flag is set to {@code true}, certain methods in the tree class, like insert, delete, and find,
     * will use a recursive implementation. Conversely, when it is set to {@code false}, the iterative approach will be used.
     * The choice of using a recursive or iterative approach may affect the performance and memory usage of the tree operations.
     * </p>
     * <p>
     * By default, the value of this flag is set to {@code true}, meaning the recursive approach is used.
     * Users of the tree class can modify this flag to change the behavior of tree operations accordingly.
     * </p>
     */
    protected boolean useRecursiveApproach;

    /**
     * Constructs a new binary tree with no elements.
     */
    public BinaryTree() {
        root = null;
        size = 0;
        useRecursiveApproach = true;
    }

    /**
     * Constructs a binary tree with the given root node.
     *
     * @param root The root node of the binary tree.
     */
    public BinaryTree(Node<T> root) {
        this.root = root;
        size = 1;
        useRecursiveApproach = true;
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
        if (nodes.isEmpty() || isAllElementsNull(nodes)) return;

        // Calculate the number of edge lines and spaces for this level of the tree
        int floor = maxLevel - level;
        int edgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        // Add the necessary first spaces to the StringBuilder
        appendWhitespaces(firstSpaces, sb);

        // Create a new list to store the child nodes for the next level of the tree
        List<Node<T>> newNodes = new ArrayList<>();

        // Iterate through the nodes in the current level
        for (Node<T> node : nodes) {
            if (node != null) {
                // Append the value of the node to the StringBuilder
                sb.append(node.getValue());

                // Add the child nodes of the current node to the new list
                newNodes.add(node.getLeft());
                newNodes.add(node.getRight());
            } else {
                // If the current node is null,
                // add two null nodes and a space to the new list
                // and append a space to the StringBuilder
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
            for (Node<T> node : nodes) {
                // Add the necessary spaces before the edge symbol
                appendWhitespaces(firstSpaces - i, sb);

                // Print the left-edge symbol if there is a left child node, otherwise add a space
                if (node == null) {
                    appendWhitespaces(edgeLines + edgeLines + i + 1, sb);
                    continue;
                }
                if (node.getLeft() != null) sb.append("/");
                else appendWhitespaces(1, sb);

                // Add the necessary spaces between the edge symbols
                appendWhitespaces(i + i - 1, sb);

                // Print the right-edge symbol if there is a right child node, otherwise add a space
                if (node.getRight() != null) sb.append("\\");
                else appendWhitespaces(1, sb);

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
     * @param count the number spaces to append
     * @param sb    the StringBuilder to append the spaces to
     */
    private static void appendWhitespaces(int count, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, count)));
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

    /**
     * Checks if the tree contains a node with the given value.
     *
     * @param value the value to search for
     * @return true if the tree contains a node with the given value, false otherwise
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
     */
    @Override
    public boolean contains(T value) {
        return find(value) != null;
    }

    /**
     * Finds the node with the specified value in the tree.
     *
     * @param value the value to search for
     * @return the node with the specified value, or null if it is not found
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
     */
    @Override
    public Node<T> find(T value) {
        if (useRecursiveApproach) return findRecursive(root, value);
        else return findIterative(root, value);
    }

    /**
     * Recursive helper method for finding the node with the specified value in the tree.
     *
     * @param node  the root node of the current subtree
     * @param value the value to search for
     * @return the node with the specified value in the subtree, or null if it is not found
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
     */
    private Node<T> findRecursive(Node<T> node, T value) {
        if (node == null) {
            return null;
        } else if (node.getValue().equals(value)) {
            return node;
        } else {
            Node<T> left = findRecursive(node.getLeft(), value);
            Node<T> right = findRecursive(node.getRight(), value);
            if (left != null) {
                return left;
            } else return right;
        }
    }

    /**
     * Iteratively finds the node with the specified value in the tree.
     *
     * @param root  the root node of the current subtree
     * @param value the value to search for
     * @return the node with the specified value in the subtree, or null if it is not found
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
     */
    private Node<T> findIterative(Node<T> root, T value) {
        if (root == null) {
            return null;
        }

        Deque<Node<T>> stack = new LinkedList<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node<T> current = stack.pop();
            if (current.getValue().equals(value)) {
                return current;
            }

            // Push the right child first to ensure the left child is processed first
            if (current.getRight() != null) {
                stack.push(current.getRight());
            }

            // Push the left child
            if (current.getLeft() != null) {
                stack.push(current.getLeft());
            }
        }

        return null;
    }

    /**
     * Counts the number of occurrences for the specified value in the tree.
     *
     * @param value the value to count
     * @return the number of occurrences for the value in the tree
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
     */
    @Override
    public int count(T value) {
        if (useRecursiveApproach) return countRecursive(root, value);
        else return countIterative(root, value);
    }

    /**
     * Recursive helper method for counting the number of occurrences for the specified value in the tree.
     *
     * @param node  the root node of the current subtree
     * @param value the value to count
     * @return the number of occurrences for the value in the subtree
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
     */
    private int countRecursive(Node<T> node, T value) {
        if (node == null) {
            return 0;
        } else {
            int count = 0;
            if (node.getValue().equals(value)) {
                count++;
            }
            count += countRecursive(node.getLeft(), value);
            count += countRecursive(node.getRight(), value);
            return count;
        }
    }

    /**
     * Iterative helper method
     * for counting the number of occurrences for the specified value in the subtree rooted at the given node.
     *
     * @param node  the root node of the current subtree
     * @param value the value to count
     * @return the number of occurrences for the value in the subtree
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
     */
    private int countIterative(Node<T> node, T value) {
        int count = 0;

        if (node == null) {
            return count;
        }

        Stack<Node<T>> stack = new Stack<>();
        stack.push(node);

        while (!stack.isEmpty()) {
            Node<T> current = stack.pop();
            if (current.getValue().equals(value)) {
                count++;
            }

            if (current.getLeft() != null) {
                stack.push(current.getLeft());
            }

            if (current.getRight() != null) {
                stack.push(current.getRight());
            }
        }

        return count;
    }

    /**
     * Performs a pre-order traversal of the tree and applies the specified action to each node.
     *
     * @param action the action to apply to each node
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
     */
    @Override
    public void preOrder(Consumer<T> action) {
        if (useRecursiveApproach) preOrderRecursive(root, action);
        else preOrderIterative(root, action);
    }

    /**
     * Recursive method for performing a pre-order traversal and applying an action on each node.
     *
     * @param node   the root node of the current subtree
     * @param action the action to perform on each node
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
     */
    private void preOrderRecursive(Node<T> node, Consumer<T> action) {
        if (node != null) {
            action.accept(node.getValue());
            preOrderRecursive(node.getLeft(), action);
            preOrderRecursive(node.getRight(), action);
        }
    }

    /**
     * Iterative method for performing a pre-order traversal and applying an action on each node.
     *
     * @param node   the root node of the current subtree
     * @param action the action to perform on each node
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
     */
    private void preOrderIterative(Node<T> node, Consumer<T> action) {
        if (node == null) {
            return;
        }

        Stack<Node<T>> stack = new Stack<>();
        stack.push(node);

        while (!stack.isEmpty()) {
            Node<T> current = stack.pop();
            action.accept(current.getValue());

            if (current.getRight() != null) {
                stack.push(current.getRight());
            }

            if (current.getLeft() != null) {
                stack.push(current.getLeft());
            }
        }
    }

    /**
     * Performs an in-order traversal of the tree and applies the specified action to each node.
     *
     * @param action the action to apply to each node
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
     */
    @Override
    public void inOrder(Consumer<T> action) {
        if (useRecursiveApproach) inOrderRecursive(root, action);
        else inOrderIterative(root, action);
    }

    /**
     * Recursive method for performing an in-order traversal and applying an action on each node.
     *
     * @param node   the root node of the current subtree
     * @param action the action to perform on each node
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
     */
    private void inOrderRecursive(Node<T> node, Consumer<T> action) {
        if (node != null) {
            inOrderRecursive(node.getLeft(), action);
            action.accept(node.getValue());
            inOrderRecursive(node.getRight(), action);
        }
    }

    /**
     * Iterative method for performing an in-order traversal and applying an action on each node.
     *
     * @param node   the root node of the current subtree
     * @param action the action to perform on each node
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
     */
    private void inOrderIterative(Node<T> node, Consumer<T> action) {
        if (node == null) {
            return;
        }

        Stack<Node<T>> stack = new Stack<>();
        Node<T> current = node;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.getLeft();
            }

            current = stack.pop();
            action.accept(current.getValue());
            current = current.getRight();
        }
    }

    /**
     * Performs a post-order traversal of the tree and applies the specified action to each node.
     *
     * @param action the action to apply to each node
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
     */
    @Override
    public void postOrder(Consumer<T> action) {
        if (useRecursiveApproach) postOrderRecursive(root, action);
        else postOrderIterative(root, action);
    }

    /**
     * Recursive method for performing a post-order traversal and applying an action on each node.
     *
     * @param node   the root node of the current subtree
     * @param action the action to perform on each node
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
     */
    private void postOrderRecursive(Node<T> node, Consumer<T> action) {
        if (node != null) {
            postOrderRecursive(node.getLeft(), action);
            postOrderRecursive(node.getRight(), action);
            action.accept(node.getValue());
        }
    }

    /**
     * Iterative method for performing a post-order traversal and applying an action on each node.
     *
     * @param node   the root node of the current subtree
     * @param action the action to perform on each node
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
     */
    private void postOrderIterative(Node<T> node, Consumer<T> action) {
        if (node == null) {
            return;
        }

        Stack<Node<T>> stack1 = new Stack<>();
        Stack<Node<T>> stack2 = new Stack<>();
        stack1.push(node);

        while (!stack1.isEmpty()) {
            Node<T> current = stack1.pop();
            stack2.push(current);

            if (current.getLeft() != null) {
                stack1.push(current.getLeft());
            }

            if (current.getRight() != null) {
                stack1.push(current.getRight());
            }
        }

        while (!stack2.isEmpty()) {
            action.accept(stack2.pop().getValue());
        }
    }

    /**
     * Returns the maximum depth of the tree.
     *
     * @return the maximum depth of the tree
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
     */
    @Override
    public int getDepth() {
        if (useRecursiveApproach) return getDepthRecursive(root);
        else return getDepthIterative(root);
    }

    /**
     * Calculates the depth of the binary tree starting from the specified node using a Recursive approach.
     *
     * @param node the root node of the current subtree
     * @return the depth of the subtree, or 0 if the node is null
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
     */
    private int getDepthRecursive(Node<T> node) {
        if (node == null) {
            return -1;
        }
        int leftDepth = getDepthRecursive(node.getLeft());
        int rightDepth = getDepthRecursive(node.getRight());
        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * Calculates the depth of the binary tree starting from the specified node using an iterative approach.
     *
     * @param node the root node of the current subtree
     * @return the depth of the subtree, or 0 if the node is null
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
     */
    private int getDepthIterative(Node<T> node) {
        if (node == null) {
            return -1;
        }

        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(node);
        int depth = -1;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            // Process nodes at the current level and enqueue their children for the next level
            for (int i = 0; i < levelSize; i++) {
                Node<T> current = queue.poll();

                assert current != null;
                if (current.getLeft() != null) {
                    queue.add(current.getLeft());
                }

                if (current.getRight() != null) {
                    queue.add(current.getRight());
                }
            }

            // Increment the depth for each level processed
            depth++;
        }

        return depth;
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
     * Returns the node with the minimum value in the tree.
     *
     * @return the node with the minimum value in the tree, or null if the tree is empty
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
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
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
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
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
     */
    @Override
    public Node<T> getMax() {
        if (root == null) {
            return null;
        }
        return getMax(root);
    }

    @Override
    abstract public Node<T> getSuccessor(Node<T> node);

    @Override
    abstract public Node<T> getPredecessor(Node<T> node);

    @Override
    abstract public boolean insert(T value);

    @Override
    abstract public boolean delete(T value);

    /**
     * Recursive helper method for finding the node with the maximum value in the tree.
     *
     * @param node the root node of the current subtree
     * @return the node with the maximum value in the subtree, or null if the subtree is empty
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
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
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
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
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
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
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
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
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
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
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
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
        return isComplete(node.getLeft(), 2 * index + 1, maxsize) && isComplete(node.getRight(), 2 * index + 2, maxsize);
    }

    /**
     * Checks whether the binary tree is a full binary tree.
     *
     * @return true if the binary tree is a full binary tree, false otherwise
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
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
     * @implNote This method has a time complexity of O(1)
     */
    public int getMaxHeight() {
        return size() - 1;
    }

    /**
     * Returns the minimum height of the tree.
     *
     * @return the minimum height of the tree
     * @implNote This method has a time complexity of O(1)
     */
    public int getMinHeight() {
        return (int) Math.floor(Math.log(size() + 1) / Math.log(2));
    }

    /**
     * Returns the minimum number of nodes in a binary tree of the same height as the tree.
     *
     * @return the minimum number of nodes in a binary tree of the same height as the tree
     * @implNote This method has a time complexity of O(1)
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
     * @implNote This method has a time complexity of O(log(n))
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
     * Returns the height of the left subtree for the given node.
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
     * Returns the height of the right subtree for the given node.
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
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
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
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
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
    public boolean isBST(T min, T max) {
        if (root == null) {
            return true;
        } else {
            return isBST(root, min, max);
        }
    }

    /**
     * Recursively checks if the subtree rooted at the specified node is a binary search tree.
     *
     * @param node the root of the subtree to check
     * @param min  the minimum value that nodes in the subtree can have
     * @param max  the maximum value that node in the subtree can have
     * @return true if the subtree is a binary search tree, false otherwise
     */
    private boolean isBST(Node<T> node, T min, T max) {
        if (node == null) {
            return true;
        }
        if ((min != null && node.getValue().compareTo(min) <= 0) || (max != null && node.getValue().compareTo(max) >= 0)) {
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
        // Clears the console before printing the tree
//        try {
//            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

        // Get the maximum depth of the tree
        int maxLevel;
        if (useRecursiveApproach) maxLevel = getDepthRecursive(root) + 1;
        else maxLevel = getDepthIterative(root) + 1;
        // Create a StringBuilder to store the string representation of the tree
        StringBuilder sb = new StringBuilder();

        // Print the nodes of the tree in a vertical format
        printNodeInternal(Collections.singletonList(root), 1, maxLevel, sb);

        // Returning the StringBuilder to the toString function
        return sb.toString();
    }

    /**
     * Sets the approach to use for certain operations in the binary tree.
     *
     * @param useRecursiveApproach true to use the recursive approach, false to use the iterative approach
     * @implNote By default, the binary tree uses the recursive approach for operations. Setting this flag to false
     * will switch to the iterative approach, which may affect the performance and memory usage of the tree operations.
     */
    public void setUseRecursiveApproach(boolean useRecursiveApproach) {
        this.useRecursiveApproach = useRecursiveApproach;
    }

    /**
     * Makes the tree empty
     */
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public String toString() {
        return printTree(root);
    }

}