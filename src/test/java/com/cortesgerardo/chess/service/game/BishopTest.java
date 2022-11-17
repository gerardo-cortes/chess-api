package com.cortesgerardo.chess.service.game;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.cortesgerardo.chess.service.game.Player.Color.BLACK;
import static com.cortesgerardo.chess.service.game.Player.Color.WHITE;
import static org.junit.jupiter.api.Assertions.*;

class BishopTest {
    private final Board board = Board.empty();
    private final Bishop bishop = Bishop.of(WHITE);
    private Board.Square bishopSquare;

    @BeforeEach
    void setUp() {
        bishopSquare = board.getSquare(3, 3).setPiece(bishop);
    }

    @Test
    void canMoveDiagonallyUpwards() {
        // When /
        val movesUpwardsForward = bishop.canMove(board, bishopSquare, board.getSquare(7, 7));
        val movesUpwardsBackwards = bishop.canMove(board, bishopSquare, board.getSquare(0, 0));

        // Then
        assertTrue(movesUpwardsForward);
        assertTrue(movesUpwardsBackwards);
    }

    @Test
    void canMoveDiagonallyDownwards() {
        // When \
        val movesDownwardForward = bishop.canMove(board, bishopSquare, board.getSquare(0, 6));
        val movesDownwardBackwards = bishop.canMove(board, bishopSquare, board.getSquare(6, 0));

        // Then
        assertTrue(movesDownwardForward);
        assertTrue(movesDownwardBackwards);
    }

    @Test
    void preventFriendlyFire() {
        // Given
        val pawnSquare = board.getSquare(7, 7).setPiece(Pawn.of(WHITE));

        // When
        val moveToFriend = bishop.canMove(board, bishopSquare, pawnSquare);

        // Then
        assertFalse(moveToFriend);
    }

    @Test
    void canCaptureEnemy() {
        // Given
        val pawnSquare = board.getSquare(7, 7).setPiece(Pawn.of(BLACK));

        // When
        val moveToEnemy = bishop.canMove(board, bishopSquare, pawnSquare);

        // Then
        assertTrue(moveToEnemy);
    }

    @Test
    void canNotJump() {
        // Given
        val pawnUpperRight = board.getSquare(7, 7).setPiece(Pawn.of(BLACK));
        board.getSquare(6, 6).setPiece(Pawn.of(WHITE));

        val pawnDownwardLeft = board.getSquare(0, 0).setPiece(Pawn.of(BLACK));
        board.getSquare(1, 1).setPiece(Pawn.of(WHITE));

        val pawnDownwardRight = board.getSquare(0, 6).setPiece(Pawn.of(BLACK));
        board.getSquare(1, 5).setPiece(Pawn.of(WHITE));

        val pawnUpperLeft = board.getSquare(6, 0).setPiece(Pawn.of(BLACK));
        board.getSquare(5, 1).setPiece(Pawn.of(WHITE));

        // When
        val movesUpperRight = bishop.canMove(board, bishopSquare, pawnUpperRight);
        val movesDownwardLeft = bishop.canMove(board, bishopSquare, pawnDownwardLeft);
        val movesDownwardRight = bishop.canMove(board, bishopSquare, pawnDownwardRight);
        val movesUpperLeft = bishop.canMove(board, bishopSquare, pawnUpperLeft);

        // Then
        assertFalse(movesUpperRight);
        assertFalse(movesDownwardLeft);
        assertFalse(movesDownwardRight);
        assertFalse(movesUpperLeft);
    }
}