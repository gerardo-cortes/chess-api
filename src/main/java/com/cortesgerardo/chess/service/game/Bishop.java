package com.cortesgerardo.chess.service.game;

import static com.cortesgerardo.chess.service.game.Piece.Title.BISHOP;

public class Bishop extends Piece {

    public static Bishop of(final Player.Color color) {
        return new Bishop(color);
    }

    private Bishop(final Player.Color color) {
        super(color, BISHOP);
    }

    @Override
    protected boolean canDefend(final Board board, final Board.Square start, final Board.Square end) {
        return (movesDiagonallyDownwards(start, end) && !isBlockedDiagonallyDownwards(board, start, end)) ^
                (movesDiagonallyUpwards(start, end) && !isBlockedDiagonallyUpwards(board, start, end));
    }

}
