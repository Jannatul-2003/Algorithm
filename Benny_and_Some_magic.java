//
//16 tle
import java.util.*;

public class Benny_and_Some_magic {
    static class Edge {
        int to, weight;
        
        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }
    
    static List<List<Edge>> graph;
    static boolean[] visited;
    static long maxScore = 0;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        
        // Initialize graph with initial capacity
        graph = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        
        // Read edges
        for (int i = 0; i < m; i++) {
            int v = sc.nextInt();
            int u = sc.nextInt();
            int w = sc.nextInt();
            graph.get(v).add(new Edge(u, w));
        }
        
        Thread solver = new Thread(null, () -> {
            try {
                solve(n);
            } catch (Exception e) {
                return;
            }
        }, "1", 1 << 26);  // 64MB stack size
        solver.start();
        try {
            solver.join();
        } catch (InterruptedException e) {
            return;
        }
        
        System.out.println(maxScore);
        sc.close();
    }
    
    static void solve(int n) {
        visited = new boolean[n + 1];
        // Start DFS from each node in parallel if possible
        for (int i = 1; i <= n; i++) {
            Arrays.fill(visited, false);
            dfs(i, Long.MAX_VALUE, Long.MIN_VALUE);
        }
    }
    
    static void dfs(int node, long min, long max) {
        visited[node] = true;
        List<Edge> edges = graph.get(node);
        
        for (int i = 0; i < edges.size(); i++) {
            Edge e = edges.get(i);
            long newMin = min == Long.MAX_VALUE ? e.weight : Math.min(min, e.weight);
            long newMax = max == Long.MIN_VALUE ? e.weight : Math.max(max, e.weight);
            
            // Update max score if we've used at least one edge
            if (min != Long.MAX_VALUE) {
                maxScore = Math.max(maxScore, newMax - newMin);
            }
            
            // Only continue DFS if we can improve score
            if (!visited[e.to] || (newMax - newMin > maxScore)) {
                dfs(e.to, newMin, newMax);
            }
        }
        
        visited[node] = false; // Backtrack
    }
}
//
//16 tle without thread
/*import java.util.*;

public class Benny_and_Some_magic {
    static class Edge {
        int to, weight;
        
        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }
    
    static List<List<Edge>> graph;
    static long maxScore = 0;
    static Map<String, Long> memo;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        
        // Initialize graph with initial capacity
        graph = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        
        // Read edges
        for (int i = 0; i < m; i++) {
            int v = sc.nextInt();
            int u = sc.nextInt();
            int w = sc.nextInt();
            graph.get(v).add(new Edge(u, w));
        }
        
        // Solve with each node as the starting point
        solve(n);
        
        System.out.println(maxScore);
        sc.close();
    }
    
    static void solve(int n) {
        memo = new HashMap<>();
        
        // Start BFS from each node to calculate maxScore
        for (int i = 1; i <= n; i++) {
            bfs(i, n);
        }
    }
    
    static void bfs(int startNode, int n) {
        Queue<PathState> queue = new LinkedList<>();
        queue.offer(new PathState(startNode, Long.MAX_VALUE, Long.MIN_VALUE));
        
        while (!queue.isEmpty()) {
            PathState state = queue.poll();
            int node = state.node;
            long min = state.min;
            long max = state.max;
            
            String memoKey = node + "," + min + "," + max;
            if (memo.containsKey(memoKey)) continue;
            memo.put(memoKey, max - min);
            
            for (Edge edge : graph.get(node)) {
                long newMin = Math.min(min, edge.weight);
                long newMax = Math.max(max, edge.weight);
                
                if (min != Long.MAX_VALUE) {
                    long score = newMax - newMin;
                    maxScore = Math.max(maxScore, score);
                }
                
                queue.offer(new PathState(edge.to, newMin, newMax));
            }
        }
    }

    // Helper class to maintain path state
    static class PathState {
        int node;
        long min, max;

        public PathState(int node, long min, long max) {
            this.node = node;
            this.min = min;
            this.max = max;
        }
    }
}
 */