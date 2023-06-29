import java.util.ArrayList;
import java.util.Scanner;

public class MinHeap extends Heap {
    public void minHeapifyTopDown(ArrayList<Integer> array, int elementsNumber, int index) {
        // because The Tree Is Complete :
        // Time Complexity : O(log(n))
        int smallest = index;
        int right = rightSonIndex(index);
        int left = leftSonIndex(index);
        if (left <= elementsNumber && array.get(left) < array.get(smallest))
            smallest = left;
        if (right <= elementsNumber && array.get(right) < array.get(smallest))
            smallest = right;
        if (smallest != index) {
            swap(array, index, smallest);
            minHeapifyTopDown(array, elementsNumber, smallest);
        }
    }


    public void insert(int element) {
        if (heapArray.size() > (heapSize + 1))
            heapArray.set(heapSize + 1, element);
        else
            heapArray.add(element);
        heapSize++;
        minHeapifyBottomUp(heapSize);
    }

    private void minHeapifyBottomUp(int index) {
        if (index != 1 && heapArray.get(index) < heapArray.get(parentIndex(index))) {
            swap(heapArray, index, parentIndex(index));
            minHeapifyBottomUp(parentIndex(index));
        }
    }

    public int search(int element) {
        for (int i = 1; i <= heapSize; i++) {
            if (element == heapArray.get(i))
                return i;
        }
        return -1;
    }

    public void delete(int index) {
        if (index <= 0 || index > heapSize)
            return;
        heapArray.set(index, heapArray.get(heapSize));
        heapSize--;
        if (index != 1 && heapArray.get(index) < heapArray.get(parentIndex(index)))
            minHeapifyBottomUp(index);
        else
            minHeapifyTopDown(heapArray, heapSize, index);
    }

    public void buildMinHeap() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Number Of Elements : ");
        int n = in.nextInt(), element;
        ArrayList<Integer> array = new ArrayList<>(n + 1);
        System.out.print("Enter Elements : ");
        array.add(0);
        for (int i = 1; i <= n; i++) {
            element = in.nextInt();
            array.add(element);
        }
        heapSize = array.size() - 1;
        heapArray = array;
        buildMinHeapArray(heapArray);
        printHeapArray();
    }

    public void buildMinHeapArray(ArrayList<Integer> array) {
        // Time Complexity : O(nlog(n))
        for (int i = array.size() / 2; i >= 1; i--) {
            minHeapifyTopDown(heapArray, heapSize, i);
        }
    }

    public ArrayList<Integer> HeapSortDescending(ArrayList<Integer> array) {
        // Time Complexity : O(nlog(n))
        buildMinHeapArray(array);
        int elementsNumber = array.size() - 1;
        for (int i = array.size() - 1; i >= 2; i--) {
            swap(array, 1, i);
            elementsNumber--;
            minHeapifyTopDown(array, elementsNumber, 1);
        }
        return array;
    }

    public void HandleInputChoices(MinHeap heap) {
        Scanner in = new Scanner(System.in);
        buildMinHeap();
        int choice;
        boolean input = false;
        while (!input) {
            System.out.print("Enter : "
                    + "\n 1- Print Min Heap Array"
                    + "\n 2- Print Min Heap Tree"
                    + "\n 3- Print Heap Sort Descending Array"
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
                    ArrayList<Integer> sorted = new ArrayList<>();
                    for (int i = 0; i <= heapSize; i++) {
                        sorted.add(heapArray.get(i));
                    }
                    System.out.println("Heap Sort Array : " + HeapSortDescending(sorted));
                    break;
                }
                case 4: {
                    System.out.print("Enter Element You Want To Add : ");
                    int element = in.nextInt();
                    insert(element);
                    printHeapArray();
                    break;
                }
                case 5: {
                    System.out.print("Enter Element You Want To Delete : ");
                    int element = in.nextInt();
                    int index = search(element);
                    if (index != -1) {
                        delete(index);
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
