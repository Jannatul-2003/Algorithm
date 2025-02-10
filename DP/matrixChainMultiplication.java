public class matrixChainMultiplication {

    // Function to compute the minimum cost of matrix multiplication
    public static int[][] matrixChainOrder(int[] p) {
        int n = p.length - 1; // Number of matrices
        int[][] m = new int[n + 1][n + 1]; // Table for minimum cost
        int[][] s = new int[n + 1][n + 1]; // Table for optimal split points

        // Initialize the diagonal to zero (cost of multiplying one matrix is zero)
        for (int i = 1; i <= n; i++) {
            m[i][i] = 0;
        }

        // l is the chain length
        for (int l = 2; l <= n; l++) {
            for (int i = 1; i <= n - l + 1; i++) {
                int j = i + l - 1;
                m[i][j] = Integer.MAX_VALUE; // Initialize to infinity
                for (int k = i; k <= j - 1; k++) {
                    // Calculate the cost of splitting at k
                    int q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];
                    if (q < m[i][j]) {
                        m[i][j] = q; // Update minimum cost
                        s[i][j] = k; // Store the split point
                    }
                }
            }
        }
        return s; // Return the table of splits (can also return m for costs)
    }

    // Function to print the optimal parenthesization
    public static void printOptimalParens(int[][] s, int i, int j) {
        if (i == j) {
            System.out.print("A" + i);
        } else {
            System.out.print("(");
            printOptimalParens(s, i, s[i][j]);
            printOptimalParens(s, s[i][j] + 1, j);
            System.out.print(")");
        }
    }

    public static void main(String[] args) {
        // Example input: dimensions of matrices
        int[] p = {30, 35, 15, 5, 10, 20, 25}; // Dimensions of matrices
        int n = p.length - 1; // Number of matrices

        // Get the table of splits
        int[][] s = matrixChainOrder(p);

        // Print the optimal parenthesization
        System.out.print("Optimal Parenthesization: ");
        printOptimalParens(s, 1, n);
        System.out.println();
    }
}
