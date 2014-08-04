package com.mtn.player;

import com.mtn.GameState;
import com.mtn.PieceManager;
import com.mtn.object.ChessBoard;
import com.mtn.object.ChessCell;
import com.mtn.piece.King;
import com.mtn.piece.Piece;

import java.util.*;

/**
 * @author Mahdi
 */
public class Player {
    private PieceManager pieceManager;
    private ChessBoard chessBoard;
    private List<Piece> myPieces;
    private List<Piece> enemyPieces;
    private String name;
    private Piece.Color color;

    public Player(PieceManager pieceManager, ChessBoard chessBoard, Piece.Color myColor, Piece.Color enemyColor) {
        this.pieceManager = pieceManager;
        this.chessBoard = chessBoard;
        this.color = myColor;
        this.name = color.name();
        this.myPieces = new ArrayList<Piece>(pieceManager.getPieces(myColor));
        this.enemyPieces = new ArrayList<Piece>(pieceManager.getPieces(enemyColor));

    }

    public Piece.Color getColor() {
        return color;
    }

    public void setColor(Piece.Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void updateEveryPieces() {
        for (Piece piece : enemyPieces) {
            piece.update();
        }

        for (Piece piece : myPieces) {
            piece.update();
        }
    }

    public GameState turn() {
        updateEveryPieces();

        GameState checkState = checkState();

        return checkState;
    }

    public GameState checkState() {
        King myKing = getKing(myPieces);
        GameState state = GameState.CONTINIOUS;
        if (myKing == null) {
            return state;
        }
        List<Piece> checkingPieces = new ArrayList<Piece>();
        Map<ChessCell, List<Piece>> dangerCellsMap = new HashMap<ChessCell, List<Piece>>();
//        List<Piece> shouldCheckingPieces = new ArrayList<Piece>();
//        List<ChessCell> dangerCells = new ArrayList<ChessCell>();
        boolean arrested = false;
//        Set<ChessCell> canGoCells = new HashSet<ChessCell>();
//        canGoCells.addAll(myKing.getCanAttackCells());
//        canGoCells.addAll(myKing.getCanGoCells());
        for (Piece piece : enemyPieces) {
            if (piece.getCanAttackCells().contains(myKing.getPosition())) {
                checkingPieces.add(piece);
                state = GameState.CHECKED;
            }
            for (ChessCell cell : myKing.getCanGoCells()) {
                if (piece.getCanGoCells().contains(cell) || piece.getCanReplaceCell().contains(cell)) {
                    if (!dangerCellsMap.containsKey(cell)) {
                        dangerCellsMap.put(cell, new ArrayList<Piece>());
                    }
                    dangerCellsMap.get(cell).add(piece);
                }
            }
        }

        if (state.equals(GameState.CHECKED)) {
            //detect checkmate state

            for (ChessCell cell : myKing.getCanGoCells()) {
//                if (dangerCellsMap.containsKey(cell)) {
//                    continue;
//                }
                for (Piece enemy : checkingPieces) {
                    List<ChessCell> moveCandidateCells = enemy.getMoveCandidateCells().get(myKing);
                    if (moveCandidateCells != null && moveCandidateCells.contains(cell)) {
                        if (!dangerCellsMap.containsKey(cell)) {
                            dangerCellsMap.put(cell, new ArrayList<Piece>());
                        }
                        dangerCellsMap.get(cell).add(enemy);
                    }
                }
            }

            if (dangerCellsMap.size() < myKing.getCanGoCells().size()) {
                state = GameState.CHECKED;
            } else {//it means king can go nowhere
                if (checkingPieces.size() == 1) {
                    Piece enemy = checkingPieces.get(0);
                    ChessCell myKingPosition = myKing.getPosition();
                    int[][] pattern = enemy.getPattern(myKingPosition);
                    List<ChessCell> enemyRayCell = enemy.getCanGoCellsByPattern(pattern);
                    Set<Piece> myPiecesCanBlockEnemiesWay = new HashSet<Piece>();
                    for (Piece my : myPieces) {
                        if (my instanceof King) {
                            continue;
                        }
                        if (my.getCanAttackCells().contains(enemy.getPosition())) {
                            myPiecesCanBlockEnemiesWay.add(my);
                        } else {
                            for (ChessCell cell : enemyRayCell) {
                                if (my.getCanGoCells().contains(cell)) {
                                    myPiecesCanBlockEnemiesWay.add(my);
                                    break;
                                }
                            }
                        }
                    }
                    if (!myPiecesCanBlockEnemiesWay.isEmpty()) {
                        Set<Piece> toRemove = new HashSet<Piece>();
                        for (Piece en : enemyPieces) {
                            for (Piece my : myPiecesCanBlockEnemiesWay) {
                                if (en.getMoveCandidateCells().containsKey(my)) {
                                    List<ChessCell> moveCandidates = en.getMoveCandidateCells().get(my);
                                    if (moveCandidates.contains(myKingPosition)) {
                                        toRemove.add(my);
                                    }
                                }
                            }
                            myPiecesCanBlockEnemiesWay.removeAll(toRemove);

                        }
                        if (myPiecesCanBlockEnemiesWay.isEmpty()) {
                            state = GameState.CHECKMATED;
                        } else {
                            state = GameState.CHECKED;
                        }
                    } else {
                        state = GameState.CHECKMATED;
                    }
                } else {
                    state = GameState.CHECKMATED;
                }
            }
        }

        return state;
    }

    private King getKing(List<Piece> pieces) {
        for (Piece piece : pieces) {
            if (piece instanceof King) {
                return (King) piece;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return name;
    }
}
