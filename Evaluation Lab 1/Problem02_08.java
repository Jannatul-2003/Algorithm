import java.io.*;
import java.util.*;

class Vertex {
    int from, to, cost;

    public Vertex(int from, int to, int cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
}

class Graph {
    private final int cityCount;
    private final List<Vertex> Vertexs;
    private List<Integer>[] adjacencyList;

    public Graph(int cityCount) {
        this.cityCount = cityCount;
        this.Vertexs = new ArrayList<>();
        initializeAdjacencyList();
    }

    public void addVertex(int from, int to, int cost) {
        Vertexs.add(new Vertex(from, to, cost));
        adjacencyList[from].add(to);
    }

    @SuppressWarnings("unchecked")
    private void initializeAdjacencyList() {
        adjacencyList = new List[cityCount + 1];
        for (int i = 1; i <= cityCount; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
    }

    public int calculateMinRedirectionCost() {
        int minCost = Integer.MAX_VALUE;

        int totalCombinations = 1 << Vertexs.size(); 
        for (int mask = 0; mask < totalCombinations; mask++) {
            int currentCost = applyCombinationAndCalculateCost(mask);
            if (isStronglyConnected()) {
                minCost = Math.min(minCost, currentCost);
            }
            resetAdjacencyList();
        }
        return minCost;
    }

    private int applyCombinationAndCalculateCost(int mask) {
        int cost = 0;
        for (int i = 0; i < Vertexs.size(); i++) {
            Vertex Vertex = Vertexs.get(i);
            if ((mask & (1 << i)) == 0) {
                adjacencyList[Vertex.from].add(Vertex.to);
            } else {
                adjacencyList[Vertex.to].add(Vertex.from);
                cost += Vertex.cost;
            }
        }
        return cost;
    }

    private void resetAdjacencyList() {
        for (int i = 1; i <= cityCount; i++) {
            if (adjacencyList[i] == null) {
                adjacencyList[i] = new ArrayList<>();
            } else {
                adjacencyList[i].clear();
            }
        }
        for (Vertex Vertex : Vertexs) {
            adjacencyList[Vertex.from].add(Vertex.to);
        }
    }

    private boolean isStronglyConnected() {
        for (int start = 1; start <= cityCount; start++) {
            if (!isReachableFrom(start)) {
                return false;
            }
        }
        return true;
    }

    private boolean isReachableFrom(int start) {
        boolean[] visited = new boolean[cityCount + 1];
        dfs(start, visited);
        for (int i = 1; i <= cityCount; i++) {
            if (!visited[i])
                return false;
        }
        return true;
    }

    private void dfs(int node, boolean[] visited) {
        visited[node] = true;
        for (int neighbor : adjacencyList[node]) {
            if (!visited[neighbor]) {
                dfs(neighbor, visited);
            }
        }
    }
}

public class Problem02_08 {

    public static void main(String[] args) {
        try {

            File inputFile = new File("input2.txt");
            Scanner sc = new Scanner(inputFile);
            int cityCount = sc.nextInt();
            Graph graph = new Graph(cityCount);

            for (int i = 0; i < cityCount; i++) {
                int from = sc.nextInt();
                int to = sc.nextInt();
                int cost = sc.nextInt();
                graph.addVertex(from, to, cost);
            }
            sc.close();
            System.out.println(graph.calculateMinRedirectionCost());

        } catch (FileNotFoundException e) {
            System.out.println("File not found: input2.txt");
        } catch (Exception e) {
            System.out.println("An error occurred while processing the input.");
            e.printStackTrace();
        }
    }
}
