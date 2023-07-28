import java.util.ArrayList;

import static java.util.Collections.reverse;

public class Main {
    public static void main(String[] args) {
        MinHeap<Integer> heap = new MinHeap<>();
        ArrayList<Integer> array = new ArrayList<>();
        heap.setUseRecursiveApproach(false);
        ExecutionTimeCalculator.start();
        for (int i = 1; i <= 1E5; i++) {
            array.add(i);
        }
        reverse(array);
        heap.heapSort(array);
        ExecutionTimeCalculator.stop();
        ExecutionTimeCalculator.printExecutionTime();
    }
}