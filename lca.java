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
    Map<Integer, Node> path1 = new HashMap<>();

    BST() {
        root = null;
    }

    void insert(int name) {
        Node newNode = new Node(name);
        path1.put(name, newNode);
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

     Node lcaNode1(int node1, int node2) {
        Node current = root;
        while (current != null) {
            // If both nodes are smaller, move left
            if (node1 < current.name && node2 < current.name) {
                current = current.left;
            }
            // If both nodes are greater, move right
            else if (node1 > current.name && node2 > current.name) {
                current = current.right;
            }
            // If they are on either side, or one matches the current node, we've found the LCA
            else {
                return current;
            }
        }
        return null; // In case one or both nodes are not in the tree
    }
    Node lcaNode2(int node1, int node2) {
        Node n1 = path1.get(node1);
        Node n2 = path1.get(node2);
        Set<Node> visited = new HashSet<>();
        while(n1 != null){
            visited.add(n1);
            n1 = n1.parent;
        }
        while(n2 != null){
            if(visited.contains(n2)){
                return n2;
            }
            n2 = n2.parent;
        }
        return null;
    }
    Node lcaNode3(int n1, int n2) {
        Node current = root;
        int min = Math.min(n1, n2);
        int max = Math.max(n1, n2);
        
        while (current != null) {
            if (current.name > max) {
                current = current.left;
            } else if (current.name < min) {
                current = current.right;
            } else {
                return current;  // Current node is between n1 and n2, so it's the LCA
            }
        }
        return null;
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

public class lca {
    public static void main(String[] args) {
        BST tree = new BST();
        tree.insert(20);
        tree.insert(8);
        tree.insert(22);
        tree.insert(4);
        tree.insert(12);
        tree.insert(10);
        tree.insert(14);
        System.out.println("LCA of 10 and 14 is " + tree.lcaNode1(10, 14).name);
        System.out.println("LCA of 14 and 8 is " + tree.lcaNode2(14, 8).name);
        System.out.println("LCA of 10 and 22 is " + tree.lcaNode3(10, 22).name);
    }
}
