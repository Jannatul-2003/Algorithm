import java.util.*;
import java.io.*;

class Edge {
    int from, to, weight;
    
    Edge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}

class EdgeComparator implements Comparator<Edge> {
    @Override
    public int compare(Edge e1, Edge e2) {
        return Integer.compare(e1.weight, e2.weight);
    }
}

class Graph {
    PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
    Map<Integer, List<Edge>> adjacencyList = new HashMap<>();
    boolean[] visited;
    int[] parent, distance;

    Graph(int n) {
        visited = new boolean[n + 1];
        parent = new int[n + 1];
        distance = new int[n + 1];
        Arrays.fill(visited, false);
        Arrays.fill(parent, -1);
        Arrays.fill(distance, Integer.MAX_VALUE);
    }

    void addEdge(int from, int to, int cost) {
        adjacencyList.putIfAbsent(from, new ArrayList<>());
        adjacencyList.putIfAbsent(to, new ArrayList<>());
        adjacencyList.get(from).add(new Edge(from, to, cost));
        adjacencyList.get(to).add(new Edge(to, from, cost)); // for undirected graph
    }

    int totalCost = 0;

    ArrayList<Integer> getMST(int src) {
        ArrayList<Integer> mst = new ArrayList<>();
        distance[src] = 0;
        priorityQueue.add(new Edge(src, src, 0)); // Add initial edge with zero weight

        while (!priorityQueue.isEmpty()) {
            Edge u = priorityQueue.poll();
            if (visited[u.to]) continue; // Skip if already visited

            mst.add(u.to);
            visited[u.to] = true;
            totalCost += u.weight;

            for (Edge edge : adjacencyList.get(u.to)) {
                if (!visited[edge.to] && edge.weight < distance[edge.to]) {
                    parent[edge.to] = u.to;
                    distance[edge.to] = edge.weight;
                    priorityQueue.add(edge);
                }
            }
        }
        return mst;
    }
}

public class Prim_MST {
    public static void main(String[] args) {
        try {
            File file = new File("input.txt");
            Scanner sc = new Scanner(file);
            int n = sc.nextInt();
            int m = sc.nextInt();
            Graph mstGraph = new Graph(n);

            for (int i = 0; i < m; i++) {
                int from = sc.nextInt();
                int to = sc.nextInt();
                int cost = sc.nextInt();
                mstGraph.addEdge(from, to, cost);
            }

            ArrayList<Integer> result = mstGraph.getMST(1);
            System.out.println("Minimum Spanning Tree edges: " + result);
            System.out.println("Total cost of MST: " + mstGraph.totalCost);

            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}