import java.io.*;
import java.util.*;
 
class Graph {
    List<List<Integer>> adj;
    int len;
 
    Graph(int n) {
        adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }
    }
 
    void addEdge(int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }
 
    // BFS function to find frequency of nodes at distance i from start node
    List<Integer> bfs(int s, int t, int n) {
        Queue<Integer> q = new LinkedList<>();
        int[] dist = new int[n + 1];
        Arrays.fill(dist, -1);
        List<Integer> layer = new ArrayList<>(Collections.nCopies(n, 0));
 
        q.add(s);
        dist[s] = 0;
 
        while (!q.isEmpty()) {
            int u = q.poll();
            layer.set(dist[u], layer.get(dist[u]) + 1);
 
            for (int v : adj.get(u)) {
                if (dist[v] == -1) {
                    dist[v] = dist[u] + 1;
                    q.add(v);
                }
            }
        }
 
        len = dist[t]; // Set len as distance between node s and node t
        return layer;
    }
}
 
public class StationPairs {
    public static long stationPairs(int n, int m, int x, int y, List<int[]> stationPairs) {
        Graph graph = new Graph(n);
 
        // Adding edges to the graph
        for (int[] pair : stationPairs) {
            graph.addEdge(pair[0], pair[1]);
        }
 
        // Get number of nodes at distance i from x and y using BFS
        List<Integer> dx = graph.bfs(x, y, n);
        List<Integer> dy = graph.bfs(y, x, n);
 
        // Prefix sum array ps to store number of nodes <= i distance from y
        long[] ps = new long[n + 1];
        for (int i = 0; i < n; i++) {
            ps[i + 1] = ps[i] + dx.get(i);
        }
 
        // Start with max possible pairs (n * (n - 1) / 2) - m
        long ans = (long) n * (n - 1) / 2 - m;
 
        // Subtract invalid pairs
        for (int i = 0; i < graph.len - 1; i++) {
            ans -= dy.get(i) * ps[graph.len - i - 1];
        }
 
        return ans;
    }
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
 
        while (t-- > 0) {
            String[] input = br.readLine().split(" ");
            int n = Integer.parseInt(input[0]);
            int m = Integer.parseInt(input[1]);
            int x = Integer.parseInt(input[2]);
            int y = Integer.parseInt(input[3]);
 
            List<int[]> stationPairs = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                String[] pair = br.readLine().split(" ");
                stationPairs.add(new int[]{Integer.parseInt(pair[0]), Integer.parseInt(pair[1])});
            }
 
            System.out.println(stationPairs(n, m, x, y, stationPairs));
        }
    }
}
