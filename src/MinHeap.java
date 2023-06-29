import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class representing a minimum heap data structure.
 *
 * @param <T> the type of elements stored in the heap
 */
public class MinHeap<T extends Comparable<T>> extends Heap<T> {

    /**
     * Constructs a new empty minimum heap.
     */
    public MinHeap() {
        super();
    }

    /**
     * Constructs a new minimum heap with the given array of elements.
     *
     * @param heapArray the array of elements to initialize the heap with
     */
    public MinHeap(ArrayList<T> heapArray) {
        super(heapArray);
        buildMinHeapArray(heapArray);
    }

    /**
     * Heapify an element at the given index in the heap in a top-down manner.
     *
     * @param index the index of the element to heapify
     */
    protected void heapifyTopDown(int index) {
        int smallest = index;
        int right = getRightChildIndex(index);
        int left = getLeftChildIndex(index);
        if (heapArray.get(smallest) != null && left <= heapSize && heapArray.get(left).compareTo(heapArray.get(smallest)) < 0)
            smallest = left;
        if (heapArray.get(smallest) != null && right <= heapSize && heapArray.get(right).compareTo(heapArray.get(smallest)) < 0)
            smallest = right;
        if (smallest != index) {
            swap(index, smallest);
            heapifyTopDown(smallest);
        }
    }

    /**
     * Insert an element into the heap.
     *
     * @param element the element to be inserted
     */
    public void insert(T element) {
        heapArray.add(element);
        heapSize++;
        heapifyBottomUp(heapSize);
    }

    /**
     * Heapify an element at the given index in the heap in a bottom-up manner.
     *
     * @param index the index of the element to heapify
     */
    protected void heapifyBottomUp(int index) {
        if (index != 1 && heapArray.get(index).compareTo(heapArray.get(getParentIndex(index))) < 0) {
            swap(index, getParentIndex(index));
            heapifyBottomUp(getParentIndex(index));
        }
    }

    /**
     * Search for an element in the heap and return its index.
     *
     * @param element the element to search for
     * @return the index of the element, or -1 if it is not found
     */
    public int search(T element) {
        for (int i = 1; i <= heapSize; i++) {
            if (element.equals(heapArray.get(i)))
                return i;
        }
        return -1;
    }

    /**
     * Removes the minimum element from the heap and returns it.
     *
     * @return the minimum element in the heap, or null if the heap is empty
     */
    @Override
    public T remove() {
        if (heapSize == 0) {
            return null;
        }

        // Save the minimum element to be returned later
        T minElement = heapArray.get(1);

        // Replace the minimum element with the last element in the heap
        heapArray.set(1, heapArray.get(heapSize));
        heapSize--;

        // Heapify the root element down to maintain the heap property
        heapifyTopDown(1);

        return minElement;
    }

    /**
     * Delete an element from the heap.
     *
     * @param index the index of the element to be deleted
     */
    public void delete(int index) {
        if (index <= 0 || index > heapSize)
            return;
        heapArray.set(index, heapArray.get(heapSize));
        heapSize--;
        if (index != 1 && heapArray.get(index).compareTo(heapArray.get(getParentIndex(index))) < 0)
            heapifyBottomUp(index);
        else
            heapifyTopDown(index);
    }

    /**
     * Build a minimum heap from the given array of elements.
     *
     * @param array the array of elements to build the heap from
     */
    public void buildMinHeapArray(ArrayList<T> array) {
        heapSize = array.size() - 1;
        for (int i = heapSize / 2; i >= 1; i--) {
            heapifyTopDown(i);
        }
    }

    /**
     * Sort the elements of the heap in descending order using heap sort.
     *
     * @return the sorted array of elements
     */
    public ArrayList<T> heapSort(ArrayList<T> array) {
        // Time Complexity : O(nlog(n))
        buildMinHeapArray(array);
        int elementsNumber = heapSize;
        for (int i = array.size() - 1; i >= 1; i--) {
            swap(array, 0, i);
            elementsNumber--;
            heapifyTopDown(0);
        }
        return array;
    }

    /**
     * This method swaps two elements in an ArrayList.
     *
     * @param array the ArrayList containing the elements to be swapped
     * @param i     the index of the first element
     * @param j     the index of the second element
     */
    private void swap(ArrayList<T> array, int i, int j) {
        T temp = array.get(i);
        array.set(i, array.get(j));
        array.set(j, temp);
    }

    /**
     * Handle the user's choice of operation on the heap.
     */
    public void handleChoices(MinHeap heap) {
        Scanner in = new Scanner(System.in);
        int choice;
        boolean input = false;
        while (!input) {
            System.out.print("Enter : "
                    + "\n 1- Print Min Heap Array"
                    + "\n 2- Print Min Heap Tree"
                    + "\n 3- Print Heap Sort Descending Array"
                    + "\n 4- Insert An Element"
                    + "\n 5- Delete An Element"
                    + "\n6- Search for an Element"
                    + "\n 7- Exit"
                    + "\nChoice: ");
            choice = in.nextInt();
            switch (choice) {
                case 1:
                    heap.printHeapArray();
                    break;
                case 2:
                    heap.printHeapTree();
                    break;
                case 3:
                    ArrayList<T> sorted = new ArrayList<>(heap.heapArray.subList(1, heap.heapSize + 1));
                    System.out.println("Heap Sort Array : " + heapSort(sorted));
                    break;
                case 4:
                    System.out.print("Enter element to insert: ");
                    int element = in.nextInt();
                    heap.insert(element);
                    System.out.println("Element " + element + " inserted into the heap.");
                    break;
                case 5:
                    System.out.print("Enter index of element to delete: ");
                    int index = in.nextInt();
                    heap.delete(index);
                    System.out.println("Element at index " + index + " deleted from the heap.");
                    break;
                case 6:
                    System.out.print("Enter element to search for: ");
                    int elementToSearch = in.nextInt();
                    int indexFound = heap.search(elementToSearch);
                    if (indexFound != -1) {
                        System.out.println("Element " + elementToSearch + " found at index " + indexFound + ".");
                    } else {
                        System.out.println("Element " + elementToSearch + " not found in the heap.");
                    }
                    break;
                case 7:
                    input = true;
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }
}