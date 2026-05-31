import java.util.*;

/**
 * LC 765 - Couples Holding Hands.
 *
 * Pattern: Greedy swap / Union-Find on seat pairs.
 *
 * Partner formula: person p's partner = p ^ 1 (XOR).
 *   0↔1, 2↔3, 4↔5, ...
 *
 * Approach 1 (Greedy):
 *   For each seat pair, if not a couple, find the partner and swap in.
 *   Each swap fixes exactly one couple → always optimal.
 *   Time: O(n²) worst case (finding partner), O(n) with position map.
 *
 * Approach 2 (Union-Find):
 *   Each seat pair = a "couch". If two people on different couches are partners,
 *   union those couches. Answer = n - number of components.
 *   Time: O(n * α(n))
 */
public class CouplesHoldingHands {

    // ---- Approach 1: Greedy with position map ----
    public int minSwapsCouples(int[] row) {
        int n = row.length;
        // pos[person] = seat index of that person.
        int[] pos = new int[n];
        for (int i = 0; i < n; i++) pos[row[i]] = i;

        int swaps = 0;
        for (int i = 0; i < n; i += 2) {
            int partner = row[i] ^ 1; // partner of person at seat i
            if (row[i + 1] != partner) {
                // Find where the partner is and swap them into seat i+1.
                int partnerSeat = pos[partner];

                // Swap row[i+1] and row[partnerSeat]
                pos[row[i + 1]] = partnerSeat;
                pos[partner] = i + 1;

                int temp = row[i + 1];
                row[i + 1] = row[partnerSeat];
                row[partnerSeat] = temp;

                swaps++;
            }
        }
        return swaps;
    }

    // ---- Approach 2: Using DisjointSet class ----
    public int minSwapsCouplesUF(int[] row) {
        int n = row.length / 2; // number of couples
        DisjointSet ds = new DisjointSet(n);

        // For each seat pair, union the couple-IDs of the two people sitting there.
        for (int i = 0; i < row.length; i += 2) {
            int couple1 = row[i] / 2;
            int couple2 = row[i + 1] / 2;
            ds.unionByRank(couple1, couple2);
        }

        // Count connected components.
        int components = 0;
        for (int i = 0; i < n; i++) {
            if (ds.findUlpParent(i) == i) components++;
        }

        // A component of size k needs k-1 swaps.
        return n - components;
    }

    public static void main(String[] args) {
        CouplesHoldingHands sol = new CouplesHoldingHands();

        System.out.println(sol.minSwapsCouples(new int[]{0, 2, 1, 3}));    // 1
        System.out.println(sol.minSwapsCouples(new int[]{3, 2, 0, 1}));    // 0

        System.out.println(sol.minSwapsCouplesUF(new int[]{0, 2, 1, 3}));  // 1
        System.out.println(sol.minSwapsCouplesUF(new int[]{3, 2, 0, 1}));  // 0
        System.out.println(sol.minSwapsCouplesUF(new int[]{5, 4, 2, 6, 3, 1, 0, 7})); // 2
    }
}
