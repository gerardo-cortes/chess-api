package com.cortesgerardo.chess.service.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MoveParserTest {

    @Test
    void parseCompleteNotation() {
        final MoveEntry entry = MoveParser.parse("Ba2g5");
        System.out.println(entry);
    }

    @Test
    void parseWithStartRankNotation() {
        final MoveEntry entry = MoveParser.parse("Q4g5");
        System.out.println(entry);
    }

    @Test
    void parseWithStartFileNotation() {
        final MoveEntry entry = MoveParser.parse("Rag5");
        System.out.println(entry);
    }

    @Test
    void parsePawnSimplestNotation() {
        final MoveEntry entry = MoveParser.parse("g5");
        System.out.println(entry);
    }

    @Test
    void parseInvalidEntry() {
        assertThrows(RuntimeException.class, () -> MoveParser.parse("Ba2g9"));
    }

}