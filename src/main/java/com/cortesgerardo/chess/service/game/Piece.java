package com.cortesgerardo.chess.service.game;

import com.cortesgerardo.chess.util.Pair;
import lombok.Getter;
import lombok.val;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

import static com.cortesgerardo.chess.util.TraverseHelper.*;
import static com.cortesgerardo.chess.util.TraverseHelper.downwardsBackwards;

public abstract class Piece {
    @Getter
    private final Player.Color color;
    @Getter
    private final Title title;

    protected Piece(final Player.Color color, final Title title) {
        this.color = color;
        this.title = title;
    }

    public boolean canMove(final Board board, final Board.Square start, final Board.Square end) {
        return !isFriendlyFire(end) &&
                canDefend(board, start, end);
    }

    protected abstract boolean canDefend(final Board board, final Board.Square start, final Board.Square end);

    /**
     * let you know if you are attacking your own pieces
     */
    protected boolean isFriendlyFire(final Board.Square end) {
        return !end.isEmpty() && end.getPiece().getColor() == this.getColor();
    }

    /**
     * check for horizontal movement -
     */
    protected static boolean movesHorizontally(final Board.Square start, final Board.Square end) {
        return (start.getRank() - end.getRank()) == 0;
    }

    /**
     * check if there is no other piece in the path
     *
     * @param board
     * @param start
     * @param end
     * @return {@code true} if there is another {@code Piece} in the path
     */
    protected static boolean isBlockedHorizontally(final Board board, final Board.Square start, final Board.Square end) {
        // Define direction
        val movesTowardsRight = start.getFile() < end.getFile();
        final IntPredicate hasBlock = file -> !board.getSquare(start.getRank(), file).isEmpty();

        return movesTowardsRight ?
                // check block towards right
                asc(start.getFile() + 1, end.getFile())
                        .anyMatch(hasBlock) :
                // check block towards left
                desc(start.getFile(), end.getFile())
                        .anyMatch(hasBlock);
    }

    /**
     * check for vertical movement |
     */
    protected static boolean movesVertically(final Board.Square start, final Board.Square end) {
        return (start.getFile() - end.getFile()) == 0;
    }

    /**
     * @param board
     * @param start
     * @param end
     * @return {@code true} if there is another {@code Piece} in the path
     */
    protected static boolean isBlockedVertically(final Board board, final Board.Square start, final Board.Square end) {
        // Define direction
        val movesTowardsTop = start.getRank() < end.getRank();
        final IntPredicate hasBlock = rank -> !board.getSquare(rank, start.getFile()).isEmpty();

        return movesTowardsTop ?
                // check block towards top
                asc(start.getRank() + 1, end.getRank())
                        .anyMatch(hasBlock) :
                // check block towards bottom
                desc(start.getRank(), end.getRank())
                        .anyMatch(hasBlock);
    }

    /**
     * from left to right check for upward diagonal movement /
     */
    protected static boolean movesDiagonallyUpwards(final Board.Square start, final Board.Square end) {
        val startVector = start.getRank() - start.getFile();
        val endVector = end.getRank() - end.getFile();
        return startVector == endVector;
    }

    /**
     * @param board
     * @param start
     * @param end
     * @return
     */
    protected static boolean isBlockedDiagonallyDownwards(final Board board, final Board.Square start, final Board.Square end) {
        val moveForward = start.getFile() < end.getFile();
        final Predicate<Pair> hasBlock = pair -> !board.getSquare(pair.first(), pair.second()).isEmpty();
        return moveForward ?
                downwardsForward(Pair.of(start.getRank(), start.getFile()), Pair.of(end.getRank(), end.getFile()))
                        .anyMatch(hasBlock) :
                downwardsBackwards(Pair.of(start.getRank(), start.getFile()), Pair.of(end.getRank(), end.getFile()))
                        .anyMatch(hasBlock);
    }

    /**
     * from left to right check for downward diagonal movement \
     */
    protected static boolean movesDiagonallyDownwards(final Board.Square start, final Board.Square end) {
        val startVector = start.getRank() + start.getFile();
        val endVector = end.getRank() + end.getFile();
        return startVector == endVector;
    }

    /**
     * @param board
     * @param start
     * @param end
     * @return
     */
    protected static boolean isBlockedDiagonallyUpwards(final Board board, final Board.Square start, final Board.Square end) {
        // Define direction
        val moveForward = start.getRank() < end.getRank();
        final Predicate<Pair> hasBlock = pair -> !board.getSquare(pair.first(), pair.second()).isEmpty();

        return moveForward ?
                upwardsForward(Pair.of(start.getRank(), start.getFile()), Pair.of(end.getRank(), end.getFile()))
                        .anyMatch(hasBlock) :
                upwardsBackwards(Pair.of(start.getRank(), start.getFile()), Pair.of(end.getRank(), end.getFile()))
                        .anyMatch(hasBlock);
    }

    @Override
    public String toString() {
        return title.toString();
    }

    public String toUnicode() {
        return String.valueOf(title.unicode(color));
    }

    public enum Title {
        KING('K', '♔', '♚'),
        QUEEN('Q', '♕', '♛'),
        ROOK('R', '♖', '♜'),
        BISHOP('B', '♗', '♝'),
        KNIGHT('N', '♘', '♞'),
        PAWN('P', '♙', '♟');

        private final char symbol;
        private final char black;
        private final char white;

        Title(final char symbol, final char black, final char white) {
            this.symbol = symbol;
            this.black = black;
            this.white = white;
        }

        public char unicode(Player.Color color) {
            return switch (color) {
                case WHITE -> this.white;
                case BLACK -> this.black;
            };
        }

        @Override
        public String toString() {
            return "" + symbol;
        }
    }
}
