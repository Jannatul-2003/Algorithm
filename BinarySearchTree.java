import java.io.*;
import java.util.*;

class Node {
    int name;
    Node parent = null;
    Node left = null;
    Node right = null;

    Node(int name) {
        this.name = name;
    }
}

class BST {
    Node root;

    BST() {
        root = null;
    }

    void insert(int name) {
        Node newNode = new Node(name);
        if (root == null) {
            root = newNode;
            return;
        }
        Node current = root;
        Node parent = null;
        while (true) {
            parent = current;
            if (name < current.name) {
                current = current.left;
                if (current == null) {
                    parent.left = newNode;
                    newNode.parent = parent;
                    return;
                }
            } else {
                current = current.right;
                if (current == null) {
                    parent.right = newNode;
                    newNode.parent = parent;
                    return;
                }
            }
        }
    }

    Boolean search(int name) {
        Node current = root;
        while (current != null) {
            if (current.name == name) {
                return true;
            } else if (name < current.name) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return false;
    }

    void inorder(Node node) {
        if (node == null) {
            return;
        }
        inorder(node.left);
        System.out.print(node.name + " ");
        inorder(node.right);
    }

}

public class BinarySearchTree {
    public static void main(String[] args) throws Exception {
        int n;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        BST bst = new BST();
        String[] str = new String[n];
        str = br.readLine().split(", ");
        for (int i = 0; i < str.length; i++) {
            bst.insert(Integer.parseInt(str[i]));
        }

        n = Integer.parseInt(br.readLine());
        if (bst.search(n)) {
            System.out.println(n + " is found in the BST");
        } else {
            System.out.println(n + " is not found in the BST");
        }
        System.out.print("Inorder Traversal: ");
        bst.inorder(bst.root);
    }
}
