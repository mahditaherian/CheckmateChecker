package com.mtn.object;

import com.mtn.piece.Piece;

/**
 * @author Mahdi
 */
public class ChessCell {
    private int row, col;
    private Piece piece;


    public ChessCell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isEmpty() {
        return piece == null || !piece.getPosition().equals(this);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        ChessCell chessCell = (ChessCell) o;

        if (col != chessCell.col) return false;
        if (row != chessCell.row) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        return result;
    }

    @Override
    public String toString() {
        return "ChessCell{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}
