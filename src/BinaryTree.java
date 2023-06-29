import java.util.*;

public abstract class BinaryTree implements Tree {
    Node root;
    private Vector<Node> PreTreeVector;

    public BinaryTree() {
        root = null;
    }

    public BinaryTree(Node root) {
        this.root = root;
    }

    public boolean isStrict(Node node) {
        if (node == null)
            return true;
        if ((node.left != null && node.right != null) || (node.left == null && node.right == null))
            return isStrict(node.left) && isStrict(node.right);
        return false;
    }

    public boolean isComplete(Node node) {
        if (node == null) {
            return true;
        }
        Queue<Node> queue = new LinkedList<>();
        boolean isNonFullNodeSeen = false;
        queue.offer(node);
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            if (curr.left != null) {
                if (isNonFullNodeSeen) {
                    return false;
                }
                queue.offer(curr.left);
            } else {
                isNonFullNodeSeen = true;
            }
            if (curr.right != null) {
                if (isNonFullNodeSeen) {
                    return false;
                }
                queue.offer(curr.right);
            } else {
                isNonFullNodeSeen = true;
            }
        }
        return true;
    }

    public boolean isFull(Node node) {
        return size(node) == getMaxNodes(node);
    }

    int size(Node node) {
        if (node == null)
            return 0;
        return 1 + size(node.left) + size(node.right);
    }

    public int getMaxLevel(Node node) {
        if (node == null) {
            return 0;
        }
        return Math.max(getMaxLevel(node.left), getMaxLevel(node.right)) + 1;
    }

    public int getHeight(Node node) {
        if (node == null) {
            return -1;
        }
        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    public int getMaxHeight(Node node) {
        return size(node) - 1;
    }

    public int getMinHeight(Node node) {
        return (int) Math.floor(Math.log(size(node)));
    }

    public int getMaxNodes(Node node) {
        return (1 << (getHeight(node) + 1)) - 1;
    }

    public int getMinNodes(Node node) {
        return getHeight(node) + 1;
    }

    public int countNodesCompleteBT(Node node) {
        // Complexity : O(h) But Complete Binary Tree => h = log(n)
        // ==> Complexity : O(log(n))
        if (node == null)
            return 0;
        int leftHeight = leftHeight(node);
        int rightHeight = rightHeight(node);
        if (leftHeight == rightHeight)
            return (1 << (leftHeight + 1)) - 1;
        else
            return 1 + countNodesCompleteBT(node.left) + countNodesCompleteBT(node.right);

    }

    private int leftHeight(Node node) {
        if (node == null)
            return -1;
        return 1 + leftHeight(node.left);
    }

    private int rightHeight(Node node) {
        if (node == null)
            return -1;
        return 1 + rightHeight(node.right);
    }

    public int countLeaves(Node node) {
        if (node == null)
            return 0;
        if (node.left == null && node.right == null)
            return 1;
        return countLeaves(node.left) + countLeaves(node.right);
    }

    public int countNonLeaves(Node node) {
        if (node == null || (node.left == null && node.right == null))
            return 0;
        return 1 + countNonLeaves(node.right) + countNonLeaves(node.left);
    }

    public boolean isBST(Node node) {
        Node min = new Node(Integer.MIN_VALUE);
        Node max = new Node(Integer.MAX_VALUE);
        return isBSTHelper(node, min, max);
    }

    private boolean isBSTHelper(Node node, Node left, Node right) {
        // Complexity : O(n)
        if (node == null)
            return true;
        if (left != null && node.value <= left.value)
            return false;
        if (right != null && node.value >= right.value)
            return false;
        return isBSTHelper(node.left, left, node) && isBSTHelper(node.right, node, right);
    }

    public boolean check(Node node, int data) {
        if (node == null)
            return false;
        if (node.value == data)
            return true;
        return check(node.left, data) || check(node.right, data);
    }

    public Node find(Node node, int data) {
        if (node == null || node.value == data)
            return node;
        Node founded = find(node.left, data);
        if (founded == null)
            return find(node.right, data);
        else
            return founded;
    }

    public int count(Node node, int data) {
        if (node == null)
            return 0;
        if (node.value == data)
            return count(node.right, data) + count(node.left, data) + 1;
        else
            return count(node.right, data) + count(node.left, data);
    }

    public void preOrder(Node node) {
        if (node != null) {
            System.out.print(node.value + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.value + " ");
            inOrder(node.right);
        }
    }

    public void postOrder(Node node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.print(node.value + " ");
        }
    }

    public void AddRoot(int data) {
        Node son = new Node(data);
        if (root != null) {
            son.right = root.right;
            son.left = root.left;
            if (root.left != null)
                root.left.parent = son;
            if (root.right != null)
                root.right.parent = son;
        }
        root = son;
    }

    public void buildBalanced(int level) {
        int p = 1 << level;
        p /= 2;
        Node node;
        node = new Node(p, null, null);
        insert(node);
        int dif = p;
        buildHelper(node, dif / 2);
    }

    private void buildHelper(Node node, int dif) {
        if (node != null && dif != 0) {
            insert(new Node(node.value - dif, null, null));
            buildHelper(node.left, dif / 2);
            insert(new Node(node.value + dif, null, null));
            buildHelper(node.right, dif / 2);
        }
    }

    static int preIndex = 0;

    public Node buildTreeInPre(int[] in, int[] pre, int inStart, int inEnd) {

        if (inStart > inEnd)
            return null;

        Node node = new Node(pre[preIndex++], null, null);

        if (inStart == inEnd)
            return node;

        int inIndex = searchElement(in, inStart, inEnd, node.value);

        node.left = buildTreeInPre(in, pre, inStart, inIndex - 1);

        if (node.left != null)
            node.left.parent = node;

        node.right = buildTreeInPre(in, pre, inIndex + 1, inEnd);

        if (node.right != null)
            node.right.parent = node;

        return node;
    }

    private int searchElement(int[] a, int iStart, int iEnd, int element) {
        int i;
        for (i = iStart; i <= iEnd; i++) {
            if (a[i] == element)
                break;
        }
        return i;
    }

    private void makeFullTree(Node root) {
        int height = getMaxLevel(root);
        makeFullTreeHelper(root, height);
    }

    private void makeFullTreeHelper(Node node, int height) {
        if (node == null || height == 1) {
            return;
        }
        if (node.left == null && node.right == null) {
            // If the node is a leaf, add left and right children as 0
            node.left = new Node(0, null, null);
            node.right = new Node(0, null, null);
        } else if (node.left == null) {
            // If the node has only a right child, add a left child as 0
            node.left = new Node(0, null, null);
        } else if (node.right == null) {
            // If the node has only a left child, add a right child as 0
            node.right = new Node(0, null, null);
        }
        makeFullTreeHelper(node.left, height - 1);
        makeFullTreeHelper(node.right, height - 1);
    }

    public void printTree(Node root) {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int maxLevel = getMaxLevel(root);
        StringBuilder sb = new StringBuilder();
        printNodeInternal(Collections.singletonList(root), 1, maxLevel, sb);
        System.out.println(sb);
    }

    private static void printNodeInternal(List<Node> nodes, int level, int maxLevel, StringBuilder sb) {
        if (nodes.isEmpty() || isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        appendWhitespaces(firstSpaces, sb);

        List<Node> newNodes = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node != null) {
                sb.append(node.value);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                sb.append(" ");
            }

            appendWhitespaces(betweenSpaces, sb);
        }
        sb.append("\n");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                appendWhitespaces(firstSpaces - i, sb);
                if (nodes.get(j) == null) {
                    appendWhitespaces(endgeLines + endgeLines + i + 1, sb);
                    continue;
                }

                if (nodes.get(j).left != null)
                    if (nodes.get(j).left.value > 10)
                        sb.append(" /");
                    else
                        sb.append("/");
                else
                    appendWhitespaces(1, sb);

                appendWhitespaces(i + i - 1, sb);

                if (nodes.get(j).right != null)
                    if (nodes.get(j).right.value > 10)
                        sb.append("\\ ");
                    else
                        sb.append("\\");

                else
                    appendWhitespaces(1, sb);

                appendWhitespaces(endgeLines + endgeLines - i, sb);
            }

            sb.append("\n");
        }

        printNodeInternal(newNodes, level + 1, maxLevel, sb);
    }

    private static void appendWhitespaces(int count, StringBuilder sb) {
        for (int i = 0; i < count; i++)
            sb.append(" ");
    }

    private static boolean isAllElementsNull(List<Node> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

    public void add(int value) {
        if (root == null) {
            root = new Node(value);
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.left == null) {
                current.left = new Node(value);
                return;
            } else {
                queue.offer(current.left);
            }
            if (current.right == null) {
                current.right = new Node(value);
                return;
            } else {
                queue.offer(current.right);
            }
        }
    }

    private void preArrayRepresentation(Node node) {
        if (node != null) {
            PreTreeVector.add(node);
            preArrayRepresentation(node.left);
            preArrayRepresentation(node.right);
        }
    }

    public Node search(Node node, int data) {
        preArrayRepresentation(root);
        int index = 0;
        for (index = 0; index < PreTreeVector.size(); index++) {
            if (PreTreeVector.get(index).value == data)
                return PreTreeVector.get(index);
        }
        return null;
    }

    public Node min(Node node) {
        PreTreeVector.clear();
        preArrayRepresentation(node);
        Node min = new Node(Integer.MAX_VALUE);
        for (Node n : PreTreeVector) {
            if (n.value < min.value)
                min = n;
        }
        return min;
    }

    public Node max(Node node) {
        PreTreeVector.clear();
        preArrayRepresentation(node);
        Node max = new Node(Integer.MIN_VALUE);
        for (Node n : PreTreeVector) {
            if (n.value > max.value)
                max = n;
        }
        return max;
    }

    private int[] inOrderMatrix(Node node) {
        List<Integer> inorderList = new ArrayList<>();
        if (node != null) {
            int[] left = inOrderMatrix(node.left);
            for (int i = 0; i < left.length; i++) {
                inorderList.add(left[i]);
            }
            inorderList.add(node.value);
            int[] right = inOrderMatrix(node.right);
            for (int i = 0; i < right.length; i++) {
                inorderList.add(right[i]);
            }
        }
        return inorderList.stream().mapToInt(Integer::intValue).toArray();
    }

    boolean isInorder() {
        int[] arr = inOrderMatrix(root);
        int n = arr.length;
        // Complexity : O(n)
        if (n == 0 || n == 1)
            return true;
        for (int i = 1; i < n; i++) {
            if (arr[i - 1] > arr[i])
                return false;
        }
        return true;
    }

    public void HandleInputChoices(BST Tree) {
        Scanner in = new Scanner(System.in);
        int element, choice;
        boolean input = false;
        while (!input) {
            System.out.print("Enter : "
                    + "\n 1- To Build A Balanced Tree By Entering Tree Levels Number"
                    + "\n 2- To Build A Tree By Entering Input Matrix"
                    + "\n 3- To Build A Tree By Entering In-Matrix & Pre-Matrix"
                    + "\nCommand :");
            choice = in.nextInt();
            switch (choice) {
                case 1: {
                    System.out.print("Enter Tree Levels Number : ");
                    int level;
                    level = in.nextInt();
                    Tree.buildBalanced(level);
                    input = true;
                    break;
                }
                case 2: {
                    System.out.print("Enter Number Of Elements : ");
                    int n = in.nextInt();
                    System.out.print("Enter Elements : ");
                    for (int i = 0; i < n; i++) {
                        element = in.nextInt();
                        Node node = new Node(element, null, null);
                        Tree.insert(node);
                    }
                    input = true;
                    break;
                }
                case 3: {
                    System.out.print("Enter Number Of Elements : ");
                    int n = in.nextInt();
                    int[] In = new int[n];
                    int[] Pre = new int[n];
                    System.out.print("Enter In-Matrix Elements : ");
                    for (int i = 0; i < n; i++) {
                        In[i] = in.nextInt();
                    }
                    System.out.print("Enter Pre-Matrix Elements : ");
                    for (int i = 0; i < n; i++) {
                        Pre[i] = in.nextInt();
                    }
                    Tree.root = Tree.buildTreeInPre(In, Pre, 0, n - 1);
                    input = true;
                    break;
                }
                default: {
                    break;
                }
            }
        }
        Tree.printTree(Tree.root);
        input = false;
        while (!input) {
            System.out.print("Enter :"
                    + "\n 1- To Get Min"
                    + "\n 2- To Get Max"
                    + "\n 3- To Add Element as Leaf"
                    + "\n 4- To Add Element as Root"
                    + "\n 5- To Delete Element"
                    + "\n 6- To Check Element Existence"
                    + "\n 7- To Count Element Existence"
                    + "\n 8- To Search About Element"
                    + "\n 9- To Get The Next Element"
                    + "\n10- To Get The Previous Element"
                    + "\n11- Preorder Printing"
                    + "\n12- Inorder Printing"
                    + "\n13- PostOrder Printing"
                    + "\n14- Print All Orders "
                    + "\n15- To Check If Its Inorder "
                    + "\n16- To Check If Dead End Exist "
                    + "\n17- To Get The Lowest Common Ancestor Between Two Elements "
                    + "\n18- To Calculate Distance Between Two Elements "
                    + "\n19- To Count The Number Of Leaves"
                    + "\n20- To Count The Number Of Non-Leaves"
                    + "\n21- To Check If The Tree Is Full"
                    + "\n22- To Check If The Tree Is Complete"
                    + "\n23- To Check If The Tree Is Strict"
                    + "\n24- To Count Nodes In A Complete Tree"
                    + "\n25- To Convert The BST To AVL"
                    + "\n-1- To Back"
                    + "\n 0- To Exit"
                    + "\nCommand :");
            choice = in.nextInt();
            switch (choice) {
                case 1: {
                    Tree.printTree(Tree.root);
                    System.out.println("Min = " + Tree.min(Tree.root).value);
                    break;
                }
                case 2: {
                    Tree.printTree(Tree.root);
                    System.out.println("Max = " + Tree.max(Tree.root).value);
                    break;
                }
                case 3: {
                    System.out.print("Enter Element that You Want To Add : ");
                    element = in.nextInt();
                    Node node = new Node(element, null, null);
                    Tree.insert(node);
                    Tree.printTree(Tree.root);
                    break;

                }
                case 4: {
                    if (Tree.getClass() != AVL.class) {
                        System.out.print("Enter Element that You Want To Add : ");
                        element = in.nextInt();
                        Tree.AddRoot(element);
                        Tree.printTree(Tree.root);
                    } else {
                        Tree.printTree(Tree.root);
                        System.out.println("Its Not Allowed To Add Root In AVL!");
                    }
                    break;
                }
                case 5: {
                    System.out.print("Enter Element that You Want To Delete : ");
                    element = in.nextInt();
                    Node node = Tree.search(Tree.root, element);
                    if (node == null) {
                        Tree.printTree(Tree.root);
                        System.out.println("Element Not Found!");
                    } else {
                        Tree.root = Tree.erase(Tree.root, element);
                        Tree.printTree(Tree.root);
                    }
                    break;
                }
                case 6: {
                    System.out.print("Enter Element that You Want To Check : ");
                    element = in.nextInt();
                    Tree.printTree(Tree.root);
                    if (Tree.check(Tree.root, element))
                        System.out.println("Element Does Exist!");
                    else
                        System.out.println("Element Does NOT Exist!");
                    break;
                }
                case 7: {
                    System.out.print("Enter Element that You Want To Count : ");
                    element = in.nextInt();
                    Tree.printTree(Tree.root);
                    System.out.println("Element Does Exist " + Tree.count(Tree.root, element) + " Times!");
                    break;
                }
                case 8: {
                    System.out.print("Enter Element that You Want To Search About : ");
                    element = in.nextInt();
                    Node node = Tree.find(Tree.root, element);
                    Tree.printTree(Tree.root);
                    if (node == null) {
                        System.out.println("Element Was NOT Found!");
                    } else {
                        System.out.println("Element Was Found!\nElement : " + node.value);
                        if (node.left != null) {
                            System.out.println("Left Child : " + node.left.value);
                        } else {
                            System.out.println("Left Child : NULL");
                        }
                        if (node.right != null) {
                            System.out.println("Right Child : " + node.right.value);
                        } else {
                            System.out.println("Right Child : NULL");
                        }
                        if (node.parent != null) {
                            System.out.println("Parent : " + node.parent.value);
                        } else {
                            System.out.println("Parent : NULL");
                        }
                        if (Tree.getClass() == AVL.class) {
                            System.out.println("Height : " + node.height);
                            System.out.println("Balance : " + node.balance);
                        }
                    }
                    break;
                }
                case 9: {
                    System.out.print("Enter Element that You Want To Get Its Next : ");
                    element = in.nextInt();
                    Tree.printTree(Tree.root);
                    Node node = Tree.find(Tree.root, element);
                    if (node == null) {
                        System.out.println("The Element That You Have Entered Does Not Exist!");
                    } else {
                        Node next = Tree.successor(node);
                        if (next == null) {
                            System.out.println("Next Element Was Not Found!\nThe Element You Have Entered Is Max!");
                        } else {
                            System.out.println("Next Element : " + next.value);
                        }
                    }
                    break;
                }
                case 10: {
                    System.out.print("Enter Element that You Want To Get Its Previous : ");
                    element = in.nextInt();
                    Tree.printTree(Tree.root);
                    Node node = Tree.find(Tree.root, element);
                    if (node == null) {
                        System.out.println("The Element That You Have Entered Does Not Exist!");
                    } else {
                        Node next = Tree.predecessor(node);
                        if (next == null) {
                            System.out.println("Next Element Was Not Found!\nThe Element You Have Entered Is Min!");
                        } else {
                            System.out.println("Next Element : " + next.value);
                        }
                    }
                    break;
                }
                case 11: {
                    Tree.printTree(Tree.root);
                    System.out.print("Preorder : ");
                    Tree.preOrder(Tree.root);
                    System.out.println();
                    break;
                }
                case 12: {
                    Tree.printTree(Tree.root);
                    System.out.print("Inorder : ");
                    Tree.inOrder(Tree.root);
                    System.out.println();
                    break;
                }
                case 13: {
                    Tree.printTree(Tree.root);
                    System.out.print("Postorder : ");
                    Tree.postOrder(Tree.root);
                    System.out.println();
                    break;
                }
                case 14: {
                    Tree.printTree(Tree.root);
                    System.out.print("Preorder : ");
                    Tree.preOrder(Tree.root);
                    System.out.println();
                    System.out.print("Inorder : ");
                    Tree.inOrder(Tree.root);
                    System.out.println();
                    System.out.print("Postorder : ");
                    Tree.postOrder(Tree.root);
                    System.out.println();
                    break;
                }
                case 15: {
                    Tree.printTree(Tree.root);
                    if (Tree.isInorder())
                        System.out.println("Its Inorder!");
                    else
                        System.out.println("Its Not Inorder!");
                    break;
                }
                case 16: {
                    Tree.printTree(Tree.root);
                    if (Tree.deadEnd(Tree.root))
                        System.out.println("Dead End Does Exist!");
                    else
                        System.out.println("Dead End Does Not Exist!");
                    break;
                }
                case 17: {
                    System.out.print("Enter the First Element : ");
                    int one = in.nextInt();
                    System.out.print("Enter the Second Element : ");
                    int two = in.nextInt();
                    Node n1 = Tree.search(Tree.root, one);
                    Node n2 = Tree.search(Tree.root, two);
                    if (n1 != null && n2 != null) {
                        Node lca = Tree.LCA(Tree.root, one, two);
                        Tree.printTree(Tree.root);
                        if (lca != null)
                            System.out.println("The Lowest Common Ancestor is : " + lca.value);
                        else
                            System.out.println("The Lowest Common Ancestor is : Null");
                    } else {
                        Tree.printTree(Tree.root);
                        System.out.println("The Element That You Have Entered Does Not Exist in The Tree!");
                    }
                    break;
                }
                case 18: {
                    System.out.print("Enter the First Element : ");
                    int one = in.nextInt();
                    System.out.print("Enter the Second Element : ");
                    int two = in.nextInt();
                    Node n1 = Tree.search(Tree.root, one);
                    Node n2 = Tree.search(Tree.root, two);
                    if (n1 != null && n2 != null) {
                        int distance = Tree.calcDistanceBetween2(Tree.root, one, two);
                        Tree.printTree(Tree.root);
                        System.out.println("The Distance Between The Two Elements is : " + distance);
                    } else {
                        Tree.printTree(Tree.root);
                        System.out.println("The Element That You Have Entered Does Not Exist in The Tree!");
                    }
                    break;
                }
                case 19: {
                    Tree.printTree(Tree.root);
                    System.out.println("The Number of Leaves : " + Tree.countLeaves(Tree.root));
                    break;
                }
                case 20: {
                    Tree.printTree(Tree.root);
                    System.out.println("The Number of Non-Leaves : " + Tree.countNonLeaves(Tree.root));
                    break;
                }
                case 21: {
                    System.out.print("Enter The Element : ");
                    element = in.nextInt();
                    Node node = Tree.search(Tree.root, element);
                    Tree.printTree(Tree.root);
                    if (node != null) {
                        if (Tree.isFull(node)) {
                            System.out.println("The Tree is Full!");
                        } else {
                            System.out.println("The Tree Is Not Full!");
                        }
                    } else {
                        System.out.println("The Element That You Have Entered Does Not Exist!");
                    }
                    break;
                }
                case 22: {
                    System.out.print("Enter The Element : ");
                    element = in.nextInt();
                    Node node = Tree.search(Tree.root, element);
                    Tree.printTree(Tree.root);
                    if (node != null) {
                        if (Tree.isComplete(node)) {
                            System.out.println("The Tree is Complete!");
                        } else {
                            System.out.println("The Tree Is Not Complete!");
                        }
                    } else {
                        System.out.println("The Element That You Have Entered Does Not Exist!");
                    }
                    break;
                }
                case 23: {
                    System.out.print("Enter The Element : ");
                    element = in.nextInt();
                    Node node = Tree.search(Tree.root, element);
                    Tree.printTree(Tree.root);
                    if (node != null) {
                        if (Tree.isStrict(node)) {
                            System.out.println("The Tree is Strict!");
                        } else {
                            System.out.println("The Tree Is Not Strict!");
                        }
                    } else {
                        System.out.println("The Element That You Have Entered Does Not Exist!");
                    }
                    break;
                }
                case 24: {
                    Tree.printTree(Tree.root);
                    if (Tree.isComplete(Tree.root)) {
                        System.out.println("The Number Of Nodes is : " + Tree.countNodesCompleteBT(Tree.root));
                    } else {
                        System.out.println("The Tree Is Not Complete!");
                    }
                    break;
                }
                case 25: {
                    Tree = Tree.ConvertToAVL(Tree);
                    Tree.printTree(Tree.root);
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
                    break;
                }
            }
        }
    }

}
