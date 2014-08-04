package com.mtn.pattern;

/**
 * @author Mahdi
 */
public class Pattern {
    private int pattern[][];
    private boolean extendable;

    public Pattern(int[][] pattern, boolean extendable) {
        this.pattern = pattern;
        this.extendable = extendable;
    }

    public Pattern translate(int row, int col) {
        Pattern p;
        int size = pattern.length;
        int[][] translate = new int[size][2];
        for (int i = 0; i < size; i++) {
            translate[i][0] = pattern[i][0] + row;
            translate[i][1] = pattern[i][1] + col;
        }
        p = new Pattern(translate, extendable);
        return p;
    }

    public int[][] getPattern() {
        return pattern;
    }

    public void setPattern(int[][] pattern) {
        this.pattern = pattern;
    }

    public boolean isExtendable() {
        return extendable;
    }

    public void setExtendable(boolean extendable) {
        this.extendable = extendable;
    }
}
