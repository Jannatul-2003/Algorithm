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
        if (this == obj)
            return true;
        if (!(obj instanceof Vertex))
            return false;
        Vertex vertex = (Vertex) obj;
        return this.name == vertex.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String toString() {
        return Integer.toString(name);
    }
}

class Graph {
    HashMap<Integer, Vertex> graph = new HashMap<>();

    public void init(){
        time=0;
        topologicalSort.clear();
        dfsTraversal.clear();;
        for(Vertex v: graph.values())
        {
            v.color=Vertex.Color.WHITE;
            v.discoveredTime=Integer.MAX_VALUE;
            v.finishedTime=Integer.MAX_VALUE;
        }
    }
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

    public void displayGraph() {
        for (Vertex v : graph.values()) {
            System.out.print(v.name + "->");
            for (Vertex adjacenVertex : v.neighbours) {
                System.out.print(" " + adjacenVertex.name);
            }
            System.out.println();
        }
    }

    public void addEdge(int from, int to) {
        Vertex v1 = getVertex(from);
        Vertex v2 = getVertex(to);
        v1.neighbours.add(v2);
    }

    int time = 0;
    Stack<Integer> topologicalSort = new Stack<>();
    ArrayList<Integer> dfsTraversal = new ArrayList<>();

    public void DFS(int v1Name) {
        init();
        Vertex v = getVertex(v1Name);
        dfsVisit(v);
        System.out.print("DFS Traversal starting from vertex 5:" + dfsTraversal.get(0));
        dfsTraversal.remove(0);
        for (int u : dfsTraversal) {
            System.out.print(" " + u);
        }
        System.out.println();
    }

    public List<Integer> topologicalSort() {
        init();
        for (Vertex v : graph.values()) {
            if (v.color == Vertex.Color.WHITE) {
                dfsVisit(v);
            }
        }
        List<Integer> result = new ArrayList<>();
    while (!topologicalSort.isEmpty()) {
        result.add(topologicalSort.pop());
    }
    return result;
    }

    public void dfsVisit(Vertex u) {
        time++;
        u.color = Vertex.Color.GREY;
        u.discoveredTime = time;
        dfsTraversal.add(u.name);

        for (Vertex v : u.neighbours) {
            if (v.color == Vertex.Color.WHITE) {
                v.parent = u;
                dfsVisit(v);
            }
        }
        time++;
        u.color = Vertex.Color.BLACK;
        u.finishedTime = time;
        topologicalSort.push(u.name);
        //System.out.print("Finished: " + u.name + " at time " + u.finishedTime);
    }

}

public class Lab2 {
    public static void main(String[] args) throws IOException {

        Graph graph = new Graph("input.txt");

        System.out.println("Graph adjacency list:");
        graph.displayGraph();

        System.out.println("\nPerforming DFS starting from vertex 5:");
        graph.DFS(5);

        System.out.println("\nPerforming Topological Sort:");
        List<Integer> topoOrder = graph.topologicalSort();
        System.out.println("Topological Sort order: " + topoOrder);
    }
}
