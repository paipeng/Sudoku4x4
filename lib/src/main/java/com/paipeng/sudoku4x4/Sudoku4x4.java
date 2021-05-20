package com.paipeng.sudoku4x4;

import java.util.Random;

public class Sudoku4x4 {
    public static int ROW = 4;
    public static int COL = 4;
    public static int BLOCK_ROW = 2;
    public static int BLOCK_COL = 2;
    public static int BLOCK_NUM = ROW * COL / BLOCK_COL / BLOCK_ROW;

    private int[][] number = new int[ROW][COL];

    public void reset() {
        for (int j = 0; j < ROW; j++) {
            for (int i = 0; i < COL; i++) {
                setNumber(i, j, 0);
            }
        }
    }
    public void dummy() {
        for (int i = 0; i < COL; i++) {
            //number[j][i] = j;//(i+j)%4 + 1;
            setNumber(i, 0, i + 1);
        }
        for (int j = 1; j < ROW; j++) {
            for (int i = 0; i < COL; i++) {
                //number[j][i] = j;//(i+j)%4 + 1;
                for (int n = 1; n <=4; n++) {
                    setNumber(i, j, n);
                    if (validate()) {
                        break;
                    }
                }
            }
        }
    }

    public void genRandom() {
        Random rn = new Random();

        for (int j = 0; j < ROW; j++) {
            for (int i = 0; i < COL; i++) {
                do {
                    setNumber(i, j, rn.nextInt(4) + 1);
                } while (!validate());
            }
        }
    }

    public boolean validate() {
        for (int j = 0; j < ROW; j++) {
            if (!validateRow(j)) {
                return false;
            }
        }
        for (int j = 0; j < COL; j++) {
            if (!validateCol(j)) {
                return false;
            }
        }
        for (int j = 0; j < BLOCK_NUM; j++) {
            if (!validateBlock(j)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateCol(int col) {
        for (int i = 0; i < ROW; i++) {
            for (int a = 0; a < ROW; a++) {
                if (i != a && number[i][col] == number[a][col] && number[a][col] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean validateRow(int row) {
        for (int i = 0; i < COL; i++) {
            for (int a = 0; a < COL; a++) {
                if (i != a && number[row][i] == number[row][a] && number[row][a] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean validateBlock(int block) {
        int row_index = 0;
        int col_index = 0;
        if (block == 1) {
            col_index = 2;
        } else if (block == 2) {
            row_index = 2;
        } else if (block == 3) {
            col_index = 2;
            row_index = 2;
        }
        int[] t = new int[ROW / BLOCK_ROW * COL / BLOCK_COL];
        int index = 0;
        for (int j = row_index; j < row_index + ROW / BLOCK_ROW; j++) {
            for (int i = col_index; i < col_index + COL / BLOCK_COL; i++) {
                t[index++] = number[j][i];
            }
        }
        for (int i = 0; i < ROW / BLOCK_ROW * COL / BLOCK_COL; i++) {
            for (int a = 0; a < ROW / BLOCK_ROW * COL / BLOCK_COL; a++) {
                if (i != a && t[i] == t[a] && t[a] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setNumber(int col, int row, int number) {
        if (col >= 0 && col < COL && row >= 0 && row < ROW) {
            this.number[row][col] = number;
        }
    }

    @Override
    public String toString() {
        String n = "";
        for (int j = 0; j < ROW; j++) {
            for (int i = 0; i < COL; i++) {
                n = n + number[j][i] + " ";
            }
            n = n + "\n";
        }
        return n;
    }
}
