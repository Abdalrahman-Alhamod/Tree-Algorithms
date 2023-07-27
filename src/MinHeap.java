import java.util.ArrayList;

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
        useRecursiveApproach = true;
        buildMinHeapArray(heapArray);
    }

    /**
     * Heapify an element at the given index in the heap in a top-down manner
     *
     * @param index the index of the element to heapify
     * @implNote This method has a time complexity of O(log(n))
     */
    protected void heapifyTopDown(int index) {
        if (useRecursiveApproach)
            heapifyTopDownRecursive(index);
        else
            heapifyTopDownIterative(index);
    }

    /**
     * Heapify an element at the given index in the heap in a top-down manner
     * using a recursive approach.
     *
     * @param index the index of the element to heapify
     * @implNote This method has a time complexity of O(log(n))
     */
    protected void heapifyTopDownRecursive(int index) {
        int smallest = index;
        int right = getRightChildIndex(index);
        int left = getLeftChildIndex(index);
        if (heapArray.get(smallest) != null && left <= heapSize && heapArray.get(left).compareTo(heapArray.get(smallest)) < 0)
            smallest = left;
        if (heapArray.get(smallest) != null && right <= heapSize && heapArray.get(right).compareTo(heapArray.get(smallest)) < 0)
            smallest = right;
        if (smallest != index) {
            swap(index, smallest);
            heapifyTopDownRecursive(smallest);
        }
    }

    /**
     * Heapify an element at the given index in the heap in a top-down manner
     * using an iterative approach.
     *
     * @param index the index of the element to heapify
     * @implNote This method has a time complexity of O(log(n))
     */
    protected void heapifyTopDownIterative(int index) {
        int current = index;

        while (true) {
            int smallest = current;
            int rightChildIndex = getRightChildIndex(current);
            int leftChildIndex = getLeftChildIndex(current);

            if (leftChildIndex <= heapSize &&
                    heapArray.get(leftChildIndex).compareTo(heapArray.get(smallest)) < 0) {
                smallest = leftChildIndex;
            }

            if (rightChildIndex <= heapSize &&
                    heapArray.get(rightChildIndex).compareTo(heapArray.get(smallest)) < 0) {
                smallest = rightChildIndex;
            }

            if (smallest != current) {
                swap(current, smallest);
                current = smallest;
            } else {
                break; // The heap property is satisfied, no need to continue.
            }
        }
    }

    /**
     * Insert an element into the heap.
     *
     * @param element the element to be inserted
     * @implNote This method has a time complexity of O(log(n))
     */
    public void insert(T element) {
        heapArray.add(element);
        heapSize++;
        heapifyBottomUp(heapSize);
    }

    /**
     * Heapify an element at the given index in the heap in a bottom-up manner
     *
     * @param index the index of the element to heapify
     * @implNote This method has a time complexity of O(log(n))
     */
    protected void heapifyBottomUp(int index) {
        if (useRecursiveApproach)
            heapifyBottomUpRecursive(index);
        else
            heapifyBottomUpIterative(index);
    }

    /**
     * Heapify an element at the given index in the heap in a bottom-up manner
     * using a recursive approach.
     *
     * @param index the index of the element to heapify
     * @implNote This method has a time complexity of O(log(n))
     */
    protected void heapifyBottomUpRecursive(int index) {
        if (index != 1 && heapArray.get(index).compareTo(heapArray.get(getParentIndex(index))) < 0) {
            swap(index, getParentIndex(index));
            heapifyBottomUpRecursive(getParentIndex(index));
        }
    }

    /**
     * Heapify an element at the given index in the heap in a bottom-up manner
     * using an iterative approach.
     *
     * @param index the index of the element to heapify
     * @implNote This method has a time complexity of O(log(n))
     */
    protected void heapifyBottomUpIterative(int index) throws IndexOutOfBoundsException {
        int current = index;

        while (current > 1) {
            int parentIndex = getParentIndex(current);

            if (heapArray.get(current).compareTo(heapArray.get(parentIndex)) < 0) {
                swap(current, parentIndex);
                current = parentIndex;
            } else {
                break; // The heap property is satisfied, no need to continue.
            }
        }
    }

    /**
     * Search for an element in the heap and return its index.
     *
     * @param element the element to search for
     * @return the index of the element, or -1 if it is not found
     * @implNote This method has a time complexity of O(n)
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
     * @implNote This method has a time complexity of O(log(n))
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
     * This method deletes a given element from the heap if it exists.
     * It then restores the min-heap property if necessary.
     *
     * @param element the element to be deleted
     * @return true if deleting done successfully, false otherwise
     * @implNote This method has a time complexity of O(log(n))
     */
    public boolean delete(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        int index = search(element);
        if (index == -1)
            return false;
        heapArray.set(index, heapArray.get(heapSize));
        heapSize--;
        if (index != 1 && heapArray.get(index).compareTo(heapArray.get(getParentIndex(index))) > 0) {
            heapifyTopDown(index);
        } else {
            heapifyBottomUp(index);
        }
        return true;
    }

    /**
     * Build a minimum heap from the given array of elements.
     *
     * @param array the array of elements to build the heap from
     * @implNote This method has a time complexity of O(n)
     */
    public void buildMinHeapArray(ArrayList<T> array) {
        heapSize = array.size();
        array.add(0, null);
        heapArray = array;
        for (int i = heapSize / 2; i >= 1; i--) {
            heapifyTopDown(i);
        }
    }

    /**
     * This method sorts an ArrayList of elements using the heap sort algorithm.
     *
     * @param array the ArrayList of elements to be sorted
     * @return the sorted ArrayList of elements
     * @implNote This method has a time complexity of O(n log(n))
     */
    public ArrayList<T> heapSort(ArrayList<T> array) {
        // Time Complexity : O(nlog(n))
        ArrayList<T> beforeSortingArray = heapArray;
        int beforeSortingSize = heapSize;
        buildMinHeapArray(array);
        for (int i = heapSize; i >= 1; i--) {
            swap(heapArray, 1, i);
            heapSize--;
            heapifyTopDown(1);
        }
        array = heapArray;
        array.remove(0);
        heapSize = beforeSortingSize;
        heapArray = beforeSortingArray;
        return array;
    }

    /**
     * This method swaps two elements in an ArrayList.
     *
     * @param array the ArrayList containing the elements to be swapped
     * @param i     the index of the first element
     * @param j     the index of the second element
     * @implNote This method has a time complexity of O(1)
     */
    private void swap(ArrayList<T> array, int i, int j) {
        T temp = array.get(i);
        array.set(i, array.get(j));
        array.set(j, temp);
    }

}