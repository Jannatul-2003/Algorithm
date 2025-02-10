public class RockClimbingMaxPath {

    public int rockClimbing(int[][] wall) {
        int row = wall.length + 1;
        int col = wall[0].length + 2;
        int max, r, c;
        max = Integer.MIN_VALUE;
        r = c = 0;
        int dp[][] = new int[row][col];
        for (int i = 0; i < row; i++) {
            dp[i][0] = Integer.MIN_VALUE;
            dp[i][col - 1] = Integer.MIN_VALUE;
        }
        for (int j = 0; j < col; j++) {
            dp[0][j] = 0;
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col - 1; j++) {
                dp[i][j] = wall[i - 1][j - 1] + Math.max(dp[i - 1][j - 1], Math.max(dp[i - 1][j], dp[i - 1][j + 1]));
                if (i == row - 1 && dp[i][j] > max) {
                    max = dp[i][j];
                    r = i;
                    c = j;
                }

            }
        }
        printBestPath(dp, r, c, wall);
        return max;
    }

    public void printBestPath(int dp[][], int r, int c, int[][] wall) {
        int col = dp[0].length;
        if (r == 0 || c == 0 || c == col - 1) {
            return;
        }
        System.out.println("(" + (r - 1) + ", " + (c - 1) + ") -> " + wall[r - 1][c - 1]);

        if (dp[r - 1][c - 1] >= dp[r - 1][c] && dp[r - 1][c - 1] >= dp[r - 1][c + 1]) {
            printBestPath(dp, r - 1, c - 1, wall);
        } else if (dp[r - 1][c] >= dp[r - 1][c - 1] && dp[r - 1][c] >= dp[r - 1][c + 1]) {
            printBestPath(dp, r - 1, c, wall);
        } else {
            printBestPath(dp, r - 1, c + 1, wall);
        }
    }

    public static void main(String[] args) {
        RockClimbingMaxPath rc = new RockClimbingMaxPath();
        int wall[][] = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 9 },
        };
        System.out.println(rc.rockClimbing(wall));
    }
}
