package com.cortesgerardo.chess.service.game;

import lombok.val;
import org.junit.jupiter.api.Test;

import static com.cortesgerardo.chess.service.game.Player.Color.BLACK;
import static com.cortesgerardo.chess.service.game.Player.Color.WHITE;
import static org.junit.jupiter.api.Assertions.*;

class PawnTest {
    private final Board board = Board.empty();
    private final Pawn whitePawn = Pawn.of(WHITE);
    private final Pawn blackPawn = Pawn.of(BLACK);
    private final Board.Square whitePawnSquare = board.getSquare(1, 3).setPiece(whitePawn);
    private final Board.Square blackPawnSquare = board.getSquare(6, 3).setPiece(blackPawn);

    @Test
    void whiteFirstMove(){
        // When
        val moveDouble = whitePawn.canMove(board, whitePawnSquare, board.getSquare(3, 3));
        val moveSingle = whitePawn.canMove(board, whitePawnSquare, board.getSquare(2, 3));

        // Then
        assertTrue(moveDouble);
        assertTrue(moveSingle);
    }

    @Test
    void blackFirstMove(){
        // When
        val moveDouble = blackPawn.canMove(board, blackPawnSquare, board.getSquare(4, 3));
        val moveSingle = blackPawn.canMove(board, blackPawnSquare, board.getSquare(5, 3));

        // Then
        assertTrue(moveDouble);
        assertTrue(moveSingle);
    }

    @Test
    void whiteMovedAfterFirst(){
        // Given
        whitePawn.moved();

        // When
        val moveDouble = whitePawn.canMove(board, whitePawnSquare, board.getSquare(3, 3));
        val moveSingle = whitePawn.canMove(board, whitePawnSquare, board.getSquare(2, 3));

        // Then
        assertFalse(moveDouble);
        assertTrue(moveSingle);
    }

    @Test
    void blackMovedAfterFirst(){
        // Given
        blackPawn.moved();

        // When
        val moveDouble = blackPawn.canMove(board, blackPawnSquare, board.getSquare(4, 3));
        val moveSingle = blackPawn.canMove(board, blackPawnSquare, board.getSquare(5, 3));

        // Then
        assertFalse(moveDouble);
        assertTrue(moveSingle);
    }

    @Test
    void whiteCanCapture() {
        // Given
        board.getSquare(2,2).setPiece(Pawn.of(BLACK));
        board.getSquare(2,4).setPiece(Pawn.of(BLACK));

        // When
        val capture1 = whitePawn.canMove(board, whitePawnSquare, board.getSquare(2, 2));
        val capture2 = whitePawn.canMove(board, whitePawnSquare, board.getSquare(2, 4));

        // Then
        assertTrue(capture1);
        assertTrue(capture2);
    }

    @Test
    void blackCanCapture() {
        // Given
        board.getSquare(5,2).setPiece(Pawn.of(WHITE));
        board.getSquare(5,4).setPiece(Pawn.of(WHITE));

        // When
        val capture1 = blackPawn.canMove(board, blackPawnSquare, board.getSquare(5, 2));
        val capture2 = blackPawn.canMove(board, blackPawnSquare, board.getSquare(5, 4));

        // Then
        assertTrue(capture1);
        assertTrue(capture2);
    }

}