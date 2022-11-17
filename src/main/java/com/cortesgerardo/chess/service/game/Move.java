package com.cortesgerardo.chess.service.game;

import com.cortesgerardo.chess.service.game.Board;

public record Move(Board.Square start, Board.Square end) {
}
