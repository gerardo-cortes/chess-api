package com.cortesgerardo.chess.service.game;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    @Test
    public void testEmptyBoard() {
        val board = Board.empty();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertTrue(board.getSquare(i, j).isEmpty());
            }
        }
    }

    @Test
    public void testIndexOutOfBoundException() {
        val board = Board.empty();

        assertThrows(IndexOutOfBoundsException.class, () ->
                board.getSquare(-1, 0)
        );

        assertThrows(IndexOutOfBoundsException.class, () ->
                board.getSquare(0, -1)
        );

        assertThrows(IndexOutOfBoundsException.class, () ->
                board.getSquare(8, 0)
        );

        assertThrows(IndexOutOfBoundsException.class, () ->
                board.getSquare(0, 8)
        );
    }

    @Test
    public void testTraverse(){
        val board = Board.empty();
        board.stream();

    }

}