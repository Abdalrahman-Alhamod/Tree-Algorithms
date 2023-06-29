class Node {
    public int value;
    public Node left, right;
    public Node parent;
    public int height;
    public int balance;

    public Page leftPage, rightPage;

    public Node() {
        value = 0;
        left = null;
        right = null;
        parent = null;
        height = 0;
        balance = 0;
        leftPage = null;
        rightPage = null;
    }

    public Node(int value) {
        this.value = value;
        left = null;
        right = null;
        parent = null;
        height = 0;
        balance = 0;
        leftPage = null;
        rightPage = null;
    }

    public Node(int value, Node left, Node right) {
        this.value = value;
        this.left = left;
        this.right = right;
        parent = null;
        height = 0;
        balance = 0;
        leftPage = null;
        rightPage = null;
    }
}

