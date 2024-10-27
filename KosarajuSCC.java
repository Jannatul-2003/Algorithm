import java.util.*;

public class KosarajuSCC {

    static final int MAX_N = 20001;
    static int n, m;

    // Node structure to store the adjacency list and reverse adjacency list
    static class Node {
        List<Integer> adj = new ArrayList<>();     // Adjacency list for the original graph
        List<Integer> revAdj = new ArrayList<>();  // Adjacency list for the transposed graph
    }

    static Node[] g = new Node[MAX_N];
    static Stack<Integer> S = new Stack<>();
    static boolean[] visited = new boolean[MAX_N];

    static int[] result = new int[MAX_N];      // Result array to store the SCC index for each node
    static int[] component = new int[MAX_N];          // Component array to store SCC index for each node
    static List<Integer>[] components = new ArrayList[MAX_N];  // List of components
    static int numComponents = 0;                     // Counter for the number of SCCs

    // Initialize the graph
    static {
        for (int i = 0; i < MAX_N; i++) {
            g[i] = new Node();
            components[i] = new ArrayList<>();
        }
    }

    // First DFS to order nodes by their finishing time
    static void dfs1(int x) {
        visited[x] = true;
        for (int neighbor : g[x].adj) {
            if (!visited[neighbor]) {
                dfs1(neighbor);
            }
        }
        S.push(x);  // Push node to stack after it's fully explored
    }

    // Second DFS on the transposed graph
    static void dfs2(int x) {
        result[x] = 1;
        component[x] = numComponents;
        components[numComponents].add(x);
        visited[x] = true;
        for (int neighbor : g[x].revAdj) {
            if (!visited[neighbor]) {
                dfs2(neighbor);
            }
        }
    }

    // Kosaraju's algorithm to find all SCCs
    static void kosaraju() {
        // First DFS pass on the original graph
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs1(i);
            }
        }

        // Reset visited array for the second DFS
        Arrays.fill(visited, false);

        // Second DFS pass on the transposed graph
        while (!S.isEmpty()) {
            int v = S.pop();
            if (!visited[v]) {
                //System.out.print("Component " + numComponents + ": ");
                dfs2(v);
                numComponents++;
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read number of vertices (n) and edges (m)
        n = scanner.nextInt();
        m = scanner.nextInt();

        // Read edges and build both original and transposed graphs
        for (int i = 0; i < m; i++) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            g[a].adj.add(b);         // Add edge a -> b in original graph
            g[b].revAdj.add(a);      // Add edge b -> a in transposed graph
        }

        // Run Kosaraju's algorithm to find SCCs
        kosaraju();

        // Output the total number of SCCs
        for(int i=0; i<result.length; i++) {
            System.out.print(result[i]+" ");
        }

        scanner.close();
    }
}
