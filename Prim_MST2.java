import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Edge {
    int neighbor, cost;

    Edge(int neighbor, int cost) {
        this.neighbor = neighbor;
        this.cost = cost;
    }
}

public class Prim_MST2 {
    private List<List<Edge>> adjacencyList;
    private int[] parent, minEdgeCost;
    private boolean[] includedInMST;
    private int vertexCount;

    public Prim_MST2(int vertexCount) {
        this.vertexCount = vertexCount;
        adjacencyList = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        parent = new int[vertexCount];
        minEdgeCost = new int[vertexCount];
        includedInMST = new boolean[vertexCount];
        Arrays.fill(minEdgeCost, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
    }

    public void addEdge(int u, int v, int cost) {
        adjacencyList.get(u).add(new Edge(v, cost));
        adjacencyList.get(v).add(new Edge(u, cost));
    }

    public void primMST(int startVertex) {
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingInt(e -> e.cost));
        minEdgeCost[startVertex] = 0; // Start with the source vertex
        minHeap.add(new Edge(startVertex, 0)); // Add source with cost 0

        while (!minHeap.isEmpty()) {
            Edge current = minHeap.poll();
            int u = current.neighbor;

            if (includedInMST[u]) continue; // Skip if already included in MST

            includedInMST[u] = true; // Include u in MST
            for (Edge edge : adjacencyList.get(u)) {
                int v = edge.neighbor;
                int weight = edge.cost;

                // If vertex v is not yet in MST and weight is smaller, update
                if (!includedInMST[v] && weight < minEdgeCost[v]) {
                    minEdgeCost[v] = weight;
                    parent[v] = u;
                    minHeap.add(new Edge(v, weight)); // Add vertex to the priority queue
                }
            }
        }

        // Print the MST
        System.out.println("Edge \tWeight");
        for (int i = 1; i < vertexCount; i++) { // Start from 1 to skip the source
            if (parent[i] != -1) {
                System.out.println(parent[i] + " - " + i + "\t" + minEdgeCost[i]);
            }
        }
    }

    public static void main(String[] args) {
        try {
            File file = new File("input.txt");
            Scanner scanner = new Scanner(file);

            // Read the number of vertices and edges
            int vertexCount = scanner.nextInt();
            int edgeCount = scanner.nextInt();

            Prim_MST2 graph = new Prim_MST2(vertexCount);

            // Read edges from the file
            for (int i = 0; i < edgeCount; i++) {
                int u = scanner.nextInt();
                int v = scanner.nextInt();
                int cost = scanner.nextInt();
                graph.addEdge(u, v, cost);
            }
            scanner.close();

            // Run Prim's MST algorithm starting from vertex 0
            graph.primMST(0);

        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + e.getMessage());
        }
    }
}
