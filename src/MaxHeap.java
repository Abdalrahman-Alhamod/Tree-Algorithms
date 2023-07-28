import java.util.ArrayList;

/**
 * This class represents a max binary heap data structure that extends the abstract Heap class.
 * The MaxHeap class provides implementations of the abstract insert and remove methods, which define the specific behavior of the binary heap.
 */
public class MaxHeap<T extends Comparable<T>> extends Heap<T> {

    /**
     * Constructs a new empty minimum heap.
     */
    public MaxHeap() {
        super();
    }

    /**
     * Constructs a new maximum heap with the given array of elements.
     *
     * @param heapArray the array of elements to initialize the heap with
     */
    public MaxHeap(ArrayList<T> heapArray) {
        useRecursiveApproach = true;
        buildMaxHeapArray(heapArray);
    }


    /**
     * This method restores the max-heap property from the given index to the leaves of the heap,
     * assuming that the left and right children of the given index are already max-heaps
     *
     * @param index the index to start heapifying from
     * @implNote This method has a time complexity of O(log(n))
     */
    private void heapifyDown(int index) {
        if (useRecursiveApproach)
            heapifyDownRecursive(index);
        else
            heapifyDownIterative(index);
    }

    /**
     * This method restores the max-heap property from the given index to the leaves of the heap,
     * assuming that the left and right children of the given index are already max-heaps
     * using a recursive approach.
     *
     * @param index the index to start heapifying from
     * @implNote This method has a time complexity of O(log(n))
     */
    private void heapifyDownRecursive(int index) {
        int leftChildIndex = getLeftChildIndex(index);
        int rightChildIndex = getRightChildIndex(index);
        int largest = index;
        if (heapArray.get(largest) != null && leftChildIndex <= heapSize &&
                heapArray.get(leftChildIndex).compareTo(heapArray.get(largest)) > 0) {
            largest = leftChildIndex;
        }

        if (heapArray.get(largest) != null && rightChildIndex <= heapSize &&
                heapArray.get(rightChildIndex).compareTo(heapArray.get(largest)) > 0) {
            largest = rightChildIndex;
        }

        if (largest != index) {
            swap(index, largest);
            heapifyDownRecursive(largest);
        }
    }

    /**
     * This method restores the max-heap property from the given index to the leaves of the heap,
     * assuming that the left and right children of the given index are already max-heaps
     * using an iterative approach.
     *
     * @param index the index to start heapifying from
     * @implNote This method has a time complexity of O(log(n))
     */
    private void heapifyDownIterative(int index) {
        int current = index;

        while (true) {
            int leftChildIndex = getLeftChildIndex(current);
            int rightChildIndex = getRightChildIndex(current);
            int largest = current;

            if (leftChildIndex <= heapSize &&
                    heapArray.get(leftChildIndex).compareTo(heapArray.get(largest)) > 0) {
                largest = leftChildIndex;
            }

            if (rightChildIndex <= heapSize &&
                    heapArray.get(rightChildIndex).compareTo(heapArray.get(largest)) > 0) {
                largest = rightChildIndex;
            }

            if (largest != current) {
                swap(current, largest);
                current = largest;
            } else {
                break; // The heap property is satisfied, no need to continue.
            }
        }
    }

    /**
     * This method restores the max-heap property from the given index to the root of the heap,
     * assuming that the left and right children of the given index are already max-heap
     *
     * @param index the index to start heapifying from
     * @implNote This method has a time complexity of O(log(n))
     */
    private void heapifyUp(int index) {
        if (useRecursiveApproach)
            heapifyUpRecursive(index);
        else
            heapifyUpIterative(index);
    }

    /**
     * This method restores the max-heap property from the given index to the root of the heap,
     * assuming that the left and right children of the given index are already max-heaps
     * using a recursive approach.
     *
     * @param index the index to start heapifying from
     * @implNote This method has a time complexity of O(log(n))
     */
    private void heapifyUpRecursive(int index) {
        int parentIndex = getParentIndex(index);
        if (parentIndex >= 1 && heapArray.get(index).compareTo(heapArray.get(parentIndex)) > 0) {
            swap(index, parentIndex);
            heapifyUpRecursive(parentIndex);
        }
    }

    /**
     * This method restores the max-heap property from the given index to the root of the heap,
     * assuming that the left and right children of the given index are already max-heaps
     * using an iterative approach.
     *
     * @param index the index to start heapifying from
     * @implNote This method has a time complexity of O(log(n))
     */
    private void heapifyUpIterative(int index) {
        int current = index;
        int parentIndex;

        while (current > 1) {
            parentIndex = getParentIndex(current);

            if (heapArray.get(current).compareTo(heapArray.get(parentIndex)) > 0) {
                swap(current, parentIndex);
                current = parentIndex;
            } else {
                break; // The heap property is satisfied, no need to continue.
            }
        }
    }

    /**
     * This method inserts an element into the heap and restores the max-heap property if necessary.
     *
     * @param element the element to be inserted
     * @implNote This method has a time complexity of O(log(n))
     */
    @Override
    public void insert(T element) {
        heapArray.add(element);
        heapSize++;
        heapifyUp(heapSize);
    }

    /**
     * This method removes the element with the maximum value from the heap and restores the max-heap property if necessary.
     *
     * @return the element with the maximum value
     * @implNote This method has a time complexity of O(log(n))
     */
    @Override
    public T remove() {
        if (heapSize == 0) {
            return null;
        }
        T max = heapArray.get(1);
        heapArray.set(1, heapArray.get(heapSize));
        heapSize--;
        heapifyDown(1);
        return max;
    }

    /**
     * This method searches for an element in the heap and returns its index if found, or -1 otherwise.
     *
     * @param element the element to search for
     * @return the index of the element if found, or -1 otherwise
     * @implNote This method has a time complexity of O(n)
     */
    public int search(T element) {
        for (int i = 1; i <= heapSize; i++) {
            if (heapArray.get(i).equals(element)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * This method deletes a given element from the heap if it exists.
     * It then restores the max-heap property if necessary.
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
            heapifyUp(index);
        } else {
            heapifyDown(index);
        }
        return true;
    }

    /**
     * This method builds a max heap from an ArrayList of elements.
     * It assumes that the ArrayList is a complete binary tree,
     * and starts heapifying from the last non-leaf node to the root.
     *
     * @param array the ArrayList of elements to be transformed into a max heap
     * @implNote This method has a time complexity of O(n)
     */
    public void buildMaxHeapArray(ArrayList<T> array) {
        // Time Complexity : O(n)
        heapSize = array.size();
        array.add(0, null);
        heapArray = array;
        for (int i = heapSize / 2; i >= 1; i--) {
            heapifyDown(i);
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
        buildMaxHeapArray(array);
        for (int i = heapSize; i >= 1; i--) {
            swap(heapArray, 1, i);
            heapSize--;
            heapifyDown(1);
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