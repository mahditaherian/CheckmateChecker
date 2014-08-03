package com.mtn;

import com.mtn.object.ChessBoard;
import com.mtn.object.ChessCell;
import com.mtn.object.IllegalChessPositionException;
import com.mtn.piece.*;
import com.mtn.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahdi
 */
public class TestLauncher {

    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        Piece piece = new King(board, Piece.Color.WHITE, board.getCell(0, 4));
        Piece bishop1 = new Bishop(board, Piece.Color.WHITE, board.getCell(2, 4));
        Piece rook1 = new Rook(board, Piece.Color.BLACK, board.getCell(1, 0));
        Piece rook2 = new Rook(board, Piece.Color.BLACK, board.getCell(0, 0));
        Piece queen = new Rook(board, Piece.Color.BLACK, board.getCell(7, 4));
        PieceManager pieceManager = new PieceManager();

        Piece.Color myColor, enemyColor;
        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Welcome to Farid Ghoorchian Simple Chess Checkmate Detector!");
            while (true) {
                System.out.print("Please type your color(\"white\", \"black\"):");
                String ln = bufferRead.readLine();
                if (ln.equalsIgnoreCase("white")) {
                    myColor = Piece.Color.WHITE;
                    enemyColor = Piece.Color.BLACK;
                    break;
                } else if (ln.equalsIgnoreCase("black")) {
                    myColor = Piece.Color.BLACK;
                    enemyColor = Piece.Color.WHITE;
                    break;
                } else {
                    System.out.println("you typed wrong input!");
                }
            }
            System.out.println("so you are " + myColor.toString().toLowerCase() + " and your opponent is " + enemyColor.toString().toLowerCase() + ".");
            int mypn = -1;
            while (true) {
                System.out.print("Please enter your pieces count in chessboard: ");
                String ln = bufferRead.readLine();
                try {
                    mypn = Integer.parseInt(ln);
                    if (mypn < 1 || mypn > 16) {
                        System.out.println("Out of range(1-16)");
                    } else {
                        break;
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("The input entry is not a valid integer number!");
                }
            }
            int enemypn = -1;
            while (true) {
                System.out.print("Please enter your opponent pieces count in chessboard: ");
                String ln = bufferRead.readLine();
                try {
                    enemypn = Integer.parseInt(ln);
                    if (enemypn < 1 || enemypn > 16) {
                        System.out.println("Out of range(1-16)");
                    } else {
                        break;
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("The input entry is not a valid integer number!");
                }
            }
            registeredCells.clear();
            System.out.println("following pattern type is used to piece naming:");
            System.out.println("\t\tK\tfor King");
            System.out.println("\t\tQ\tfor Queen");
            System.out.println("\t\tR\tfor Rook");
            System.out.println("\t\tKn\tfor Knight");
            System.out.println("\t\tB\tfor Bishop");
            System.out.println("\t\tP\tfor Pawn");
            System.out.println("And following pattern is used to set position:");
            System.out.println("\"row character\"+ \"column number\"    for example \"b6\" means 2nd row and 6th column." +
                    "\nPlease note that valid row range is between a-h and valid col range is between 1-8.");
            System.out.println("To add pieces you should use following instruction: \"piece type\" + space + \"position\". " +
                    "\nExamples : K a5 , P b0 , R a0 , ...");
            List<Piece> myPieces = new ArrayList<Piece>(initPieces(bufferRead, board, myColor, mypn));
            System.out.println("All of your pieces were registered successfully!");
            System.out.println("Now please enter your opponent's pieces:");
            List<Piece> enemyPieces = new ArrayList<Piece>(initPieces(bufferRead, board, enemyColor, enemypn));
            System.out.println("All of your opponent's pieces were registered successfully too!");
            System.out.print("Please enter any key to detect state:");
            bufferRead.readLine();

            pieceManager.initPieces(myPieces);
            pieceManager.initPieces(enemyPieces);

            Player player1 = new Player(pieceManager, board, myColor, enemyColor);
            Player player2 = new Player(pieceManager, board, enemyColor, myColor);
            GameState s1 = player1.turn();
            GameState s2 = player2.turn();

            System.out.println("Your state is " + s1);
            System.out.println("Your opponent's state is " + s2);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        pieceManager.addPiece(queen);


//        p1.connect(pawn);
//        p1.connect(piece);
//        System.out.println("Player1 is " + p1.turn());
    }

    private static List<ChessCell> registeredCells = new ArrayList<ChessCell>();

    private static List<Piece> initPieces(BufferedReader bufferRead, ChessBoard board, Piece.Color color, int count) throws IOException {
        List<Piece> pieces = new ArrayList<Piece>();
        for (int i = 0; i < count; i++) {
            while (true) {
                System.out.print("Piece #" + (i + 1) + ": ");
                String ln = bufferRead.readLine();

                try {
                    Piece p = convertToPiece(board, color, ln);

                    if (p != null) {
                        if (registeredCells.contains(p.getPosition())) {
                            System.out.println("The position you entered was registered by another piece. please enter somewhere else!");
                        } else {
                            registeredCells.add(p.getPosition());
                            pieces.add(p);
                            System.out.println(p.toString() + " is registered!");
                            break;
                        }
                    } else {
                        System.out.println("Wrong entry input! Please try again...");
                    }
                } catch (IllegalChessPositionException e) {
                    System.out.println("The cell you entered is full or invalid! Please enter valid instruction as explained above!(ex: B a3)");
                }
            }
        }
        return pieces;
    }

    private static Piece convertToPiece(ChessBoard board, Piece.Color color, String str) throws IllegalChessPositionException {
        Piece piece = null;
        ChessCell cell;
        String[] split = str.split(" ");
        if (split.length != 2) {
            return null;
        }
        String type = split[0];
        String position = split[1];
        cell = convertToCell(board, position);
        if (type.equalsIgnoreCase("K")) {
            piece = new King(board, color, cell);
        } else if (type.equalsIgnoreCase("Q")) {
            piece = new Queen(board, color, cell);
        } else if (type.equalsIgnoreCase("R")) {
            piece = new Rook(board, color, cell);
        } else if (type.equalsIgnoreCase("Kn")) {
            piece = new Knight(board, color, cell);
        } else if (type.equalsIgnoreCase("B")) {
            piece = new Bishop(board, color, cell);
        } else if (type.equalsIgnoreCase("P")) {
            piece = new Pawn(board, color, cell);
        }
        return piece;

    }

    private static ChessCell convertToCell(ChessBoard board, String str) throws IllegalChessPositionException {
        char c = str.charAt(0);
        int row = -1;
        switch (c) {
            case 'a':
                row = 0;
                break;
            case 'b':
                row = 1;
                break;
            case 'c':
                row = 2;
                break;
            case 'd':
                row = 3;
                break;
            case 'e':
                row = 4;
                break;
            case 'f':
                row = 5;
                break;
            case 'g':
                row = 6;
                break;
            case 'h':
                row = 7;
                break;

        }
        int col = -1;
        try {
            col = Integer.parseInt(str.trim().substring(1)) - 1;
        } catch (NumberFormatException ex) {
            System.err.println(ex.getMessage());
        }
        if (col < 0 || col > 7 || row == -1) {
            throw new IllegalChessPositionException();
        }
        return board.getCell(row, col);
    }
}
