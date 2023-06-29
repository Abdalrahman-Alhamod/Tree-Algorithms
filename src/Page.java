import java.util.ArrayList;

public class Page {
    public AVL Elements;
    public Page parentPage;
    public int level;
    public int minNodes;
    public int maxNodes;

    public Page() {
        Elements = null;
        parentPage = null;
        level = 0;
        minNodes = 0;
        maxNodes = 0;
    }

    public Page(AVL Elements) {
        this.Elements = Elements;
        parentPage = null;
        level = 0;
        minNodes = 0;
        maxNodes = 0;
    }

    public Page(Node root) {
        Elements = new AVL(root);
        parentPage = null;
        level = 0;
        minNodes = 0;
        maxNodes = 0;
    }

    public boolean isPageLeaf() {
        return isLeafHelper(Elements.root);
    }

    private boolean isLeafHelper(Node node) {
        if (node == null)
            return true;
        if (node.leftPage != null || node.rightPage != null)
            return false;
        else
            return isLeafHelper(node.left) && isLeafHelper(node.right);

    }

    public boolean hasMaxNodes() {
        return Elements.size(Elements.root) == maxNodes;
    }

    public boolean hasMinNodes() {
        return Elements.size(Elements.root) == minNodes;
    }

    public void makeRoot() {
        parentPage = null;
        minNodes = 1;
        level = 1;
    }

    public boolean isRoot() {
        return parentPage == null;
    }

    public void printPage() {
        System.out.print(getValue());
    }

    public void getInorderNodesMatrix(ArrayList<Node> Inorder, Node node) {
        if (node != null) {
            getInorderNodesMatrix(Inorder, node.left);
            Node newNode = new Node();
            newNode = copyNode(newNode, node);
            Inorder.add(newNode);
            getInorderNodesMatrix(Inorder, node.right);
        }
    }

    public boolean isValidPage() {
        return isPageLeaf() || hasMaxPages();
    }

    public boolean hasMaxPages() {
        return hasMaxPagesHelper(Elements.root);
    }

    private boolean hasMaxPagesHelper(Node node) {
        if (node == null)
            return true;
        if (node.leftPage == null || node.rightPage == null)
            return false;
        return hasMaxPagesHelper(node.left) && hasMaxPagesHelper(node.right);
    }

    public boolean search(int data) {
        return Elements.check(Elements.root, data);
    }

    public Node find(int data) {
        return Elements.search(Elements.root, data);
    }

    public void insert(Node node) {
        Elements.insert(node);
    }

    public void delete(int data) {
        //Elements.root = Elements.Erase(Elements.root,data);
        Elements.delete(Elements.search(Elements.root, data));
    }

    public int size() {
        return Elements.size(Elements.root);
    }

    public int getMinValue() {
        return Elements.min(Elements.root).value;
    }

    public int getMaxValue() {
        return Elements.max(Elements.root).value;
    }

    public Page getLeftPage() {
        return Elements.min(Elements.root).leftPage;
    }

    public void setLeftPage(Page newLeftPage) {
        Elements.min(Elements.root).leftPage = newLeftPage;
    }

    public Page getRightPage() {
        return Elements.max(Elements.root).rightPage;
    }

    public void setRightPage(Page newRightPage) {
        Elements.max(Elements.root).rightPage = newRightPage;
    }

    public void initiatePage(int rank, int level) {
        minNodes = rank;
        maxNodes = 2 * rank;
        this.level = level;
    }

    public Page getContentPage(int data) {
        Node current = Elements.root;
        Node next = null;
        while (true) {
            if (data < current.value)
                next = current.left;
            if (data > current.value)
                next = current.right;
            if (data > current.value && next == null || data > current.value && data < next.value)
                return current.rightPage;
            if (data < current.value && next == null || data < current.value && data > next.value)
                return current.leftPage;
            current = next;
        }
    }

    public void setElements(Node node) {
        Elements.root = node;
    }

    public Node getMiddleNode() {
        ArrayList<Node> arr = new ArrayList<>();
        getInorderNodesMatrix(arr, Elements.root);
        return arr.get((arr.size() / 2));
    }

    public Node getBeforeMiddleNode() {
        ArrayList<Node> arr = new ArrayList<>();
        getInorderNodesMatrix(arr, Elements.root);
        AVL tree = new AVL(null);
        for (int i = 0; i < arr.size() / 2; i++) {
            tree.insert(arr.get(i));
        }
        return tree.root;
    }

    public Node getAfterMiddleNode() {
        ArrayList<Node> arr = new ArrayList<>();
        getInorderNodesMatrix(arr, Elements.root);
        AVL tree = new AVL(null);
        for (int i = (arr.size() / 2 + 1); i < arr.size(); i++) {
            tree.insert(arr.get(i));
        }
        return tree.root;
    }

    private Node copyNode(Node newNode, Node oldNode) {
        newNode.value = oldNode.value;
        newNode.rightPage = oldNode.rightPage;
        newNode.leftPage = oldNode.leftPage;
        return newNode;
    }

    public ArrayList<Page> getChildren() {
        ArrayList<Node> arr = new ArrayList<>();
        getInorderNodesMatrix(arr, Elements.root);
        ArrayList<Page> pages = new ArrayList<>();
        pages.add(arr.get(0).leftPage);
        for (Node node : arr) {
            pages.add(node.rightPage);
        }
        return pages;
    }

    public ArrayList<Integer> getValue() {
        ArrayList<Node> InorderNodes = new ArrayList<>();
        getInorderNodesMatrix(InorderNodes, Elements.root);
        ArrayList<Integer> InorderValues = new ArrayList<>();
        for (int i = 0; i < InorderNodes.size(); i++) {
            InorderValues.add(i, InorderNodes.get(i).value);
        }
        return InorderValues;
    }

    public Node getRightParentNode() {
        int currentMax = Elements.max(Elements.root).value;
        return getUpperBoundNode(parentPage.Elements.root, currentMax);
    }

    private Node getUpperBoundNode(Node root, int data) {
        Node current = root;
        Node next = null;
        while (true) {
            if (data < current.value)
                next = current.left;
            if (data > current.value)
                next = current.right;
            if (data > current.value && next == null || data > current.value && data < next.value)
                return next;
            if (data < current.value && next == null || data < current.value && data > next.value)
                return current;
            current = next;
        }
    }

    public Node getLeftParentNode() {
        int currentMin = Elements.min(Elements.root).value;
        return getLowerBoundNode(parentPage.Elements.root, currentMin);
    }

    private Node getLowerBoundNode(Node root, int data) {
        Node current = root;
        Node next = null;
        while (true) {
            if (data < current.value)
                next = current.left;
            if (data > current.value)
                next = current.right;
            if (data > current.value && next == null || data > current.value && data < next.value)
                return current;
            if (data < current.value && next == null || data < current.value && data > next.value)
                return next;
            current = next;
        }
    }

    public Page getRightBrotherPage() {
        if (getRightParentNode() != null)
            return getRightParentNode().rightPage;
        else
            return null;
    }

    public Page getLeftBrotherPage() {
        if (getLeftParentNode() != null)
            return getLeftParentNode().leftPage;
        else
            return null;

    }

}
