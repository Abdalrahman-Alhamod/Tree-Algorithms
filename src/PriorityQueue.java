import java.util.Scanner;

public class PriorityQueue extends MaxHeap {
    public int top() {
        return heapArray.get(1);
    }

    public void enqueue(int element) {
        // Time Complexity : O(log(n))
        heapSize++;
        heapArray.add(heapSize, element);
        int index = heapSize;
        while (index != 1 && heapArray.get(index) > heapArray.get(parentIndex(index))) {
            swap(heapArray, index, parentIndex(index));
            index = parentIndex(index);
        }
    }

    public void dequeue() {
        // Time Complexity : O(log(n))
        if (heapSize != 0) {
            swap(heapArray, 1, heapSize);
            heapSize--;
            maxHeapifyTopDown(heapArray, heapSize, 1);
        } else {
            System.out.println("The Queue Is Empty!");
        }
    }

    public void printHeapArray() {
        System.out.print("Priority Queue : ");
        for (int i = 1; i <= heapSize; i++) {
            System.out.print(heapArray.get(i) + " ");
        }
        System.out.println();
    }

    public void HandleInputChoices(MaxHeap heap) {
        Scanner in = new Scanner(System.in);
        buildMaxHeap();
        int choice;
        boolean input = false;
        while (!input) {
            System.out.print("Enter : "
                    + "\n 1- Top"
                    + "\n 2- Enqueue"
                    + "\n 3- Dequeue"
                    + "\n-1- Back"
                    + "\n 0- Exit"
                    + "\nCommand :");
            choice = in.nextInt();
            switch (choice) {
                case 1: {
                    printHeapArray();
                    if (heapSize >= 1)
                        System.out.println("Top : " + top());
                    else
                        System.out.println("The Queue Is Empty!");
                    break;
                }
                case 2: {
                    System.out.print("Enter Element : ");
                    int element = in.nextInt();
                    enqueue(element);
                    printHeapArray();
                    break;
                }
                case 3: {
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
