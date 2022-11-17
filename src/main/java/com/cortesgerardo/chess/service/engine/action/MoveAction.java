package com.cortesgerardo.chess.service.engine.action;

import com.cortesgerardo.chess.service.engine.Events;
import com.cortesgerardo.chess.service.engine.States;
import com.cortesgerardo.chess.service.game.Move;
import com.cortesgerardo.chess.service.game.Player;
import com.cortesgerardo.chess.service.parser.MoveEntry;
import com.cortesgerardo.chess.service.parser.MoveParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.Map;

@Slf4j
public class MoveAction implements Action<States, Events> {
    @Override
    public void execute(final StateContext<States, Events> context) {
        final String move = context.getMessageHeaders().get("move", String.class);

        // get Context
        final Map<Object, Object> variables = context.getExtendedState().getVariables();
        final Player activePlayer = (Player) variables.get("activePlayer");

        // Validate move
        if (move == null) {
            throw new RuntimeException("No move was inserted");
        }
        final MoveEntry moveEntry = MoveParser.parse(move);
        final Move move1 = activePlayer.processInstruction(
                moveEntry.getTitle(),
                moveEntry.getStartRank(),
                moveEntry.getStartFile(), moveEntry.getEndRank(),
                moveEntry.getEndFile()
        );

        log.info(move1.toString());
        // set Context result
        variables.put("activePlayer", activePlayer);
    }
}
