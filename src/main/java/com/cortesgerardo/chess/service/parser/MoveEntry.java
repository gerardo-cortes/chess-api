package com.cortesgerardo.chess.service.parser;

import com.cortesgerardo.chess.service.game.Piece;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public final class MoveEntry {
    @Getter
    @Setter
    private Piece.Title title;
    @Getter
    @Setter
    private Integer startRank;
    @Getter
    @Setter
    private Integer startFile;
    @Getter
    @Setter
    private Integer endRank;
    @Getter
    @Setter
    private Integer endFile;
}
