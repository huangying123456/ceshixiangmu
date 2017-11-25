package com.interview;

/**
 * Created by hy on 2017/11/13.
 */
public class PrintCircularMatrix {

    /**
     * http://www.merlinchinta.com/201506/1300.html
     * @param args
     */
    public static void main(String[] args) {
        int SIZE = 4;
        int[][] arr = new int[SIZE][SIZE];
        int orient = 0;
        for (int i = 1, row = 0, col = 0; i <= SIZE * SIZE; i++) {
            arr[row][col] = i;
            if (row + col == SIZE - 1 && row >= SIZE / 2) {
                orient = 1;
            }
            if (row + col == SIZE - 1 && row < SIZE / 2) {
                orient = 3;
            }
            if (row == col && row >= SIZE / 2) {
                orient = 2;
            }
            if (col - row == 1 && row < SIZE / 2) {
                orient = 0;
            }

            switch (orient) {
                case 0:
                    row++;
                    break;
                case 1:
                    col++;
                    break;
                case 2:
                    row--;
                    break;
                case 3:
                    col--;
                    break;

            }

        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (arr[i][j] < 10) {
                    System.out.print("0" + arr[i][j] + "\t");
                } else {
                    System.out.print(arr[i][j] + "\t");
                }
            }
            System.out.println();
        }
    }

}
