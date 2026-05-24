package DynamicProgramming;

public class CoinchangeDp20 {
    public int f(int ind, int nums[], int T){

        // ind -> 0 -> n, T -> 0-> Target
        int n = nums.length;
        int dp[][] = new int[n][T];

        for(int )

        // if(ind == 0){
        //     if(T%nums[0] == 0){

        //         return T/nums[0];

        //     }
        //     return Integer.MAX_VALUE;
        // }
        // int notTake = f(ind - 1, nums, T);
        // int take = Integer.MAX_VALUE;
        // if(nums[ind] <= T){
        //     take = 1 +  f(ind, nums, T - nums[ind]);
        // }
        // return Math.max(take, notTake);
    }
}
