package DynamicProgramming;

public class BestTimeToBuyStock {
    public int maxProfit(int[] prices){
        int mini = prices[0];
        int maxProfit = 0;
        int n = prices.length;
        for(int i=1;i<n;i++){
            int cost = prices[i] - mini;
            maxProfit = Math.max(maxProfit,cost);
            mini = Math.max(mini,prices[i]);
        }
        return maxProfit;
    }
}
