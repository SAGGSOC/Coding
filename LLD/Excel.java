import java.util.*;

/**
 * LC 631 - Design Excel Sum Formula.
 *
 * Key Design Decisions:
 *   - Each cell either holds a plain value OR a sum formula (not both).
 *   - When a cell has a formula, its value is computed dynamically by
 *     recursively evaluating the cells it depends on.
 *   - set() overwrites any existing formula (the cell becomes a plain value).
 *   - sum() sets a formula on the cell (replaces any plain value).
 *   - get() evaluates the cell: if it has a formula, recursively compute;
 *     otherwise return the stored value.
 *
 * Data Structures:
 *   - int[][] mat         : stores plain values.
 *   - String[][][] formulas : stores the formula (list of number strings) for each cell.
 *                             null means no formula (use mat value).
 *
 * Why recursive get()?
 *   Formulas can reference other formula cells. E.g., A1 = sum(B1:B3) and B2 = sum(C1:C2).
 *   When we get(A1), we need the live value of B2, which itself needs to evaluate its formula.
 *   Since no circular references are guaranteed, recursion terminates.
 */
public class Excel {

    private int[][] mat;
    private String[][][] formulas; // formulas[r][c] = the numbers[] list, or null

    public Excel(int height, char width) {
        int cols = width - 'A' + 1;
        mat = new int[height + 1][cols]; // 1-indexed rows
        formulas = new String[height + 1][cols][];
    }

    public void set(int row, char column, int val) {
        int c = column - 'A';
        mat[row][c] = val;
        formulas[row][c] = null; // setting a value clears any formula
    }

    public int get(int row, char column) {
        int c = column - 'A';
        // If this cell has a formula, compute it dynamically.
        if (formulas[row][c] != null) {
            return computeSum(formulas[row][c]);
        }
        return mat[row][c];
    }

    public int sum(int row, char column, String[] numbers) {
        int c = column - 'A';
        formulas[row][c] = numbers; // store the formula
        int val = computeSum(numbers);
        mat[row][c] = val; // cache (but get() will recompute if formula exists)
        return val;
    }

    // Overload for List<String> (LeetCode signature).
    public int sum(int row, char column, List<String> numbers) {
        return sum(row, column, numbers.toArray(new String[0]));
    }

    /**
     * Evaluates a sum formula by parsing each number string.
     * "B3"     → single cell
     * "B3:F7"  → range of cells
     */
    private int computeSum(String[] numbers) {
        int total = 0;
        for (String s : numbers) {
            if (s.contains(":")) {
                // Range: "ColRow1:ColRow2"
                String[] parts = s.split(":");
                int[] topLeft = parseCell(parts[0]);
                int[] botRight = parseCell(parts[1]);
                for (int r = topLeft[0]; r <= botRight[0]; r++) {
                    for (int c = topLeft[1]; c <= botRight[1]; c++) {
                        total += get(r, (char) ('A' + c));
                    }
                }
            } else {
                // Single cell: "ColRow"
                int[] cell = parseCell(s);
                total += get(cell[0], (char) ('A' + cell[1]));
            }
        }
        return total;
    }

    /**
     * Parses "F7" → {row=7, col=5} (F='A'+5).
     */
    private int[] parseCell(String s) {
        int col = s.charAt(0) - 'A';
        int row = Integer.parseInt(s.substring(1));
        return new int[]{row, col};
    }

    // ---- Demo ----
    public static void main(String[] args) {
        // Excel(3, 'C') → 3 rows, columns A-C
        Excel excel = new Excel(3, 'C');

        excel.set(1, 'A', 2);
        System.out.println(excel.get(1, 'A')); // 2

        excel.sum(3, 'C', new String[]{"A1", "A1:B2"});
        // C3 = A1 + sum(A1:B2) = 2 + (2+0+0+0) = 4
        System.out.println(excel.get(3, 'C')); // 4

        excel.set(1, 'A', 5);
        // Now A1=5. C3's formula still references A1.
        // C3 = A1 + sum(A1:B2) = 5 + (5+0+0+0) = 10
        System.out.println(excel.get(3, 'C')); // 10

        excel.set(1, 'B', 3);
        // C3 = A1 + sum(A1:B2) = 5 + (5+3+0+0) = 13
        System.out.println(excel.get(3, 'C')); // 13

        // Overwrite C3 with a plain value → formula is gone.
        excel.set(3, 'C', 99);
        System.out.println(excel.get(3, 'C')); // 99

        excel.set(1, 'A', 100);
        // C3 no longer has a formula, so it stays 99.
        System.out.println(excel.get(3, 'C')); // 99
    }
}
