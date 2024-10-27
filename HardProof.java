//https://community.topcoder.com/stat?c=problem_statement&pm=14334
import java.io.*;
import java.util.*;

public class HardProof {

    public int minimumCost(int[] D) {
        int N = (int) Math.sqrt(D.length); // Number of nodes/statements
        int maxDifficulty = Arrays.stream(D).max().getAsInt();
        int minDifference = 0, maxDifference = maxDifficulty;
        
        while (minDifference < maxDifference) {
            int mid = (minDifference + maxDifference) / 2;
            if (canFormSCC(D, N, mid)) {
                maxDifference = mid;
            } else {
                minDifference = mid + 1;
            }
        }
        
        return minDifference;
    }
    
    // Check if we can make the graph strongly connected within a given difficulty difference.
    private boolean canFormSCC(int[] D, int N, int maxDiff) {
        for (int minD = 0; minD <= 150000 - maxDiff; minD++) {
            int maxD = minD + maxDiff;
            
            List<List<Integer>> graph = buildGraph(D, N, minD, maxD);
            if (isStronglyConnected(graph, N)) {
                return true;
            }
        }
        return false;
    }
    
    // Build a graph with edges only within the difficulty range [minD, maxD].
    private List<List<Integer>> buildGraph(int[] D, int N, int minD, int maxD) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            graph.add(new ArrayList<>());
            for (int j = 0; j < N; j++) {
                int difficulty = D[i * N + j];
                if (difficulty >= minD && difficulty <= maxD) {
                    graph.get(i).add(j);
                }
            }
        }
        return graph;
    }
    
    // Check if the graph is strongly connected using Kosaraju's algorithm.
private boolean isStronglyConnected(List<List<Integer>> graph, int N) {
    boolean[] visited = new boolean[N];
    Stack<Integer> finishStack = new Stack<>();
    
    // Step 1: Perform DFS on the original graph to get nodes in finishing order
    for (int i = 0; i < N; i++) {
        if (!visited[i]) {
            fillFinishStack(graph, i, visited, finishStack);
        }
    }

    // Step 2: Reverse the graph
    List<List<Integer>> reversedGraph = new ArrayList<>();
    for (int i = 0; i < N; i++) reversedGraph.add(new ArrayList<>());
    for (int i = 0; i < N; i++) {
        for (int j : graph.get(i)) {
            reversedGraph.get(j).add(i);
        }
    }

    // Step 3: Perform DFS on the reversed graph in the order of decreasing finishing time
    Arrays.fill(visited, false);
    int components = 0;
    while (!finishStack.isEmpty()) {
        int node = finishStack.pop();
        if (!visited[node]) {
            dfs(reversedGraph, node, visited);
            components++;
            if (components > 1) return false; // More than one SCC found, not strongly connected
        }
    }
    
    return true; // If we only found one SCC, the graph is strongly connected
}

// Standard DFS to mark all reachable nodes and record finishing order in a stack
private void fillFinishStack(List<List<Integer>> graph, int node, boolean[] visited, Stack<Integer> finishStack) {
    visited[node] = true;
    for (int neighbor : graph.get(node)) {
        if (!visited[neighbor]) {
            fillFinishStack(graph, neighbor, visited, finishStack);
        }
    }
    finishStack.push(node);
}

    // Standard DFS to mark all reachable nodes
    private void dfs(List<List<Integer>> graph, int node, boolean[] visited) {
        visited[node] = true;
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfs(graph, neighbor, visited);
            }
        }
    }
    public static void main(String[] args) {
        // Specify the path to the input file
        String filePath = "input.txt";
        
        try {
            // Reading input from file
            Scanner scanner = new Scanner(new File(filePath));
            
            // Reading data size (N^2 elements)
            List<Integer> data = new ArrayList<>();
            while (scanner.hasNextInt()) {
                data.add(scanner.nextInt());
            }
            
            // Converting List<Integer> to int[]
            int[] D = data.stream().mapToInt(i -> i).toArray();
            
            // Instantiate HardProof and call minimumCost
            HardProof solution = new HardProof();
            int result = solution.minimumCost(D);
            
            System.out.println("Minimum Difficulty Difference: " + result);
            
            scanner.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            e.printStackTrace();
        }
    }
}
