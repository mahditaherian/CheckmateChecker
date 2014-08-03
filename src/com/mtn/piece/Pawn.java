package com.mtn.piece;

import com.mtn.object.ChessBoard;
import com.mtn.object.ChessCell;
import com.mtn.pattern.AttackStyle;
import com.mtn.pattern.MoveStyle;
import com.mtn.pattern.Pattern;

import java.util.List;

/**
 * @author Mahdi
 */
public class Pawn extends Piece {
    private boolean firstTurn;
    private ChessCell firstPosition;

    private Pattern movePattern = new Pattern(new int[][]{
            {1, 0},
    }, false);

    private Pattern firstMovePattern = new Pattern(new int[][]{
            {1, 0},
            {2, 0},
    }, false);

    private Pattern attackPattern = new Pattern(new int[][]{
            {1, -1},
            {1, 1},
    }, false);

    private final MoveStyle firstMoveStyle;
    private final AttackStyle attackStyle;
    private boolean homePiece;


    public Pawn(ChessBoard chessBoard, Color color, ChessCell position) {
        super(chessBoard, color, position);
        firstPosition = position;

        if (color.equals(Color.WHITE)) {
            homePiece = true;
        } else {
            homePiece = false;
        }

        if (!homePiece) {
            movePattern = new Pattern(new int[][]{
                    {-1, 0},
            }, false);
            firstMovePattern = new Pattern(new int[][]{
                    {-1, 0},
                    {-2, 0},
            }, false);

            attackPattern = new Pattern(new int[][]{
                    {-1, -1},
                    {-1, 1},
            }, false);
        }

        firstMoveStyle = new MoveStyle(chessBoard, this, firstMovePattern, true);
        moveStyle = new MoveStyle(chessBoard, this, movePattern, true);
        attackStyle = new AttackStyle(chessBoard, this, attackPattern, true);
    }

    @Override
    public List<ChessCell> getCanAttackCells() {
        return attackStyle.getCanGoCells();
    }

    @Override
    public List<ChessCell> getCanReplaceCell() {
        return attackStyle.getCanReplaceCell();
    }

    @Override
    public List<ChessCell> getCanGoCells() {
        if (firstPosition.equals(position)) {
            return firstMoveStyle.getCanGoCells();
        } else {
            return moveStyle.getCanGoCells();
        }
    }

    @Override
    public Pattern getMovePattern() {
        return this.movePattern;
    }

    @Override
    public void update() {
        if (firstPosition.equals(position)) {
            firstMoveStyle.update();
        } else {
            moveStyle.update();
        }
        attackStyle.update();
    }

    @Override
    protected boolean piecesCanBlockWay() {
        return true;
    }
}
