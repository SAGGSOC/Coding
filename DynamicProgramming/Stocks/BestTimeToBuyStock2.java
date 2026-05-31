package DynamicProgramming;

public class BestTimeToBuyStock2 {
    /*
      int f(int ind, int buy, long value[], int n){
        if(ind == n){
          return 0;
        }
        long profit = 0;
        if(buy == 1){
            profit = max(-values[ind] + f(ind + 1, 0, value[], n) , 0 + f(ind + 1, 1, value, n);
        } else {
            profit = max(values[ind] + f(ind + 1, 1), 0 + f(ind + 1, 0));
        }
        return profit;
      }
     */

    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n+1][2];
        dp[n][0] = dp[n][1] = 0;
        for(int ind = n-1; ind >= 0; ind--){
            for(int buy = 0; buy <= 1; buy++){
                long profit = 0;
                if(buy == 1){
                    profit = Math.max(-prices[ind] + dp[ind+1][0], 0 + dp[ind+1][1]);
                } else {
                    profit = Math.max(prices[ind] + dp[ind+1][1], 0 + dp[ind+1][0]);
                }
                dp[ind][buy] = (int)profit;
            }
        }
        return dp[0][1];
    }
}
