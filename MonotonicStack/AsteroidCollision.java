import java.util.*;

/**
 * LC 735 - Asteroid Collision.
 *
 * Pattern: Stack-based Collision Simulation.
 *
 * Positive = moving right, Negative = moving left.
 * Collision happens only when a right-moving asteroid meets a left-moving one
 * (i.e., stack top is positive and current is negative).
 *
 * Rules:
 *   - Larger asteroid survives.
 *   - Equal size → both destroyed.
 *   - Smaller one destroyed.
 *
 * Time  : O(n)
 * Space : O(n)
 */
public class AsteroidCollision {

    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();

        for (int ast : asteroids) {
            boolean destroyed = false;

            // Collision: stack top going right (+) and current going left (-)
            while (!stack.isEmpty() && ast < 0 && stack.peek() > 0) {
                if (stack.peek() < -ast) {
                    // Stack top is smaller → destroyed, keep checking
                    stack.pop();
                } else if (stack.peek() == -ast) {
                    // Equal → both destroyed
                    stack.pop();
                    destroyed = true;
                    break;
                } else {
                    // Stack top is larger → current asteroid destroyed
                    destroyed = true;
                    break;
                }
            }

            if (!destroyed) stack.push(ast);
        }

        int[] result = new int[stack.size()];
        for (int i = result.length - 1; i >= 0; i--) result[i] = stack.pop();
        return result;
    }

    public static void main(String[] args) {
        AsteroidCollision sol = new AsteroidCollision();
        System.out.println(Arrays.toString(sol.asteroidCollision(new int[]{5, 10, -5})));
        // [5, 10]
        System.out.println(Arrays.toString(sol.asteroidCollision(new int[]{8, -8})));
        // []
        System.out.println(Arrays.toString(sol.asteroidCollision(new int[]{10, 2, -5})));
        // [10]
        System.out.println(Arrays.toString(sol.asteroidCollision(new int[]{-2, -1, 1, 2})));
        // [-2, -1, 1, 2]
    }
}
