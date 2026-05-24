package ExpenseValidator;

/**
 * Represents a rule violation for a specific expense.
 */
public class Violation {
    private Expense expense;
    private Rule rule;

    public Violation(Expense expense, Rule rule) {
        this.expense = expense;
        this.rule = rule;
    }

    public Expense getExpense() { return expense; }
    public Rule getRule() { return rule; }

    @Override
    public String toString() {
        return "VIOLATION: " + expense + " → " + rule.getDescription();
    }
}
