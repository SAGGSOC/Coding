package ExpenseValidator;

/**
 * Represents a single expense entry.
 */
public class Expense {
    private String expenseId;
    private String itemId;
    private String expenseType;
    private double amountInUsd;
    private String sellerType;
    private String sellerName;

    public Expense(String expenseId, String itemId, String expenseType,
                   double amountInUsd, String sellerType, String sellerName) {
        this.expenseId = expenseId;
        this.itemId = itemId;
        this.expenseType = expenseType;
        this.amountInUsd = amountInUsd;
        this.sellerType = sellerType;
        this.sellerName = sellerName;
    }

    public String getExpenseId() { return expenseId; }
    public String getItemId() { return itemId; }
    public String getExpenseType() { return expenseType; }
    public double getAmountInUsd() { return amountInUsd; }
    public String getSellerType() { return sellerType; }
    public String getSellerName() { return sellerName; }

    @Override
    public String toString() {
        return "Expense{id=" + expenseId + ", type=" + expenseType +
               ", amount=" + amountInUsd + ", seller=" + sellerName + "}";
    }
}
