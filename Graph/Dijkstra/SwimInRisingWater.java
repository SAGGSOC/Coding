import java.util.*;

/**
 * LC 778 - Swim in Rising Water.
 *
 * Pattern: Modified Dijkstra (minimize the maximum cell value on the path).
 *
 * Same as PathWithMinEffort but the "weight" is the cell value itself (elevation).
 * You can swim from (0,0) to (n-1,n-1) at time t if all cells on the path have value <= t.
 * Answer = minimum t = max cell value on the optimal path.
 *
 * Time  : O(n² * log n)
 * Space : O(n²)
 */
public class SwimInRisingWater {

    public int swimInWater(int[][] grid) {
        int n = grid.length;
        int[][] dist = new int[n][n];
        for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
        dist[0][0] = grid[0][0];

        // PQ: {time, row, col}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{grid[0][0], 0, 0});

        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int t = curr[0], r = curr[1], c = curr[2];

            if (r == n - 1 && c == n - 1) return t;
            if (t > dist[r][c]) continue;

            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                    int newTime = Math.max(t, grid[nr][nc]);
                    if (newTime < dist[nr][nc]) {
                        dist[nr][nc] = newTime;
                        pq.offer(new int[]{newTime, nr, nc});
                    }
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        SwimInRisingWater sol = new SwimInRisingWater();
        System.out.println(sol.swimInWater(new int[][]{{0,2},{1,3}})); // 3
        System.out.println(sol.swimInWater(new int[][]{
            {0,1,2,3,4},{24,23,22,21,5},{12,13,14,15,16},{11,17,18,19,20},{10,9,8,7,6}})); // 16
    }
}
