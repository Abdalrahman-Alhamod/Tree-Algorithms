public class AVL extends BST {
    public AVL() {
        root = null;
    }

    public AVL(Node root) {
        this.root = root;
    }

    private void L_Rotation(Node node) {
        Node rightSon = node.right;
        Node grandChild = null;
        if (node.right != null)
            grandChild = node.right.left;
        Node grandParent = node.parent;

        //Checking Son Direction According To Parent
        boolean flag = false;
        if (node.parent != null && node == node.parent.right)
            flag = true;
        else if (node.parent != null && node == node.parent.left)
            flag = false;

        //Update Parent Right
        node.right = grandChild;
        if (grandChild != null)
            grandChild.parent = node;

        //Update Son Parent
        node.parent = rightSon;

        if (rightSon != null) {

            //Update Son Left
            rightSon.left = node;

            //Update Son Parent
            rightSon.parent = grandParent;
        }

        //Update GrandParent Child
        if (grandParent != null && flag/*Son Should Be on The Right*/)
            grandParent.right = rightSon;
        else if (grandParent != null)   /*Son Should Be On The Left*/
            grandParent.left = rightSon;

        //Right Son Should Be Root
        if (grandParent == null)
            root = rightSon;

    }

    private void R_Rotation(Node node) {
        Node leftSon = node.left;
        Node grandChild = null;
        if (node.left != null)
            grandChild = node.left.right;
        Node grandParent = node.parent;

        //Checking Son Direction According To Parent
        boolean flag = false;
        if (node.parent != null && node == node.parent.right)
            flag = true;
        else if (node.parent != null && node == node.parent.left)
            flag = false;

        //Update Parent Left
        node.left = grandChild;
        if (grandChild != null)
            grandChild.parent = node;

        //Update Node Parent
        node.parent = leftSon;

        if (leftSon != null) {

            //Update Son Right
            leftSon.right = node;

            //Update Son Parent
            leftSon.parent = grandParent;
        }

        //Update GrandParent Child
        if (grandParent != null && flag/*Son Should Be on The Right*/)
            grandParent.right = leftSon;
        else if (grandParent != null)   /*Son Should Be On The Left*/
            grandParent.left = leftSon;

        //Left Son Should Be Root
        if (grandParent == null)
            root = leftSon;

    }

    private void RL_Rotation(Node node) {
        R_Rotation(node.right);
        L_Rotation(node);
    }

    private void LR_Rotation(Node node) {
        L_Rotation(node.left);
        R_Rotation(node);
    }

    private void UpdateHeight(Node node) {
        if (node.right != null && node.left != null)
            node.height = Math.max(node.left.height, node.right.height) + 1;
        else if (node.left == null && node.right != null)
            node.height = node.right.height + 1;
        else if (node.left != null)
            node.height = node.left.height + 1;
        else
            node.height = 0;
    }

    private void UpdateBalance(Node node) {
        if (node.right != null && node.left != null)
            node.balance = node.left.height - node.right.height;
        else if (node.left == null && node.right != null)
            node.balance = -node.right.height - 1;
        else if (node.left != null)
            node.balance = node.left.height + 1;
        else
            node.balance = 0;
    }

    private boolean badBalance(Node node) {
        return node.balance != 1 && node.balance != 0 && node.balance != -1;
    }

    private boolean sameSign(Node n1, Node n2) {
        return (n1.balance > 0 && n2.balance > 0) || (n1.balance < 0 && n2.balance < 0);
    }

    private void Fix(Node parent, Node son) {
        if (sameSign(parent, son)) {
            if (parent.balance > 0)
                R_Rotation(parent);
            else
                L_Rotation(parent);
        } else {
            if (parent.balance > 0)
                LR_Rotation(parent);
            else
                RL_Rotation(parent);
        }

        //Updating Parents Height And Balance
        Node p = son;
        UpdateHeight(p);
        UpdateBalance(p);
        p = parent;
        while (p != null) {
            UpdateHeight(p);
            UpdateBalance(p);
            p = p.parent;
        }

    }

    private void FindFlawAdd(Node node) {
        Node s = null;
        Node p = node;
        while (p != null) {
            if (badBalance(p)) {
                break;
            }
            s = p;
            p = p.parent;
        }
        if (p != null)
            Fix(p, s);
    }

    private Node MostHeightSon(Node node) {
        if (node != null && node.right != null && node.left != null) {
            if (node.right.height > node.left.height)
                return node.right;

            else
                return node.left;
        } else if (node != null && node.left == null && node.right != null)
            return node.right;
        else if (node != null && node.left != null)
            return node.left;
        else
            return null;
    }

    private void FindFlawDelete(Node node) {
        Node p = node;
        Node s = MostHeightSon(p);
        while (p != null) {
            if (badBalance(p)) {
                Fix(p, s);
            }
            p = p.parent;
            if (p != null)
                s = MostHeightSon(p);
        }

    }

    public void insert(Node node) {
        super.insert(node);

        //Update Height And Balance
        Node p = node.parent;
        while (p != null) {
            UpdateHeight(p);
            UpdateBalance(p);
            p = p.parent;
        }

        //Find Flaw And Fix It
        p = node.parent;
        FindFlawAdd(p);
    }

    protected void Transplant(Node deleted, Node replacement) {
        super.Transplant(deleted, replacement);

        //Update Height And Balance
        Node p;
        if (replacement != null)
            p = replacement;
        else
            p = deleted.parent;
        while (p != null) {
            UpdateHeight(p);
            UpdateBalance(p);
            p = p.parent;
        }

        //Find Flaw And Fix It
        if (replacement != null)
            p = replacement;
        else
            p = deleted.parent;
        FindFlawDelete(p);
    }

    public Node erase(Node node, int data) {
        Node deleted = search(root, data);
        root = super.erase(node, data);

        Node p;
        p = deleted.parent;
        while (p != null) {
            UpdateHeight(p);
            UpdateBalance(p);
            p = p.parent;
        }

        p = deleted.parent;
        FindFlawDelete(p);
        return root;
    }


}
