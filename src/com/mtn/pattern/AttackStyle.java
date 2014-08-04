package com.mtn.pattern;

import com.mtn.object.ChessBoard;
import com.mtn.object.ChessCell;
import com.mtn.piece.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mahdi
 */
public class AttackStyle extends MoveStyle {
    protected List<ChessCell> canAttackCells;

    public AttackStyle(ChessBoard chessBoard, Piece piece, Pattern pattern, boolean piecesCanBlockMyWay) {
        super(chessBoard, piece, pattern, piecesCanBlockMyWay);
        canAttackCells = new ArrayList<ChessCell>();
    }


    @Override
    public void update() {
        super.update();
        canAttackCells.clear();
        for (ChessCell cell : canGoCells) {
            if (!cell.isEmpty() && !cell.getPiece().getColor().equals(piece.getColor())) {
                canAttackCells.add(cell);
            }
        }
    }
}
