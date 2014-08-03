package com.mtn.piece;

import com.mtn.object.ChessBoard;
import com.mtn.object.ChessCell;
import com.mtn.pattern.Pattern;

/**
 * @author Mahdi
 */
public class King extends Piece {
    private final static Pattern movePattern = new Pattern(new int[][]{
            {1,1},
            {1,0},
            {1,-1},
            {-1,1},
            {-1,0},
            {-1,-1},
            {0,1},
            {0,-1},

    }, false);

    public King(ChessBoard chessBoard, Color color, ChessCell position) {
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
