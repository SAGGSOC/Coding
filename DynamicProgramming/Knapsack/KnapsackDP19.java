import java.util.*;

//Greedy Not Applicable because of uniformity

class KnapSackDP19 {
    static int f(int ind, int W, ArrayList<Integer> wt, ArrayList<Integer> val) {
       
        if(ind == 0){
            if(wt.get(0) <= W)
                return val.get(0);
            return 0;
        }
        int take = Integer.MIN_VALUE;
        if(wt.get(ind)<=W)
          take = val.get(ind) + f(ind - 1,W-wt.get(0),wt,val);
        int notTake = f(ind -1, W, wt, val);
        return Math.max(take, notTake);
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the size of weights/values Array");
        int n = sc.nextInt();
        int arr[] = new int[n];
        for(int i=0;i<n;i++){
            arr[i] = sc.nextInt();
        }


    }
}