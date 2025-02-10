public class SubsetSum {

    public static boolean isSubsetSum(int[] arr, int sum) {
        int n = arr.length;

        // Create a 2D DP table
        boolean[][] dp = new boolean[n + 1][sum + 1];

        // Base case: dp[i][0] = true (sum 0 can always be achieved)
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }

        // Base case: dp[0][j] = false (no sum > 0 can be achieved with no elements)
        for (int j = 1; j <= sum; j++) {
            dp[0][j] = false;
        }

        // Fill the DP table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                // Exclude the current element
                dp[i][j] = dp[i - 1][j];

                // Include the current element if it doesn't exceed the sum
                if (j >= arr[i - 1]) {
                    dp[i][j] = dp[i][j] || dp[i - 1][j - arr[i - 1]];
                }
            }
        }

        // The answer is in dp[n][sum]
        return dp[n][sum];
    }

    public static void main(String[] args) {
        int[] arr = {3, 34, 4, 12, 5, 2};
        int sum = 9;
        System.out.println("Is there a subset with the given sum? " + isSubsetSum(arr, sum));
    }
}
