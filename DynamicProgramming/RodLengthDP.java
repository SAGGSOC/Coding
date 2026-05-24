package DynamicProgramming;
/*
   Lets understand with an example:

    length   | 1   2   3   4   5   6   7   8  
   --------------------------------------------
    price    | 1   5   8   9  10  17  17  20
Given a rod of length of n (here n=8) and its prices if it is sold in that many piece.
That is:

 If we sell 1 piece its price will be 1
 if we sell 2 piece its price will be 5

 So recursive solution
 Unbounded Knapsack 

 Take/notTake problem 


    Here we need to maximize the price

    Hence we will use unbounded knapsack

    Time Complexity: O(n*n)
 */
public class RodLengthDP {
    public static int maxRodLength(int prices[],int ind, int N){
        if(ind == 0){
           return N*prices[0];
        }

       
                int notTake = maxRodLength(prices, ind - 1, N);

                int take = Integer.MIN_VALUE;
                int rod_length = ind + 1;
                if(rod_length <= N){
                    take = prices[ind] + maxRodLength(prices, ind, N - rod_length);
                }
        

        return Math.max(take, notTake);
    }

    
    public static void main(String[] args) {
        int[] length = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] price = {1, 5, 8, 9, 10, 17, 17, 20};
        int n = 8;
        int[][] t = new int[n + 1][n + 1];

        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (i == 0 || j == 0) {
                    t[i][j] = 0;
                }
            }
        }

        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (length[i - 1] <= j) {
                    t[i][j] = Math.max(price[i - 1] + t[i][j - length[i - 1]], t[i - 1][j]);
                } else {
                    t[i][j] = t[i - 1][j];
                }
            }
        }
        System.out.println(t[n][n]);
    }
}
