
public class sumOfSubset {

    // Main function to check if a subset with the given sum exists
    boolean isSubsetSum(int[] set, int n, int target) {
        // Step 1: Initialize the DP table
        boolean[][] dp = initializeDPTable(n, target);
        
        // Step 2: Fill the DP table
        fillDPTable(dp, set, n, target);

        // Step 3: Return the result from the DP table
        return dp[n][target];
    }

    // Initialize the DP table with base cases
    private boolean[][] initializeDPTable(int n, int target) {
        boolean[][] dp = new boolean[n + 1][target + 1];
        // Base case: dp[i][0] = true for all i
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }
        return dp;
    }

    // Fill the DP table using the recurrence relation
    private void fillDPTable(boolean[][] dp, int[] set, int n, int target) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= target; j++) {
                if (j < set[i - 1]) {
                    // Exclude the current element
                    dp[i][j] = dp[i - 1][j];
                } else {
                    // Include the current element
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - set[i - 1]];
                }
            }
        }
    }

    // Driver method to test the implementation
    public static void main(String[] args) {
        sumOfSubset subsetSum = new sumOfSubset();
        int[] set = {3, 34, 4, 12, 5, 2};
        int target = 9;

        if (subsetSum.isSubsetSum(set, set.length, target)) {
            System.out.println("Yes, a subset exists that adds up to " + target);
        } else {
            System.out.println("No, no subset adds up to " + target);
        }
    }
}
