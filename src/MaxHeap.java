import java.util.ArrayList;
import java.util.Scanner;

public class MaxHeap extends Heap {
    protected void maxHeapifyTopDown(ArrayList<Integer> array, int elementsNumber, int index) {
        // because The Tree Is Complete :
        // Time Complexity : O(log(n))
        int largest = index;
        int right = rightSonIndex(index);
        int left = leftSonIndex(index);
        if (left <= elementsNumber && array.get(left) > array.get(largest))
            largest = left;
        if (right <= elementsNumber && array.get(right) > array.get(largest))
            largest = right;
        if (largest != index) {
            swap(array, index, largest);
            maxHeapifyTopDown(array, elementsNumber, largest);
        }
    }

    public void insert(int element) {
        if (heapArray.size() > (heapSize + 1))
            heapArray.set(heapSize + 1, element);
        else
            heapArray.add(element);
        heapSize++;
        maxHeapifyBottomUp(heapSize);
    }

    private void maxHeapifyBottomUp(int index) {
        if (index != 1 && heapArray.get(index) > heapArray.get(parentIndex(index))) {
            swap(heapArray, index, parentIndex(index));
            maxHeapifyBottomUp(parentIndex(index));
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
        if (index != 1 && heapArray.get(index) > heapArray.get(parentIndex(index)))
            maxHeapifyBottomUp(index);
        else
            maxHeapifyTopDown(heapArray, heapSize, index);
    }

    public void buildMaxHeap() {
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
        buildMaxHeapArray(heapArray);
        printHeapArray();
    }

    private void buildMaxHeapArray(ArrayList<Integer> array) {
        // Time Complexity : O(nlog(n))
        for (int i = array.size() / 2; i >= 1; i--) {
            maxHeapifyTopDown(heapArray, heapSize, i);
        }
    }

    public ArrayList<Integer> HeapSort(ArrayList<Integer> array) {
        // Time Complexity : O(nlog(n))
        buildMaxHeapArray(array);
        int elementsNumber = array.size() - 1;
        for (int i = array.size() - 1; i >= 2; i--) {
            swap(array, 1, i);
            elementsNumber--;
            maxHeapifyTopDown(array, elementsNumber, 1);
        }
        return array;
    }

    public void HandleInputChoices(MaxHeap heap) {
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
                    ArrayList<Integer> sorted = new ArrayList<>();
                    for (int i = 0; i <= heapSize; i++) {
                        sorted.add(heapArray.get(i));
                    }
                    System.out.println("Heap Sort Array : " + HeapSort(sorted));
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
