import java.util.*;

/**
 * LC 1631 - Path With Minimum Effort.
 *
 * Pattern: Modified Dijkstra (minimize the maximum edge weight on the path).
 *
 * effort[i][j] = minimum possible "max absolute difference" on any path from (0,0) to (i,j).
 * Relaxation: effort[v] = min(effort[v], max(effort[u], |height[u] - height[v]|))
 *
 * Time  : O(m * n * log(m * n))
 * Space : O(m * n)
 */
public class PathWithMinEffort {

    public int minimumEffortPath(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        int[][] effort = new int[m][n];
        for (int[] row : effort) Arrays.fill(row, Integer.MAX_VALUE);
        effort[0][0] = 0;

        // PQ: {effort, row, col}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, 0, 0});

        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int e = curr[0], r = curr[1], c = curr[2];

            if (r == m - 1 && c == n - 1) return e;
            if (e > effort[r][c]) continue;

            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < m && nc >= 0 && nc < n) {
                    int newEffort = Math.max(e, Math.abs(heights[nr][nc] - heights[r][c]));
                    if (newEffort < effort[nr][nc]) {
                        effort[nr][nc] = newEffort;
                        pq.offer(new int[]{newEffort, nr, nc});
                    }
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        PathWithMinEffort sol = new PathWithMinEffort();
        System.out.println(sol.minimumEffortPath(new int[][]{{1,2,2},{3,8,2},{5,3,5}})); // 2
        System.out.println(sol.minimumEffortPath(new int[][]{{1,2,3},{3,8,4},{5,3,5}})); // 1
    }
}
