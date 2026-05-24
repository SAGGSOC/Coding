package ExpenseValidator;

import java.util.*;

/**
 * Core evaluator: runs all rules against all expenses and collects violations.
 */
public class ExpenseRuleEvaluator {

    /**
     * Evaluates each rule against each expense.
     * Returns a list of violations (expense + rule that was violated).
     *
     * Time  : O(R * E) where R = rules, E = expenses.
     */
    public List<Violation> evaluateRules(List<Rule> rules, List<Expense> expenses) {
        List<Violation> violations = new ArrayList<>();

        for (Expense expense : expenses) {
            for (Rule rule : rules) {
                if (!rule.evaluate(expense)) {
                    violations.add(new Violation(expense, rule));
                }
            }
        }

        return violations;
    }

    /**
     * Groups violations by expense ID for easier reporting.
     */
    public Map<String, List<Violation>> evaluateGroupedByExpense(List<Rule> rules,
                                                                  List<Expense> expenses) {
        Map<String, List<Violation>> grouped = new LinkedHashMap<>();

        for (Expense expense : expenses) {
            for (Rule rule : rules) {
                if (!rule.evaluate(expense)) {
                    grouped.computeIfAbsent(expense.getExpenseId(), k -> new ArrayList<>())
                           .add(new Violation(expense, rule));
                }
            }
        }

        return grouped;
    }
}
