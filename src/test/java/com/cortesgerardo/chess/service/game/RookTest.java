package com.cortesgerardo.chess.service.game;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.cortesgerardo.chess.service.game.Player.Color.BLACK;
import static com.cortesgerardo.chess.service.game.Player.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RookTest {
    private final Board board = Board.empty();
    private final Rook rook = Rook.of(WHITE);
    private Board.Square rookSquare;

    @BeforeEach
    void setUp() {
        rookSquare = board.getSquare(3, 3).setPiece(rook);
    }

    @Test
    void canMoveHorizontally() {
        // When
        val movesRight = rook.canMove(board, rookSquare, board.getSquare(3, 7));
        val movesLeft = rook.canMove(board, rookSquare, board.getSquare(3, 0));

        // Then
        assertTrue(movesRight);
        assertTrue(movesLeft);
    }

    @Test
    void canMoveVertically() {
        // When
        val movesTop = rook.canMove(board, rookSquare, board.getSquare(7, 3));
        val movesDown = rook.canMove(board, rookSquare, board.getSquare(0, 3));

        // Then
        assertTrue(movesTop);
        assertTrue(movesDown);
    }

    @Test
    void preventFriendlyFire() {
        // Given
        val pawnSquare = board.getSquare(3, 7).setPiece(Pawn.of(WHITE));

        // When
        val moveToFriend = rook.canMove(board, rookSquare, pawnSquare);

        // Then
        assertFalse(moveToFriend);
    }

    @Test
    void canCaptureEnemy() {
        // Given
        val pawnSquare = board.getSquare(3, 7).setPiece(Pawn.of(BLACK));

        // When
        val moveToEnemy = rook.canMove(board, rookSquare, pawnSquare);

        // Then
        assertTrue(moveToEnemy);
    }

    @Test
    void canNotJump() {
        // Given
        val blackPawnSquareTop = board.getSquare(7, 3).setPiece(Pawn.of(BLACK));
        board.getSquare(6, 3).setPiece(Pawn.of(WHITE));

        val blackPawnSquareDown = board.getSquare(0, 3).setPiece(Pawn.of(BLACK));
        board.getSquare(1, 3).setPiece(Pawn.of(WHITE));

        val blackPawnSquareRight = board.getSquare(3, 7).setPiece(Pawn.of(BLACK));
        board.getSquare(3, 6).setPiece(Pawn.of(WHITE));

        val blackPawnSquareLeft = board.getSquare(3, 0).setPiece(Pawn.of(BLACK));
        board.getSquare(3, 1).setPiece(Pawn.of(WHITE));


        // When
        val moveTowardsTopEnemy = rook.canMove(board, rookSquare, blackPawnSquareTop);
        val moveTowardsDownEnemy = rook.canMove(board, rookSquare, blackPawnSquareDown);
        val moveTowardsRightEnemy = rook.canMove(board, rookSquare, blackPawnSquareRight);
        val moveTowardsLeftEnemy = rook.canMove(board, rookSquare, blackPawnSquareLeft);

        // Then
        assertFalse(moveTowardsTopEnemy);
        assertFalse(moveTowardsDownEnemy);
        assertFalse(moveTowardsRightEnemy);
        assertFalse(moveTowardsLeftEnemy);
    }
}