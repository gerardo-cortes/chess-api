package com.cortesgerardo.chess.service.parser;

import com.cortesgerardo.chess.service.game.Piece;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.antlr.v4.runtime.tree.TerminalNode;

@Slf4j
public class MoveListener extends AlgebraicNotationBaseListener {
//    private
    @Getter
    private MoveEntry current;

    @Override
    public void enterMove(final AlgebraicNotationParser.MoveContext ctx) {
        current = new MoveEntry();
    }

    @Override
    public void enterPiece(final AlgebraicNotationParser.PieceContext ctx) {
        final String ctxText = ctx.getText();
        log.debug(ctxText);
        val title = switch (ctxText) {
            case "K" -> Piece.Title.KING;
            case "Q" -> Piece.Title.QUEEN;
            case "R" -> Piece.Title.ROOK;
            case "B" -> Piece.Title.BISHOP;
            case "N" -> Piece.Title.KNIGHT;
            default -> Piece.Title.PAWN;
        };
        current.setTitle(title);
    }

    @Override
    public void enterStart(final AlgebraicNotationParser.StartContext ctx) {
        val rank = let(ctx.RANK());
        if (rank != null) {
            current.setStartRank(getRank(rank));
        }

        val file = let(ctx.FILE());
        if (file != null) {
            current.setStartFile(getFile(file));
        }
    }

    @Override
    public void enterEnd(final AlgebraicNotationParser.EndContext ctx) {
        val rank = let(ctx.RANK());
        if (rank != null) {
            current.setEndRank(getRank(rank));
        }

        val file = let(ctx.FILE());
        if (file != null) {
            current.setEndFile(getFile(file));
        }
    }

    private String let(final TerminalNode node) {
        return node != null ? node.getText() : null;
    }

    private Integer getRank(final String input) {
        log.debug(input);
        return switch (input) {
            case "8" -> 7;
            case "7" -> 6;
            case "6" -> 5;
            case "5" -> 4;
            case "4" -> 3;
            case "3" -> 2;
            case "2" -> 1;
            case "1" -> 0;
            default -> null;
        };
    }

    private Integer getFile(final String input) {
        log.debug(input);
        return switch (input) {
            case "h" -> 7;
            case "g" -> 6;
            case "f" -> 5;
            case "e" -> 4;
            case "d" -> 3;
            case "c" -> 2;
            case "b" -> 1;
            case "a" -> 0;
            default -> null;
        };
    }
}

