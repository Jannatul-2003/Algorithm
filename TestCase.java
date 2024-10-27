
//https://www.hackerearth.com/practice/algorithms/graphs/strongly-connected-components/tutorial/
import java.util.*;
import java.io.*;

class Vertex {
    int number;
    Set<Vertex> adjList;
    Color color;

    Vertex(int number) {
        this.number = number;
        adjList = new HashSet<>();
        color = Color.WHITE;
    }

    enum Color {
        WHITE, GREY, BLACK;
    }

    public boolean equals(Object o) {
        if (o instanceof Vertex) {
            return number == ((Vertex) o).number;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(number);
    }

    public String toString() {
        return Integer.toString(number);
    }
}

class Graph {
    HashMap<Integer, Vertex> originalGraph = new HashMap<>();
    HashMap<Integer, Vertex> transposeGraph = new HashMap<>();

    Graph(int n) {
        for (int i = 1; i <= n; i++) {
            originalGraph.put(i, new Vertex(i));
            transposeGraph.put(i, new Vertex(i));
        }
    }

    Vertex findOrCreateVertex(int u, HashMap<Integer, Vertex> graph) {
        if (!graph.containsKey(u)) {
            graph.put(u, new Vertex(u));
        }
        return graph.get(u);
    }

    void addEdge(int u, int v) {
        Vertex from = findOrCreateVertex(u, originalGraph);
        Vertex to = findOrCreateVertex(v, originalGraph);
        from.adjList.add(to);
        Vertex toTransposeVertex = findOrCreateVertex(u, transposeGraph);
        Vertex fromTransposVertex = findOrCreateVertex(v, transposeGraph);
        fromTransposVertex.adjList.add(toTransposeVertex);
    }

    Collection<Vertex> getVertices() {
        return originalGraph.values();
    }
}

class SCC {
    Graph graph;
    Stack<Integer> stack;
    boolean isTranspose = false;
    List<Integer> list;

    SCC(Graph graph) {
        this.graph = graph;
        stack = new Stack<>();
    }

    void DFS() {
        for (Vertex vertex : graph.getVertices()) {
            if (vertex.color == Vertex.Color.WHITE) {
                DFSVisit(vertex);
            }
        }
    }

    void DFSVisit(Vertex u) {
        u.color = Vertex.Color.GREY;
        if (isTranspose)
            list.add(u.number);

        for (Vertex v : u.adjList) {
            if (v.color == Vertex.Color.WHITE) {
                DFSVisit(v);
            }
        }
        u.color = Vertex.Color.BLACK;
        if (!isTranspose)
            stack.push(u.number);
    }

    int getResultbySCC() {
        DFS();
        int e, o;
        e = o = 0;
        isTranspose = true;
        while (!stack.isEmpty()) {
            list = new ArrayList<>();
            int u = stack.pop();
            Vertex vertex = graph.transposeGraph.get(u);
            if (vertex.color == Vertex.Color.WHITE) {
                DFSVisit(vertex);
                if (list.size() % 2 == 0)
                    e += list.size();
                else
                    o += list.size();
                // System.out.println(list+" "+list.size()+" "+e+" "+o);

            }
        }
        return o - e;
    }
}

public class TestCase {
    public static void main(String[] args) {
        int n, m;
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        Graph graph = new Graph(n);
        while (m-- != 0) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            graph.addEdge(u, v);
        }
        SCC scc = new SCC(graph);
        System.out.println(scc.getResultbySCC());
    }
}