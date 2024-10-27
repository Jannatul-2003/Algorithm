
//https://lightoj.com/problem/forwarding-emails
import java.util.*;

public class ForwardingEmails {
    static int N;
    static int[] child;
    static int[] dis;
    static boolean[] visited;

    static void createGraph(int n) {
        N = n;
        child = new int[N];
        visited = new boolean[N];
        dis = new int[N];
    }

    static int getResult() {
        int receiver = -1;
        int max = -1;

        for (int i = 0; i < N; i++) {
            if (dis[i] == 0)
                dfsVisit(i);
            if (dis[i] > max) {
                max = dis[i];
                receiver = i;
            }
        }
        return receiver;
    }

    static int dfsVisit(int u) {
        visited[u] = true;
        int v = child[u];
        int r = 1;
        if (!visited[v]) {
            r = dfsVisit(v) + 1;
        }
        visited[u] = false;
        return dis[u] = r;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            int n = sc.nextInt();
            createGraph(n);
            for (int j = 0; j < n; j++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                child[u - 1] = v - 1;
            }
            int receiver = getResult();
            System.out.println("Case " + (i + 1) + ": " + (receiver + 1));
        }
    }
}
