package com.mtn;

import com.mtn.object.ChessBoard;
import com.mtn.object.ChessCell;
import com.mtn.object.IllegalChessPositionException;
import com.mtn.object.PieceType;
import com.mtn.piece.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mahdi
 */
public class PieceManager {
    protected ChessBoard chessBoard;
    //    protected List<Piece> whitePieces;
//    protected List<Piece> blackPieces;
    protected Map<Piece.Color, List<Piece>> colorPiecesMap;


    public PieceManager() {
        chessBoard = new ChessBoard();
//        whitePieces = new ArrayList<Piece>();
//        blackPieces = new ArrayList<Piece>();
        colorPiecesMap = new HashMap<Piece.Color, List<Piece>>();
        for (Piece.Color color : Piece.Color.values()) {
            colorPiecesMap.put(color, new ArrayList<Piece>());
        }
    }

    public void initPieces(List<Piece> pieceList) {
        for (Piece piece : pieceList) {
            addPiece(piece);
        }
    }

    public void addPiece(PieceType pieceType, Piece.Color color, int row, int col) throws IllegalChessPositionException {
        Piece piece = null;
        ChessCell cell = chessBoard.getCell(row, col);
        if (!cell.isEmpty()) {
            throw new IllegalChessPositionException();
        }

        switch (pieceType) {
            case PAWN:
                piece = new Pawn(chessBoard, color, cell);
                break;
            case ROOK:
                piece = new Rook(chessBoard, color, cell);
                break;
            case QUEEN:
                piece = new Queen(chessBoard, color, cell);
                break;
            case KING:
                piece = new King(chessBoard, color, cell);
                break;
            case KNIGHT:
                piece = new Knight(chessBoard, color, cell);
                break;
            case BISHOP:
                piece = new Bishop(chessBoard, color, cell);
                break;


        }
        addPiece(piece);
    }

    public List<Piece> getPieces(Piece.Color color) {
        return colorPiecesMap.get(color);
    }

    public void addPiece(Piece piece) {
        colorPiecesMap.get(piece.getColor()).add(piece);
        chessBoard.register(piece);
    }

}
