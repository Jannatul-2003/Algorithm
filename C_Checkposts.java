import java.io.*;
import java.util.*;
 
public class C_Checkposts {
 
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static ArrayList<ArrayList<Integer>> adj = new ArrayList<>(), rev_adj = new ArrayList<>();
    public static Integer[] cost;
    public static boolean[] visited;
    public static ArrayList<ArrayList<ArrayList<Integer>>> strongly_connected;
    public static Stack<Integer> stack = new Stack<Integer>();
 
    public static void DFS(Integer node) {
 
        if (visited[node])
            return;
 
        visited[node] = true;
 
        for (int child : adj.get(node)) {
 
            if (visited[child])
                continue;
 
            DFS(child);
        }
 
        stack.push(node);
 
    }
 
    public static void rev_DFS(Integer node, Integer index) {
 
        if (visited[node])
            return;
 
        visited[node] = true;
 
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(cost[node - 1]);
        arrayList.add(node);
 
        strongly_connected.get(index).add(arrayList);
 
        for (int child : rev_adj.get(node)) {
 
            if (visited[child])
                continue;
 
            rev_DFS(child, index);
        }
 
    }
 
    public static void main(String[] args) throws IOException {
 
        StringTokenizer st_t = new StringTokenizer(br.readLine());
 
        Integer n = Integer.parseInt(st_t.nextToken());
 
        cost = new Integer[n + 1];
        visited = new boolean[n + 1];
        strongly_connected = new ArrayList<>();
 
        adj.add(new ArrayList<Integer>());
        rev_adj.add(new ArrayList<Integer>());
 
 
        st_t = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            cost[i] = Integer.parseInt(st_t.nextToken());
 
            adj.add(new ArrayList<>());
            rev_adj.add(new ArrayList<>());
            strongly_connected.add(new ArrayList<ArrayList<Integer>>());
        }
 
        st_t = new StringTokenizer(br.readLine());
        Integer m = Integer.parseInt(st_t.nextToken());
 
        for (int i = 0; i < m; i++) {
            st_t = new StringTokenizer(br.readLine());            
 
            Integer u = Integer.parseInt(st_t.nextToken());
            Integer v = Integer.parseInt(st_t.nextToken());
 
            adj.get(u).add(v);
            rev_adj.get(v).add(u);
        }
 
        for (int i = 1; i <= n; i++) {
            if (!visited[i])
                DFS(i);
        }
 
        // System.out.println(stack);
        // System.out.println(stack.peek());
 
        visited = new boolean[n + 1];
        int index = 0;
 
        while (!stack.empty()) {
            Integer node = stack.pop();
 
            if (!visited[node])
                rev_DFS(node, index);
 
            Collections.sort(strongly_connected.get(index), (list1, list2) -> {
 
                return list1.get(0).compareTo(list2.get(0));
 
            });
 
            index++;
        }
 
        long cost = 0;
 
        // System.out.println(strongly_connected);
 
        long total_count = 1;
 
        for (ArrayList<ArrayList<Integer>> a : strongly_connected) {
 
            boolean isFirst = true;
            int count = 1;
            int first_cost = 0;
 
            for (ArrayList<Integer> b : a) {
                // System.err.println(b);
                if (isFirst) {
                    cost += b.get(0);
                    first_cost = b.get(0);
                    isFirst = false;
                } else if (b.get(0) == first_cost) {
                    count++;
                } else
                    break;
            }
 
            total_count = (total_count * count) % (1000000007);
        }
 
        System.out.println(cost + " " + total_count);
 
    }
}