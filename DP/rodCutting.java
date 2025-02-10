import java.util.ArrayList;

public class rodCutting {
    int maxProfit(int[] prices, int n) {
        int[][] dp = new int[n + 1][n + 1];
        for(int i = 0; i <= n; i++) {
            dp[i][0] = 0;
        }
        for(int i = 0; i <= n; i++) {
            dp[0][i] = 0;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (j >= i) {
                    dp[i][j] = Math.max(dp[i - 1][j], prices[i - 1] + dp[i][j - i]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        System.out.println(printPieces(dp));
        return dp[n][n];
    }
    public ArrayList<Integer> printPieces(int[][] dp){
        ArrayList<Integer> pieces = new ArrayList<>();
        int i = dp.length - 1;
        int j = dp[0].length - 1;
        while(i > 0 && j > 0){
            if(dp[i][j] == dp[i - 1][j]){
                i--;
            }else{
                pieces.add(i);
                j -= i;
            }
        }
        return pieces;
    }
    public static void main(String[] args) {
        rodCutting rc = new rodCutting();
        int[] prices = {1, 5, 8, 9, 10, 17, 17, 20};
        int n = 8;
        System.out.println(rc.maxProfit(prices, n));
    }

}
