package com.cortesgerardo.chess.service.engine.command;

import com.cortesgerardo.chess.service.engine.Events;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import static com.cortesgerardo.chess.service.engine.Events.CYCLE;
import static com.cortesgerardo.chess.service.engine.Events.DONE;

@Slf4j
public class EndOfTurnPhaseCommand extends GameCommand {
    @Override
    protected Events execute() {
        val endTheMatch = activePlayer.hasEndTheMatch();
        log.info("Has match ended: " + endTheMatch);
        return endTheMatch ? DONE : CYCLE;
    }
}
