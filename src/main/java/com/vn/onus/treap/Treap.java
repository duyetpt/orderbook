package com.vn.onus.treap;

import com.vn.onus.ComparingResult;
import com.vn.onus.order.Order;
import lombok.Data;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Random;

@Data
public class Treap<T extends Order> implements Queue<T> {
    TreapNode root;

    private TreapNode rightRotate(TreapNode y) {
        TreapNode x = y.left;
        TreapNode T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Return new root
        return x;
    }

    // A utility function to left rotate subtree rooted with x
// See the diagram given above.
    private TreapNode leftRotate(TreapNode x) {
        TreapNode y = x.right;
        TreapNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Return new root
        return y;
    }

    /* Recursive implementation of insertion in Treap */
    public void insert(TreapNode node) {
        this.root = insert(this.root, node);
    }
    private TreapNode insert(TreapNode root, TreapNode node) {
        // If treap root is null
        if (this.root == null) {
            this.root = node;
            return this.root;
        }

        // If root is null, create a new node and return it
        if (root == null) {
            root = node;
            return root;
        }

        // If key is smaller than root
        if (ComparingResult.isEqualOrGreater(node.key.compareTo(root.getKey()))) {
            // Insert in left subtree
            root.left = insert(root.left, node);

            // Fix Heap property if it is violated
            if (root.left.priority > root.priority) {
                root = rightRotate(root);
            }
        } else { // If key is greater
            // Insert in right subtree
            root.right = insert(root.right, node);

            // Fix Heap property if it is violated
            if (root.right.priority > root.priority) {
                root = leftRotate(root);
            }
        }
        return root;
    }

    /* Recursive implementation of Delete() */
    public void deleteNode(TreapNode node) {
        this.root = deleteNode(this.root, node);
    }

    private TreapNode deleteNode(TreapNode root, TreapNode node) {
        if (root == null)
            return root;

        var keyComparingResult = root.key.compareTo(node.key);
        if (ComparingResult.isLess(keyComparingResult)) {
            root.left = deleteNode(root.left, node);
        } else if (ComparingResult.isGreater(keyComparingResult)) {
            root.right = deleteNode(root.right, node);

            // IF KEY IS AT ROOT

            // If left is NULL
        } else if (root.left == null) {
            TreapNode temp = root.right;
            root = temp;  // Make right child as root
        }
        // If Right is NULL
        else if (root.right == null) {
            TreapNode temp = root.left;
            root = temp;  // Make left child as root
        }
        // If key is at root and both left and right are not NULL
        else if (root.left.priority < root.right.priority) {
            root = leftRotate(root);
            root.left = deleteNode(root.left, node);
        } else {
            root = rightRotate(root);
            root.right = deleteNode(root.right, node);
        }

        return root;
    }

    // Java function to search a given key in a given BST
    public TreapNode search(TreapNode node) {
        return this.search(this.root, node);
    }

    private TreapNode search(TreapNode root, TreapNode node) {
        // Base Cases: root is null or key is present at root
        if (root == null || root.key.equals(node.key))
            return root;

        // Key is greater than root's key
        if (ComparingResult.isLess(root.key.compareTo(node.key)))
            return search(root.right, node);

        // Key is smaller than root's key
        return search(root.left, node);
    }

    public void inorder() {
        this.inorder(this.root);
    }
    private void inorder(TreapNode root) {
        if (root != null) {
            inorder(root.left);
            System.out.print("key: " + root.key + " | priority: " + root.priority);
            if (root.left != null)
                System.out.print(" | left child: " + root.left.key);
            if (root.right != null)
                System.out.print(" | right child: " + root.right.key);
            System.out.println();
            inorder(root.right);
        }
    }

    static void insert(Treap treap, String key, long priority) {
        var node1 = new TreapNode();
        node1.setKey(key);
        node1.setPriority(priority);
        treap.insert(node1);
    }

    static void delete(Treap treap, String key) {
        var node1 = new TreapNode();
        node1.setKey(key);
        treap.deleteNode(node1);
    }

    // Driver Program to test above functions
    public static void main(String[] args) {
        Random rand = new Random();

        var treap = new Treap();
        insert(treap, "50", 46);
        insert(treap, "30", 92);
        insert(treap, "20", 20);
        insert(treap, "40", 87);
        insert(treap, "70", 10);
        insert(treap, "60", 62);
        insert(treap, "80", 57);

        System.out.println("Inorder traversal of the given tree:");
        treap.inorder();

        System.out.println("\nDelete 20");
        delete(treap, "20");
        System.out.println("Inorder traversal of the modified tree:");
        treap.inorder();

        System.out.println("\nDelete 30");
        delete(treap, "30");
        System.out.println("Inorder traversal of the modified tree:");
        treap.inorder();

        System.out.println("\nDelete 50");
        delete(treap, "50");
        System.out.println("Inorder traversal of the modified tree:");
        treap.inorder();

//        TreapNode res = search(root, 50);
//        System.out.println(res == null ? "\n50 Not Found" : "\n50 found");
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public boolean contains(Object o) {
        return search(new TreapNode((Order) o)) != null;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(Order order) {
        insert(new TreapNode(order));
        return true;
    }

    @Override
    public boolean remove(Object o) {
        deleteNode(new TreapNode((Order) o));
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offer(T t) {
        return false;
    }

    @Override
    public T remove() {
        var oldRoot = this.root;
        deleteNode(this.root);
        return (T) oldRoot.getOrder();
    }

    @Override
    public T poll() {
        return remove();
    }

    @Override
    public T element() {
        return (T) getRoot().getOrder();
    }

    @Override
    public T peek() {
        return element();
    }
}
