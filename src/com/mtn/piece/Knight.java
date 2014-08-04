package com.mtn.piece;

import com.mtn.object.ChessBoard;
import com.mtn.object.ChessCell;
import com.mtn.pattern.Pattern;

/**
 * @author Mahdi
 */
public class Knight extends Piece {
    private final static Pattern movePattern = new Pattern(new int[][]{
            {1, 2},
            {2, 1},
            {1, -2},
            {2, -1},
            {-1, 2},
            {-2, 1},
            {-1, -2},
            {-2, -1},

    }, false);

    public Knight(ChessBoard chessBoard, Color color, ChessCell position) {
        super(chessBoard, color, position);
    }

    @Override
    public Pattern getMovePattern() {
        return movePattern;
    }

    @Override
    protected boolean piecesCanBlockWay() {
        return false;
    }


}
