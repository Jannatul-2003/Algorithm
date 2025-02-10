import java.util.*;

public class canSum {
    public static boolean solve(long target, long[] numbers, Map<Long, Boolean> memo) {
        if(memo.containsKey(target)) {
            return memo.get(target);
        }
        if(target == 0) {
            return true;
        }

        if(target < 0) {
            return false;
        }

        for(long i : numbers) {
            long rem = target - i;
            if(solve(rem, numbers, memo)) {
                memo.put(target, true);
                return true;
            }
        }
        memo.put(target, false);
        return false;
    }

    public static void main(String[] args) {
        HashMap<Long, Boolean> memo = new HashMap<>();
        boolean temp = solve(7, new long[]{2,2}, memo);
        System.out.println(temp);
    }
}