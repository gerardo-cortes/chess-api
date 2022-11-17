package com.cortesgerardo.chess.service;

import com.cortesgerardo.chess.controller.response.ErrorResponse;
import com.cortesgerardo.chess.controller.response.GameResponse;
import com.cortesgerardo.chess.controller.response.Response;
import com.cortesgerardo.chess.service.engine.Events;
import com.cortesgerardo.chess.service.engine.States;
import com.cortesgerardo.chess.service.game.Board;
import com.cortesgerardo.chess.service.game.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ChessService {
    private final StateMachine<States, Events> stateMachine;

    @Autowired
    public ChessService( final StateMachine<States, Events> stateMachine) {
        this.stateMachine = stateMachine;
    }

    public GameResponse getGameStatus() {
        final Map<Object, Object> variables = stateMachine.getExtendedState().getVariables();
        final Player player = (Player) variables.get("activePlayer");
        final Board board = (Board) variables.get("board");

        return GameResponse.of(player, board);
    }

    public Response setMove(final String move) {
        Message<Events> message = MessageBuilder
                .withPayload(Events.PLAY)
                .setHeader("move", move)
                .build();

        return stateMachine.sendEvent(message) && !hasError() ?
                new Response(true) :
                ErrorResponse.of(getErrorMessage());
    }

    private boolean hasError() {
        final Map<Object, Object> variables = stateMachine.getExtendedState().getVariables();
        final boolean hasError = (boolean) variables.getOrDefault("hasError", false);
        variables.remove("hasError");
        return hasError;
    }

    private String getErrorMessage() {
        final Map<Object, Object> variables = stateMachine.getExtendedState().getVariables();
        final String errorMessage = (String) variables.getOrDefault("errorMessage", "Unknown Exception");
        variables.remove("errorMessage");
        return errorMessage;
    }

}
