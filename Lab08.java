import java.io.*;
import java.util.*;

// Merge Sort Implementation
class MergeSort {
    public static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }

    public static void merge(int[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; i++)
            L[i] = array[left + i];
        for (int j = 0; j < n2; j++)
            R[j] = array[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                array[k++] = L[i++];
            } else {
                array[k++] = R[j++];
            }
        }

        while (i < n1)
            array[k++] = L[i++];
        while (j < n2)
            array[k++] = R[j++];
    }
}

// Floyd-Warshall Algorithm Implementation
class FloydWarshall {
    public static void floydWarshall(int[][] graph) {
        int V = graph.length;
        int[][] dist = new int[V][V];

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                dist[i][j] = graph[i][j];
            }
        }

        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE
                            && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (dist[i][j] == Integer.MAX_VALUE) {
                    System.out.print("INF ");
                } else {
                    System.out.print(dist[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}

// Graph for Articulation Points and Bridges
class ArticulationPoint_Bridge {
    List<Set<Integer>> adj;
    int parent[];
    int discoverTime[];
    int low[];
    int finishtime[];
    Color color[];
    Set<Integer> articulationPoints = new HashSet<>();
    Set<Pair<Integer, Integer>> bridges = new HashSet<>();
    int time = 0;

    enum Color {
        WHITE, GREY, BLACK
    }

    void Graph(int v) {
        adj = new ArrayList<>();
        parent = new int[v + 1];
        discoverTime = new int[v + 1];
        finishtime = new int[v + 1];
        low = new int[v + 1];
        color = new Color[v + 1];
        for (int i = 0; i <= v; i++) {
            adj.add(new HashSet<>());
            parent[i] = -1;
            finishtime[i] = discoverTime[i] = Integer.MAX_VALUE;
            color[i] = Color.WHITE;
        }
    }

    void addEdge(int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }

    void find_articulationPoints_bridges() {
        for (int i = 1; i < adj.size(); i++) {
            if (color[i] == Color.WHITE) {
                DFSVisit(i);
            }
        }
    }

    void DFSVisit(int u) {
        color[u] = Color.GREY;
        discoverTime[u] = low[u] = ++time;
        int childCount = 0;

        for (int v : adj.get(u)) {
            if (color[v] == Color.WHITE) {
                parent[v] = u;
                DFSVisit(v);
                childCount++;

                // Update low[u] considering the subtree rooted at v
                low[u] = Math.min(low[u], low[v]);

                // Articulation point condition
                if (parent[u] == -1 && childCount > 1) {
                    articulationPoints.add(u); // root node with more than one child
                } else if (parent[u] != -1 && discoverTime[u] <= low[v]) {
                    articulationPoints.add(u); // non-root articulation point
                }

                // Bridge condition
                if (discoverTime[u] < low[v]) {
                    bridges.add(new Pair<>(u, v));
                }

            } else if (v != parent[u]) { // Back edge case
                low[u] = Math.min(low[u], discoverTime[v]);
            }
        }

        color[u] = Color.BLACK;
        finishtime[u] = ++time;
    }
}

public class Lab08 {
    public static void main(String[] args) throws IOException {
        // Merge Sort
        int[] array = { 12, 11, 13, 5, 6, 7 };
        System.out.println("Input Array: " + Arrays.toString(array));
        MergeSort.mergeSort(array, 0, array.length - 1);
        System.out.println("Sorted Array: " + Arrays.toString(array));

        // Floyd-Warshall
        int[][] graph = {
                { 0, 3, Integer.MAX_VALUE, 5 },
                { 2, 0, Integer.MAX_VALUE, 4 },
                { Integer.MAX_VALUE, 1, 0, Integer.MAX_VALUE },
                { Integer.MAX_VALUE, Integer.MAX_VALUE, 2, 0 }
        };
        System.out.println("Shortest distances:");
        FloydWarshall.floydWarshall(graph);

        // Articulation Points and Bridges
        Graph graphObj = new Graph("input.txt");
        System.out.println("Articulation Points:");
        graphObj.findArticulationPoints();
        System.out.println("Bridges:");
        graphObj.findBridges();
    }
}
