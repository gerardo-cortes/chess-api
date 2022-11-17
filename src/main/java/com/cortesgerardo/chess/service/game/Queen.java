package com.cortesgerardo.chess.service.game;

import static com.cortesgerardo.chess.service.game.Piece.Title.QUEEN;

public class Queen extends Piece {

    public static Queen of(final Player.Color color) {
        return new Queen(color);
    }

    private Queen(final Player.Color color) {
        super(color, QUEEN);
    }

    @Override
    protected boolean canDefend(final Board board, final Board.Square start, final Board.Square end) {
        return (movesVertically(start, end) && !isBlockedVertically(board, start, end)) ^
                (movesHorizontally(start, end) && !isBlockedHorizontally(board, start, end)) ^
                (movesDiagonallyUpwards(start, end) && !isBlockedDiagonallyUpwards(board, start, end)) ^
                (movesDiagonallyDownwards(start, end) && !isBlockedDiagonallyDownwards(board, start, end));
    }
}
