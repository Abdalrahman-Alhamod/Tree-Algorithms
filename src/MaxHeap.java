import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents a max binary heap data structure that extends the abstract Heap class.
 * The MaxHeap class provides implementations of the abstract insert and remove methods, which define the specific behavior of the binary heap.
 */
public class MaxHeap<T extends Comparable<T>> extends Heap<T> {

    /**
     * This method restores the max-heap property from the given index to the root of the heap,
     * assuming that the left and right children of the given index are already max-heaps.
     *
     * @param index the index to start heapifying from
     */
    protected void heapifyUp(int index) {
        // Time Complexity : O(log(n))
        int parentIndex = getParentIndex(index);
        if (parentIndex >= 0 && heapArray.get(index).compareTo(heapArray.get(parentIndex)) > 0) {
            swap(index, parentIndex);
            heapifyUp(parentIndex);
        }
    }

    /**
     * This method restores the max-heap property from the given index to the leaves of the heap,
     * assuming that the left and right children of the given index are already max-heaps.
     *
     * @param index the index to start heapifying from
     */
    protected void heapifyDown(int index) {
        // Time Complexity : O(log(n))
        int leftChildIndex = getLeftChildIndex(index);
        int rightChildIndex = getRightChildIndex(index);
        int largest = index;
        if (heapArray.get(largest) != null && leftChildIndex < heapSize && heapArray.get(leftChildIndex).compareTo(heapArray.get(largest)) > 0) {
            largest = leftChildIndex;
        }

        if (heapArray.get(largest) != null && rightChildIndex < heapSize && heapArray.get(rightChildIndex).compareTo(heapArray.get(largest)) > 0) {
            largest = rightChildIndex;
        }

        if (largest != index) {
            swap(index, largest);
            heapifyDown(largest);
        }
    }

    /**
     * This method inserts an element into the heap and restores the max-heap property if necessary.
     *
     * @param element the element to be inserted
     */
    @Override
    public void insert(T element) {
        heapArray.add(element);
        heapSize++;
        heapifyUp(heapSize - 1);
    }

    /**
     * This method removes the element with the maximum value from the heap and restores the max-heap property if necessary.
     *
     * @return the element with the maximum value
     */
    @Override
    public T remove() {
        if (heapSize == 0) {
            return null;
        }
        T max = heapArray.get(0);
        heapArray.set(0, heapArray.get(heapSize - 1));
        heapSize--;
        heapifyDown(0);
        return max;
    }

    /**
     * This method searches for an element in the heap and returns its index if found, or -1 otherwise.
     *
     * @param element the element to search for
     * @return the index of the element if found, or -1 otherwise
     */
    public int search(T element) {
        for (int i = 0; i < heapSize; i++) {
            if (heapArray.get(i).equals(element)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * This method deletes an element from the heap at the given index, if it exists.
     * It then restores the max-heap property if necessary.
     *
     * @param index the index of the element to be deleted
     */
    public void delete(int index) {
        if (index < 0 || index >= heapSize) {
            return;
        }
        heapArray.set(index, heapArray.get(heapSize - 1));
        heapSize--;
        if (index != 0 && heapArray.get(index).compareTo(heapArray.get(getParentIndex(index))) > 0) {
            heapifyUp(index);
        } else {
            heapifyDown(index);
        }
    }

    /**
     * This method builds a max heap from an ArrayList of elements.
     * It prompts the user to enter the number of elements and the elements themselves.
     */
    public void buildMaxHeap() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Number Of Elements : ");
        int n = in.nextInt();
        ArrayList<T> array = new ArrayList<>(n + 1);
        System.out.print("Enter Elements : ");
        array.add(null);
        for (int i = 1; i <= n; i++) {
            T element = (T) in.next();
            array.add(element);
        }
        heapSize = array.size() - 1;
        heapArray = array;
        buildMaxHeapArray(heapArray);
        printHeapArray();
    }

    /**
     * This method builds a max heap from an ArrayList of elements.
     * It assumes that the ArrayList is a complete binary tree, and starts heapifying from the last non-leaf node to the root.
     *
     * @param array the ArrayList of elements to be transformed into a max heap
     */
    private void buildMaxHeapArray(ArrayList<T> array) {
        // Time Complexity : O(n)
        for (int i = heapSize / 2; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    /**
     * This method sorts an ArrayList of elements using the heap sort algorithm.
     *
     * @param array the ArrayList of elements to be sorted
     * @return the sorted ArrayList of elements
     */
    public ArrayList<T> heapSort(ArrayList<T> array) {
        // Time Complexity : O(nlog(n))
        buildMaxHeapArray(array);
        int elementsNumber = heapSize;
        for (int i = array.size() - 1; i >= 1; i--) {
            swap(array, 0, i);
            elementsNumber--;
            heapifyDown(0);
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
     * This method handles user input choices for a MaxHeap object.
     * It prompts the user to enter a command to print the heap array, print the heap tree, sort the heap array,
     * insert an element, delete an element, go back to the previous menu, or exit the program.
     *
     * @param heap the MaxHeap object to handle input choices for
     */
    public void handleInputChoices(MaxHeap heap) {
        Scanner in = new Scanner(System.in);
        buildMaxHeap();
        int choice;
        boolean input = false;
        while (!input) {
            System.out.print("Enter : "
                    + "\n 1- Print Max Heap Array"
                    + "\n 2- Print Max Heap Tree"
                    + "\n 3- Print Heap Sort Array"
                    + "\n 4- Insert An Element"
                    + "\n 5- Delete An Element"
                    + "\n-1- Back"
                    + "\n 0- Exit"
                    + "\nCommand :");
            choice = in.nextInt();
            switch (choice) {
                case 1: {
                    printHeapArray();
                    break;
                }
                case 2: {
                    printHeapTree();
                    break;
                }
                case 3: {
                    ArrayList<T> sorted = new ArrayList<>(heap.heapArray.subList(1, heap.heapSize + 1));
                    System.out.println("Heap Sort Array : " + heapSort(sorted));
                    break;
                }
                case 4: {
                    System.out.print("Enter Element You Want To Add : ");
                    T element = (T) in.next();
                    heap.insert(element);
                    printHeapArray();
                    break;
                }
                case 5: {
                    System.out.print("Enter Element You Want To Delete : ");
                    T element = (T) in.next();
                    int index = heap.search(element);
                    if (index != -1) {
                        heap.delete(index);
                        printHeapArray();
                    } else {
                        printHeapArray();
                        System.out.println("The Element Was Not Found!");
                    }
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
}