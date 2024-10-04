import java.io.*;
import java.util.*;

class Vertex {
    public int name;
    public Vertex parent = null;
    public int distance = Integer.MAX_VALUE;
    public HashSet<Vertex> neighbours = new HashSet<Vertex>();
    public Color color = Color.WHITE;

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
}

class Graph {
    HashMap<Integer, Vertex> graph = new HashMap<Integer, Vertex>();

    Graph()
    {
        Scanner scanner=new Scanner(System.in);
        int vertexNum = scanner.nextInt();

        int edgeNum = scanner.nextInt();

        for (int i = 0; i < edgeNum; i++) {
            int v1Name = scanner.nextInt();
            int v2Name = scanner.nextInt();
            addEdge(v1Name, v2Name);
        }
        BFS bfs=new BFS(graph.entrySet().iterator().next().getValue());

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

    // ki bolse bujhi nai
    public void addVertex(int v1Name) {
        if (!graph.containsKey(v1Name)) {
            graph.put(v1Name, new Vertex(v1Name));
        }
    }

    public void addMultipleVertex(int n) {
        Scanner sc = new Scanner(System.in);
        while (n-- != 0) {
            int vName = sc.nextInt();
            addVertex(vName);
        }
        //sc.close();
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

    public int numOfVertices() {
        return this.graph.size();
    }

    public void getAdjacentVertices(Vertex vertex) {
        HashSet<Vertex> neighbours = vertex.neighbours;
        for (Vertex neighbour : neighbours) {
            System.out.print(neighbour.name + " ");
        }
    }

    public void printAdjacencyList() {
        for (Vertex vertex : graph.values()) {
            System.out.print(vertex.name);
            for (Vertex neighbour : vertex.neighbours) {
                System.out.print("-->" + neighbour.name);

            }
            System.out.println();
        }
    }

    class BFS {
        Queue<Vertex> queue = new LinkedList<Vertex>();
        Vertex src;

        BFS(Vertex src) {
            this.src = src;
            System.out.println("BFS Traversal");
            BFSTraversal();
            System.out.println();
            for (Vertex destinationVertex : graph.values()) {
                System.out.println("Shortest path traversal to " + destinationVertex.name);
                printShortestPath(destinationVertex);
                System.out.println();
            }

        }

        void BFSTraversal() {
            queue.add(src);
            src.color = Vertex.Color.GREY;
            src.distance = 0;
            while (!queue.isEmpty()) {
                Vertex vertex = queue.poll();
                for (Vertex adjacentVertex : vertex.neighbours) {
                    if (adjacentVertex.color == Vertex.Color.WHITE) {
                        adjacentVertex.parent = vertex;
                        adjacentVertex.distance = vertex.distance + 1;
                        queue.add(adjacentVertex);
                        adjacentVertex.color = Vertex.Color.GREY;
                    }
                }
                vertex.color = Vertex.Color.BLACK;
                if (vertex != src)
                    System.out.print("-->");
                System.out.print(vertex.name);
            }
        }

        void printShortestPath(Vertex destinationVertex) {
            if (src == destinationVertex)
                System.out.print(src.name);
            else if (destinationVertex.parent == null)
                System.out.println("No Path");// for disconnected vertex
            else {
                printShortestPath(destinationVertex.parent);
                System.out.print("-->" + destinationVertex.name);
            }
        }
    }
}

public class Lab1 {
    public static void main(String[] args) {
        Graph g = new Graph("input.txt");
    }
}
