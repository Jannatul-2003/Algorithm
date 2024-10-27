
//https://lightoj.com/problem/forwarding-emails
import java.util.*;

public class Practice {
    static int N;
    static int[] child;
    static int dis;
    static boolean[] visited;
    static Queue<Integer> queue = new LinkedList<>();

    static void createGraph(int n) {
        N = n;
        child = new int[N];
        visited = new boolean[N];
    }

    static int getResult() {
        int receiver = -1;
        int max = -1;

        for (int i = 0; i < N; i++) {
            Arrays.fill(visited, false);
                dis=1;
                bfs(i);
                if (dis > max) {
                    max = dis;
                    receiver = i;
                }
            }
        
        return receiver;
    }

    static void bfs(int s) {
        if(!visited[s])
            queue.add(s);
        visited[s] = true;
        while(!queue.isEmpty()){
            int u=queue.poll();
        int v=child[u];
        if(!visited[v]) {
            visited[v] = true;
            queue.add(v);
            dis++;
            bfs(v);
        }}
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
