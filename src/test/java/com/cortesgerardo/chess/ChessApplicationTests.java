package com.cortesgerardo.chess;

import com.cortesgerardo.chess.controller.ChessController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChessApplicationTests {

    @Autowired
    private ChessController chessController;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(chessController);
    }

}
