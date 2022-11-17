package com.cortesgerardo.chess.service.game;

import lombok.Getter;

import java.util.Optional;

import static com.cortesgerardo.chess.service.game.Piece.Title.KING;
import static java.lang.String.format;

public class Player {
    @Getter
    private final Color color;
    private final Board board;
    private Move selectedMove;

    public static Player of(final Color color, final Board board) {
        return new Player(color, board);
    }

    private Player(final Color color, final Board board) {
        this.color = color;
        this.board = board;
    }

    public Move processInstruction(Piece.Title title, Integer startRank, Integer startFile, Integer endRank, Integer endFile) {
        final Board.Square end = board.getSquare(endRank, endFile);
        final Optional<Board.Square> first = this.board
                .stream()
                // find all possible pieces
                .filter(square -> !square.isEmpty() && square.getPiece().getColor() == color && square.getPiece().getTitle() == title)
                // find only pieces able to move
                .filter(square -> square.getPiece().canMove(board, square, end))
                .findFirst(); // FIXME ambiguity error if more than one

        if (first.isEmpty()) {
            throw new RuntimeException(format("Piece %s can't be moved to square %s%s", title, (char) (endFile + 97), (endRank + 1)));
        }
        final Board.Square start = first.get();
        selectedMove = new Move(start, end);
        return selectedMove;
    }

    public void executeMove() {
        selectedMove.end().capturedBy(selectedMove.start());
    }

    public boolean hasEndTheMatch() {
        final boolean didCheckMate = board
                .stream()
                .filter(square ->
                        !square.isEmpty() &&
                                square.getPiece().getColor() == color.getEnemy() &&
                                square.getPiece().getTitle() == KING)
                .anyMatch(kingSquare -> {
                    final King king = (King) kingSquare.getPiece();
                    return king.isCheckMate(board, kingSquare);
                });
        return didCheckMate;
    }

    @Override
    public String toString() {
        return color.toString();
    }

    public enum Color {
        WHITE,
        BLACK;

        @Getter
        private Color enemy;

        static {
            WHITE.enemy = BLACK;
            BLACK.enemy = WHITE;
        }
    }
}
