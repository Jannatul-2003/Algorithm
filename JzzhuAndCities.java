// //https://codeforces.com/problemset/problem/449/B

// import java.util.*;
// import java.io.*;

// public class JzzhuAndCities {
//     BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

//     public static void main(String[] args) throws Exception {
//         JzzhuAndCities obj = new JzzhuAndCities();
//         StringTokenizer tokenizer = new StringTokenizer(obj.reader.readLine());
//         int numOfVertices = Integer.parseInt(tokenizer.nextToken());
//         int numOfEdges = Integer.parseInt(tokenizer.nextToken());
//         int numOfTrainRoutes = Integer.parseInt(tokenizer.nextToken());
//         for (int i = 0; i < numOfEdges; i++) {
//             tokenizer = new StringTokenizer(obj.reader.readLine());
//             int u = Integer.parseInt(tokenizer.nextToken());
//             int v = Integer.parseInt(tokenizer.nextToken());
//             int cost = Integer.parseInt(tokenizer.nextToken());
//         }
//         for (int i = 0; i < numOfTrainRoutes; i++) {
//             tokenizer = new StringTokenizer(obj.reader.readLine());
//             int s = Integer.parseInt(tokenizer.nextToken());
//             int cost = Integer.parseInt(tokenizer.nextToken());
//         }
//     }
// }

// class Vertex implements Comparable<Vertex> {
//     int name;
//     int cost;
//     Vertex parent = null;
//     HashMap<Vertex, Integer> neighbours = new HashMap<>();

//     Vertex(int name) {
//         this.name = name;
//         this.cost = Integer.MAX_VALUE; // Initialize with infinity
//     }
//     public void setCost(int cost) {
//         if(cost<this.cost)
//         this.cost = cost;
//     }

//     @Override
//     public int compareTo(Vertex other) {
//         return Integer.compare(this.cost, other.cost);
//     }
// }

// class Dijkstra {
//     HashMap<Integer, Vertex> graph = new HashMap<>();

//     Vertex getVertex(int name) {
//         return graph.computeIfAbsent(name, k -> new Vertex(name));
//     }

//     void addEdge(int u, int v, int weight) {
//         Vertex uVertex = getVertex(u);
//         Vertex vVertex = getVertex(v);
//         uVertex.neighbours.put(vVertex, weight);
//         vVertex.neighbours.put(uVertex, weight);
//     }

//     void dijkstra(int start) {
//         Vertex source = getVertex(start);
//         source.cost = 0;

//         PriorityQueue<Vertex> queue = new PriorityQueue<>();
//         queue.add(source);

//         while (!queue.isEmpty()) {
//             Vertex current = queue.poll();

//             // Relaxation of edges
//             for (Map.Entry<Vertex, Integer> entry : current.neighbours.entrySet()) {
//                 Vertex neighbor = entry.getKey();
//                 int weight = entry.getValue();

//                 int newCost = current.cost + weight;
//                 if (newCost < neighbor.cost) {
//                     neighbor.cost = newCost;
//                     neighbor.parent = current;
//                     queue.add(neighbor); // Re-insert without removing
//                 }
//             }
//         }
//     }

//     void printPaths(int start) {
//         for (Vertex v : graph.values()) {
//             if (v.cost == Integer.MAX_VALUE) {
//                 System.out.println("Vertex " + v.name + " is unreachable from vertex " + start);
//             } else {
//                 System.out.print("Cost to reach vertex " + v.name + " from vertex " + start + " is " + v.cost);
//                 System.out.print(". Path: ");

//                 List<Integer> path = new ArrayList<>();
//                 for (Vertex at = v; at != null; at = at.parent) {
//                     path.add(at.name);
//                 }
//                 Collections.reverse(path);
//                 System.out.println(path);
//             }
//         }
//     }
// }
import java.io.*;
import java.util.*;

public class JzzhuAndCities {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    static class Edge {
        int to;
        long weight;
        
        Edge(int to, long weight) {
            this.to = to;
            this.weight = weight;
        }
    }
    
    static class State implements Comparable<State> {
        int vertex;
        long distance;
        
        State(int vertex, long distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
        
        @Override
        public int compareTo(State other) {
            return Long.compare(this.distance, other.distance);
        }
    }
    
    public static void main(String[] args) throws Exception {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        int n = Integer.parseInt(tokenizer.nextToken());
        int m = Integer.parseInt(tokenizer.nextToken());
        int k = Integer.parseInt(tokenizer.nextToken());
        
        // Adjacency list for roads
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        
        // Add roads
        for (int i = 0; i < m; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int u = Integer.parseInt(tokenizer.nextToken());
            int v = Integer.parseInt(tokenizer.nextToken());
            long x = Long.parseLong(tokenizer.nextToken());
            graph.get(u).add(new Edge(v, x));
            graph.get(v).add(new Edge(u, x));
        }
        
        // Map to store shortest train route to each city
        Map<Integer, List<Long>> trainRoutes = new HashMap<>();
        for (int i = 0; i < k; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int dest = Integer.parseInt(tokenizer.nextToken());
            long length = Long.parseLong(tokenizer.nextToken());
            trainRoutes.computeIfAbsent(dest, key -> new ArrayList<>()).add(length);
        }
        
        // Run Dijkstra including all possible paths
        long[] shortestDist = new long[n + 1];
        int[] usedTrains = new int[n + 1];
        Arrays.fill(shortestDist, Long.MAX_VALUE);
        shortestDist[1] = 0;
        
        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.offer(new State(1, 0));
        
        while (!pq.isEmpty()) {
            State current = pq.poll();
            int v = current.vertex;
            
            if (current.distance > shortestDist[v]) {
                continue;
            }
            
            // Process road connections
            for (Edge edge : graph.get(v)) {
                if (shortestDist[edge.to] > current.distance + edge.weight) {
                    shortestDist[edge.to] = current.distance + edge.weight;
                    pq.offer(new State(edge.to, shortestDist[edge.to]));
                    usedTrains[edge.to] = usedTrains[v];
                }
            }
            
            // If current vertex is the capital, process all train routes
            if (v == 1 && trainRoutes.size() > 0) {
                for (Map.Entry<Integer, List<Long>> entry : trainRoutes.entrySet()) {
                    int dest = entry.getKey();
                    for (long trainLength : entry.getValue()) {
                        if (shortestDist[dest] > trainLength) {
                            shortestDist[dest] = trainLength;
                            pq.offer(new State(dest, trainLength));
                            usedTrains[dest] = 1;
                        }
                    }
                }
            }
        }
        
        // Count unnecessary train routes
        int unnecessaryRoutes = 0;
        for (Map.Entry<Integer, List<Long>> entry : trainRoutes.entrySet()) {
            int dest = entry.getKey();
            List<Long> routes = entry.getValue();
            
            // Sort routes by length
            Collections.sort(routes);
            
            // Count unnecessary routes for this destination
            for (int i = 0; i < routes.size(); i++) {
                long trainLength = routes.get(i);
                if (i > 0 && trainLength >= routes.get(0)) {
                    // All routes longer than the shortest one are unnecessary
                    unnecessaryRoutes++;
                } else if (shortestDist[dest] < trainLength || usedTrains[dest] == 0) {
                    // Route is unnecessary if there's a shorter path or if no train was used
                    unnecessaryRoutes++;
                }
            }
        }
        
        System.out.println(unnecessaryRoutes);
    }
}