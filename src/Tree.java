public interface Tree {
    boolean check(Node node, int data);

    Node find(Node node, int data);

    int count(Node node, int data);

    void preOrder(Node node);

    void inOrder(Node node);

    void postOrder(Node node);

    int getMaxLevel(Node node);

    void printTree(Node root);

    Node search(Node node, int data);

    Node min(Node node);

    Node max(Node node);

    Node successor(Node node);

    Node predecessor(Node node);

    void insert(Node node);

    void delete(Node node);
}
