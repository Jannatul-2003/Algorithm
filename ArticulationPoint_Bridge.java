import java.util.*;
record Pair<T, U>(T u, U v) {
}
public class ArticulationPoint_Bridge {
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

    void  find_articulationPoints_bridges() {
        for (int i = 1; i < adj.size(); i++) {
            if (color[i] == Color.WHITE) {
                DFSVisit(i);
            }
        }
    }

    /*void DFSVisit(int u) {
        color[u] = Color.GREY;
        discoverTime[u] = low[u] = ++time;
        int childCount=0;
        for (int v : adj.get(u)) {
            if (color[v] == Color.WHITE) {
                parent[v] = u;
                DFSVisit(v);
                childCount++;
                low[u] = Math.min(low[u], low[v]);
                if (parent[u] == -1) {
                    if (childCount > 1){
                        articulationPoints.add(u);
                    }
                } else if (discoverTime[u] <= low[v]) {
                    articulationPoints.add(u);
                }
                if (discoverTime[u] < low[v]) {
                    bridges.add(new Pair<>(u, v));
                }
            } else if (v != parent[u]) // (u,v) is a back edge and v is not immediate parent of u
            {
                low[u] = Math.min(low[u], discoverTime[v]);
            }
        }
        color[u] = Color.BLACK;
        finishtime[u] = ++time;
    }*/
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
    
    public static void main(String[] args) {
        ArticulationPoint_Bridge g = new ArticulationPoint_Bridge();
        g.Graph(5);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);
        g.addEdge(4, 5);
        g.addEdge(3, 4);
        g.find_articulationPoints_bridges();
        System.out.println("Articulation Points: " + g.articulationPoints);
        System.out.println("Bridges: " + g.bridges);
    }
}
