import java.util.Arrays;

/**
 * LC 1340 - Jump Game V.
 *
 * From index i, you can jump to index j if:
 *   - |i - j| <= d
 *   - arr[i] > arr[j]
 *   - arr[i] > arr[k] for all k between i and j (no blocking bar)
 *
 * Return the maximum number of indices you can visit starting from any index.
 *
 * ============================================================
 * APPROACH 1: Pure Recursion (brute force, no memoization)
 * ============================================================
 * For each starting index, recursively explore all valid jumps.
 * Time  : Exponential in worst case (overlapping subproblems recomputed).
 * Space : O(n) recursion stack.
 *
 * ============================================================
 * APPROACH 2: Recursion + Memoization (Top-down DP)
 * ============================================================
 * Same logic but cache dp[i] so each index is solved only once.
 * Time  : O(n * d)
 * Space : O(n)
 */
public class JumpGameV {

    // ---- Approach 1: Pure Recursion (TLE on large inputs) ----
    public int maxJumpsRecursive(int[] arr, int d) {
        int n = arr.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, solve(arr, d, i));
        }
        return ans;
    }

    private int solve(int[] arr, int d, int i) {
        int n = arr.length;
        int best = 1; // visit index i itself

        // Jump RIGHT
        for (int x = 1; x <= d && i + x < n; x++) {
            if (arr[i + x] >= arr[i]) break;
            best = Math.max(best, 1 + solve(arr, d, i + x));
        }

        // Jump LEFT
        for (int x = 1; x <= d && i - x >= 0; x++) {
            if (arr[i - x] >= arr[i]) break;
            best = Math.max(best, 1 + solve(arr, d, i - x));
        }

        return best;
    }

    // ---- Approach 2: Recursion + Memoization ----
    public int maxJumps(int[] arr, int d) {
        int n = arr.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);

        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, solveMemo(arr, d, i, dp));
        }
        return ans;
    }

    private int solveMemo(int[] arr, int d, int i, int[] dp) {
        if (dp[i] != -1) return dp[i];

        int n = arr.length;
        int best = 1;

        // Jump RIGHT
        for (int x = 1; x <= d && i + x < n; x++) {
            if (arr[i + x] >= arr[i]) break;
            best = Math.max(best, 1 + solveMemo(arr, d, i + x, dp));
        }

        // Jump LEFT
        for (int x = 1; x <= d && i - x >= 0; x++) {
            if (arr[i - x] >= arr[i]) break;
            best = Math.max(best, 1 + solveMemo(arr, d, i - x, dp));
        }

        dp[i] = best;
        return best;
    }

    public static void main(String[] args) {
        JumpGameV sol = new JumpGameV();

        int[] arr1 = {6, 4, 14, 6, 8, 13, 9, 7, 10, 6, 12};
        System.out.println("Recursive: " + sol.maxJumpsRecursive(arr1, 2)); // 4
        System.out.println("Memoized:  " + sol.maxJumps(arr1, 2));          // 4

        int[] arr2 = {3, 3, 3, 3, 3};
        System.out.println("Recursive: " + sol.maxJumpsRecursive(arr2, 3)); // 1
        System.out.println("Memoized:  " + sol.maxJumps(arr2, 3));          // 1

        int[] arr3 = {7, 6, 5, 4, 3, 2, 1};
        System.out.println("Recursive: " + sol.maxJumpsRecursive(arr3, 1)); // 7
        System.out.println("Memoized:  " + sol.maxJumps(arr3, 1));          // 7
    }
}
