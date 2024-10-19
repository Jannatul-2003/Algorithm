//https://leetcode.com/problems/sort-items-by-groups-respecting-dependencies/

import java.util.*;

class Solution {
    public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        // Assign each ungrouped item (-1) to a new group
        int unknownGroup = m;
        for (int i = 0; i < n; i++) {
            if (group[i] == -1) {
                group[i] = unknownGroup++;
            }
        }

        // Build adjacency lists and in-degree arrays for items and groups
        List<List<Integer>> itemGraph = new ArrayList<>();
        List<List<Integer>> groupGraph = new ArrayList<>();
        for (int i = 0; i < n; i++) itemGraph.add(new ArrayList<>());
        for (int i = 0; i < unknownGroup; i++) groupGraph.add(new ArrayList<>());

        int[] itemInDegree = new int[n];
        int[] groupInDegree = new int[unknownGroup];

        // Build the item and group dependency graph
        for (int i = 0; i < n; i++) {
            int currentGroup = group[i];
            for (int beforeItem : beforeItems.get(i)) {
                int beforeGroup = group[beforeItem];
                if (beforeGroup != currentGroup) {
                    // Add group-level dependency
                    groupGraph.get(beforeGroup).add(currentGroup);
                    groupInDegree[currentGroup]++;
                } else {
                    // Add item-level dependency within the same group
                    itemGraph.get(beforeItem).add(i);
                    itemInDegree[i]++;
                }
            }
        }

        // Topologically sort the groups
        List<Integer> groupOrder = topologicalSort(groupGraph, groupInDegree);
        if (groupOrder == null) return new int[0];  // Cycle detected

        // Topologically sort the items within each group
        Map<Integer, List<Integer>> groupToItems = new HashMap<>();
        for (int i = 0; i < unknownGroup; i++) {
            groupToItems.put(i, new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            groupToItems.get(group[i]).add(i);
        }

        List<Integer> result = new ArrayList<>();
        for (int grp : groupOrder) {
            List<Integer> itemsInGroup = topologicalSort(groupToItems.get(grp), itemGraph, itemInDegree);
            if (itemsInGroup == null) return new int[0];  // Cycle detected
            result.addAll(itemsInGroup);
        }

        return result.stream().mapToInt(i -> i).toArray();
    }

    private List<Integer> topologicalSort(List<List<Integer>> graph, int[] inDegree) {
        int n = inDegree.length;
        List<Integer> result = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();

        // Add nodes with zero in-degree to the queue
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        // Kahn's algorithm (BFS)
        while (!queue.isEmpty()) {
            int node = queue.poll();
            result.add(node);

            for (int neighbor : graph.get(node)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        // If not all nodes were processed, a cycle was detected
        return result.size() == n ? result : null;
    }

    private List<Integer> topologicalSort(List<Integer> nodes, List<List<Integer>> graph, int[] inDegree) {
        List<Integer> result = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();

        // Add nodes with zero in-degree to the queue
        for (int node : nodes) {
            if (inDegree[node] == 0) {
                queue.offer(node);
            }
        }

        // Kahn's algorithm (BFS)
        while (!queue.isEmpty()) {
            int node = queue.poll();
            result.add(node);

            for (int neighbor : graph.get(node)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        // If not all nodes were processed, a cycle was detected
        return result.size() == nodes.size() ? result : null;
    }
}
