package com.cortesgerardo.chess.service.engine.command;

import com.cortesgerardo.chess.service.engine.Events;
import com.cortesgerardo.chess.service.game.Board;
import com.cortesgerardo.chess.service.game.Player;
import com.cortesgerardo.chess.util.CircularIterator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.cortesgerardo.chess.service.engine.Events.DONE;
import static com.cortesgerardo.chess.service.game.Player.Color.BLACK;
import static com.cortesgerardo.chess.service.game.Player.Color.WHITE;

@Slf4j
public class SetupCommand extends GameCommand {
    @Override
    protected Events execute() {
        board = Board.setUp();
        turn = CircularIterator.of(List.of(Player.of(WHITE, board), Player.of(BLACK, board)));

        log.info("\n"+ board.toUnicode());
        return DONE;
    }
}
