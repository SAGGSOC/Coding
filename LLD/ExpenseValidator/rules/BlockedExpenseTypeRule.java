package ExpenseValidator.rules;

import ExpenseValidator.Expense;
import ExpenseValidator.Rule;

/**
 * Rule: A specific expense type should not be charged at all.
 * Example: "Entertainment expense type should not be charged"
 */
public class BlockedExpenseTypeRule implements Rule {
    private String blockedType;

    public BlockedExpenseTypeRule(String blockedType) {
        this.blockedType = blockedType;
    }

    @Override
    public boolean evaluate(Expense expense) {
        return !expense.getExpenseType().equalsIgnoreCase(blockedType);
    }

    @Override
    public String getDescription() {
        return "Expense type '" + blockedType + "' is not allowed";
    }
}
