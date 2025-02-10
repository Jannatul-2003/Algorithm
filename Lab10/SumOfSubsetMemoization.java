import java.util.*;

public class SumOfSubsetMemoization {
    private Map<String, Boolean> memo = new HashMap<>();

    boolean isSubsetSum(int[] set, int n, int target) {
        return subsetSumHelper(set, n, target);
    }

    private boolean subsetSumHelper(int[] set, int n, int target) {
        // Base cases
        if (target == 0) return true;
        if (n == 0) return false;

        // Create a unique key for the current state
        String key = n + "|" + target;

        // Check if the result is already memoized
        if (memo.containsKey(key)) return memo.get(key);

        // Recursive cases
        if (set[n - 1] > target) {
            memo.put(key, subsetSumHelper(set, n - 1, target));
        } else {
            memo.put(key, subsetSumHelper(set, n - 1, target) || subsetSumHelper(set, n - 1, target - set[n - 1]));
        }

        return memo.get(key);
    }

    public static void main(String[] args) {
        SumOfSubsetMemoization subsetSum = new SumOfSubsetMemoization();
        int[] set = {3, 34, 4, 12, 5, 2};
        int target = 9;

        if (subsetSum.isSubsetSum(set, set.length, target)) {
            System.out.println("Yes, a subset exists that adds up to " + target);
        } else {
            System.out.println("No, no subset adds up to " + target);
        }
    }
}
