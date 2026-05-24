package Recursion;

import java.util.HashSet;
import java.util.Scanner;

class RegexMatch {

    private static boolean f(int i, int j, String str, String pattern){
        if(i < 0 && j < 0){
            return true;
        }
        if(i < 0  &&  j >= 0){
            return  false;
        }
        if(j > 0 && i >= 0){
            for(int ii=0;ii<=i;i)
        }
    }
    private static boolean match(String str, String pattern, int i, int j) {
        if (i == str.length() && j == pattern.length()) {
            return true;
        }

        if (j == pattern.length()) {
            return false;
        }

        if (i == str.length()) {
            if (pattern.charAt(j) == '*') {
                return match(str, pattern, i, j + 1);
            }
            return false;
        }
        char p = pattern.charAt(j);
        char s = str.charAt(i);

        if(p == '?' || p == s){
            return match(str, pattern, i + 1, j + 1);
        }

        if(p == '*') {
            return match(str, pattern, i, j + 1)  || match(str, pattern, i + 1, j);
        }

        return false;
    }
    public static boolean regexMatch(String str, String pattern) {
        return match(str, pattern, 0, 0);
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the String");

        String str = sc.next();
        HashSet<String> set = new  HashSet<>();
        regexMatch(str, );
    }
}