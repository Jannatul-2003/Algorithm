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

    void createGraph() {
        Scanner scanner = new Scanner(System.in);

        int numVertices = scanner.nextInt();
        int numEdges = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < numEdges; i++) {
            String[] parts = scanner.nextLine().split(" ");
            Vertex fromVertex = findOrCreateVertex(parts[0].trim());
            Vertex toVertex = findOrCreateVertex(parts[1].trim());
            fromVertex.adjList.add(toVertex);
        }
        scanner.close();
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
    int i = 0;
    ArrayList<ArrayList<Integer>> stronglyConnected = new ArrayList<>();

    SCC(Graph graph) {
        mainGraph = graph;

    }

    ArrayList<ArrayList<Integer>> getStronlyConnected() {
        transposeGraph = getTransposeGraph();
        DFS(mainGraph);
        isTranspose = true;
        while (!stack.isEmpty()) {
            Vertex u = stack.pop();
            if (u.color == Vertex.Color.WHITE) {
                stronglyConnected.add(new ArrayList<>());
                DFSVisit(u);
                i++;
            }
        }
        return stronglyConnected;
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
            stronglyConnected.get(i).add(Integer.parseInt(u.name));
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

public class AWalkToRemember {
    public static void main(String[] args) {
        try {
            Graph g = new Graph();

            g.createGraph();
            SCC scc = new SCC(g);

            int arr[] = new int[g.graph.size() + 1];
            ArrayList<ArrayList<Integer>> sList = scc.getStronlyConnected();
            for (List<Integer> list : sList) {
                if (list.size() > 1) {
                    for (int i = 0; i < list.size(); i++)
                        arr[list.get(i)] = 1;
                }
            }
            for (int i = 1; i < arr.length; i++)
                System.out.print(arr[i] + " ");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
}