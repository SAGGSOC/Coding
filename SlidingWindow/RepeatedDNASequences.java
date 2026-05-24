import java.util.*;

/**
 * LC 187 - Repeated DNA Sequences.
 *
 * Find all 10-letter substrings that appear more than once.
 *
 * Approach: Sliding window of size 10 + two HashSets.
 *   - "seen" tracks every 10-char substring encountered so far.
 *   - "repeated" collects substrings seen more than once (avoids duplicates in result).
 *
 * Time  : O(n * 10) — substring creation is O(10) per window position.
 * Space : O(n)      — storing substrings in the sets.
 */
public class RepeatedDNASequences {

    public List<String> findRepeatedDnaSequences(String s) {
        Set<String> seen = new HashSet<>();
        Set<String> repeated = new HashSet<>();

        for (int i = 0; i <= s.length() - 10; i++) {
            String sub = s.substring(i, i + 10);
            if (!seen.add(sub)) {   // add returns false if already present
                repeated.add(sub);
            }
        }
        return new ArrayList<>(repeated);
    }

    public static void main(String[] args) {
        RepeatedDNASequences sol = new RepeatedDNASequences();

        System.out.println(sol.findRepeatedDnaSequences("AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"));
        // [AAAAACCCCC, CCCCCAAAAA]

        System.out.println(sol.findRepeatedDnaSequences("AAAAAAAAAAAAA"));
        // [AAAAAAAAAA]
    }
}
