import java.io.*;
import java.util.*;

class Vertex implements Comparable<Vertex> {
    int name;
    int cost = Integer.MAX_VALUE;
    Vertex parent = null;
    HashMap<Vertex, Integer> neighboursWithWeight = new HashMap<>();

    Vertex(int name) {
        this.name = name;
    }

    public int compareTo(Vertex that) {
        return Integer.compare(this.cost, that.cost);
    }

    public String toString() {
        return this.name + " " + this.cost;
    }
}

public class Dijkstra {
    HashMap<Integer, Vertex> graph = new HashMap<>();
    PriorityQueue<Vertex> queue = new PriorityQueue<>();

    Vertex getVertex(int u) {
        return graph.computeIfAbsent(u, key -> new Vertex(u));
    }

    void relaxation(Vertex u, Vertex v, int w) {
        if (v.cost > u.cost + w) {
            v.cost = u.cost + w;
            v.parent = u;
        }
    }

    void addEdge(int u, int v, int weight) {
        Vertex uVertex = getVertex(u);
        Vertex vVertex = getVertex(v);
        uVertex.neighboursWithWeight.put(vVertex, weight);
    }

    HashSet<Vertex> getShortestPath(int s) {
        HashSet<Vertex> set = new HashSet<>();
        Vertex src = getVertex(s);
        src.cost = 0;
        queue.addAll(graph.values());
        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            set.add(current);
            // for(Vertex v: queue){
            // System.out.println(v.name+ " "+v.cost);
            // }
            // System.out.println(current.name+" ");
            for (Map.Entry<Vertex, Integer> entry : current.neighboursWithWeight.entrySet()) {
                relaxation(current, entry.getKey(), entry.getValue());
            }
        }
        return set;
    }

    List<String> getPaths(int source) {
        List<String> paths = new ArrayList<>();
        for (Vertex vertex : graph.values()) {
            StringBuilder path = new StringBuilder();
            path.append("Path to vertex ").append(vertex.name).append(": ");
            if (vertex.cost == Integer.MAX_VALUE) {
                path.append("Unreachable");
            } else {
                path.append(vertex.cost).append(" (");
                // Build the path
                List<Integer> pathList = new ArrayList<>();
                for (Vertex v = vertex; v != null; v = v.parent) {
                    pathList.add(v.name);
                }
                Collections.reverse(pathList);
                path.append(String.join(" -> ", pathList.stream().map(String::valueOf).toArray(String[]::new)));
                path.append(")");
            }
            paths.add(path.toString());
        }
        return paths;
    }

    public static void main(String[] args) {
        Dijkstra dijkstra = new Dijkstra();

        // Read edges from a file
        try {
            Scanner scanner = new Scanner(new File("graph_input.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    int u = Integer.parseInt(parts[0]);
                    int v = Integer.parseInt(parts[1]);
                    int cost = Integer.parseInt(parts[2]);
                    dijkstra.addEdge(u, v, cost);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            return;
        }

        // Run Dijkstra's algorithm from a source vertex
        int sourceVertex = 0; // Change this to your desired source vertex
        System.out.println(dijkstra.getShortestPath(sourceVertex));
        List<String> paths = dijkstra.getPaths(sourceVertex);

        // Print the paths and costs
        for (String path : paths) {
            System.out.println(path);
        }
    }
}