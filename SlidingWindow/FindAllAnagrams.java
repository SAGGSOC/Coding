import java.util.*;

/**
 * LC 438 - Find All Anagrams in a String.
 *
 * Pattern : Fixed-size sliding window + frequency matching.
 * Insight : Maintain a "matchCount" (how many of 26 chars have equal frequency
 *           in window vs p). When matchCount == 26, the window is an anagram.
 *
 * Time  : O(n)  — each character is added/removed from the window exactly once.
 * Space : O(1)  — two arrays of size 26.
 */
public class FindAllAnagrams {

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if (s.length() < p.length()) return result;

        int[] pCount = new int[26];
        int[] wCount = new int[26]; // window count

        // Initialize frequency of p and the first window.
        for (int i = 0; i < p.length(); i++) {
            pCount[p.charAt(i) - 'a']++;
            wCount[s.charAt(i) - 'a']++;
        }

        // Count how many of the 26 characters already match.
        int matches = 0;
        for (int i = 0; i < 26; i++) {
            if (pCount[i] == wCount[i]) matches++;
        }

        // Slide the window across s.
        int left = 0;
        for (int right = p.length(); right < s.length(); right++) {
            // Check current window before expanding.
            if (matches == 26) result.add(left);

            // Add character at 'right' into the window.
            int idx = s.charAt(right) - 'a';
            wCount[idx]++;
            if (wCount[idx] == pCount[idx]) matches++;
            else if (wCount[idx] == pCount[idx] + 1) matches--; // was matching, now over

            // Remove character at 'left' from the window.
            idx = s.charAt(left) - 'a';
            wCount[idx]--;
            if (wCount[idx] == pCount[idx]) matches++;
            else if (wCount[idx] == pCount[idx] - 1) matches--; // was matching, now under

            left++;
        }
        // Check the last window.
        if (matches == 26) result.add(left);

        return result;
    }

    public static void main(String[] args) {
        FindAllAnagrams sol = new FindAllAnagrams();
        System.out.println(sol.findAnagrams("cbaebabacd", "abc")); // [0, 6]
        System.out.println(sol.findAnagrams("abab", "ab"));        // [0, 1, 2]
    }
}
