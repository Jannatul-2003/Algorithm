//https://www.hackerearth.com/practice/algorithms/graphs/strongly-connected-components/practice-problems/algorithm/gcd-on-directed-graph-1122228a/
import java.io.*;
import java.util.*;

public class GCDOnDirectedGraph {

    static final int N = 100010;
    static List<List<Integer>> divs = new ArrayList<>(N);

    static class Graph {
        int n;
        List<List<Integer>> adj;
        int[] val;
        int[] I;

        Graph(int n) {
            this.n = n;
            adj = new ArrayList<>(n);
            val = new int[n];
            I = new int[n];
            for (int i = 0; i < n; i++) {
                adj.add(new ArrayList<>());
            }
        }

        void addEdge(int src, int dst) {
            adj.get(src).add(dst);
        }

        List<List<Integer>> stronglyConnectedComponents() {
            List<List<Integer>> scc = new ArrayList<>();
            List<Integer> S = new ArrayList<>();
            List<Integer> B = new ArrayList<>();
            Arrays.fill(I, 0);

            for (int u = 0; u < n; u++) {
                if (I[u] == 0) {
                    dfs(u, S, B, scc);
                }
            }

            for (int i = 0; i < n; i++) {
                I[i] -= n + 1;
            }
            return scc;
        }

        void dfs(int u, List<Integer> S, List<Integer> B, List<List<Integer>> scc) {
            B.add(I[u] = S.size());
            S.add(u);
            for (int v : adj.get(u)) {
                if (I[v] == 0) {
                    dfs(v, S, B, scc);
                } else {
                    while (I[v] < B.get(B.size() - 1)) B.remove(B.size() - 1);
                }
            }
            if (I[u] == B.get(B.size() - 1)) {
                scc.add(new ArrayList<>());
                B.remove(B.size() - 1);
                for (; I[u] < S.size(); S.remove(S.size() - 1)) {
                    int last = S.get(S.size() - 1);
                    scc.get(scc.size() - 1).add(last);
                    I[last] = n + scc.size();
                }
            }
        }

        Graph sccDag() {
            List<List<Integer>> sccs = stronglyConnectedComponents();
            Graph ret = new Graph(sccs.size());

            for (int i = 0; i < n; i++) {
                ret.val[I[i]] = gcd(ret.val[I[i]], val[i]);
                for (int j : adj.get(i)) {
                    if (I[i] != I[j]) {
                        ret.addEdge(I[i], I[j]);
                    }
                }
            }
            return ret;
        }
    }

    static void precomputeDivisors() {
        for (int i = 0; i < N; i++) divs.add(new ArrayList<>());
        for (int i = 1; i < N; i++) {
            for (int j = i; j < N; j += i) {
                divs.get(j).add(i);
            }
        }
    }

    static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static void main(String[] args) throws IOException {
        precomputeDivisors();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");
        int n = Integer.parseInt(input[0]);
        int m = Integer.parseInt(input[1]);

        Graph G = new Graph(n);
        input = br.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            G.val[i] = Integer.parseInt(input[i]);
        }

        for (int i = 0; i < m; i++) {
            input = br.readLine().split(" ");
            int u = Integer.parseInt(input[0]) - 1;
            int v = Integer.parseInt(input[1]) - 1;
            G.addEdge(u, v);
        }

        Graph g = G.sccDag();
        n = g.n;

        List<List<Integer>> con = new ArrayList<>(n);
        for (int i = 0; i < n; i++) con.add(new ArrayList<>());

        for (int i = 0; i < n; i++) {
            for (int j : g.adj.get(i)) {
                con.get(j).add(i);
            }
        }

        int ans = g.val[n - 1];
        Set<Integer>[] dp = new HashSet[n];
        for (int i = 0; i < n; i++) dp[i] = new HashSet<>();
        dp[n - 1].add(g.val[n - 1]);

        for (int i = n - 2; i >= 0; i--) {
            dp[i].add(g.val[i]);
            ans = Math.min(ans, g.val[i]);
            for (int j : con.get(i)) {
                for (int d : dp[j]) {
                    int r = gcd(d, g.val[i]);
                    dp[i].add(r);
                    ans = Math.min(ans, r);
                }
            }
        }

        System.out.println(ans);
    }
}
