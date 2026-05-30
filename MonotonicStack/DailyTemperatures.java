import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;

/**
 * LC 739 - Daily Temperatures.
 *
 * Pattern: Monotonic Stack (Next Greater Element variant).
 *
 * Problem: For each day, find how many days you have to wait until a warmer
 *          temperature. If no warmer day exists, answer is 0.
 *
 * Approach:
 *   Use a monotonic DECREASING stack (bottom = hottest, top = coolest).
 *   The stack stores INDICES of days whose "next warmer day" hasn't been found yet.
 *
 *   For each new day i:
 *     - While stack top has a temperature LESS THAN temps[i]:
 *         Pop it (call it j). The answer for j = i - j.
 *     - Push i onto the stack.
 *
 *   Elements remaining in the stack at the end have no warmer future day → answer = 0.
 *
 * Why it works:
 *   The stack holds "unresolved" days in decreasing temperature order.
 *   When a warmer day arrives, it "resolves" all cooler days on top of the stack.
 *   Each element is pushed once and popped once → O(n) total.
 *
 * Time  : O(n) — each index pushed and popped at most once.
 * Space : O(n) — stack can hold all indices in worst case (strictly decreasing input).
 *
 * Dry Run: temps = [73, 74, 75, 71, 69, 72, 76, 73]
 *
 *   i=0 (73): stack=[] → push 0.                    Stack: [0]
 *   i=1 (74): 74>73 → pop 0, ans[0]=1-0=1. Push 1. Stack: [1]
 *   i=2 (75): 75>74 → pop 1, ans[1]=2-1=1. Push 2. Stack: [2]
 *   i=3 (71): 71<75 → push 3.                       Stack: [2,3]
 *   i=4 (69): 69<71 → push 4.                       Stack: [2,3,4]
 *   i=5 (72): 72>69 → pop 4, ans[4]=5-4=1.
 *             72>71 → pop 3, ans[3]=5-3=2.
 *             72<75 → stop. Push 5.                  Stack: [2,5]
 *   i=6 (76): 76>72 → pop 5, ans[5]=6-5=1.
 *             76>75 → pop 2, ans[2]=6-2=4.
 *             Stack empty → push 6.                  Stack: [6]
 *   i=7 (73): 73<76 → push 7.                       Stack: [6,7]
 *
 *   Remaining [6,7] → ans[6]=0, ans[7]=0.
 *   Answer: [1, 1, 4, 2, 1, 1, 0, 0] ✓
 */
public class DailyTemperatures {

    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] answer = new int[n];
        Deque<Integer> stack = new ArrayDeque<>(); // stores indices

        for (int i = 0; i < n; i++) {
            // Pop all days that are cooler than today — today is their answer.
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int j = stack.pop();
                answer[j] = i - j;
            }
            stack.push(i);
        }
        // Remaining indices in stack → no warmer day → answer stays 0.
        return answer;
    }

    // ---- Using java.util.Stack (legacy but commonly used in interviews) ----
    public int[] dailyTemperaturesStack(int[] temperatures) {
        int n = temperatures.length;
        int[] answer = new int[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int j = stack.pop();
                answer[j] = i - j;
            }
            stack.push(i);
        }
        return answer;
    }

    public static void main(String[] args) {
        DailyTemperatures sol = new DailyTemperatures();

        int[] t1 = {73, 74, 75, 71, 69, 72, 76, 73};
        System.out.println("Deque:  " + Arrays.toString(sol.dailyTemperatures(t1)));
        System.out.println("Stack:  " + Arrays.toString(sol.dailyTemperaturesStack(t1)));

        int[] t2 = {30, 40, 50, 60};
        System.out.println("Deque:  " + Arrays.toString(sol.dailyTemperatures(t2)));
        System.out.println("Stack:  " + Arrays.toString(sol.dailyTemperaturesStack(t2)));

        int[] t3 = {30, 60, 90};
        System.out.println("Deque:  " + Arrays.toString(sol.dailyTemperatures(t3)));
        System.out.println("Stack:  " + Arrays.toString(sol.dailyTemperaturesStack(t3)));
    }
}
