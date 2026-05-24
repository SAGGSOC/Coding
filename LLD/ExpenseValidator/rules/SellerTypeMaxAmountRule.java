package ExpenseValidator.rules;

import ExpenseValidator.Expense;
import ExpenseValidator.Rule;

/**
 * Rule: A specific seller type should not have expense more than a given limit.
 * Example: "Seller type 'restaurant' should not have expense more than 45"
 */
public class SellerTypeMaxAmountRule implements Rule {
    private String sellerType;
    private double maxAmount;

    public SellerTypeMaxAmountRule(String sellerType, double maxAmount) {
        this.sellerType = sellerType;
        this.maxAmount = maxAmount;
    }

    @Override
    public boolean evaluate(Expense expense) {
        // Rule only applies to matching seller type.
        if (!expense.getSellerType().equalsIgnoreCase(sellerType)) {
            return true; // not applicable → passes
        }
        return expense.getAmountInUsd() <= maxAmount;
    }

    @Override
    public String getDescription() {
        return "Seller type '" + sellerType + "' should not have expense more than $" + maxAmount;
    }
}
