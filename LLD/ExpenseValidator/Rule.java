package ExpenseValidator;

/**
 * Interface for an expense validation rule.
 * Each rule knows how to evaluate itself against an expense.
 *
 * Strategy Pattern: each concrete rule encapsulates one validation logic.
 */
public interface Rule {
    /**
     * Returns true if the expense PASSES the rule (is valid).
     * Returns false if the expense VIOLATES the rule.
     */
    boolean evaluate(Expense expense);

    /**
     * Human-readable description of the rule (for flagging violations).
     */
    String getDescription();
}
