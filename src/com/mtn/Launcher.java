package com.mtn;

import com.mtn.object.ChessBoard;
import com.mtn.piece.Bishop;
import com.mtn.piece.Pawn;
import com.mtn.piece.Piece;
import com.mtn.player.Player;

/**
 * @author Mahdi
 */
public class Launcher {


    public static void main(String[] args) {
        ChessBoard chessBoard = new ChessBoard();

        Pawn wp1 = new Pawn(chessBoard, Piece.Color.WHITE, chessBoard.getCell(1, 0));
        Pawn wp2 = new Pawn(chessBoard, Piece.Color.WHITE, chessBoard.getCell(1, 1));
        Pawn wp3 = new Pawn(chessBoard, Piece.Color.WHITE, chessBoard.getCell(1, 2));
        Pawn wp4 = new Pawn(chessBoard, Piece.Color.WHITE, chessBoard.getCell(1, 3));
        Bishop wb1 = new Bishop(chessBoard, Piece.Color.WHITE, chessBoard.getCell(0, 2));
        Bishop wb2 = new Bishop(chessBoard, Piece.Color.WHITE, chessBoard.getCell(0, 5));


        PieceManager manager = new PieceManager();
        manager.addPiece(wp1);
        manager.addPiece(wp2);
        manager.addPiece(wp3);
        manager.addPiece(wp4);
        manager.addPiece(wb1);
        manager.addPiece(wb2);

        Player white = new Player(manager, chessBoard, Piece.Color.WHITE, Piece.Color.BLACK);
        Player black = new Player(manager, chessBoard, Piece.Color.BLACK, Piece.Color.WHITE);

        while (true) {
            if (!turn(white)) {
                break;
            }
            if (!turn(black)) {
                break;
            }
        }

    }

    private static boolean turn(Player player) {
        System.out.println(player.toString() + " turn.....");
        GameState gameState = player.turn();
        switch (gameState) {
            case CHECKMATING:
                System.out.println(player.toString() + " is winning!");
                return false;
            case CHECKMATED:
                System.out.println(player.toString() + " is checkmate!");
//                    finished = true;
                return false;
            case CHECKED:
                System.out.println(player.toString() + " is check!");
        }
        return true;
    }
}
