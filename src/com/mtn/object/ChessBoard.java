package com.mtn.object;

import com.mtn.piece.Piece;

/**
 * @author Mahdi
 */
public class ChessBoard {
    ChessCell[][] cells = new ChessCell[8][8];

    public ChessBoard() {


        //init board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j] = new ChessCell(i, j);
            }
        }
    }


    public void register(Piece piece) {
        piece.getPosition().setPiece(piece);
    }

    public ChessCell getCell(int row, int col) {
        return cells[row][col];
    }

    public Piece getPieceAt(int row, int col) {
        return cells[row][col].getPiece();
    }
}
