package com.cortesgerardo.chess.service.game;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.cortesgerardo.chess.service.game.Player.Color.BLACK;
import static com.cortesgerardo.chess.service.game.Player.Color.WHITE;
import static org.junit.jupiter.api.Assertions.*;

class QueenTest {
    private Board board;
    private Board.Square queenSquare;
    private final Queen queen = Queen.of(WHITE);


    @BeforeEach
    void setUp() {
        board = Board.empty();
        queenSquare = board.getSquare(3, 3).setPiece(queen);
    }

    @Test
    void canMoveHorizontally() {
        // When
        val movesRight = queen.canMove(board, queenSquare, board.getSquare(3, 7));
        val movesLeft = queen.canMove(board, queenSquare, board.getSquare(3, 0));

        // Then
        assertTrue(movesRight);
        assertTrue(movesLeft);
    }

    @Test
    void canMoveVertically() {
        // When
        val movesTop = queen.canMove(board, queenSquare, board.getSquare(7, 3));
        val movesDown = queen.canMove(board, queenSquare, board.getSquare(0, 3));

        // Then
        assertTrue(movesTop);
        assertTrue(movesDown);
    }

    @Test
    void canMoveDiagonallyUpwards() {
        // When /
        val movesUpwardsForward = queen.canMove(board, queenSquare, board.getSquare(7, 7));
        val movesUpwardsBackwards = queen.canMove(board, queenSquare, board.getSquare(0, 0));

        // Then
        assertTrue(movesUpwardsForward);
        assertTrue(movesUpwardsBackwards);
    }

    @Test
    void canMoveDiagonallyDownwards() {
        // When \
        val movesDownwardForward = queen.canMove(board, queenSquare, board.getSquare(0, 6));
        val movesDownwardBackwards = queen.canMove(board, queenSquare, board.getSquare(6, 0));

        // Then
        assertTrue(movesDownwardForward);
        assertTrue(movesDownwardBackwards);
    }


    @Test
    void preventFriendlyFire() {
        // Given
        val pawnSquare = board.getSquare(7, 7).setPiece(Pawn.of(WHITE));

        // When
        val moveToFriend = queen.canMove(board, queenSquare, pawnSquare);

        // Then
        assertFalse(moveToFriend);
    }


    @Test
    void canCaptureEnemy() {
        // Given
        val blackPawnSquareTop = board.getSquare(7, 3).setPiece(Pawn.of(BLACK));

        val pawnUpperRight = board.getSquare(7, 7).setPiece(Pawn.of(BLACK));

        val blackPawnSquareRight = board.getSquare(3, 7).setPiece(Pawn.of(BLACK));

        val pawnDownwardRight = board.getSquare(0, 6).setPiece(Pawn.of(BLACK));

        val blackPawnSquareDown = board.getSquare(0, 3).setPiece(Pawn.of(BLACK));

        val pawnDownwardLeft = board.getSquare(0, 0).setPiece(Pawn.of(BLACK));

        val blackPawnSquareLeft = board.getSquare(3, 0).setPiece(Pawn.of(BLACK));

        val pawnUpperLeft = board.getSquare(6, 0).setPiece(Pawn.of(BLACK));

        // When
        val moveTowardsTopEnemy = queen.canMove(board, queenSquare, blackPawnSquareTop);
        val moveTowardsDownEnemy = queen.canMove(board, queenSquare, blackPawnSquareDown);
        val moveTowardsRightEnemy = queen.canMove(board, queenSquare, blackPawnSquareRight);
        val moveTowardsLeftEnemy = queen.canMove(board, queenSquare, blackPawnSquareLeft);
        val movesUpperRight = queen.canMove(board, queenSquare, pawnUpperRight);
        val movesDownwardLeft = queen.canMove(board, queenSquare, pawnDownwardLeft);
        val movesDownwardRight = queen.canMove(board, queenSquare, pawnDownwardRight);
        val movesUpperLeft = queen.canMove(board, queenSquare, pawnUpperLeft);

        // Then
        assertTrue(moveTowardsTopEnemy);
        assertTrue(moveTowardsDownEnemy);
        assertTrue(moveTowardsRightEnemy);
        assertTrue(moveTowardsLeftEnemy);
        assertTrue(movesUpperRight);
        assertTrue(movesDownwardLeft);
        assertTrue(movesDownwardRight);
        assertTrue(movesUpperLeft);
    }


    @Test
    void canNotJump() {
        // Given
        val blackPawnSquareTop = board.getSquare(7, 3).setPiece(Pawn.of(BLACK));
        board.getSquare(6, 3).setPiece(Pawn.of(WHITE));

        val pawnUpperRight = board.getSquare(7, 7).setPiece(Pawn.of(BLACK));
        board.getSquare(6, 6).setPiece(Pawn.of(WHITE));

        val blackPawnSquareRight = board.getSquare(3, 7).setPiece(Pawn.of(BLACK));
        board.getSquare(3, 6).setPiece(Pawn.of(WHITE));

        val pawnDownwardRight = board.getSquare(0, 6).setPiece(Pawn.of(BLACK));
        board.getSquare(1, 5).setPiece(Pawn.of(WHITE));

        val blackPawnSquareDown = board.getSquare(0, 3).setPiece(Pawn.of(BLACK));
        board.getSquare(1, 3).setPiece(Pawn.of(WHITE));

        val pawnDownwardLeft = board.getSquare(0, 0).setPiece(Pawn.of(BLACK));
        board.getSquare(1, 1).setPiece(Pawn.of(WHITE));

        val blackPawnSquareLeft = board.getSquare(3, 0).setPiece(Pawn.of(BLACK));
        board.getSquare(3, 1).setPiece(Pawn.of(WHITE));

        val pawnUpperLeft = board.getSquare(6, 0).setPiece(Pawn.of(BLACK));
        board.getSquare(5, 1).setPiece(Pawn.of(WHITE));

        // When
        val moveTowardsTopEnemy = queen.canMove(board, queenSquare, blackPawnSquareTop);
        val moveTowardsDownEnemy = queen.canMove(board, queenSquare, blackPawnSquareDown);
        val moveTowardsRightEnemy = queen.canMove(board, queenSquare, blackPawnSquareRight);
        val moveTowardsLeftEnemy = queen.canMove(board, queenSquare, blackPawnSquareLeft);
        val movesUpperRight = queen.canMove(board, queenSquare, pawnUpperRight);
        val movesDownwardLeft = queen.canMove(board, queenSquare, pawnDownwardLeft);
        val movesDownwardRight = queen.canMove(board, queenSquare, pawnDownwardRight);
        val movesUpperLeft = queen.canMove(board, queenSquare, pawnUpperLeft);

        // Then
        assertFalse(moveTowardsTopEnemy);
        assertFalse(moveTowardsDownEnemy);
        assertFalse(moveTowardsRightEnemy);
        assertFalse(moveTowardsLeftEnemy);
        assertFalse(movesUpperRight);
        assertFalse(movesDownwardLeft);
        assertFalse(movesDownwardRight);
        assertFalse(movesUpperLeft);
    }

}