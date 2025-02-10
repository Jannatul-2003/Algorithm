import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MinimumNumberofNodes {
    int [] arr={1,2,5,10,20,50,100,200,500,1000};
    public int lowerBound(int n){
        int l=0;
        int r=arr.length-1;
        while(l<r){
            int mid=(l+r)/2;
            if(arr[mid]<n){
                l=mid+1;
            }else{
                r=mid;
            }
        }
        return l;
    }
    public int getMinimumNumberofNodes(int n) {
        int ans=0;
        while(n>0){
            int index=lowerBound(n);
            if(arr[index]>n){
                index--;
            }
            n-=arr[index];
            ans++;
        }
        return ans;
    }
    public static void main(String[] args) throws Exception {
        int n ;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        MinimumNumberofNodes m = new MinimumNumberofNodes();
        System.out.println(m.getMinimumNumberofNodes(n));
    }
}
