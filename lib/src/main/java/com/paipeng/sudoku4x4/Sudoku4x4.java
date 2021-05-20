package com.paipeng.sudoku4x4;

import java.util.Random;

public class Sudoku4x4 {
    public static int ROW = 4;
    public static int COL = 4;
    public static int BLOCK_ROW = 2;
    public static int BLOCK_COL = 2;
    public static int BLOCK_NUM = ROW * COL / BLOCK_COL / BLOCK_ROW;

    private int[][] number = new int[ROW][COL];
    private int[][] givenNumber = new int[ROW][COL];

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

    public void genRandom(int level) {
        System.out.println("genRandom " + level);
        Random rn = new Random();

        int temp = 0;
        for (int j = 0; j < ROW; j++) {
            for (int i = 0; i < COL; i++) {
                do {
                    temp = rn.nextInt(4) + 1;
                    setNumber(i, j, temp);
                    //System.out.println("output: \n" + toString());
                } while (!validate());
            }
        }

        genRandomGivenNumber(level);
    }

    private void genRandomGivenNumber(int level) {
        System.out.println("genRandomGivenNumber: " + level);
        for (int j = 0; j < ROW; j++) {
            for (int i = 0; i < COL; i++) {
                givenNumber[j][i] = number[j][i];
            }
        }
        for (int i = 0; i < ROW / BLOCK_ROW * COL / BLOCK_COL; i++) {
            genRandomBlockGivenNumber(i, level);
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

        if (number[0][3] != 0 && number[1][3] != 0 && number[2][0] != 0 && number[2][1] != 0) {
            if (number[0][3] == number[2][0] && number[1][3] == number[2][1] ||
                    number[0][3] == number[2][1] && number[1][3] == number[2][0]) {
                return false;
            }
        }
        if (number[0][2] != 0 && number[1][2] != 0 && number[2][0] != 0 && number[2][1] != 0) {
            if (number[0][2] == number[2][0] && number[1][2] == number[2][1] ||
                    number[0][2] == number[2][1] && number[1][2] == number[2][0]) {
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

    private void genRandomBlockGivenNumber(int block, int level) {
        System.out.println("genRandomBlockGivenNumber " + block + " level: " + level);
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
        Random rn = new Random();
        // level: 1- 3;
        int[] n = new int[level];
        int index = 0;
        int temp;
        boolean duplicated = false;
        for (int j = 0; j < level; j++) {
            do {
                duplicated = false;
                temp = rn.nextInt(4);
                for (int i = 0; i < index; i++) {
                    if (temp == n[i]) {
                        // re-gen-random
                        duplicated = true;
                    }
                }
                if (!duplicated) {
                    n[index] = temp;
                    index++;
                }
            } while (duplicated);

        }
        for (int i = 0; i < level; i++) {
            //System.out.println("random: " + n[i]);
            this.givenNumber[row_index + n[i] / 2][col_index + n[i] % 2] = 0;
        }

    }

    public void setNumber(int col, int row, int number) {
        if (col >= 0 && col < COL && row >= 0 && row < ROW) {
            this.number[row][col] = number;
        }
    }


    public int[] convert1DArray() {
        int[] a = new int[ROW*COL];
        for (int j = 0; j < ROW; j++) {
            for (int i = 0; i < COL; i++) {
                a[j*COL+i] = number[j][i];
            }
        }
        return a;
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


    public String getGivenNumberString() {
        String n = "";
        for (int j = 0; j < ROW; j++) {
            for (int i = 0; i < COL; i++) {
                n = n + givenNumber[j][i] + " ";
            }
            n = n + "\n";
        }
        return n;
    }
}
