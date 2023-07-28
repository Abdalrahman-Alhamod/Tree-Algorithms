import java.util.function.Consumer;

/**
 * Author :
 * <pre>
 *
 *   /$$$$$$  /$$             /$$        /$$   /$$ /$$      /$$
 *  /$$__  $$| $$            | $$       | $$  | $$| $$$    /$$$
 * | $$  \ $$| $$$$$$$   /$$$$$$$       | $$  | $$| $$$$  /$$$$
 * | $$$$$$$$| $$__  $$ /$$__  $$       | $$$$$$$$| $$ $$/$$ $$
 * | $$__  $$| $$  \ $$| $$  | $$       | $$__  $$| $$  $$$| $$
 * | $$  | $$| $$  | $$| $$  | $$       | $$  | $$| $$\  $ | $$
 * | $$  | $$| $$$$$$$/|  $$$$$$$       | $$  | $$| $$ \/  | $$
 * |__/  |__/|_______/  \_______//$$$$$$|__/  |__/|__/     |__/
 *                              |______/
 *
 * </pre>
 * Contact me :<br>
 * <a href="mailto:abd.alrrahman.alhamod@gmail.com">E-mail</a><br>
 * <a href="https://github.com/Abdalrahman-Alhamod">Github</a><br>
 * <a href="https://www.linkedin.com/in/abd-alrrahman-alhamod/">LinkedIn</a><br>
 * <a href="https://www.facebook.com/profile.php?id=100011427430343">Facebook</a>
 */
public class Main {
    public static void main(String[] args) {

        Consumer<BST<Integer>> testBST = integerBST -> {
            integerBST.insert(4);
            integerBST.insert(7);
            integerBST.insert(2);
            integerBST.insert(9);
            integerBST.insert(10);
            integerBST.insert(3);
            integerBST.insert(8);
            integerBST.insert(1);
            System.out.println(integerBST);
        };

        System.out.println("#################################################");

        // BST Recursive Testing
        System.out.println("Recursive BST");
        ExecutionTimeCalculator.start();
        BST<Integer> bstTree = new BST<>();
        bstTree.setUseRecursiveApproach(true);
        testBST.accept(bstTree);
        ExecutionTimeCalculator.stop();
        ExecutionTimeCalculator.printExecutionTime();

        System.out.println("#################################################");

        // BST Iterative Testing
        System.out.println("Iterative BST");
        ExecutionTimeCalculator.start();
        bstTree.clear();
        bstTree.setUseRecursiveApproach(false);
        testBST.accept(bstTree);
        ExecutionTimeCalculator.stop();
        ExecutionTimeCalculator.printExecutionTime();

        System.out.println("*************************************************");

        Consumer<AVL<Integer>> avlTest = integerAVL -> {
            for (int i = 1; i <= 13; i++) {
                integerAVL.insert(i);
            }
            System.out.println(integerAVL);
        };

        System.out.println("#################################################");

        // AVL Recursive Testing
        System.out.println("Recursive AVL");
        AVL<Integer> avlTree = new AVL<>();
        avlTree.setUseRecursiveApproach(true);
        ExecutionTimeCalculator.start();
        avlTest.accept(avlTree);
        ExecutionTimeCalculator.stop();
        ExecutionTimeCalculator.printExecutionTime();

        System.out.println("#################################################");

        // AVL Recursive Testing
        System.out.println("Iterative AVL");
        avlTree.clear();
        avlTree.setUseRecursiveApproach(false);
        ExecutionTimeCalculator.start();
        avlTest.accept(avlTree);
        ExecutionTimeCalculator.stop();
        ExecutionTimeCalculator.printExecutionTime();

        System.out.println("*************************************************");

        Consumer<PriorityQueue<Integer>> testPriorityQueue = integerPriorityQueue -> {
            integerPriorityQueue.enqueue(3);
            integerPriorityQueue.enqueue(7);
            integerPriorityQueue.enqueue(8);
            integerPriorityQueue.enqueue(2);
            integerPriorityQueue.enqueue(10);
            integerPriorityQueue.enqueue(9);
            integerPriorityQueue.enqueue(4);
            integerPriorityQueue.enqueue(11);
            System.out.println(integerPriorityQueue);
            integerPriorityQueue.printHeapTree();
        };

        System.out.println("#################################################");

        // Priority Queue Recursive Testing
        System.out.println("Recursive Priority Queue (Max Heap)");
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.setUseRecursiveApproach(true);
        ExecutionTimeCalculator.start();
        testPriorityQueue.accept(priorityQueue);
        ExecutionTimeCalculator.stop();
        ExecutionTimeCalculator.printExecutionTime();

        System.out.println("#################################################");

        // Priority Queue Recursive Testing
        System.out.println("Iterative Priority Queue (Max Heap)");
        priorityQueue = new PriorityQueue<>();
        priorityQueue.setUseRecursiveApproach(false);
        ExecutionTimeCalculator.start();
        testPriorityQueue.accept(priorityQueue);
        ExecutionTimeCalculator.stop();
        ExecutionTimeCalculator.printExecutionTime();

        System.out.println("#################################################");
    }
}