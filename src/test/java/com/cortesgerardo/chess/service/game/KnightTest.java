package com.cortesgerardo.chess.service.game;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.cortesgerardo.chess.service.game.Player.Color.WHITE;
import static org.junit.jupiter.api.Assertions.*;

class KnightTest {
    private final Board board = Board.empty();
    private final Knight knight = Knight.of(WHITE);
    private Board.Square knightSquare;

    @BeforeEach
    void setUp() {
        knightSquare = board.getSquare(3, 3).setPiece(knight);
    }

    /**
     * Moves of a knight
     *
     * | | |x| |x| | |
     * |x| | | | | |x|
     * | | | |♘| | | |
     * |x| | | | | |x|
     * | | |x| |x| | |
     */

    @Test
    void canMoveNNE(){
        // When
        val movesNNE = knight.canMove(board, knightSquare, board.getSquare(5, 4));

        // Then
        assertTrue(movesNNE);
    }

    @Test
    void canMoveENE(){
        // When
        val movesENE = knight.canMove(board, knightSquare, board.getSquare(4, 5));

        // Then
        assertTrue(movesENE);
    }

    @Test
    void canMoveESE(){
        // When
        val movesESE = knight.canMove(board, knightSquare, board.getSquare(2, 5));

        // Then
        assertTrue(movesESE);
    }

    @Test
    void canMoveSSE(){
        // When
        val movesSSE = knight.canMove(board, knightSquare, board.getSquare(1, 4));

        // Then
        assertTrue(movesSSE);
    }

    @Test
    void canMoveSSW(){
        // When
        val movesSSW = knight.canMove(board, knightSquare, board.getSquare(1, 2));

        // Then
        assertTrue(movesSSW);
    }

    @Test
    void canMoveWSW(){
        // When
        val movesWSW = knight.canMove(board, knightSquare, board.getSquare(2, 1));

        // Then
        assertTrue(movesWSW);
    }

    @Test
    void canMoveWNW(){
        // When
        val movesWNW = knight.canMove(board, knightSquare, board.getSquare(4, 1));

        // Then
        assertTrue(movesWNW);
    }

    @Test
    void canMoveNNW(){
        // When
        val movesNNW = knight.canMove(board, knightSquare, board.getSquare(5, 2));

        // Then
        assertTrue(movesNNW);
    }

    @Test
    void preventFriendlyFire() {
        // Given
        val pawnSquareNNE = board.getSquare(5, 4).setPiece(Pawn.of(WHITE));
        val pawnSquareENE = board.getSquare(4, 5).setPiece(Pawn.of(WHITE));
        val pawnSquareESE = board.getSquare(2, 5).setPiece(Pawn.of(WHITE));
        val pawnSquareSSE = board.getSquare(1, 4).setPiece(Pawn.of(WHITE));
        val pawnSquareSSW = board.getSquare(1, 2).setPiece(Pawn.of(WHITE));
        val pawnSquareWSW = board.getSquare(2, 1).setPiece(Pawn.of(WHITE));
        val pawnSquareWNW = board.getSquare(4, 1).setPiece(Pawn.of(WHITE));
        val pawnSquareNNW = board.getSquare(5, 2).setPiece(Pawn.of(WHITE));

        // When
        val movesNNE = knight.canMove(board, knightSquare, pawnSquareNNE);
        val movesENE = knight.canMove(board, knightSquare, pawnSquareENE);
        val movesESE = knight.canMove(board, knightSquare, pawnSquareESE);
        val movesSSE = knight.canMove(board, knightSquare, pawnSquareSSE);
        val movesSSW = knight.canMove(board, knightSquare, pawnSquareSSW);
        val movesWSW = knight.canMove(board, knightSquare, pawnSquareWSW);
        val movesWNW = knight.canMove(board, knightSquare, pawnSquareWNW);
        val movesNNW = knight.canMove(board, knightSquare, pawnSquareNNW);

        // Then
        assertFalse(movesNNE);
        assertFalse(movesENE);
        assertFalse(movesESE);
        assertFalse(movesSSE);
        assertFalse(movesSSW);
        assertFalse(movesWSW);
        assertFalse(movesWNW);
        assertFalse(movesNNW);
    }
}