package com.cortesgerardo.chess.controller;

import com.cortesgerardo.chess.controller.response.ErrorResponse;
import com.cortesgerardo.chess.controller.response.GameResponse;
import com.cortesgerardo.chess.controller.response.Response;
import com.cortesgerardo.chess.service.ChessService;
import com.cortesgerardo.chess.service.game.Board;
import com.cortesgerardo.chess.service.game.Player;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.Message;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ChessController.class)
class ChessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChessService service;

    @BeforeEach
    void setUp() {
    }

    @Test
    @SneakyThrows
    void getGame() {
        // Given
        final Board board = Board.setUp();
        final Player player = Player.of(Player.Color.WHITE, board);
        when(service.getGameStatus()).thenReturn(GameResponse.of(player, board));

        // When
        final ResultActions resultActions = mockMvc
                .perform(
                        get("/game")
                );

        //Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.activePlayer", is("WHITE")))
                .andExpect(jsonPath("$.white").isArray())
                .andExpect(jsonPath("$.white[0]", is("Ra1")))
                .andExpect(jsonPath("$.white[1]", is("Nb1")))
                .andExpect(jsonPath("$.white[2]", is("Bc1")))
                .andExpect(jsonPath("$.white[3]", is("Qd1")))
                .andExpect(jsonPath("$.white[4]", is("Ke1")))
                .andExpect(jsonPath("$.white[5]", is("Bf1")))
                .andExpect(jsonPath("$.white[6]", is("Ng1")))
                .andExpect(jsonPath("$.white[7]", is("Rh1")))
                .andExpect(jsonPath("$.white[8]", is("Pa2")))
                .andExpect(jsonPath("$.white[9]", is("Pb2")))
                .andExpect(jsonPath("$.white[10]", is("Pc2")))
                .andExpect(jsonPath("$.white[11]", is("Pd2")))
                .andExpect(jsonPath("$.white[12]", is("Pe2")))
                .andExpect(jsonPath("$.white[13]", is("Pf2")))
                .andExpect(jsonPath("$.white[14]", is("Pg2")))
                .andExpect(jsonPath("$.white[15]", is("Ph2")))
                .andExpect(jsonPath("$.black").isArray())
                .andExpect(jsonPath("$.black[0]", is("Pa7")))
                .andExpect(jsonPath("$.black[1]", is("Pb7")))
                .andExpect(jsonPath("$.black[2]", is("Pc7")))
                .andExpect(jsonPath("$.black[3]", is("Pd7")))
                .andExpect(jsonPath("$.black[4]", is("Pe7")))
                .andExpect(jsonPath("$.black[5]", is("Pf7")))
                .andExpect(jsonPath("$.black[6]", is("Pg7")))
                .andExpect(jsonPath("$.black[7]", is("Ph7")))
                .andExpect(jsonPath("$.black[8]", is("Ra8")))
                .andExpect(jsonPath("$.black[9]", is("Nb8")))
                .andExpect(jsonPath("$.black[10]", is("Bc8")))
                .andExpect(jsonPath("$.black[11]", is("Qd8")))
                .andExpect(jsonPath("$.black[12]", is("Ke8")))
                .andExpect(jsonPath("$.black[13]", is("Bf8")))
                .andExpect(jsonPath("$.black[14]", is("Ng8")))
                .andExpect(jsonPath("$.black[15]", is("Rh8")))
                .andDo(log());
    }

    @Test
    @SneakyThrows
    public void testWhenPutMoveThenReturn202() {
        // Given
        when(service.setMove(any())).thenReturn(new Response(true));

        // When
        mockMvc.perform(
                        put("/game/move")
                                .param("move", "0")
                )

                // Then
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andDo(log());
    }

    @Test
    @SneakyThrows
    public void testWhenPutMoveThenReturn400() {
        // When
        mockMvc.perform(
                        put("/game/move")
                )

                // Then
                .andExpect(status().isBadRequest())
                .andDo(log());
    }

    @Test
    @SneakyThrows
    public void testWhenPutMoveThenReturn422() {
        // Given
        when(service.setMove(any())).thenReturn(ErrorResponse.of("Error Message"));

        // When
        mockMvc.perform(
                        put("/game/move")
                                .param("move", "0")
                )

                // Then
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andDo(log());
    }

}