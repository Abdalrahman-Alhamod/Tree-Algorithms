import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.print("Enter : "
                    + "\n 1- [BST] : Binary Search Tree"
                    + "\n 2- [AVL] : Balanced Binary Search Tree "
                    + "\n 3- Heap"
                    + "\n 4- Bayer-Tree"
                    + "\n 0- To Exit"
                    + "\nCommand :");
            choice = in.nextInt();
            switch (choice) {
                case 1: {
                    BST Tree = new BST(null);
                    Tree.HandleInputChoices(Tree);
                    break;
                }
                case 2: {
                    BST Tree = new AVL(null);
                    Tree.HandleInputChoices(Tree);
                    break;
                }
                case 3: {
                    Heap.HandleChoices();
                    break;
                }
                case 4: {
                    BTree.handleInputChoices();
                    break;
                }
                case 0: {
                    System.exit(0);
                }
                default: {
                    break;
                }
            }
        }


    }
}


