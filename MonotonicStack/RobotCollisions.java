import java.util.*;

/**
 * LC 2751 - Robot Collisions.
 *
 * Pattern: Stack-based Collision Simulation (same as Asteroid Collision but with health).
 *
 * Approach:
 *   1. Sort robots by position (keep original index for output order).
 *   2. Process left to right:
 *      - 'R' robot → push onto stack.
 *      - 'L' robot → collide with stack top (R-robots) until resolved.
 *   3. Collision rules:
 *      - R.health > L.health → R survives (health-=1), L destroyed.
 *      - R.health < L.health → L survives (health-=1), R destroyed (pop).
 *      - R.health == L.health → both destroyed.
 *   4. Collect survivors, return in original input order.
 *
 * Time  : O(n log n)
 * Space : O(n)
 */
public class RobotCollisions {

    public List<Integer> survivedRobotsHealths(int[] positions, int[] healths, String directions) {
        int n = positions.length;

        // Create index array and sort by position.
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) indices[i] = i;
        Arrays.sort(indices, (a, b) -> positions[a] - positions[b]);

        // Stack stores original indices of R-moving robots.
        Stack<Integer> stack = new Stack<>();
        // Track which robots survive (health > 0 means alive).
        int[] health = healths.clone();

        for (int idx : indices) {
            if (directions.charAt(idx) == 'R') {
                stack.push(idx);
            } else {
                // 'L' robot collides with R-robots on stack.
                while (!stack.isEmpty() && health[idx] > 0) {
                    int top = stack.peek();

                    if (health[top] > health[idx]) {
                        // R-robot wins
                        health[top]--;
                        health[idx] = 0; // L-robot destroyed
                    } else if (health[top] < health[idx]) {
                        // L-robot wins
                        health[idx]--;
                        health[top] = 0; // R-robot destroyed
                        stack.pop();
                    } else {
                        // Both destroyed
                        health[top] = 0;
                        health[idx] = 0;
                        stack.pop();
                    }
                }
            }
        }

        // Collect survivors in original input order.
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (health[i] > 0) result.add(health[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        RobotCollisions sol = new RobotCollisions();

        System.out.println(sol.survivedRobotsHealths(
            new int[]{5, 4, 3, 2, 1},
            new int[]{2, 17, 9, 15, 10},
            "RRRRL"));
        // [2, 17, 9, 15, 10] — no collisions (L is leftmost)

        System.out.println(sol.survivedRobotsHealths(
            new int[]{3, 5, 2, 6},
            new int[]{10, 10, 15, 12},
            "RLRL"));
        // [14]

        System.out.println(sol.survivedRobotsHealths(
            new int[]{1, 2, 5, 6},
            new int[]{10, 10, 11, 11},
            "RLRL"));
        // []
    }
}
