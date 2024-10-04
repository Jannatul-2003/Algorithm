import java.io.*;
import java.util.*;

class Vertex {
    public int name;
    public Vertex parent = null;
    public Set<Vertex> neighbours = new HashSet<>();
    public Color color = Color.WHITE;
    public int discoveredTime = Integer.MAX_VALUE;
    public int finishedTime = Integer.MAX_VALUE;

    public enum Color {
        WHITE, GREY, BLACK;
    }

    Vertex(int name) {
        this.name = name;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vertex)) return false;
        Vertex vertex = (Vertex) obj;
        return this.name == vertex.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

class Graph {
    HashMap<Integer, Vertex> graph = new HashMap<>();

    Graph(String filePath) {
        try {
            createGraphFromFile(filePath);
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error reading the graph: " + e.getMessage());
        }
    }

    private void createGraphFromFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        if (!scanner.hasNextInt()) {
            throw new RuntimeException("No vertex count found in the input file.");
        }
        int vertexNum = scanner.nextInt();

        if (!scanner.hasNextInt()) {
            throw new RuntimeException("No edge count found in the input file.");
        }
        int edgeNum = scanner.nextInt();

        for (int i = 0; i < edgeNum; i++) {
            if (!scanner.hasNextInt() || !scanner.hasNextInt()) {
                throw new RuntimeException("Not enough data for edges.");
            }
            int v1Name = scanner.nextInt();
            int v2Name = scanner.nextInt();
            addEdge(v1Name, v2Name);
        }
        scanner.close();
    }

    public Vertex getVertex(int name) {
        if (!graph.containsKey(name)) {
            graph.put(name, new Vertex(name));
        }
        return graph.get(name);
    }

    public void addEdge(int v1Name, int v2Name) {
        Vertex v1 = getVertex(v1Name);
        Vertex v2 = getVertex(v2Name);
        v1.neighbours.add(v2);
        v2.neighbours.add(v1);
    }
}

public class DFS {
    int time = 0;

    public void dfsVisit(Vertex u) {
        time++;
        u.color = Vertex.Color.GREY;
        u.discoveredTime = time;
        System.out.println("Discovered: " + u.name + " at time " + u.discoveredTime);

        for (Vertex v : u.neighbours) {
            if (v.color == Vertex.Color.WHITE) {
                v.parent = u;
                dfsVisit(v);
            }
        }
        time++;
        u.color = Vertex.Color.BLACK;
        u.finishedTime = time;
        System.out.println("Finished: " + u.name + " at time " + u.finishedTime);
    }

    public static void main(String[] args) { 
        Graph g = new Graph("input.txt");
        DFS dfs = new DFS();
        
        for (Vertex v : g.graph.values()) {
            if (v.color == Vertex.Color.WHITE) {
                dfs.dfsVisit(v);
            }
        }
    }
}
