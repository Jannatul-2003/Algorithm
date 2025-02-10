import java.io.*;
import java.util.*;

public class FractionalKnapSack {
    Map<Integer, Integer> mrp_weight = new HashMap<>();
    public int getMaxValue(int W) {
        int maxValue = 0;
        while (W!=0) {
            
        }
       
        return maxValue;
    }
    public static void main(String[] args) throws Exception {
        FractionalKnapSack f = new FractionalKnapSack();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int val = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            f.mrp_weight.put(val/weight, weight);
        }

    }
}
