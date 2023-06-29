import java.util.LinkedList;
import java.util.Queue;

public class BST extends BinaryTree {

    public BST() {
        root = null;
    }

    public BST(Node root) {
        this.root = root;
    }

    public boolean check(Node node, int data) {
        if (node == null)
            return false;
        if (node.value == data)
            return true;
        else if (node.value > data)
            return check(node.left, data);
        else
            return check(node.right, data);
    }

    public Node find(Node node, int data) {
        if (node == null || node.value == data)
            return node;
        else if (node.value > data)
            return find(node.left, data);
        else
            return find(node.right, data);
    }

    public int count(Node node, int data) {
        if (node == null)
            return 0;
        if (node.value == data)
            return this.count(node.right, data) + 1;
        else if (node.value > data)
            return this.count(node.left, data);
        else
            return this.count(node.right, data);
    }

    private Node[] Cut(Node node, int data) {
        if (node == null) {
            return new Node[]{null, null};
        }

        if (node.value == data) {
            Node less_sub = node.left;
            Node more_sub = node.right;
            node.left = null;
            node.right = null;
            return new Node[]{less_sub, more_sub};
        }

        if (node.value > data) {
            Node[] subNodes = Cut(node.left, data);
            node.left = subNodes[1];
            return new Node[]{subNodes[0], node};
        } else {
            Node[] subNodes = Cut(node.right, data);
            node.right = subNodes[0];
            return new Node[]{node, subNodes[1]};
        }
    }

    public void AddRoot(int data) {
        Node r = new Node(data, null, null);
        Node[] subNodes = Cut(root, data);
        r.left = subNodes[0];
        r.right = subNodes[1];
        if (r.right != null)
            r.right.parent = r;
        if (r.left != null)
            r.left.parent = r;
        root = r;
    }

