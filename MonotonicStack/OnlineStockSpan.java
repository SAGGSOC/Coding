import java.util.*;

/**
 * LC 901 - Online Stock Span.
 *
 * Pattern: Monotonic Decreasing Stack (Previous Greater Element).
 *
 * The "span" of a stock price today = number of consecutive days (including today)
 * where the price was <= today's price. Equivalently, it's the distance to the
 * previous day with a STRICTLY GREATER price.
 *
 * Approach: Maintain a stack of (price, span) pairs in decreasing order of price.
 * When a new price comes in, pop all entries with price <= current price and
 * accumulate their spans.
 *
 * Time  : O(1) amortized per call (each element pushed/popped once).
 * Space : O(n) worst case.
 */
public class OnlineStockSpan {

    private Stack<int[]> stack; // each entry: [price, span]

    public OnlineStockSpan() {
        stack = new Stack<>();
    }

    public int next(int price) {
        int span = 1;
        // Pop all days with price <= today's price. Absorb their spans.
        while (!stack.isEmpty() && stack.peek()[0] <= price) {
            span += stack.pop()[1];
        }
        stack.push(new int[]{price, span});
        return span;
    }

    public static void main(String[] args) {
        OnlineStockSpan sol = new OnlineStockSpan();
        int[] prices = {100, 80, 60, 70, 60, 75, 85};
        // Expected spans: [1, 1, 1, 2, 1, 4, 6]

        System.out.print("Spans: [");
        for (int i = 0; i < prices.length; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(sol.next(prices[i]));
        }
        System.out.println("]");
    }
}
