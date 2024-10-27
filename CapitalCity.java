//https://www.spoj.com/problems/CAPCITY/
import java.io.*;
import java.util.*;

public class CapitalCity {
    static int N, M, t = 1, cycle = 1;
    static int[] dt, ft, ct, outdegree;
    static boolean[] visit;
    static ArrayList<Integer>[] G, GT;
    static ArrayList<Integer> Q;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");
        N = Integer.parseInt(line[0]);
        M = Integer.parseInt(line[1]);

        dt = new int[N + 1];
        ft = new int[N + 1];
        ct = new int[N + 1];
        outdegree = new int[N + 1];
        visit = new boolean[N + 1];
        G = new ArrayList[N + 1];
        GT = new ArrayList[N + 1];
        Q = new ArrayList<>();

        for (int i = 1; i <= N; i++) {
            G[i] = new ArrayList<>();
            GT[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            line = br.readLine().split(" ");
            int u = Integer.parseInt(line[0]);
            int v = Integer.parseInt(line[1]);
            G[u].add(v);
            GT[v].add(u);
        }

        for (int i = 1; i <= N; i++) {
            if (!visit[i]) dfs1(i);
        }

        for (int i = Q.size() - 1; i >= 0; i--) {
            int u = Q.get(i);
            if (visit[u]) {
                dfs2(u, ft[u]);
                cycle++;
            }
        }

        for (int u = 1; u <= N; u++) {
            for (int v : G[u]) {
                if (ct[u] != ct[v]) {
                    outdegree[ct[u]]++;
                }
            }
        }

        int count = 0;
        for (int i = 1; i < cycle; i++) {
            if (outdegree[i] == 0) count++;
        }

        if (count > 1) {
            System.out.println("0");
        } else {
            List<Integer> result = new ArrayList<>();
            for (int i = 1; i <= N; i++) {
                if (outdegree[ct[i]] == 0) result.add(i);
            }
            System.out.println(result.size());
            for (int i = 0; i < result.size(); i++) {
                if (i != 0) System.out.print(" ");
                System.out.print(result.get(i));
            }
            System.out.println();
        }
    }

    static void dfs1(int u) {
        visit[u] = true;
        dt[u] = t++;
        for (int v : G[u]) {
            if (!visit[v]) dfs1(v);
        }
        ft[u] = t++;
        Q.add(u);
    }

    static void dfs2(int u, int cmp) {
        visit[u] = false;
        ct[u] = cycle;
        for (int v : GT[u]) {
            if (visit[v] && cmp > ft[v]) dfs2(v, cmp);
        }
    }
}
