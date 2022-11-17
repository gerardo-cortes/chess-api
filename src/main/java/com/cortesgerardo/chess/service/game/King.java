package com.cortesgerardo.chess.service.game;

import lombok.val;

import static com.cortesgerardo.chess.service.game.Piece.Title.KING;

public class King extends Piece {

    public static King of(final Player.Color color) {
        return new King(color);
    }

    private King(final Player.Color color) {
        super(color, KING);
    }

    @Override
    public boolean canMove(final Board board, final Board.Square start, final Board.Square end) {
        return !isFriendlyFire(end) &&
                moves1Around(start, end) &&
                !isCheckMate(board, end);
    }

    @Override
    protected boolean canDefend(final Board board, final Board.Square start, final Board.Square end) {
        return moves1Around(start, end);
    }

    private static boolean moves1Around(final Board.Square start, final Board.Square end) {
        val move1Rank = Math.abs(start.getRank() - end.getRank()) <= 1;
        val moves1File = Math.abs(start.getFile() - end.getFile()) <= 1;
        return move1Rank && moves1File;
    }

    public boolean isCheckMate(final Board board, final Board.Square end) {
        return board
                .stream()
                .filter(square ->
                        !square.isEmpty() &&
                                square.getPiece().getColor() == this.getColor().getEnemy()
                )
                .anyMatch(enemy ->
                        enemy.getPiece().canDefend(board, enemy, end)
                );
    }

}
