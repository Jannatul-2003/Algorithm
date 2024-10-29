import java.util.*;
import java.io.*;

public class Kruskal_MST {
    int[] parent;
    int[] rank;
    int verticeNum;
    PriorityQueue<Edge> edges = new PriorityQueue<>(new EdgeComparator());

    class Edge {
        int from, to, cost;

        Edge(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return from + " " + to + " " + cost;
        }
    }

    class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return Integer.compare(o1.cost, o2.cost); // Ascending order of edge cost
        }
    }

    void makeSet(int n) {
        verticeNum = n;
        parent = new int[n + 1];
        rank = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    void addEdge(int from, int to, int cost) {
        edges.add(new Edge(from, to, cost));
    }

    int findSet(int x) {
        if (parent[x] != x) {
            parent[x] = findSet(parent[x]); // Path compression
        }
        return parent[x];
    }

    void union(int x, int y) {
        int rootX = findSet(x);
        int rootY = findSet(y);
        if (rootX != rootY) {
            // Union by rank
            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }

    Set<Edge> kruskal(int n) {
        Set<Edge> result = new HashSet<>();
        makeSet(n);

        while (!edges.isEmpty()) {
            Edge edge = edges.poll();
            int fromParent = findSet(edge.from);
            int toParent = findSet(edge.to);

            if (fromParent != toParent) {
                result.add(edge);
                union(fromParent, toParent);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            Kruskal_MST kruskalMST = new Kruskal_MST();
            File file = new File("input.txt");
            Scanner sc = new Scanner(file);
            int n = sc.nextInt();
            int m = sc.nextInt();

            for (int i = 0; i < m; i++) {
                int from = sc.nextInt();
                int to = sc.nextInt();
                int cost = sc.nextInt();
                kruskalMST.addEdge(from, to, cost);
            }

            Set<Edge> result = kruskalMST.kruskal(n);
            int totalCost = 0;
            System.out.println("Minimum Spanning Tree edges:");
            for (Edge edge : result) {
                totalCost += edge.cost;
                System.out.println(edge.from + " - " + edge.to + " : " + edge.cost);
            }
            System.out.println("Total cost of MST: " + totalCost);

            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
