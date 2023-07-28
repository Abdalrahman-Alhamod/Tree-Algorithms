/**
 * Binary Search Tree implementation that inherits from BinaryTree class.
 */
public class BST<T extends Comparable<T>> extends BinaryTree<T> {
    /**
     * Constructs a new binary search tree with no elements.
     */
    public BST() {
        super();
    }
    /**
     * Constructs a binary search tree with the given root node.
     *
     * @param root The root node of the binary search tree.
     */
    public BST(Node<T> root) {
        super(root);
    }

    /**
     * Inserts an element into the BST.
     *
     * @param value the value to insert into the BST
     * @implNote This method has a time complexity of O(h), where h is the height of the subtree.
     */
    @Override
    public boolean insert(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }

        if (useRecursiveApproach) {
            // If using the recursive approach, keep track of the size before insertion to check if it changes later.
            int beforeInsertSize = size;
            // Call the recursive insert method, which returns the root of the updated subtree.
            root = insertRecursive(root, value);
            // If the size changed after insertion, it means the value was inserted successfully.
            return size != beforeInsertSize;
        } else {
            // If using the iterative approach, call the iterative insert method.
            return insertIterative(root, value);
        }
    }

    /**
     * Helper method to recursively insert a value into the Binary Search Tree (BST).
     *
     * @param node  the root of the subtree to insert the value into
     * @param value the value to insert
     * @return the root of the updated subtree
     * @implNote This method has a time complexity of O(h), where h is the height of the subtree.
     */
    private Node<T> insertRecursive(Node<T> node, T value) {
        // Base case: If the subtree is empty (reached a leaf node or a null child), create a new node with the value
        if (node == null) {
            size++;
            return new Node<>(value);
        }

        // Compare the value with the current node's value to decide where to insert
        if (value.compareTo(node.getValue()) < 0) {
            // The value is less than the current node's value, so insert it into the left subtree
            Node<T> leftChild = insertRecursive(node.getLeft(), value);
            node.setLeft(leftChild);
        } else if (value.compareTo(node.getValue()) > 0) {
            // The value is greater than the current node's value, so insert it into the right subtree
            Node<T> rightChild = insertRecursive(node.getRight(), value);
            node.setRight(rightChild);
        }

        // Update the height of the current node after inserting the value into the subtree
        node.updateHeight();

        // Return the updated root of the subtree
        return node;
    }


    /**
     * Inserts a new node with the specified value into the tree using iterative approach.
     *
     * @param value the value to insert
     * @return true if the value was inserted, or false if it already exists in the tree
     * @implNote This method has a time complexity of O(h), where h is the height of the subtree.
     */
    public boolean insertIterative(Node<T> node, T value) {
        Node<T> newNode = new Node<>(value);

        // If the tree is empty, set the root node to be the new node
        if (root == null) {
            root = newNode;
            size++;
            return true;
        }

        // Traverse the tree to find the correct position to insert the new node
        Node<T> current = node;
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
        // Update the height of the current node after inserting the value into the subtree
        Node<T> parent = node.getParent();
        Node<T> nodeToUpdateHeight = (parent == null) ? root : parent;
        while (nodeToUpdateHeight != null) {
            nodeToUpdateHeight.updateHeight();
            nodeToUpdateHeight = nodeToUpdateHeight.getParent();
        }

        return true;
    }

    /**
     * Searches the tree for a node with the specified value and returns it.
     *
     * @param value the value to search for
     * @return the node with the specified value, or null if it is not found
     * @implNote This method has a time complexity of O(h), where h is the height of the subtree.
     */
    public Node<T> find(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        if (useRecursiveApproach) return findRecursive(root, value);
        else return findIterative(root, value);
    }

    /**
     * Helper method to recursively search for a value in the BST.
     *
     * @param node  the root of the subtree to search in
     * @param value the value to search for
     * @return the node with the specified value, or null if it is not found
     * @implNote This method has a time complexity of O(h), where h is the height of the subtree.
     */
    private Node<T> findRecursive(Node<T> node, T value) {
        // If the current node is null, we have reached the end of the subtree,
        // and the value is not found, so return null.
        if (node == null) {
            return null;
        }

        // Compare the value with the current node's value using the compareTo method.
        int cmp = value.compareTo(node.getValue());

        // If the value is less than the current node's value, it means the value
        // might exist in the left subtree .Recursively call the findRecursive method
        // with the left subtree root node and the same value.
        if (cmp < 0) {
            return findRecursive(node.getLeft(), value);
        }

        // If the value is greater than the current node's value, it means the value
        // might exist in the right subtree. Recursively call the findRecursive method
        // with the right subtree root node and the same value.
        else if (cmp > 0) {
            return findRecursive(node.getRight(), value);
        }

        // If the compareTo method returns 0, it means the value is equal to the current
        // node's value, so we have found the node with the specified value. Return the node.
        else {
            return node;
        }
    }

    /**
     * Helper method to iteratively search for a value in the BST.
     *
     * @param node  the root of the subtree to search in
     * @param value the value to search for
     * @return the node with the specified value, or null if it is not found
     * @implNote This method has a time complexity of O(h), where h is the height of the subtree.
     */
    private Node<T> findIterative(Node<T> node, T value) {
        // Start from the root node
        Node<T> current = node;
        while (current != null) {
            int cmp = value.compareTo(current.getValue());
            if (cmp < 0) {
                // The value is less than the current node's value, so move to the left subtree
                current = current.getLeft();
            } else if (cmp > 0) {
                // The value is greater than the current node's value, so move to the right subtree
                current = current.getRight();
            } else {
                // The value matches the current node's value, so returns the node
                return current;
            }
        }
        // If the loop exits, the value is not found, so return null
        return null;
    }

    /**
     * Counts the number of occurrences for the specified value in the tree.
     *
     * @param value the value to count
     * @return the number of occurrences for the value in the tree
     * @implNote This method has a time complexity of O(h), where h is the height of the subtree.
     */
    @Override
    public int count(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        return find(value) == null ? 0 : 1;
    }

    /**
     * Deletes an element from the BST.
     *
     * @param value the value to delete
     * @implNote This method has a time complexity of O(h), where h is the height of the subtree.
     */
    @Override
    public boolean delete(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        if (find(value) == null) return false;
        if (useRecursiveApproach) {
            root = deleteRecursive(root, value);
            return true;
        } else {
            return deleteIterative(value);
        }
    }

    /**
     * Recursive helper method for deleting a value from the Binary Search Tree (BST).
     *
     * @param node  the root of the subtree to delete the value from
     * @param value the value to delete
     * @return the root of the updated subtree after deletion
     * @implNote This method has a time complexity of O(h), where h is the height of the subtree.
     */
    private Node<T> deleteRecursive(Node<T> node, T value) {
        // Base case: If the node is null, the value is not found, return null.
        if (node == null) {
            return null;
        }
        // If the value to be deleted is less than the current node's value,
        // recursively delete the value from the left subtree.
        if (value.compareTo(node.getValue()) < 0) {
            Node<T> leftChild = deleteRecursive(node.getLeft(), value);
            node.setLeft(leftChild);
        }
        // If the value to be deleted is greater than the current node's value,
        // recursively delete the value from the right subtree.
        else if (value.compareTo(node.getValue()) > 0) {
            Node<T> rightChild = deleteRecursive(node.getRight(), value);
            node.setRight(rightChild);
        }
        // If the value is found (value.compareTo(node.getValue()) == 0),
        // handle different cases for node deletion.
        else {
            // Case 1: Node is a leaf node (no children).
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            }
            // Case 2: Node has one child (right child).
            else if (node.getLeft() == null) {
                return node.getRight();
            }
            // Case 2: Node has one child (left child).
            else if (node.getRight() == null) {
                return node.getLeft();
            }
            // Case 3: Node has two children.
            else {
                // Find the minimum value node in the right subtree (the smallest value greater than the current node).
                Node<T> temp = getMin(node.getRight());
                // Replace the current node's value with the value of the minimum node.
                node.setValue(temp.getValue());
                // Recursively delete the minimum node from the right subtree.
                Node<T> rightChild = deleteRecursive(node.getRight(), temp.getValue());
                node.setRight(rightChild);
            }
        }

        // Update the height of the current node after deletion.
        node.updateHeight();
        return node;
    }

    /**
     * Removes the node with the specified value from the tree using iterative approach.
     *
     * @param value the value to remove
     * @return true if the node was found and removed, false otherwise
     * @implNote This method has a time complexity of O(h), where h is the height of the subtree.
     */
    public boolean deleteIterative(T value) {
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
                    delete(successor.getValue());
                    current.setValue(successor.getValue());
                }

                // Case 1, 2, or 3: node has been removed, update height of nodes along the path
                Node<T> nodeToUpdateHeight = (parent == null) ? root : parent;
                while (nodeToUpdateHeight != null) {
                    nodeToUpdateHeight.updateHeight();
                    nodeToUpdateHeight = nodeToUpdateHeight.getParent();
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
     * @implNote This method has a time complexity of O(h), where h is the height of the subtree.
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
     * @implNote This method has a time complexity of O(h), where h is the height of the subtree.
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
     * Returns the node with the minimum value in the tree.
     *
     * @return the node with the minimum value in the tree, or null if the tree is empty
     * @implNote This method has a time complexity of O(h), where h is the height of the tree.
     */
    @Override
    public Node<T> getMin() {
        if (root == null) {
            return null;
        }
        return getMin(root);
    }

    /**
     * Returns the node with the minimum value in the given tree.
     *
     * @param node the root node of the current subtree
     * @implNote This method has a time complexity of O(h), where h is the height of the subtree.
     */
    public Node<T> getMin(Node<T> node) {
        if (node == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        if (useRecursiveApproach)
            return getMinRecursive(node);
        else
            return getMinIterative(node);
    }

    /**
     * Recursive helper method for finding the node with the minimum value in the tree.
     *
     * @param node the root node of the current subtree
     * @implNote This method has a time complexity of O(h), where h is the height of the subtree.
     */
    private Node<T> getMinRecursive(Node<T> node) {
        if (node.getLeft() == null) {
            return node;
        }
        return getMinRecursive(node.getLeft());
    }

    /**
     * Iterative helper method for finding the node with the minimum value in the tree.
     *
     * @param node the root node of the current subtree
     * @return the node with the minimum value in the subtree, or null if the subtree is empty
     * @implNote This method has a time complexity of O(h), where h is the height of the subtree.
     */
    private Node<T> getMinIterative(Node<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    /**
     * Returns the node with the maximum value in the tree.
     *
     * @return the node with the maximum value in the tree, or null if the tree is empty
     * @implNote This method has a time complexity of O(h), where h is the height of the tree.
     */
    @Override
    public Node<T> getMax() {
        if (root == null) {
            return null;
        }
        return getMax(root);
    }

    /**
     * Returns the node with the maximum value in the given tree.
     *
     * @param node the root node of the current subtree
     * @implNote This method has a time complexity of O(h), where h is the height of the subtree.
     */
    public Node<T> getMax(Node<T> node) {
        if (node == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        if (useRecursiveApproach)
            return getMaxRecursive(node);
        else
            return getMaxIterative(node);
    }

    /**
     * Recursive helper method for finding the node with the maximum value in the tree.
     *
     * @param node the root node of the current subtree
     * @implNote This method has a time complexity of O(h), where h is the height of the subtree.
     */
    private Node<T> getMaxRecursive(Node<T> node) {
        if (node.getRight() == null) {
            return node;
        }
        return getMaxRecursive(node.getRight());
    }

    /**
     * Recursive helper method for finding the node with the maximum value in the tree.
     *
     * @param node the root node of the current subtree
     * @return the node with the maximum value in the subtree, or null if the subtree is empty
     * @implNote This method has a time complexity of O(h), where h is the height of the subtree.
     */
    private Node<T> getMaxIterative(Node<T> node) {
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }

    /**
     * Checks if a dead end exists in the BST.
     *
     * @param min the minimum value allowed in the subtree (exclusive)
     * @param max the maximum value allowed in the subtree (exclusive)
     * @return true if a dead end exists, false otherwise
     */
    public boolean deadEnd(T min, T max) {
        return deadEnd(root, min, max);
    }

    /**
     * Helper method to recursively check for a dead end in the BST.
     *
     * @param node the root of the subtree to check
     * @param min  the minimum value allowed in the subtree (exclusive)
     * @param max  the maximum value allowed in the subtree (exclusive)
     * @return true if a dead end exists in the subtree, false otherwise
     * @implNote This method has a time complexity of O(n), where n is the number of nodes in the tree.
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
     * @implNote This method has a time complexity of O(log(n))
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
     * @implNote This method has a time complexity of O(log(n))
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
     * @implNote This method has a time complexity of O(log(n))
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
     * @implNote This method has a time complexity of O(log(n))
     */
    private int calcDistanceBetween(Node<T> node, T value) {
        if (node == null) {
            return -1;
        }
        int distance;
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