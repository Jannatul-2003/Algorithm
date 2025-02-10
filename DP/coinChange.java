public class coinChange {

    public int coinChangeWays(int[] coins, int n, int amount) {
        // Initialize the DP table
        int[][] dp = new int[n + 1][amount + 1];

        // Base case: There is one way to make amount 0, by using no coins
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }

        // Fill the DP table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= amount; j++) {
                // Exclude the current coin
                dp[i][j] = dp[i - 1][j];
                // Include the current coin if it doesn't exceed the current amount
                if (j >= coins[i - 1]) {
                    dp[i][j] += dp[i][j - coins[i - 1]];
                }
            }
        }

        // The result is stored in dp[n][amount]
        return dp[n][amount];
    }

    public static void main(String[] args) {
        coinChange cc = new coinChange();
        int[] coins = { 1, 2, 3 };
        int amount = 4;
        System.out.println("Number of ways to make change: " + cc.coinChangeWays(coins, coins.length, amount));
    }
}
