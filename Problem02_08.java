import java.util.*;

public class Problem02_08 {
    
    static List<Integer>[] adj;
    static boolean[] visited;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int R = sc.nextInt(); // Number of cities/roads
        
        // Initialize adjacency list
        adj = new List[R + 1];
        for (int i = 1; i <= R; i++) {
            adj[i] = new ArrayList<>();
        }
        
        // Store edges and their costs
        int[][] edges = new int[R][3];
        for (int i = 0; i < R; i++) {
            edges[i][0] = sc.nextInt(); // from
            edges[i][1] = sc.nextInt(); // to
            edges[i][2] = sc.nextInt(); // cost
            adj[edges[i][0]].add(edges[i][1]);
        }
        
        int minCost = Integer.MAX_VALUE;
        
        // Try all possible combinations of road directions
        for (int mask = 0; mask < (1 << R); mask++) {
            // Create temporary adjacency list for current configuration
            List<Integer>[] tempAdj = new List[R + 1];
            for (int i = 1; i <= R; i++) {
                tempAdj[i] = new ArrayList<>();
            }
            
            int cost = 0;
            // For each road
            for (int i = 0; i < R; i++) {
                if ((mask & (1 << i)) == 0) {
                    // Keep original direction
                    tempAdj[edges[i][0]].add(edges[i][1]);
                } else {
                    // Reverse direction and add cost
                    tempAdj[edges[i][1]].add(edges[i][0]);
                    cost += edges[i][2];
                }
            }
            
            // Check if current configuration makes graph strongly connected
            if (isStronglyConnected(tempAdj, R)) {
                minCost = Math.min(minCost, cost);
            }
        }
        
        System.out.println(minCost);
    }
    
    // Check if graph is strongly connected using DFS
    static boolean isStronglyConnected(List<Integer>[] graph, int n) {
        for (int start = 1; start <= n; start++) {
            visited = new boolean[n + 1];
            dfs(start, graph);
            
            // Check if all vertices are reachable from start
            for (int i = 1; i <= n; i++) {
                if (!visited[i]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    static void dfs(int v, List<Integer>[] graph) {
        visited[v] = true;
        for (int u : graph[v]) {
            if (!visited[u]) {
                dfs(u, graph);
            }
        }
    }
}