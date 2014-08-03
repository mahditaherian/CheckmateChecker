package com.mtn;

import com.mtn.object.ChessBoard;
import com.mtn.piece.*;
import com.mtn.player.Player;

/**
 * Created by Mahdi
 */
public class TestLauncher {

    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
//        Piece pawn = new Pawn(board, Piece.Color.WHITE, board.getCell(1, 4));
        Piece piece = new King(board, Piece.Color.WHITE, board.getCell(0, 4));
        Piece bishop1 = new Bishop(board, Piece.Color.WHITE, board.getCell(2, 4));
        Piece rook1 = new Rook(board, Piece.Color.BLACK, board.getCell(1, 0));
        Piece rook2 = new Rook(board, Piece.Color.BLACK, board.getCell(0, 0));
        Piece queen = new Rook(board, Piece.Color.BLACK, board.getCell(7, 4));
        PieceManager pieceManager = new PieceManager();
//        pieceManager.addPiece(pawn);
        pieceManager.addPiece(piece);
        pieceManager.addPiece(bishop1);

        pieceManager.addPiece(rook1);
        pieceManager.addPiece(rook2);
//        pieceManager.addPiece(queen);


        Player p1 = new Player(pieceManager, board, Piece.Color.WHITE, Piece.Color.BLACK);
//        p1.connect(pawn);
//        p1.connect(piece);
        System.out.println("Player1 is " + p1.turn());
    }
}
