import java.util.*;

/**
 * LC 853 - Car Fleet.
 *
 * Pattern: Stack + Sort by position.
 *
 * Cars closer to the target that are slower will block faster cars behind them,
 * forming a "fleet". Two cars form a fleet if the one behind catches up before
 * or exactly at the target.
 *
 * Approach:
 *   1. Sort cars by position (descending — closest to target first).
 *   2. For each car, compute time to reach target: (target - pos) / speed.
 *   3. Use a stack: if current car's time > stack top's time, it forms a new fleet
 *      (it's slower, won't be caught). Otherwise it merges into the fleet ahead.
 *
 * The number of fleets = stack size at the end.
 *
 * Time  : O(n log n)
 * Space : O(n)
 */
public class CarFleet {

    public int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;
        if (n == 0) return 0;

        // Pair positions with their time-to-target, sort by position descending.
        double[][] cars = new double[n][2];
        for (int i = 0; i < n; i++) {
            cars[i][0] = position[i];
            cars[i][1] = (double)(target - position[i]) / speed[i];
        }
        Arrays.sort(cars, (a, b) -> Double.compare(b[0], a[0])); // sort by position desc

        // Stack of fleet arrival times.
        Stack<Double> stack = new Stack<>();

        for (double[] car : cars) {
            double time = car[1];
            // If this car takes longer than the fleet ahead, it forms a new fleet.
            if (stack.isEmpty() || time > stack.peek()) {
                stack.push(time);
            }
            // Otherwise it catches up and merges (do nothing).
        }

        return stack.size();
    }

    public static void main(String[] args) {
        CarFleet sol = new CarFleet();
        System.out.println(sol.carFleet(12, new int[]{10, 8, 0, 5, 3}, new int[]{2, 4, 1, 1, 3}));
        // 3

        System.out.println(sol.carFleet(10, new int[]{3}, new int[]{3}));
        // 1

        System.out.println(sol.carFleet(100, new int[]{0, 2, 4}, new int[]{4, 2, 1}));
        // 1
    }
}
