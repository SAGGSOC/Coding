import java.util.*;

/**
 * LC 505 - The Maze II (Shortest Distance).
 *
 * Pattern: Dijkstra's Algorithm (weighted shortest path on a grid).
 *
 * Why Dijkstra and not BFS?
 *   The ball rolls until hitting a wall — each "move" covers a variable number
 *   of cells (variable edge weight). BFS only works for unweighted graphs.
 *
 * Algorithm:
 *   1. Priority queue: (distance, row, col), min-heap by distance.
 *   2. For each popped cell, roll in all 4 directions until hitting a wall.
 *      Count cells traveled = edge weight.
 *   3. If newDist < dist[stopRow][stopCol], update and push to PQ.
 *   4. When destination is popped → return its distance.
 *
 * Time  : O(m * n * log(m * n))
 * Space : O(m * n)
 */
public class TheMazeII {

    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        int m = maze.length, n = maze[0].length;
        int[][] dist = new int[m][n];
        for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);

        // PQ: {distance, row, col}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, start[0], start[1]});
        dist[start[0]][start[1]] = 0;

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int d = curr[0], r = curr[1], c = curr[2];

            // If we've already found a shorter path to this cell, skip.
            if (d > dist[r][c]) continue;

            // If we reached destination, return distance.
            if (r == destination[0] && c == destination[1]) return d;

            // Try rolling in all 4 directions.
            for (int[] dir : dirs) {
                int nr = r, nc = c;
                int steps = 0;

                // Roll until hitting a wall or boundary.
                while (nr + dir[0] >= 0 && nr + dir[0] < m &&
                       nc + dir[1] >= 0 && nc + dir[1] < n &&
                       maze[nr + dir[0]][nc + dir[1]] == 0) {
                    nr += dir[0];
                    nc += dir[1];
                    steps++;
                }

                // nr, nc is where the ball stops.
                int newDist = d + steps;
                if (newDist < dist[nr][nc]) {
                    dist[nr][nc] = newDist;
                    pq.offer(new int[]{newDist, nr, nc});
                }
            }
        }

        return -1; // destination unreachable
    }

    public static void main(String[] args) {
        TheMazeII sol = new TheMazeII();

        int[][] maze1 = {
            {0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0},
            {1, 1, 0, 1, 1},
            {0, 0, 0, 0, 0}
        };
        System.out.println(sol.shortestDistance(maze1, new int[]{0, 4}, new int[]{4, 4}));
        // 12

        System.out.println(sol.shortestDistance(maze1, new int[]{0, 4}, new int[]{3, 2}));
        // -1 (can't stop at destination)

        int[][] maze2 = {
            {0, 0, 0, 0, 0},
            {1, 1, 0, 0, 1},
            {0, 0, 0, 0, 0},
            {0, 1, 0, 0, 1},
            {0, 1, 0, 0, 0}
        };
        System.out.println(sol.shortestDistance(maze2, new int[]{4, 3}, new int[]{0, 1}));
        // -1
    }
}
