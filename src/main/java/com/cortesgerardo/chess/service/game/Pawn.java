package com.cortesgerardo.chess.service.game;

import lombok.val;

import static com.cortesgerardo.chess.service.game.Piece.Title.PAWN;
import static com.cortesgerardo.chess.service.game.Player.Color.WHITE;

public class Pawn extends Piece {
    private boolean isFirstMove = true;

    public static Pawn of(final Player.Color color) {
        return new Pawn(color);
    }

    private Pawn(final Player.Color color) {
        super(color, PAWN);
    }

    public void moved() {
        isFirstMove = false;
    }

    @Override
    protected boolean canDefend(final Board board, final Board.Square start, final Board.Square end) {
        return movesForward(start, end) ||
                canCapture(start, end);
    }

    private boolean movesForward(final Board.Square start, final Board.Square end) {
        val isEmpty = end.isEmpty();
        val isSameFile = start.getFile() == end.getFile();
        val isWhiteDirection = isFirstMove ? end.getRank() <= start.getRank() + 2 : end.getRank() == start.getRank() + 1;
        val isBlackDirection = isFirstMove ? end.getRank() >= start.getRank() - 2 : end.getRank() == start.getRank() - 1;
        val movesForward = (this.getColor() == WHITE) ? isWhiteDirection : isBlackDirection;


        return isEmpty && isSameFile && movesForward;
    }

    private boolean canCapture(final Board.Square start, final Board.Square end) {
        val isEnemy = !end.isEmpty() && end.getPiece().getColor() != this.getColor();
        val movesForward = (this.getColor() == WHITE) ? start.getRank() < end.getRank() : start.getRank() > end.getRank();

        return isEnemy &&
                movesForward &&
                (end.getRank() - 1 == start.getRank() || end.getRank() + 1 == start.getRank()) &&
                (end.getFile() - 1 == start.getFile() || end.getFile() + 1 == start.getFile());
    }
}
