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
    }

    void createGraph(String filepath) throws FileNotFoundException {
        try {
            File f = new File(filepath);
            Scanner scanner = new Scanner(f);

            int numVertices = scanner.nextInt();
            int numEdges = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < numEdges; i++) {
                String[] parts = scanner.nextLine().split(" ");
                Vertex fromVertex = findOrCreateVertex(parts[0].trim());
                Vertex toVertex = findOrCreateVertex(parts[1].trim());
                fromVertex.adjList.add(toVertex);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error reading the graph: " + e.getMessage());
        }
    }

    Vertex findOrCreateVertex(String name) {
        return graph.computeIfAbsent(name, Vertex::new);
    }

    Collection<Vertex> getVertices() {
        return graph.values();
    }

}

class SCC {
    Graph mainGraph;
    Graph transposeGraph;
    boolean isTranspose = false;
    Stack<Vertex> stack = new Stack<>();

    SCC(Graph graph) {
        mainGraph = graph;

    }

    int arr[];
    int iterator = 1;

    int[] getStronlyConnected() {
        transposeGraph = getTransposeGraph();
        DFS(mainGraph);
        isTranspose = true;
        while (!stack.isEmpty()) {
            Vertex u = stack.pop();
            if (u.color == Vertex.Color.WHITE) {
                DFSVisit(u);
                iterator++;
            }
        }
        return arr;
    }

    public Graph getTransposeGraph() {
        Graph transposeGraph = new Graph();
        for (Vertex v : mainGraph.getVertices()) {
            for (Vertex adj : v.adjList) {
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
                DFSVisit(v);
            }
        }
    }

    public void DFSVisit(Vertex u) {
        u.color = Vertex.Color.GREY;
        if (isTranspose)
            arr[Integer.parseInt(u.name)] = iterator;
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

}

public class Problem04_08 {
    public static void main(String[] args) throws IOException {
        try {
            Graph g = new Graph();

            g.createGraph("input4.txt");
            SCC scc = new SCC(g);

            int arr[] = new int[g.graph.size() + 1];
            scc.arr = arr;
            arr = scc.getStronlyConnected();
            int max = Arrays.stream(arr).max().orElseThrow();
            System.out.println(max);
            for (int i = 1; i < arr.length; i++)
                System.out.print(arr[i] + " ");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
}
