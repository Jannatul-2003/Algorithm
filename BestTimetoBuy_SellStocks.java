import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BestTimetoBuy_SellStocks {
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        int minPrice = Integer.MAX_VALUE;
        for(int i = 0; i < prices.length; i++){
            if(prices[i] < minPrice){
                minPrice = prices[i];
            } else if(prices[i] - minPrice > maxProfit){
                maxProfit = prices[i] - minPrice;
            }
        }
        return maxProfit;
    }
    public static void main(String[] args) throws Exception {
        BestTimetoBuy_SellStocks b = new BestTimetoBuy_SellStocks();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int[] prices = new int[n];
        String[] str = new String[n];
        str = br.readLine().split(", ");
        for (int i = 0; i < str.length; i++) {
            prices[i] = Integer.parseInt(str[i]);
        }
        System.out.println(b.maxProfit(prices));
    }
    
}
