package com.cortesgerardo.chess.controller;

import com.cortesgerardo.chess.controller.response.GameResponse;
import com.cortesgerardo.chess.controller.response.Response;
import com.cortesgerardo.chess.service.ChessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class ChessController {
    private final ChessService service;

    @Autowired
    public ChessController(final ChessService service) {
        this.service = service;
    }

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public HttpEntity<GameResponse> getGame() {
        return ResponseEntity.ok(service.getGameStatus());
    }

    @RequestMapping(value = "/game/move", method = RequestMethod.PUT)
    public HttpEntity<Response> putMove(@RequestParam(value = "move", required = true) String move) {
        log.info("Got request to send event Play move " + move);
        final Response response = service.setMove(move);

        return response.getSuccess() ?
                ResponseEntity.accepted().body(response) :
                ResponseEntity.unprocessableEntity().body(response);
    }

}
