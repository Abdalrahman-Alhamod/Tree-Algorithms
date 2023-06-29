public class Main {
    public static void main(String[] args) {
        AVL<Integer> t = new AVL<>();
        t.insert(2);
        t.insert(5);
        t.insert(1);
        t.insert(3);
        t.insert(7);
        t.insert(8);
        t.insert(9);
        t.insert(10);
        t.printTree(t.root);
        AVL<Character> tc = new AVL<>();
        tc.insert('a');
        tc.insert('b');
        tc.insert('c');
        tc.insert('d');
        tc.insert('e');
        tc.insert('f');
        tc.insert('g');
        tc.insert('h');
        tc.printTree(tc.root);
        AVL<String> ts = new AVL<>();
        ts.insert("alpha");
        ts.insert("beta");
        ts.insert("gamma");
        ts.insert("teta");
        ts.insert("zetta");
        ts.insert("phay");
        ts.insert("meta");
        ts.insert("pota");
        ts.printTree(ts.root);
        AVL<Float> tf = new AVL<>();
        tf.insert(1.29F);
        tf.insert(2.89F);
        tf.insert(7.34F);
        tf.insert(2.29F);
        tf.insert(-9.29F);
        tf.insert(5.23F);
        tf.insert(-9.29F);
        tf.insert(4.19F);
        tf.printTree(tf.root);
    }
}