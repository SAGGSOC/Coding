package ExpenseValidator;

import ExpenseValidator.rules.*;
import java.util.*;

/**
 * Demo: runs the expense rule evaluator with sample data.
 */
public class Main {

    public static void main(String[] args) {
        // ---- Define Rules ----
        List<Rule> rules = Arrays.asList(
            new MaxAmountRule(175),
            new SellerTypeMaxAmountRule("restaurant", 45),
            new BlockedExpenseTypeRule("Entertainment")
        );

        // ---- Define Expenses ----
        List<Expense> expenses = Arrays.asList(
            new Expense("1", "Item1", "Food", 250, "restaurant", "ABC Restaurant"),
            new Expense("2", "Item2", "Travel", 100, "airline", "Delta Airlines"),
            new Expense("3", "Item3", "Entertainment", 50, "theater", "AMC Theaters"),
            new Expense("4", "Item4", "Food", 30, "restaurant", "XYZ Cafe"),
            new Expense("5", "Item5", "Office", 200, "store", "Staples")
        );

        // ---- Evaluate ----
        ExpenseRuleEvaluator evaluator = new ExpenseRuleEvaluator();
        List<Violation> violations = evaluator.evaluateRules(rules, expenses);

        // ---- Report ----
        System.out.println("=== Violations Found: " + violations.size() + " ===\n");
        for (Violation v : violations) {
            System.out.println(v);
        }

        // ---- Grouped Report ----
        System.out.println("\n=== Grouped by Expense ===\n");
        Map<String, List<Violation>> grouped = evaluator.evaluateGroupedByExpense(rules, expenses);
        for (Map.Entry<String, List<Violation>> entry : grouped.entrySet()) {
            System.out.println("Expense ID: " + entry.getKey());
            for (Violation v : entry.getValue()) {
                System.out.println("  - " + v.getRule().getDescription());
            }
        }
    }
}
