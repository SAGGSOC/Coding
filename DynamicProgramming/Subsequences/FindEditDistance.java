package DynamicProgramming;

public class FindEditDistance {

    public static int findEditDistance(String str1, String str2,int i, int j, int dp[][]){
            /*
               Insert, Delete, Replace
               str1 = "geek", str2 = "gesek"

               in case of Insert the i and j-1 , i is first string, j for 
second string

               in case of Delete the i-1 and j

               in case of Replace the i-1 and j-1
            */

               if(i < 0) {
                 return j + 1;
               }

               // make 1 based index if( i == 0)

               if(j < 0){
                   return i + 1;
               }

               if(dp[i][j] != -1){
                   return dp[i][j];
               }

               if(str1.charAt(i) == str2.charAt(j)){
                   return findEditDistance(str1, str2, i-1, j-1, dp);
               }

               return dp[i][j] = 1 + Math.min(Math.min(findEditDistance(str1, str2, i, j-1, dp), findEditDistance(str1, str2, i-1, j,dp)), findEditDistance(str1, str2, i-1, j-1, dp));
    }
    public static findEditDistanceDP(String str1, String str2){

        int dp[][] = new int[str1.length()][str2.length()];

        int n = str1.length();
        int m = str2.length();



    }
    public static void main(){
        
    }
}
