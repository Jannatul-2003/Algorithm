import java.util.*;

public class howSum {
    public static List<Long> solve(long target, long[] numbers,ArrayList<Long> temp) {
        if(target == 0) {
            return temp;
        }

        if(target < 0) {
            return null;
        }

        for(long i : numbers) {
            long rem = target - i;
            ArrayList <Long> a = solve(rem, numbers,temp);
            if(temp != null) {
                temp.add(i);
                return temp;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        List<Long> temp = new ArrayList<>();
        temp = solve(7, new long[]{3,4,5,7},temp);
        if(temp != null) {
            System.out.println(temp.toString());
        }
        else {
            System.out.println("not possible");
        }
    }
}