import java.util.ArrayList;

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
        useRecursiveApproach=true;
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
     * Prints the elements of the heap array.
     */
    public void printHeapArray() {
        System.out.print("Heap Array : ");
        for (int i = 1; i <= heapSize; i++) {
            System.out.print(heapArray.get(i) + " ");
        }
        System.out.println();
    }

    /**
     * Prints the elements of the heap as a tree.
     */
    public void printHeapTree() {
        BinaryTree<T> tree = new BST<>();
        for (int i = 1; i <= heapSize; i++) {
            tree.insert(heapArray.get(i));
        }
        System.out.println(tree);
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
}