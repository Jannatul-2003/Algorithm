import java.util.*;

public class rodCutting {
    int maxProfit(int[] prices, int n) {
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i] = Math.max(dp[i], prices[j - 1] + dp[i - j]);
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        rodCutting rc = new rodCutting();
        int[] prices = { 1, 5, 8, 9, 10, 17, 17, 20 };
        int n = 8;
        System.out.println(rc.maxProfit(prices, n));
    }
}

