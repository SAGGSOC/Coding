package Recursion;

import java.util.HashSet;
import java.util.Scanner;

class MaximalRectangle {
    static int maxArea = 0;
    public static int maximalRectangle(char[][] matrix){
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return 0;
        }
        int rows = matrix.length;
        int cols = matrix[0].length;
        explore(matrix, 0, 0, rows, cols);
        return maxArea;
    }
    private static  void explore(char[][] matrix, int i, int j, int rows, int cols){
        if(i == rows){
            return;
        }
        if(j == cols){
            explore(matrix, i + 1, 0, rows, cols);
            return;
        }
        if (matrix[i][j] == '1') {
            expandFromCell(matrix, i, j, rows, cols);
        }
        explore(matrix, i, j + 1, rows, cols);
    }

    private static void expandFromCell(char[][] matrix, int r, int c, int rows, int cols) {
        int minWidth = Integer.MAX_VALUE;

        for(int i = r; i < rows && matrix[i][c] == '1'; i++){
            int width = 0;
            for (int j = c; j < cols && matrix[i][j] == '1'; j++) {
                width++;
            }
            minWidth = Math.min(minWidth, width);

            int height = i - r + 1;
            int area = height * minWidth;

            maxArea = Math.max(maxArea, area);
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Matrix");

        char[][] matrix = {
                {'1','1','0'},
                {'1','1','1'},
                {'1','1','1'}
        };

//        String str = sc.next();

        System.out.println(maximalRectangle(matrix));
    }
}