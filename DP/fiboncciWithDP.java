import java.util.HashMap;
//Top_Down approach
class fibonacciMemonization{
    HashMap<Integer, Integer> map = new HashMap<>();
    
    public int fibMemo(int n){
        if(map.containsKey(n)){
            return map.get(n);
        }
        if(n<=1){
            map.put(n, n);
            return n;
        }
        int result = fibMemo(n-1) + fibMemo(n-2);
        map.put(n, result);
        return result;
    }
}
//Bottom_Up approach
class fibonacciTabulation{
    //O(n) time and O(n) space
HashMap<Integer, Integer> map = new HashMap<>();

    public int fibTab(int n){
       map.put(0, 0);
        map.put(1, 1);
        // System.out.println(map.size());

        if(map.containsKey(n)){
            return map.get(n);
        }
        int last = map.size();
        for(int i=last; i<=n; i++){
            map.put(i, map.get(i-1) + map.get(i-2));
        }
        return map.get(n);
    }
    //O(n) time and O(1) space
    public int fibOptimized(int n) {
        if (n <= 1) return n;
        int a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            int temp = a + b;
            a = b;
            b = temp;
        }
        return b;
    }
    
}

public class fiboncciWithDP {
    public static void main(String[] args) {
        fibonacciMemonization fm = new fibonacciMemonization();
        fibonacciTabulation ft = new fibonacciTabulation();
        System.out.println(fm.fibMemo(10));
        System.out.println(ft.fibTab(10));
    }
    
}
