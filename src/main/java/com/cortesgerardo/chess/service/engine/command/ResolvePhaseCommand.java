package com.cortesgerardo.chess.service.engine.command;

import com.cortesgerardo.chess.service.engine.Events;
import lombok.extern.slf4j.Slf4j;

import static com.cortesgerardo.chess.service.engine.Events.CYCLE;

@Slf4j
public class ResolvePhaseCommand extends GameCommand{
    @Override
    protected Events execute() {
        activePlayer.executeMove();
        log.info("\n" + board.toUnicode());
        return CYCLE;
    }
}
