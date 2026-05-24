package DynamicProgramming;
import java.util.*;
class PalindromicSubstring{
    public static String longestPalindromicSubstring(String s) {
        if(s == null || s.length() == 0)
            return "";
        return findLongestPalindrome(s, 0, s.length() - 1);
    }
    public String findLongestPalindrome(String s, int start, int end){
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
    public static void main(String[] args){
        longestPalindromicSubstring("ababa");
    }
}