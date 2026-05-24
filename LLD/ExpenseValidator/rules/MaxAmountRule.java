package ExpenseValidator.rules;

import ExpenseValidator.Expense;
import ExpenseValidator.Rule;

/**
 * Rule: Total expense amount should not exceed a given limit.
 * Example: "Total expense should not be > 175"
 */
public class MaxAmountRule implements Rule {
    private double maxAmount;

    public MaxAmountRule(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    @Override
    public boolean evaluate(Expense expense) {
        return expense.getAmountInUsd() <= maxAmount;
    }

    @Override
    public String getDescription() {
        return "Expense amount should not exceed $" + maxAmount;
    }
}