    public void buildBalanced(int level) {
        int p = 1 << level;
        p /= 2;
        Node node;
        node = new Node(p);
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

    public Node search(Node node, int data) {
        while (node != null && node.value != data) {
            if (node.value > data)
                node = node.left;
            else
                node = node.right;
        }
        return node;
    }

    public Node min(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public Node max(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    public Node successor(Node node) {
        if (node.right != null)
            return min(node.right);
        Node parent = node.parent;
        while (parent != null && node == parent.right) {
            node = parent;
            parent = parent.parent;
        }
        return parent;
    }

    public Node predecessor(Node node) {
        if (node.left != null)
            return max(node.left);
        Node parent = node.parent;
        while (parent != null && node == parent.left) {
            node = parent;
            parent = parent.parent;
        }
        return parent;
    }

    public void insert(Node node) {
        Node p = null;
        Node current = root;
        while (current != null) {
            p = current;
            if (node.value < current.value)
                current = current.left;
            else
                current = current.right;
        }
        node.parent = p;
        if (p == null)
            root = node;  //Tree was Empty
        else if (node.value < p.value)
            p.left = node;
        else
            p.right = node;
    }

    protected void Transplant(Node deleted, Node replacement) {//#Replaces one subtree as a child of its parent with another subtree
        if (deleted.parent == null) {
            root = replacement;
        } else if (deleted == deleted.parent.left)
            deleted.parent.left = replacement;
        else
            deleted.parent.right = replacement;
        if (replacement != null && replacement != deleted.left) {
            replacement.parent = deleted.parent;
            replacement.left = deleted.left;
            if (replacement.left != null)
                replacement.left.parent = replacement;
        } else if (replacement != null) {
            replacement.parent = deleted.parent;
            replacement.right = deleted.right;
            if (replacement.right != null)
                replacement.right.parent = replacement;
        }
    }

    public void delete(Node node) {
        if (node.left == null)
            Transplant(node, node.right);
        else if (node.right == null) {
            Transplant(node, node.left);
        } else {
            Node next;
            next = min(node.right);
            if (next.parent != node) {
                Transplant(next, next.right);
                next.right = node.right;
                if (next.right != null)
                    next.right.parent = next;
            }
            Transplant(node, next);
        }
    }

    public Node addLeaf(Node node, int data) {
        if (node == null) {
            node = new Node(data);
            return node;
        }
        if (node.value > data) {
            Node left = addLeaf(node.left, data);
            node.left = left;
            left.parent = node;
        } else if (node.value < data) {
            Node right = addLeaf(node.right, data);
            node.right = right;
            right.parent = node;
        }
        return node;
    }

    public Node erase(Node node, int data) {
        if (node == null)
            return null;
        if (node.value > data) {
            Node left = erase(node.left, data);
            node.left = left;
            if (left != null)
                left.parent = node;
        } else if (node.value < data) {
            Node right = erase(node.right, data);
            node.right = right;
            if (right != null)
                right.parent = node;
        } else {
            if (node.left == null)
                return node.right;
            else if (node.right == null)
                return node.left;
            int Successor = min(node.right).value;
            node.value = Successor;
            Node right = erase(node.right, Successor);
            node.right = right;
            if (right != null)
                right.parent = node;
        }
        return node;
    }

    public Node buildTreeFromPre(int[] pre) {
        return buildTreeFromPreHelper(pre, 0, pre[0], Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private Node buildTreeFromPreHelper(int[] pre, int preIndex, int data, int min, int max) {
        // Complexity : O(n)
        if (preIndex >= pre.length)
            return null;
        Node node = null;
        if (data > min && data < max) {
            node = new Node(data);
            preIndex++;
            if (preIndex < pre.length) {
                Node left = buildTreeFromPreHelper(pre, preIndex, pre[preIndex], min, data);
                node.left = left;
                if (left != null)
                    left.parent = node;
            }
            if (preIndex < pre.length) {
                Node right = buildTreeFromPreHelper(pre, preIndex, pre[preIndex], data, max);
                node.right = right;
                if (right != null)
                    right.parent = node;
            }
        }
        return node;
    }

    public boolean deadEnd(Node node) {
        // All Values Should Be Integers and Distinct and Positive
        return deadEndChecker(node, min(node).value, max(node).value);
    }

    private boolean deadEndChecker(Node node, int min, int max) {
        // Complexity : O(n)
        if (node == null)
            return false;
        if (min == max)
            return true;
        return deadEndChecker(node.left, min, node.value - 1) || deadEndChecker(node.right, node.value + 1, max);
    }

    public Node LCA(Node node, int one, int two) {
        // LCA : Lowest Common Ancestor
        // Complexity : O(h)
        if (node == null)
            return null;
        if (node.value > one && node.value > two)
            return LCA(node.left, one, two);
        if (node.value < one && node.value < two)
            return LCA(node.right, one, two);
        return node;
    }

    public int calcDistanceBetween2(Node node, int one, int two) {
        // Complexity : O(h)
        if (node == null)
            return 0;
        if (node.value > one && node.value > two)
            return calcDistanceBetween2(node.left, one, two);
        if (node.value < one && node.value < two)
            return calcDistanceBetween2(node.right, one, two);
        return calcDistance(node, one) + calcDistance(node, two);
    }

    private int calcDistance(Node node, int data) {
        if (node.value == data)
            return 0;
        else if (node.value > data)
            return 1 + calcDistance(node.left, data);
        return 1 + calcDistance(node.right, data);
    }

    public AVL ConvertToAVL(BST Tree) {
        Node node = Tree.root;
        AVL AVLTree = new AVL(null);
        Queue<Node> q = new LinkedList<>();
        q.add(node);
        while (!q.isEmpty()) {
            Node curr = q.peek();
            if (curr.left != null)
                q.add(curr.left);
            if (curr.right != null)
                q.add(curr.right);
            node = new Node(curr.value);
            AVLTree.insert(node);
            q.poll();
        }
        return AVLTree;
    }
}
