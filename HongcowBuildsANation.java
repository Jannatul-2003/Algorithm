//https://codeforces.com/problemset/problem/744/A
import java.io.*;
import java.util.*;

public class HongcowBuildsANation {
    static ArrayList<ArrayList<Integer>> adjacencyList = new ArrayList<>();
    static boolean[] visited;
    static int[] parent, capital;
    static int numOfVertices, numOfEdges, numOfGovt, ans = 0, verticesWithoutGovt = 0;
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // DFS function to visit nodes
    static int dfsVisit(int u) {
        visited[u] = true;
        int size = 1;
        for (int v : adjacencyList.get(u)) {
            if (!visited[v]) {
                parent[v] = u;
                size += dfsVisit(v);
            }
        }
        return size;
    }

    public static void main(String[] args) throws Exception {
        // Input reading
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        numOfVertices = Integer.parseInt(tokenizer.nextToken());
        numOfEdges = Integer.parseInt(tokenizer.nextToken());
        numOfGovt = Integer.parseInt(tokenizer.nextToken());

        capital = new int[numOfGovt];
        tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 0; i < numOfGovt; i++) {
            capital[i] = Integer.parseInt(tokenizer.nextToken());
        }

        // Initialize arrays
        parent = new int[numOfVertices + 1];
        visited = new boolean[numOfVertices + 1];

        // Initialize adjacency list
        for (int i = 0; i <= numOfVertices; i++) {
            adjacencyList.add(new ArrayList<>());
            parent[i] = i;
        }

        // Reading edges
        for (int i = 0; i < numOfEdges; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int u = Integer.parseInt(tokenizer.nextToken());
            int v = Integer.parseInt(tokenizer.nextToken());
            adjacencyList.get(u).add(v);
            adjacencyList.get(v).add(u);
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        int maxSize=Integer.MIN_VALUE;
        for (int i = 0; i < numOfGovt; i++) {
            int u = capital[i];
            int size = dfsVisit(u);
            maxSize=Math.max(maxSize,size);
            ans += (size * (size - 1) / 2);
           // System.out.println(u+" "+size+" hello "+ans);
            map.put(u, size);
        }
        ans -= numOfEdges;
        //System.out.println(ans);
        for (int i = 1; i <= numOfVertices; i++) {
            if (!visited[i]) {
                verticesWithoutGovt ++;
            }
        }
        ans += verticesWithoutGovt * (verticesWithoutGovt - 1) / 2;
        ans += verticesWithoutGovt * maxSize;
        System.out.println(ans);
    }
}