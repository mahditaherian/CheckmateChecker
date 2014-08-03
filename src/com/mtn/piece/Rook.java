package com.mtn.piece;

import com.mtn.object.ChessBoard;
import com.mtn.object.ChessCell;
import com.mtn.pattern.Pattern;

/**
 * Created by Mahdi
 */
public class Rook extends Piece {
    private final static Pattern movePattern = new Pattern(new int[][]{
            {0,1},
            {1,0},
            {0,-1},
            {-1,0},

    }, true);
    public Rook(ChessBoard chessBoard, Color color, ChessCell position) {
        super(chessBoard, color, position);
    }

    @Override
    protected boolean piecesCanBlockWay() {
        return true;
    }

    @Override
    public Pattern getMovePattern() {
        return movePattern;
    }
}
