package Recursion;

import java.util.HashSet;
import java.util.Scanner;

class PalindromicSubstring {
    static int t[][] = new int[1001][1001];
    static int solve(String s, int i, int j){
        if(i >= j){
            return 1;
        }
        if(t[i][j] != -1){
            return t[i][j];
        }
        if(s.charAt(i) == s.charAt(j)){
            return t[i][j] = solve(s, i + 1, j - 1);
        }
        return t[i][j]=0;
    }
    static String longestPalindrome(String s){
        int n = s.length();
        int maxLen = -1;
        int sp = 0;

        for(int i=0;i<n;i++){
            for(int j=i;j<n;j++){
                if(solve(s,i,j) == 1){
                    if(j - i + 1 > maxLen){
                        maxLen = j - i + 1;
                        sp = i;
                    }
                }
            }
        }
        return s.substring(sp, maxLen);
    }
    public static String longestPalindromicSubstring(String s) {
        if(s == null || s.length() == 0)
            return "";
        return findLongestPalindrome(s, 0, s.length() - 1);
    }
    public static String findLongestPalindrome(String s, int start, int end){
        // Base case: if start index exceeds end index, return empty string
        if(start > end){
            return "";
        }
        // Base case: single character is always a palindrome
        if(start == end){
            return s.substring(start, end);
        }
        if(s.charAt(start) == s.charAt(end)){
            String inner = findLongestPalindrome(s, start + 1, end - 1);
            if(inner.length() == end - start - 1){
                return s.substring(start, end + 1);
            }
        }
        //exclude first
        String leftResult = findLongestPalindrome(s, start + 1, end);
        //exclude last
        String rightResult = findLongestPalindrome(s, start, end - 1);

        return leftResult.length() >= rightResult.length() ? leftResult : rightResult;
    }
    static String longestPalindromeTabulation(String s) {
        int n = s.length();
        if (n == 0) return "";
        
        boolean[][] dp = new boolean[n][n];
        int maxLen = 1, start = 0;

        //For Single Characters
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        //For 2 characters
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                start = i;
                maxLen = 2;
            }
        }


        
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i < n - len + 1; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                    start = i;
                    maxLen = len;
                }
            }
        }
        
        return s.substring(start, start + maxLen);
    }
    
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the String");

        String str = sc.next();
        System.out.println("Sagar");
        System.out.println("Memoization: " + longestPalindrome(str));
        System.out.println("Tabulation: " + longestPalindromeTabulation(str));
    }
}