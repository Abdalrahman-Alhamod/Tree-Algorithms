import java.util.ArrayList;
import java.util.Scanner;

public abstract class Heap {
    ArrayList<Integer> heapArray;
    int heapSize;

    public Heap() {
        heapArray = new ArrayList<>();
    }

    public Heap(ArrayList<Integer> HeapArray) {
        this.heapArray = HeapArray;
    }

    public int rightSonIndex(int index) {
        return 2 * index + 1;
    }

    public int leftSonIndex(int index) {
        return 2 * index;
    }

    public int parentIndex(int index) {
        return index / 2;
    }

    public void swap(ArrayList<Integer> heapArray, int one, int two) {
        int temp = heapArray.get(one);
        heapArray.set(one, heapArray.get(two));
        heapArray.set(two, temp);
    }

    public void printHeapArray() {
        System.out.print("Heap Array : ");
        for (int i = 1; i <= heapSize; i++) {
            System.out.print(heapArray.get(i) + " ");
        }
        System.out.println();
    }

    public static void HandleChoices() {
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
                    MaxHeap heap = new MaxHeap();
                    heap.HandleInputChoices(heap);
                    break;
                }
                case 2: {
                    MinHeap heap = new MinHeap();
                    heap.HandleInputChoices(heap);
                    break;
                }
                case 3: {
                    MaxHeap heap = new MaxHeap();
                    heap.buildMaxHeap();
                    ArrayList<Integer> sorted = heap.HeapSort(heap.heapArray);
                    sorted.remove(0);
                    System.out.println("Heap Sort Array : " + sorted);
                    break;
                }
                case 4: {
                    PriorityQueue queue = new PriorityQueue();
                    queue.HandleInputChoices(queue);
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

    public void printHeapTree() {
        BinaryTree tree = new BST(null);
        for (int i = 1; i <= heapSize; i++) {
            tree.add(heapArray.get(i));
        }
        tree.printTree(tree.root);
    }
}
