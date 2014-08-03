package com.mtn.piece;

import com.mtn.object.ChessBoard;
import com.mtn.object.ChessCell;
import com.mtn.pattern.MoveStyle;
import com.mtn.pattern.Pattern;

import java.util.List;

/**
 * @author Mahdi
 */
public abstract class Piece {
    protected ChessCell position;
    protected boolean isDead;
    private Color color;
    protected ChessBoard chessBoard;
    protected MoveStyle moveStyle;

    protected Piece(ChessBoard chessBoard, Color color, ChessCell position) {
        this.chessBoard = chessBoard;
        this.color = color;
        this.position = position;
        this.moveStyle = new MoveStyle(chessBoard, this, getMovePattern(), piecesCanBlockWay());
    }

    public void update(){
        moveStyle.update();
    }

    protected abstract boolean piecesCanBlockWay();


/*    public void updatePosition(ChessCell newPosition) throws IllegalChessPositionException {
        if (newPosition.equals(position)) {
            return;
        }

        if (!getCanGoCells().contains(newPosition)) {
            throw new IllegalChessPositionException();
        }

        position = newPosition;
    }*/


    public List<ChessCell> getCanGoCells() {
        return moveStyle.getCanGoCells();
    }

    public List<ChessCell> getCanReplaceCell() { return moveStyle.getCanReplaceCell();}

    public List<ChessCell> getCanAttackCells() {
        return moveStyle.getCanAttackCells();
    }
    public java.util.Map<Piece, List<ChessCell>> getMoveCandidateCells() {
        return moveStyle.getMoveCandidateCells();
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ChessCell getPosition() {
        return position;
    }

    public abstract Pattern getMovePattern();

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"{" +
                "position=" + position +
                ", color=" + color +
                ", isDead=" + isDead +
                '}';
    }

//    public boolean canAttackAfterMove(Piece targetPiece, Piece onWayPiece, ChessCell destination){
//        return false;
//    }

    public int[][] getPattern(ChessCell position) {
        return moveStyle.getPattern(position);
    }

    public List<ChessCell> getCanGoCellsByPattern(int[][] pattern) {
        return moveStyle.getCanGoCellsByPattern(pattern);
    }

    public enum Color {
        BLACK,
        WHITE,
    }
}
