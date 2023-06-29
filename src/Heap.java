import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

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
     * Constructs a new heap with an empty array.
     */
    public Heap() {
        heapArray = new ArrayList<>();
    }

    /**
     * Constructs a new heap with the given array of elements.
     *
     * @param heapArray the array of elements to initialize the heap with
     */
    public Heap(ArrayList<T> heapArray) {
        Objects.requireNonNull(heapArray, "Heap array cannot be null");
        this.heapArray = heapArray;
    }

    /**
     * Returns the index of the right child of the given index.
     *
     * @param index the index of the parent node
     * @return the index of the right child node
     */
    protected int getRightChildIndex(int index) {
        return 2 * index + 1;
    }

    /**
     * Returns the index of the left child of the given index.
     *
     * @param index the index of the parent node
     * @return the index of the left child node
     */
    protected int getLeftChildIndex(int index) {
        return 2 * index;
    }

    /**
     * Returns the index of the parent of the given index.
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
     * Handles the user's choice of operation on the heap.
     */
    public static void handleChoices() {
        Scanner in = new Scanner(System.in);
        int choice;
        boolean input = false;
        while (!input) {
            System.out.print("Enter : "
                    + "\n 1- Max Heap"
                    + "\n 2- Min Heap"
                    + "\n 3- Heap Sort"
                    + "\n 4- Priority Queue"
                    + "\n-1- Back"
                    + "\n 0- Exit"
                    + "\nCommand :");
            choice = in.nextInt();
            switch (choice) {
                case 1: {
                    MaxHeap<Integer> heap = new MaxHeap<>();
                    heap.handleInputChoices(heap);
                    break;
                }
                case 2: {
                    MinHeap<Integer> heap = new MinHeap<>();
                    heap.handleChoices(heap);
                    break;
                }
                case 3: {
                    MaxHeap<Integer> heap = new MaxHeap<>();
                    heap.buildMaxHeap();
                    ArrayList<Integer> sorted = heap.heapSort(heap.heapArray);
                    sorted.remove(0);
                    System.out.println("Heap Sort Array : " + sorted);
                    break;
                }
                case 4: {
                    PriorityQueue<Integer> queue = new PriorityQueue<>();
                    queue.handleInputChoices(queue);
                    break;
                }
                case -1: {
                    input = true;
                    break;
                }
                case 0: {
                    System.exit(0);
                    break;
                }
                default: {
                }
            }
        }
    }

    /**
     * Prints the elements of the heap as a tree.
     */
    public void printHeapTree() {
        BinaryTree<T> tree = new BST<>();
        for (int i = 1; i <= heapSize; i++) {
            tree.insert(heapArray.get(i));
        }
        tree.printTree(tree.root);
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
}