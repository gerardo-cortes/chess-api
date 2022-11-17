package com.cortesgerardo.chess.service.engine;

import com.cortesgerardo.chess.service.engine.action.MoveAction;
import com.cortesgerardo.chess.service.engine.command.BeginningOfTurnCommand;
import com.cortesgerardo.chess.service.engine.command.EndOfTurnPhaseCommand;
import com.cortesgerardo.chess.service.engine.command.ResolvePhaseCommand;
import com.cortesgerardo.chess.service.engine.command.SetupCommand;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;

import static com.cortesgerardo.chess.service.engine.Events.*;
import static com.cortesgerardo.chess.service.engine.States.*;

@SpringBootTest
class StateMachineTest {

    @Autowired
    private StateMachine<States, Events> stateMachine;

    // Mocks will prevent triggering transitions
    @MockBean
    private SetupCommand mockSetUpCommand;
    @MockBean
    private BeginningOfTurnCommand mockBeginningOfTurnCommand;
    @MockBean
    private ResolvePhaseCommand mockResolvePhaseCommand;
    @MockBean
    private EndOfTurnPhaseCommand mockEndOfTurnPhaseCommand;
    @MockBean
    private MoveAction mockMoveAction;

    @Test
    @SneakyThrows
    public void testGameFlow() {
        // Given
        final StateMachineTestPlan<States, Events> plan =
                StateMachineTestPlanBuilder.<States, Events>builder()
                        .stateMachine(stateMachine)
                        // Step 0
                        .step() // [*] --> (SetUp)
                        .expectState(SetUp)

                        // Step 1
                        .and().step() // (SetUp) -Done-> (PlayGame: [*] --> (BeginningOfTurn))
                        .sendEvent(DONE)
                        .expectStates(PlayGame, BeginningOfTurn)

                        // Step 2
                        .and().step() // (PlayGame: (BeginningOfTurn) -Cycle-> (PlayPhase))
                        .sendEvent(CYCLE)
                        .expectStates(PlayGame, PlayPhase)

                        // Step 3
                        .and().step() // (PlayGame: (PlayPhase) -Play-> (ResolvePhase))
                        .sendEvent(PLAY)
                        .expectStates(PlayGame, ResolvePhase)

                        // Step 4
                        .and().step() // (PlayGame: (PlayPhase) -Play-> (ResolvePhase))
                        .sendEvent(PLAY)
                        .expectStates(PlayGame, ResolvePhase)

                        // Step 4
                        .and().step() // (PlayGame: (ResolvePhase) -Cycle-> (EndOfTurnPhase))
                        .sendEvent(CYCLE)
                        .expectStates(PlayGame, EndOfTurnPhase)

                        // Step 5
                        .and().step() // (PlayGame: (EndOfTurnPhase) -Cycle-> (BeginningOfTurn))
                        .sendEvent(CYCLE)
                        .expectStates(PlayGame, BeginningOfTurn)

                        // Step 6
                        .and().step() // (PlayGame: (BeginningOfTurn) -Cycle-> (PlayPhase))
                        .sendEvent(CYCLE)
                        .expectStates(PlayGame, PlayPhase)

                        // Step 7
                        .and().step() // (PlayGame: (PlayPhase) -Play-> (ResolvePhase))
                        .sendEvent(PLAY)
                        .expectStates(PlayGame, ResolvePhase)

                        // Step 8
                        .and().step() // (PlayGame: (ResolvePhase) -Cycle-> (EndOfTurnPhase))
                        .sendEvent(CYCLE)
                        .expectStates(PlayGame, EndOfTurnPhase)

                        // Step 9
                        .and().step() // (PlayGame: (EndOfTurnPhase) -Done-> [*]) -Done-> (GameOver)
                        .sendEvent(DONE)
                        .expectState(GameOver)

                        .and().build();
        // When Then
        plan.test();
    }
}