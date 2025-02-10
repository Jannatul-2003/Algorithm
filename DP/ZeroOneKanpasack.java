import java.util.ArrayList;

class Knapsack {
    static class Result {
        int maxValue;
        // int[] selectedItems;
        ArrayList<Integer> selectedItems;

        Result(int maxValue, ArrayList<Integer> selectedItems) {
            this.maxValue = maxValue;
            this.selectedItems = selectedItems;
        }
    }

    public Result knapsack(int[] values, int[] weights, int capacity) {
        int n = values.length;
        // Create DP table with rows as items and columns as weights
        int[][] dp = new int[n + 1][capacity + 1];

        // Fill the DP table
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                if (weights[i - 1] <= w) {
                    // Maximum of including or excluding current item
                    dp[i][w] = Math.max(values[i - 1] + dp[i - 1][w - weights[i - 1]],
                            dp[i - 1][w]);
                } else {
                    // Can't include current item due to weight constraint
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Backtrack to find selected items
        ArrayList<Integer> selected = new ArrayList<>();
        int w = capacity;
        for (int i = n; i > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                selected.add(i - 1);
                w -= weights[i - 1];
            }
        }

        return new Result(dp[n][capacity], selected);
    }
}

public class ZeroOneKanpasack {
    // time complexity:O(n*capacity)
    // space complexity:O(n*capacity)
    // dp[i][j] represent the maximum value that can be achieved using the first i
    // items with a knapsack capacity of j
    public int kanpsack(int[] weights, int[] vlaues, int capacity) {
        int numOfItems = weights.length;
        int[][] dp = new int[numOfItems + 1][capacity + 1];

        for (int i = 1; i <= numOfItems; i++) {
            for (int j = 0; j <= capacity; j++) {
                if (weights[i - 1] <= j) {
                    dp[i][j] = Math.max(vlaues[i - 1] + dp[i - 1][j - weights[i - 1]], dp[i - 1][j]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
                System.err.print(" "+ dp[i][j]);
            }
            System.err.println();
        }
        return dp[numOfItems][capacity];
    }

    // time complexity:O(n*capacity)
    // space complexity:O(capacity)
    public int knapsackOptimized(int[] weights, int[] values, int capacity) {
        int[] dp = new int[capacity + 1];

        for (int i = 0; i < weights.length; i++) {
            for (int j = capacity; j >= weights[i]; j--) {
                dp[j] = Math.max(dp[j], values[i] + dp[j - weights[i]]);
            }
        }

        return dp[capacity];
    }

    public static void main(String[] args) {
        ZeroOneKanpasack zok = new ZeroOneKanpasack();
        int[] weights = { 2,1,3,2};
        int[] values = { 12,10,20,15 };
        int capacity = 5;
        System.out.println(zok.kanpsack(weights, values, capacity));

        Knapsack kn = new Knapsack();
        Knapsack.Result result = kn.knapsack(values, weights, capacity);
        System.out.println("Maximum value possible: " + result.maxValue);
        System.out.println("Selected items (indices):");
        for (int item : result.selectedItems) {
            System.out.printf("Item %d: value = %d, weight = %d%n",
                    item, values[item], weights[item]);
        }
    }

}
