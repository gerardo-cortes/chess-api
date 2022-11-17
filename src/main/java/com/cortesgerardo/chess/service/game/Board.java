package com.cortesgerardo.chess.service.game;

import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Stream;

import static com.cortesgerardo.chess.service.game.Player.Color.BLACK;
import static com.cortesgerardo.chess.service.game.Player.Color.WHITE;
import static com.cortesgerardo.chess.util.TraverseHelper.asc;
import static java.lang.String.format;

public class Board {
    private static final int LIMIT = 8;
    private final Square[][] board = new Square[LIMIT][LIMIT];

    public static Board setUp() {
        final Board newBoard = empty();
        // White
        newBoard.getSquare(0, 0).setPiece(Rook.of(WHITE));
        newBoard.getSquare(0, 1).setPiece(Knight.of(WHITE));
        newBoard.getSquare(0, 2).setPiece(Bishop.of(WHITE));
        newBoard.getSquare(0, 3).setPiece(Queen.of(WHITE));
        newBoard.getSquare(0, 4).setPiece(King.of(WHITE));
        newBoard.getSquare(0, 5).setPiece(Bishop.of(WHITE));
        newBoard.getSquare(0, 6).setPiece(Knight.of(WHITE));
        newBoard.getSquare(0, 7).setPiece(Rook.of(WHITE));

        newBoard.getSquare(1, 0).setPiece(Pawn.of(WHITE));
        newBoard.getSquare(1, 1).setPiece(Pawn.of(WHITE));
        newBoard.getSquare(1, 2).setPiece(Pawn.of(WHITE));
        newBoard.getSquare(1, 3).setPiece(Pawn.of(WHITE));
        newBoard.getSquare(1, 4).setPiece(Pawn.of(WHITE));
        newBoard.getSquare(1, 5).setPiece(Pawn.of(WHITE));
        newBoard.getSquare(1, 6).setPiece(Pawn.of(WHITE));
        newBoard.getSquare(1, 7).setPiece(Pawn.of(WHITE));

        // White
        newBoard.getSquare(7, 0).setPiece(Rook.of(BLACK));
        newBoard.getSquare(7, 1).setPiece(Knight.of(BLACK));
        newBoard.getSquare(7, 2).setPiece(Bishop.of(BLACK));
        newBoard.getSquare(7, 3).setPiece(Queen.of(BLACK));
        newBoard.getSquare(7, 4).setPiece(King.of(BLACK));
        newBoard.getSquare(7, 5).setPiece(Bishop.of(BLACK));
        newBoard.getSquare(7, 6).setPiece(Knight.of(BLACK));
        newBoard.getSquare(7, 7).setPiece(Rook.of(BLACK));

        newBoard.getSquare(6, 0).setPiece(Pawn.of(BLACK));
        newBoard.getSquare(6, 1).setPiece(Pawn.of(BLACK));
        newBoard.getSquare(6, 2).setPiece(Pawn.of(BLACK));
        newBoard.getSquare(6, 3).setPiece(Pawn.of(BLACK));
        newBoard.getSquare(6, 4).setPiece(Pawn.of(BLACK));
        newBoard.getSquare(6, 5).setPiece(Pawn.of(BLACK));
        newBoard.getSquare(6, 6).setPiece(Pawn.of(BLACK));
        newBoard.getSquare(6, 7).setPiece(Pawn.of(BLACK));

        return newBoard;
    }

    public static Board empty() {
        return new Board();
    }

    private Board() {
        asc(0, LIMIT).forEach(rank ->
                asc(0, LIMIT).forEach(file ->
                        board[rank][file] = new Square(rank, file)
                )
        );
    }

    public Square getSquare(int rank, int file) {
        if (rank < 0 || rank > 7 || file < 0 || file > 7) {
            throw new IndexOutOfBoundsException("Value should be between 0 and 7");
        }

        return board[rank][file];
    }

    public Stream<Square> stream() {
        return Arrays.stream(board).flatMap(Arrays::stream);
    }

    public String toUnicode() {
        StringBuilder aString = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                aString.append("|").append(board[i][j].toUnicode());
            }
            aString.append("|\n");
        }
        return aString.toString();
    }

    public static final class Square {
        @Getter
        private final int rank;
        @Getter
        private final int file;
        @Getter
        private Piece piece;

        private Square(int rank, int file) {
            this.rank = rank;
            this.file = file;
        }

        public Square setPiece(Piece piece) {
            if (!isEmpty()) {
                throw new IllegalStateException(format("Square with piece %s can't add new piece", this.piece));
            }

            this.piece = piece;

            return this;
        }

        public Square capturedBy(Square captor) {
            this.piece = captor.piece;
            captor.piece = null;
            return this;
        }

        public boolean isEmpty() {
            return piece == null;
        }

        @Override
        public String toString() {
            final String title = !isEmpty() ? piece.toString() : "";
            return title + (char) (file + 97) + (rank + 1);
        }

        public String toUnicode() {
            return !isEmpty() ? piece.toUnicode() : " ";
        }
    }
}
