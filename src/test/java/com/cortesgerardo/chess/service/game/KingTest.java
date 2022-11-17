package com.cortesgerardo.chess.service.game;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.cortesgerardo.chess.service.game.Player.Color.BLACK;
import static com.cortesgerardo.chess.service.game.Player.Color.WHITE;
import static org.junit.jupiter.api.Assertions.*;

class KingTest {
    private Board board;
    private Board.Square kingSquare;
    private final King king = King.of(WHITE);

    @BeforeEach
    void setUp() {
        board = Board.empty();
        kingSquare = board.getSquare(3, 3).setPiece(king);
    }

    @Test
    void canMove1Around() {
        // When
        val movesN = king.canMove(board, kingSquare, board.getSquare(4, 3));
        val movesNE = king.canMove(board, kingSquare, board.getSquare(4, 4));
        val movesE = king.canMove(board, kingSquare, board.getSquare(3, 4));
        val movesSE = king.canMove(board, kingSquare, board.getSquare(2, 4));
        val movesS = king.canMove(board, kingSquare, board.getSquare(2, 3));
        val movesSW = king.canMove(board, kingSquare, board.getSquare(2, 2));
        val movesW = king.canMove(board, kingSquare, board.getSquare(3, 2));
        val movesNW = king.canMove(board, kingSquare, board.getSquare(4, 2));

        // Then
        assertTrue(movesN);
        assertTrue(movesNE);
        assertTrue(movesE);
        assertTrue(movesSE);
        assertTrue(movesS);
        assertTrue(movesSW);
        assertTrue(movesW);
        assertTrue(movesNW);
    }

    @Test
    void preventFriendlyFire() {
        // When
        val movesN = king.canMove(board, kingSquare, board.getSquare(4, 3).setPiece(Pawn.of(WHITE)));
        val movesNE = king.canMove(board, kingSquare, board.getSquare(4, 4).setPiece(Pawn.of(WHITE)));
        val movesE = king.canMove(board, kingSquare, board.getSquare(3, 4).setPiece(Pawn.of(WHITE)));
        val movesSE = king.canMove(board, kingSquare, board.getSquare(2, 4).setPiece(Pawn.of(WHITE)));
        val movesS = king.canMove(board, kingSquare, board.getSquare(2, 3).setPiece(Pawn.of(WHITE)));
        val movesSW = king.canMove(board, kingSquare, board.getSquare(2, 2).setPiece(Pawn.of(WHITE)));
        val movesW = king.canMove(board, kingSquare, board.getSquare(3, 2).setPiece(Pawn.of(WHITE)));
        val movesNW = king.canMove(board, kingSquare, board.getSquare(4, 2).setPiece(Pawn.of(WHITE)));

        // Then
        assertFalse(movesN);
        assertFalse(movesNE);
        assertFalse(movesE);
        assertFalse(movesSE);
        assertFalse(movesS);
        assertFalse(movesSW);
        assertFalse(movesW);
        assertFalse(movesNW);
    }

    @Test
    void canNotMoveTowardsCheckmate() {
        // Given
        board.getSquare(5,2).setPiece(Bishop.of(BLACK));
        board.getSquare(2, 2).setPiece(Rook.of(BLACK));
        board.getSquare(4, 1).setPiece(Knight.of(BLACK));

        // When
        val movesN = king.canMove(board, kingSquare, board.getSquare(4, 3));
        val movesNE = king.canMove(board, kingSquare, board.getSquare(4, 4));
        val movesE = king.canMove(board, kingSquare, board.getSquare(3, 4));
        val movesSE = king.canMove(board, kingSquare, board.getSquare(2, 4));
        val movesS = king.canMove(board, kingSquare, board.getSquare(2, 3));
        val movesSW = king.canMove(board, kingSquare, board.getSquare(2, 2));
        val movesW = king.canMove(board, kingSquare, board.getSquare(3, 2));
        val movesNW = king.canMove(board, kingSquare, board.getSquare(4, 2));

        // Then
        assertFalse(movesN); // Bishop can capture
        assertTrue(movesNE); // Safe move
        assertFalse(movesE); // Bishop can capture
        assertFalse(movesSE); // Rook can capture
        assertFalse(movesS); // Rook can capture
        assertFalse(movesSW); // Knight defends Rook
        assertFalse(movesW); // Rook can capture
        assertFalse(movesNW); // Rook can capture
    }
}