import java.util.*;
import java.io.*;

class Vertex implements Comparable<Vertex> {
    int name;
    int cost;
    Vertex parent = null;
    HashMap<Vertex, Integer> neighbours = new HashMap<>();

    Vertex(int name) {
        this.name = name;
        this.cost = Integer.MAX_VALUE; // Initialize with infinity
    }

    @Override
    public int compareTo(Vertex other) {
        return Integer.compare(this.cost, other.cost);
    }
}

public class Dijkstra {
    HashMap<Integer, Vertex> graph = new HashMap<>();

    Vertex getVertex(int name) {
        return graph.computeIfAbsent(name, k -> new Vertex(name));
    }

    void addEdge(int u, int v, int weight) {
        Vertex uVertex = getVertex(u);
        Vertex vVertex = getVertex(v);
        uVertex.neighbours.put(vVertex, weight);
    }

    void dijkstra(int start) {
        Vertex source = getVertex(start);
        source.cost = 0;

        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        queue.add(source);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();

            // Relaxation of edges
            for (Map.Entry<Vertex, Integer> entry : current.neighbours.entrySet()) {
                Vertex neighbor = entry.getKey();
                int weight = entry.getValue();

                int newCost = current.cost + weight;
                if (newCost < neighbor.cost) {
                    neighbor.cost = newCost;
                    neighbor.parent = current;
                    queue.add(neighbor); // Re-insert without removing
                }
            }
        }
    }

    void printPaths(int start) {
        for (Vertex v : graph.values()) {
            if (v.cost == Integer.MAX_VALUE) {
                System.out.println("Vertex " + v.name + " is unreachable from vertex " + start);
            } else {
                System.out.print("Cost to reach vertex " + v.name + " from vertex " + start + " is " + v.cost);
                System.out.print(". Path: ");
                
                List<Integer> path = new ArrayList<>();
                for (Vertex at = v; at != null; at = at.parent) {
                    path.add(at.name);
                }
                Collections.reverse(path);
                System.out.println(path);
            }
        }
    }

    public static void main(String[] args) {
        Dijkstra dijkstra = new Dijkstra();
        int sourceVertex; 
        
        try {
            Scanner scanner = new Scanner(new File("input.txt"));
            int vertxNum = scanner.nextInt();
            int edgeNum = scanner.nextInt();
            for (int i = 0; i < edgeNum; i++) {
                int u = scanner.nextInt();
                int v = scanner.nextInt();
                int weight = scanner.nextInt();
                dijkstra.addEdge(u, v, weight);
            }
            sourceVertex = scanner.nextInt();
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            return;
        }

        // Run Dijkstra's algorithm from a source vertex
        dijkstra.dijkstra(sourceVertex);
        dijkstra.printPaths(sourceVertex);
    }
}
