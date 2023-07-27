import java.util.ArrayList;

/**
 * This class represents a priority queue data structure that extends the MaxHeap class.
 * The PriorityQueue class provides implementations of the enqueue and dequeue methods, which define the specific behavior of the priority queue.
 */
public class PriorityQueue<T extends Comparable<T>> extends MaxHeap<T> {

    /**
     * Constructs a new empty Priority Queue.
     */
    public PriorityQueue() {
        super();
    }

    /**
     * Constructs a new maximum heap with the given array of elements.
     *
     * @param heapArray the array of elements to initialize the heap with
     */
    public PriorityQueue(ArrayList<T> heapArray) {
        super(heapArray);
    }

    /**
     * Inserts an element into the priority queue in its appropriate position based on its priority.
     *
     * @param element the element to be inserted
     * @implNote This method has a time complexity of O(log(n))
     */
    public void enqueue(T element) {
        insert(element);
    }

    /**
     * Removes and returns the element with the highest priority from the priority queue.
     *
     * @return the element with the highest priority
     * @implNote This method has a time complexity of O(log(n))
     */
    public T dequeue() {
        return remove();
    }

    /**
     * Returns the element with the highest priority without removing it from the priority queue.
     *
     * @return the element with the highest priority
     * @implNote This method has a time complexity of O(log(n))
     */
    public T peek() {
        if (heapSize == 0) {
            return null;
        }
        return heapArray.get(1);
    }
}