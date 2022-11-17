package com.cortesgerardo.chess.service.game;

import lombok.extern.slf4j.Slf4j;

import static com.cortesgerardo.chess.service.game.Piece.Title.ROOK;

@Slf4j
public class Rook extends Piece {

    public static Rook of(final Player.Color color) {
        return new Rook(color);
    }

    private Rook(final Player.Color color) {
        super(color, ROOK);
    }

    @Override
    protected boolean canDefend(final Board board, final Board.Square start, final Board.Square end) {
        return (movesVertically(start, end) && !isBlockedVertically(board, start, end)) ^
                (movesHorizontally(start, end) && !isBlockedHorizontally(board, start, end));
    }

}
