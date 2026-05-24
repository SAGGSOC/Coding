package DynamicProgramming;

public class LCS {
    public int findLCSRec(String s1, String s2, int ind1, int ind2){
        /*
            LCS 
            Recursion 

            Time Complexity : O(2^(m+n))

            Space Complexity : O(m+n)

            where m and n are lengths of the strings respectively
            s1 -> First String

            s2 -> Second String

            Base case if strings are empty, then 
            length of LCS is 0

            if the characters matches that means 
            we found a character of LCS, so we add 1 to the 
            result of the remaining strings


            else we have two choices that is to
            exclude current character of first string or second string

            and take maximum of both
            choices


            s1 -> First String

            s2 -> Second String

         */

            if(s1.length() == 0 || s2.length() == 0)

            return 0;

        if(s1.charAt(ind1) == s2.charAt(ind2))
        return findLCSRec(s1, s2, ind1-1, ind2-1);


        return Math.max(findLCSRec(s1, s2, ind1-1, ind2), findLCSRec(s1, s2, ind1, ind2-1));
    }
    public int findLCS(String s1, String s2,int ind1,int ind2){

        int dp[][] = new int[s1.length()+1][s2.length()+1];
        int n = s1.length();
        int m = s2.length();
        for(int j=0;j<m;j++){
            dp[0][j] = 0;
        }

        for(int i=0;i<n;i++){
            dp[i][0] = 0;
        }

        for(int i=1;i<=n;i++) {
            for(int j=1;j<=m;j++){

                if(s1.charAt(i-1) == s2.charAt(j-1)){

                    dp[i][j] = 1 + dp[i-1][j-1];

                }else {

                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);

                }
            }
        }
        if(ind1 < 0 || ind2 < 0) 
            return 0;
        if(s1.charAt(ind1) == s2.charAt(ind2)){
            return 1 + findLCS(s1, s2, ind1-1, ind2-1);
        }
        return Math.max(findLCS(s1, s2, ind1-1, ind2), findLCS(s1, s2, ind1, ind2-1));
    }
}
