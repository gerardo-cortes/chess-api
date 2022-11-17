package com.cortesgerardo.chess.service.engine.command;

import com.cortesgerardo.chess.util.CircularIterator;
import com.cortesgerardo.chess.service.engine.Events;
import com.cortesgerardo.chess.service.engine.States;
import com.cortesgerardo.chess.service.game.Board;
import com.cortesgerardo.chess.service.game.Player;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;

import java.util.Map;

public abstract class GameCommand implements Action<States, Events> {
    protected Board board;
    protected CircularIterator<Player> turn;
    protected Player activePlayer;

    @Override
    public void execute(final StateContext<States, Events> context) {
        final StateMachine<States, Events> stateMachine = context.getStateMachine();
        final Map<Object, Object> variables = stateMachine.getExtendedState().getVariables();

        // Get variables from context
        board = (Board) variables.get("board");
        turn = (CircularIterator<Player>) variables.get("turn");
        activePlayer = (Player) variables.get("activePlayer");

        // Execute Command
        final Events result = execute();

        // Set variables with command result
        variables.put("board", board);
        variables.put("turn", turn);
        if (activePlayer != null) {
            variables.put("activePlayer", activePlayer);
        }

        // Triggers transition to next State
        stateMachine.sendEvent(result);
    }

    protected abstract Events execute();
}
