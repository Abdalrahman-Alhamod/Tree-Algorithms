import java.util.ArrayList;

import static java.util.Collections.reverse;

public class Main {
    public static void main(String[] args) {
        MinHeap<Integer> heap = new MinHeap<>();
        ArrayList<Integer> array = new ArrayList<>();
        heap.setUseRecursiveApproach(false);
        for (int i = 1; i <= 1E7; i++) {
            array.add(i);
        }
        reverse(array);
        heap.heapSort(array);

        System.out.println(ExecutionTimeCalculator.getFormattedExecutionTime());
    }
}