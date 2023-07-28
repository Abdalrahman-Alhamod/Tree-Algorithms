import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * An abstract class representing a heap data structure.
 *
 * @param <T> the type of elements stored in the heap
 */
public abstract class Heap<T extends Comparable<T>> {

    /**
     * The array that stores the elements of the heap.
     */
    protected ArrayList<T> heapArray;

    /**
     * The size of the heap.
     */
    protected int heapSize;

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
     * Constructs a new heap with an empty array.
     */
    public Heap() {
        heapArray = new ArrayList<>();
        heapArray.add(null);
        heapSize = 0;
        useRecursiveApproach = true;
    }

    /**
     * Returns the index of the right child for the given index.
     *
     * @param index the index of the parent node
     * @return the index of the right child node
     */
    protected int getRightChildIndex(int index) {
        return 2 * index + 1;
    }

    /**
     * Returns the index of the left child for the given index.
     *
     * @param index the index of the parent node
     * @return the index of the left child node
     */
    protected int getLeftChildIndex(int index) {
        return 2 * index;
    }

    /**
     * Returns the index of the parent for the given index.
     *
     * @param index the index of the child node
     * @return the index of the parent node
     */
    protected int getParentIndex(int index) {
        return index / 2;
    }

    /**
     * Swaps the elements at the given indices in the heap array.
     *
     * @param one the index of the first element
     * @param two the index of the second element
     */
    protected void swap(int one, int two) {
        T temp = heapArray.get(one);
        heapArray.set(one, heapArray.get(two));
        heapArray.set(two, temp);
    }

    /**
     * Prints the elements of the heap as a tree.
     */
    public void printHeapTree() {
        BinaryTree<T> tree = buildHeapTree();
        System.out.println(tree);
    }

    /**
     * Builds a max-heap tree from the elements in the heapArray.
     *
     * @return the max-heap tree (BinaryTree)
     */
    private BinaryTree<T> buildHeapTree() {
        // Create an empty binary tree
        BinaryTree<T> tree = new BST<>();

        // Use a queue for level-order insertion of elements
        Queue<Node<T>> queue = new LinkedList<>();

        // Insert the root (first element of the heapArray) into the tree
        if (heapSize >= 1) {
            Node<T> root = new Node<>(heapArray.get(1));
            tree.root = root;
            queue.add(root);
        }

        // Start level-order insertion of other elements
        int currentIndex = 2; // Start with the second element in heapArray
        while (!queue.isEmpty() && currentIndex <= heapSize) {
            // Get the current parent node from the queue
            Node<T> parent = queue.poll();

            // Insert the left child
            Node<T> leftChild = new Node<>(heapArray.get(currentIndex));
            parent.setLeft(leftChild);
            queue.add(leftChild);
            currentIndex++;

            // Insert the right child
            if (currentIndex <= heapSize) {
                Node<T> rightChild = new Node<>(heapArray.get(currentIndex));
                parent.setRight(rightChild);
                queue.add(rightChild);
                currentIndex++;
            }
        }

        return tree;
    }

    /**
     * Abstract method that inserts an element into the heap.
     *
     * @param element the element to be inserted
     */
    public abstract void insert(T element);

    /**
     * Abstract method that removes the root element from the heap.
     *
     * @return the root element of the heap
     */
    public abstract T remove();

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
     * Makes the heap empty
     */
    public void clear() {
        heapArray = new ArrayList<>();
        heapArray.add(null);
        heapSize = 0;
    }

    public String toString() {
        ArrayList<T> array = new ArrayList<>(heapArray);
        array.remove(0);
        while (array.size() != heapSize) {
            array.remove(heapSize);
        }
        return array.toString();
    }
}