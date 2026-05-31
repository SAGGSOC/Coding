package DynamicProgramming;

import java.util.ArrayList;

int f(int i,int j, ArrayList<ArrayList<Integer>> triangle,int n){
        if(i == n-1){
            return triangle[n-1][j];
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        int d = triangle[i][j] + f(i+1, j, triangle, n, dp);
        int dg = triangle[i][j] + f(i+1, j+1, triangle, n, dp);
        return dp[i][j] = min(d, dg);
}
public class TriangleDP {
    int minimumPathSum(ArrayList<ArrayList<Integer>> )
}
