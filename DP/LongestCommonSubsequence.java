public class LongestCommonSubsequence {
    static class Result {
        int length;
        String subsequence;
        
        Result(int length, String subsequence) {
            this.length = length;
            this.subsequence = subsequence;
        }
    }
    
    public static Result findLCS(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        
        // Create DP table
        int[][] dp = new int[m + 1][n + 1];
        
        // Fill the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        // Reconstruct the LCS
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;
        
        while (i > 0 && j > 0) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                lcs.insert(0, text1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }
        
        return new Result(dp[m][n], lcs.toString());
    }
    
    // Print the DP table (helpful for understanding)
    private static void printDPTable(int[][] dp, String text1, String text2) {
        System.out.print("    ");
        for (char c : text2.toCharArray()) {
            System.out.print(c + " ");
        }
        System.out.println();
        
        for (int i = 0; i <= text1.length(); i++) {
            if (i == 0) {
                System.out.print("  ");
            } else {
                System.out.print(text1.charAt(i-1) + " ");
            }
            for (int j = 0; j <= text2.length(); j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        // Test cases
        String[][] testCases = {
            {"ABCDGH", "AEDFHR"},
            {"AGGTAB", "GXTXAYB"},
            {"HELLO", "WORLD"},
        };
        
        for (String[] test : testCases) {
            Result result = findLCS(test[0], test[1]);
            System.out.println("\nString 1: " + test[0]);
            System.out.println("String 2: " + test[1]);
            System.out.println("LCS Length: " + result.length);
            System.out.println("LCS: " + result.subsequence);
            System.out.println();
        }
    }
}