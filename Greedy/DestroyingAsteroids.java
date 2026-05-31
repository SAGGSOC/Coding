import java.util.Arrays;

/**
 * LC 2126 - Destroying Asteroids.
 *
 * Pattern: Greedy (sort + absorb smallest first).
 *
 * Intuition: Always collide with the smallest asteroid first.
 * If you can absorb it, your mass grows, making larger ones easier.
 * If you can't absorb the next smallest, you never will (mass only grows).
 *
 * Time  : O(n log n) — sorting
 * Space : O(1)
 */
public class DestroyingAsteroids {

    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        Arrays.sort(asteroids);
        long m = mass; // long to avoid overflow
        for (int a : asteroids) {
            if (m >= a) m += a;
            else return false;
        }
        return true;
    }

    public static void main(String[] args) {
        DestroyingAsteroids sol = new DestroyingAsteroids();
        System.out.println(sol.asteroidsDestroyed(10, new int[]{3, 9, 19, 5, 21})); // true
        System.out.println(sol.asteroidsDestroyed(5, new int[]{4, 9, 23, 4}));      // false
    }
}
