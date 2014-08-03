package com.mtn.pattern;

import com.mtn.object.ChessBoard;
import com.mtn.object.ChessCell;
import com.mtn.piece.Piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mahdi
 */
public class MoveStyle {
    protected ChessBoard chessBoard;
    protected Pattern pattern;
    protected boolean piecesCanBlockMyWay;
    protected List<ChessCell> canGoCells;
    protected Map<int[][], List<ChessCell>> canGoCellsPatternMap;
    protected List<ChessCell> canAttackCells;
    protected List<ChessCell> canReplaceCell;
    protected Piece piece;

    public MoveStyle(ChessBoard chessBoard, Piece piece, Pattern pattern, boolean piecesCanBlockMyWay) {
        this.chessBoard = chessBoard;
        this.pattern = pattern;
        this.piece = piece;
//        this.piecesCanBlockMyWay = piecesCanBlockMyWay;
        this.piecesCanBlockMyWay = true;
        this.canGoCells = new ArrayList<ChessCell>();
        this.canAttackCells = new ArrayList<ChessCell>();
        this.canReplaceCell = new ArrayList<ChessCell>();
        this.canGoCellsPatternMap = new HashMap<int[][], List<ChessCell>>();
    }

    private Map<Piece, List<ChessCell>> moveCandidateCells = new HashMap<Piece, List<ChessCell>>();

    public void update() {
//        if (true) {
        tempUpdate();
//            return;
//        }
//        canGoCells.clear();
//        canReplaceCell.clear();
//        canAttackCells.clear();
//        int row = piece.getPosition().getRow();
//        int col = piece.getPosition().getCol();
//        Pattern canGoPattern = pattern.translate(row, col);
//        for (int i = 0; i < canGoPattern.getPattern().length; i++) {
//            int r = canGoPattern.getPattern()[i][0];
//            int c = canGoPattern.getPattern()[i][1];
//            if (!inRange(r) || !inRange(c)) {
//                continue;
//            }
//            ChessCell cell = chessBoard.getCell(r, c);
//            boolean moveCandidateBegin = false;
//            if (cell.isEmpty()) {
//                canGoCells.add(cell);
//                if (canGoPattern.isExtendable()) {
//                    int dr = r - row;
//                    int dc = c - col;
//                    while (inRange(r + dr) && inRange(c + dc)) {
//                        r += dr;
//                        c += dc;
//                        cell = chessBoard.getCell(r, c);
//                        if (cell.isEmpty()) {
//                            canGoCells.add(cell);
//                        } else if (!cell.getPiece().getColor().equals(piece.getColor())) {
//                            if (!moveCandidateCells.containsKey(cell.getPiece())) {
//                                moveCandidateCells.put(cell.getPiece(), new ArrayList<ChessCell>());
//                            }
//                            moveCandidateCells.get(cell.getPiece()).add(cell);
//                            if (!moveCandidateBegin) {
//                                moveCandidateBegin = true;
//                                canGoCells.add(cell);
//                                canAttackCells.add(cell);
//                            } else {
//                                break;
//                            }
//                        } else if (piecesCanBlockMyWay) {
//                            if (!moveCandidateCells.containsKey(cell.getPiece())) {
//                                moveCandidateCells.put(cell.getPiece(), new ArrayList<ChessCell>());
//                            }
//                            moveCandidateCells.get(cell.getPiece()).add(cell);
//                            if (!moveCandidateBegin) {
//                                moveCandidateBegin = true;
//                                canReplaceCell.add(cell);
//                            } else {
//                                break;
//                            }
//                        }
//
//                    }
//                }
//
//            } else if (!cell.getPiece().getColor().equals(piece.getColor())) {
//                canGoCells.add(cell);
//                canAttackCells.add(cell);
//            } else {
//                canReplaceCell.add(cell);
//            }
//        }

    }

    private void tempUpdate() {
        canGoCells.clear();
        canGoCellsPatternMap.clear();
        canReplaceCell.clear();
        canAttackCells.clear();
        int myRow = piece.getPosition().getRow();
        int myCol = piece.getPosition().getCol();
        Pattern canGoPattern = pattern.translate(myRow, myCol);
        Piece lastPiece = null;
        for (int i = 0; i < canGoPattern.getPattern().length; i++) {
            boolean moveCandidateBegin = false;
            int r = canGoPattern.getPattern()[i][0];
            int c = canGoPattern.getPattern()[i][1];
            if (!inRange(r) || !inRange(c)) {
                continue;
            }
            ChessCell cell;
            int row = myRow, col = myCol;
            int dr = r - row;
            int dc = c - col;
            int[][] pattern = {{dr, dc}};
            canGoCellsPatternMap.put(pattern, new ArrayList<ChessCell>());
            while (inRange(row + dr) && inRange(col + dc)) {
                row += dr;
                col += dc;
                cell = chessBoard.getCell(row, col);
                boolean empty = cell.isEmpty();
                if (!moveCandidateBegin && empty) {
                    canGoCells.add(cell);
                    canGoCellsPatternMap.get(pattern).add(cell);

                } else if (moveCandidateBegin || !cell.getPiece().getColor().equals(piece.getColor())) {
                    if (moveCandidateBegin) {
                        if (lastPiece == null) {
                            System.out.println("A problem occurred!");
                        }
                        moveCandidateCells.get(lastPiece).add(cell);
                    }
                    if (!moveCandidateBegin) {
                        lastPiece = cell.getPiece();
                        moveCandidateCells.put(lastPiece, new ArrayList<ChessCell>());
                        moveCandidateBegin = true;
                        canGoCells.add(cell);
                        canGoCellsPatternMap.get(pattern).add(cell);
                        canAttackCells.add(cell);
                    } else if (!empty) {
                        break;
                    }
                } else if (piecesCanBlockMyWay /*|| moveCandidateBegin*/) {
//                    if(moveCandidateBegin) {
//                        moveCandidateCells.add(cell);
//                    }
//                    if (empty || moveCandidateBegin) {
                    moveCandidateBegin = true;
                    lastPiece = cell.getPiece();
                    moveCandidateCells.put(lastPiece, new ArrayList<ChessCell>());
                    canReplaceCell.add(cell);
//                    } else {
//                        break;
//                    }
                }
                if (!canGoPattern.isExtendable()) {
                    break;
                }
            }

        }
    }


    private boolean inRange(int number) {
        return number < 8 && number >= 0;
    }

    public List<ChessCell> getCanGoCells() {
        return canGoCells;
    }

    public List<ChessCell> getCanAttackCells() {
        return canAttackCells;
    }

    public List<ChessCell> getCanReplaceCell() {
        return canReplaceCell;
    }

    public List<ChessCell> getCanGoCellsByPattern(int[][] pattern) {
        return canGoCellsPatternMap.get(pattern);
    }

    public Map<Piece, List<ChessCell>> getMoveCandidateCells() {
        return moveCandidateCells;
    }

    public int[][] getPattern(ChessCell position) {

        for (Map.Entry<int[][], List<ChessCell>> entry : canGoCellsPatternMap.entrySet()) {
            if (entry.getValue().contains(position)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
