
import java.util.*;
import java.io.*;
public class AWalkToRemember {

    ArrayList<ArrayList<Integer>> mainGraph = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> transposeGraph = new ArrayList<ArrayList<Integer>>();
    boolean[] visitedMainGraph;
    boolean[] visitedTransposeGraph;
    Stack<Integer> stack = new Stack<>();

    void Graph(int n) {
        visitedMainGraph = new boolean[n+1];
        visitedTransposeGraph = new boolean[n+1];
        for (int i = 0; i <n; i++) {
            mainGraph.add(new ArrayList<Integer>());
            transposeGraph.add(new ArrayList<Integer>());
            visitedMainGraph[i] = false;
            visitedTransposeGraph[i] = false;
        }
    }

    void addEdge(int a, int b) {
        mainGraph.get(a).add(b);
        transposeGraph.get(b).add(a);
    }

    public void DFS() {
        Queue<Integer> q = new LinkedList<>();
        for (int i = 1; i < mainGraph.size(); i++) {
            if (!visitedMainGraph[i]) {
                DFSVisit(i, mainGraph, visitedMainGraph, q);
            }
        }
        //System.out.println(stack);

        
    }

    void DFSVisit(int i, ArrayList<ArrayList<Integer>> graph, boolean[] visited, Queue<Integer> q) {
        visited[i] = true;
        System.out.print(i+ " ");
        for (int j : graph.get(i)) {
            if (!visited[j]) {
                DFSVisit(j, graph, visited, q);
            }
        }
        stack.push(i);
    }

    public void SCC() {
        //int odd=0; int even=0;
        int i=0;
        while(!stack.isEmpty()){
         i=stack.pop();
            if (!visitedTransposeGraph[i]) {
                Queue<Integer> q = new LinkedList<>();
                DFSVisit(i, transposeGraph, visitedTransposeGraph, q);
                System.out.println();
            }
        }
        

    }

    public static void main(String[] args) throws Exception {
        try {
            Scanner scanner = new Scanner(new File("input.txt"));
            int numVertices = scanner.nextInt();
            int numEdges = scanner.nextInt();

            AWalkToRemember a = new AWalkToRemember();
            a.Graph(numVertices);
            for (int i = 0; i < numEdges; i++) {
                // String[] parts = scanner.nextLine().split(" ");
                //  a.addEdge(Integer.valueOf(parts[0].trim()), Integer.valueOf(parts[1].trim()));
                int m = scanner.nextInt();
                int n = scanner.nextInt();
                a.addEdge(m-1, n-1);
            }
            a.DFS();
            a.SCC();
            scanner.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
}
