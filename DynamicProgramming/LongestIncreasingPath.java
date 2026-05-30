/**
 * LC 329 - Longest Increasing Path in a Matrix.
 *
 * Pattern: DFS + Memoization (same as Jump Game V — DP on a DAG).
 *
 * Why no cycles? You can only move to a STRICTLY greater neighbor.
 * So the graph of valid moves is a DAG → safe to memoize without visited array.
 *
 * dp[i][j] = length of the longest increasing path starting from cell (i, j).
 *
 * Recurrence:
 *   dp[i][j] = 1 + max(dp[ni][nj]) for all 4 neighbors where matrix[ni][nj] > matrix[i][j]
 *
 * Base case: cells with no greater neighbor → dp = 1 (just themselves).
 *
 * Approach 1: Pure Recursion (exponential, TLE).
 * Approach 2: Recursion + Memoization (O(m*n), each cell computed once).
 *
 * Time  : O(m * n)
 * Space : O(m * n)
 */
public class LongestIncreasingPath {

    private int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    // ---- Approach 1: Pure Recursion ----
    public int longestIncreasingPathBrute(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                ans = Math.max(ans, dfs(matrix, i, j, m, n));
            }
        }
        return ans;
    }

    private int dfs(int[][] matrix, int i, int j, int m, int n) {
        int best = 1;
        for (int[] d : dirs) {
            int ni = i + d[0], nj = j + d[1];
            if (ni >= 0 && ni < m && nj >= 0 && nj < n && matrix[ni][nj] > matrix[i][j]) {
                best = Math.max(best, 1 + dfs(matrix, ni, nj, m, n));
            }
        }
        return best;
    }

    // ---- Approach 2: Recursion + Memoization ----
    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m][n];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                ans = Math.max(ans, dfsMemo(matrix, i, j, m, n, dp));
            }
        }
        return ans;
    }

    private int dfsMemo(int[][] matrix, int i, int j, int m, int n, int[][] dp) {
        if (dp[i][j] != 0) return dp[i][j]; // already computed

        int best = 1;
        for (int[] d : dirs) {
            int ni = i + d[0], nj = j + d[1];
            if (ni >= 0 && ni < m && nj >= 0 && nj < n && matrix[ni][nj] > matrix[i][j]) {
                best = Math.max(best, 1 + dfsMemo(matrix, ni, nj, m, n, dp));
            }
        }
        dp[i][j] = best;
        return best;
    }

    // ---- Approach 3: BFS Topological Sort (Kahn's on the DAG) ----
    // Treat the matrix as a graph: edge from cell A to cell B if B > A (and adjacent).
    // Indegree of a cell = number of adjacent cells with SMALLER value.
    // BFS from all cells with indegree 0 (local minima), count layers = longest path.
    public int longestIncreasingPathBFS(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] indegree = new int[m][n];

        // Compute indegree: for each cell, count how many neighbors are smaller.
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int[] d : dirs) {
                    int ni = i + d[0], nj = j + d[1];
                    if (ni >= 0 && ni < m && nj >= 0 && nj < n
                            && matrix[ni][nj] < matrix[i][j]) {
                        indegree[i][j]++;
                    }
                }
            }
        }

        // Start BFS from all cells with indegree 0 (no smaller neighbor → path starts here).
        java.util.Queue<int[]> queue = new java.util.LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (indegree[i][j] == 0) queue.offer(new int[]{i, j});
            }
        }

        // BFS layer by layer. Each layer = one step in the increasing path.
        int layers = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            layers++;
            for (int k = 0; k < size; k++) {
                int[] cell = queue.poll();
                int i = cell[0], j = cell[1];
                for (int[] d : dirs) {
                    int ni = i + d[0], nj = j + d[1];
                    if (ni >= 0 && ni < m && nj >= 0 && nj < n
                            && matrix[ni][nj] > matrix[i][j]) {
                        indegree[ni][nj]--;
                        if (indegree[ni][nj] == 0) queue.offer(new int[]{ni, nj});
                    }
                }
            }
        }
        return layers;
    }

    public static void main(String[] args) {
        LongestIncreasingPath sol = new LongestIncreasingPath();

        int[][] m1 = {{9, 9, 4}, {6, 6, 8}, {2, 1, 1}};
        System.out.println("Brute: " + sol.longestIncreasingPathBrute(m1)); // 4
        System.out.println("Memo:  " + sol.longestIncreasingPath(m1));      // 4
        // Path: 1 → 2 → 6 → 9

        int[][] m2 = {{3, 4, 5}, {3, 2, 6}, {2, 2, 1}};
        System.out.println("Brute: " + sol.longestIncreasingPathBrute(m2)); // 4
        System.out.println("Memo:  " + sol.longestIncreasingPath(m2));      // 4
        // Path: 3 → 4 → 5 → 6

        int[][] m3 = {{1}};
        System.out.println("Memo:  " + sol.longestIncreasingPath(m3));      // 1
        System.out.println("BFS:   " + sol.longestIncreasingPathBFS(m3));   // 1

        int[][] m1b = {{9, 9, 4}, {6, 6, 8}, {2, 1, 1}};
        System.out.println("BFS:   " + sol.longestIncreasingPathBFS(m1b));  // 4

        int[][] m2b = {{3, 4, 5}, {3, 2, 6}, {2, 2, 1}};
        System.out.println("BFS:   " + sol.longestIncreasingPathBFS(m2b));  // 4
    }
}
