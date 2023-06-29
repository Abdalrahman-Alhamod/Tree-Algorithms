public class Main {
    public static void main(String[] args) {
        AVL<Integer> tree = new AVL<>();
        tree.insert(2);
        tree.insert(7);
        tree.insert(1);
        tree.insert(3);
        tree.insert(5);
        tree.insert(8);
        tree.insert(9);
        tree.insert(10);
        System.out.println(tree);
    }
}