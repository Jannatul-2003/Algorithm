import java.util.*;
import java.io.*;

class Vertex {
    String name;

    enum Color {
        WHITE, GREY, BLACK;
    }

    Color color = Color.WHITE;
    Vertex parent = null;
    Set<Vertex> adjList = new HashSet<>();

    Vertex(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Vertex))
            return false;
        Vertex vertex = (Vertex) obj;
        return this.name.equals(vertex.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

class Graph {
    Map<String, Vertex> graph = new HashMap<>();

    Graph() {
        // Default constructor
    }

    // Constructor to initialize graph from file
    Graph(String filePath) throws FileNotFoundException {
        createGraphFromFile(filePath);
    }

    // Read the graph from a file
    void createGraphFromFile(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));

        // Read number of vertices and edges
        int numVertices = scanner.nextInt();
        int numEdges = scanner.nextInt();
        scanner.nextLine(); // Move to the next line

        // Process each edge definition
        for (int i = 0; i < numEdges; i++) {
            String[] parts = scanner.nextLine().split(" ");

            // Create vertices if they don't exist and add edge
            Vertex fromVertex = findOrCreateVertex(parts[0].trim());
            Vertex toVertex = findOrCreateVertex(parts[1].trim());
            fromVertex.adjList.add(toVertex);
        }
        scanner.close();
    }

    // Find or create a vertex by name
    Vertex findOrCreateVertex(String name) {
        return graph.computeIfAbsent(name, Vertex::new);
    }

    // Get all vertices
    Collection<Vertex> getVertices() {
        return graph.values();
    }

    // Print the graph (for debugging)
    void printGraph() {
        for (Vertex v : graph.values()) {
            System.out.print(v + " -> ");
            for (Vertex adj : v.adjList) {
                System.out.print(adj + " ");
            }
            System.out.println();
        }
    }
}

public class SCC {
    Graph mainGraph;
    Graph transposeGraph;
    boolean isTranspose = false;
    Stack<Vertex> stack = new Stack<>();

    SCC(String filePath) {
        try {
            mainGraph = new Graph(filePath);
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + e.getMessage());
        }
        transposeGraph = getTransposeGraph();
        DFS(mainGraph);
        isTranspose = true;
        while (!stack.isEmpty()) {
            Vertex u = stack.pop();
            if (u.color == Vertex.Color.WHITE) {
                System.out.println("\nSCC:");
                DFSVisit(u);
                System.out.println();
            }
        }
    }

    public Graph getTransposeGraph() {
        Graph transposeGraph = new Graph();
        for (Vertex v : mainGraph.graph.values()) {
            for (Vertex adj : v.adjList) {
                // Add the reverse edge in the transpose graph
                Vertex fromVertex = transposeGraph.findOrCreateVertex(adj.name);
                Vertex toVertex = transposeGraph.findOrCreateVertex(v.name);
                fromVertex.adjList.add(toVertex);
            }
        }
        return transposeGraph;
    }

    public void DFS(Graph currentGraph) {
        for (Vertex v : currentGraph.graph.values()) {
            if (v.color == Vertex.Color.WHITE) {
                System.out.println("DFS:");
                DFSVisit(v);
            }
        }
    }

    public void DFSVisit(Vertex u) {
        u.color = Vertex.Color.GREY;
        System.out.print(" " + u);
        for (Vertex v : u.adjList) {
            if (v.color == Vertex.Color.WHITE) {
                v.parent = u;
                DFSVisit(v);
            }

        }
        u.color = Vertex.Color.BLACK;
        if (!isTranspose) {
            stack.push(transposeGraph.findOrCreateVertex(u.name));
        }
    }

    public static void main(String[] args) {
        try {
            // Redirect stdout to a file
            PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
            System.setOut(out);

            // Redirect stderr to a file
            PrintStream err = new PrintStream(new FileOutputStream("error.txt"));
            System.setErr(err);
            SCC scc = new SCC("input.txt");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
}
