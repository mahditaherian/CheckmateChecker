package com.mtn.piece;

import com.mtn.object.ChessBoard;
import com.mtn.object.ChessCell;
import com.mtn.object.IllegalChessPositionException;
import com.mtn.pattern.AttackStyle;
import com.mtn.pattern.MoveStyle;
import com.mtn.pattern.Pattern;

import java.util.List;

/**
 * @author Mahdi
 */
public class Pawn extends Piece {
    private boolean firstPosition;

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

        if (color.equals(Color.WHITE)) {
            homePiece = true;
        } else {
            homePiece = false;
        }
            firstPosition = false;
        if (!homePiece) {
            if(position.getRow() == 6){
                firstPosition = true;
            }
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
        }else {
            if(position.getRow() == 1){
                firstPosition = true;
            }
        }

        firstMoveStyle = new MoveStyle(chessBoard, this, firstMovePattern, true);
        moveStyle = new MoveStyle(chessBoard, this, movePattern, true);
        attackStyle = new AttackStyle(chessBoard, this, attackPattern, true);
    }

    @Override
    public void updatePosition(ChessCell newPosition) throws IllegalChessPositionException {
        if(firstPosition && !newPosition.equals(position)){
            firstPosition = false;
        }
        super.updatePosition(newPosition);
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
        if (firstPosition) {
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
        if (firstPosition) {
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
