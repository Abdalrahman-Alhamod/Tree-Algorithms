import java.util.Scanner;

/**
 * This class represents a priority queue data structure that extends the MaxHeap class.
 * The PriorityQueue class provides implementations of the enqueue and dequeue methods, which define the specific behavior of the priority queue.
 */
public class PriorityQueue<T extends Comparable<T>> extends MaxHeap<T> {

    /**
     * Inserts an element into the priority queue in its appropriate position based on its priority.
     *
     * @param element the element to be inserted
     */
    public void enqueue(T element) {
        insert(element);
    }

    /**
     * Removes and returns the element with the highest priority from the priority queue.
     *
     * @return the element with the highest priority
     */
    public T dequeue() {
        return remove();
    }

    /**
     * Returns the element with the highest priority without removing it from the priority queue.
     *
     * @return the element with the highest priority
     */
    public T peek() {
        if (heapSize == 0) {
            return null;
        }
        return heapArray.get(0);
    }


    /**
     * This method handles user input choices for a PriorityQueue object.
     * It prompts the user to enter a command to print the priority queue, print the element with the highest priority,
     * add an element to the priority queue, remove an element from the priority queue, go back to the previous menu, or exit the program.
     *
     * @param queue the PriorityQueue object to handle input choices for
     */
    public void handleInputChoices(PriorityQueue<T> queue) {
        Scanner in = new Scanner(System.in);
        buildMaxHeap();
        int choice;
        boolean input = false;
        while (!input) {
            System.out.print("Enter : "
                    + "\n 1- Print Priority Queue"
                    + "\n 2- Print Element With Highest Priority"
                    + "\n 3- Add An Element To The Priority Queue"
                    + "\n 4- Remove An Element From The Priority Queue"
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
                    if (heapSize >= 1)
                        System.out.println("Element With Highest Priority : " + peek());
                    else
                        System.out.println("The Priority Queue Is Empty!");
                    break;
                }
                case 3: {
                    System.out.print("Enter Element You Want To Add : ");
                    T element = (T) in.next();
                    enqueue(element);
                    printHeapArray();
                    break;
                }
                case 4: {
                    dequeue();
                    printHeapArray();
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