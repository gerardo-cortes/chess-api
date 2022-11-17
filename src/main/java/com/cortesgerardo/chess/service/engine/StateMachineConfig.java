package com.cortesgerardo.chess.service.engine;

import com.cortesgerardo.chess.service.engine.action.MoveAction;
import com.cortesgerardo.chess.service.engine.command.BeginningOfTurnCommand;
import com.cortesgerardo.chess.service.engine.command.EndOfTurnPhaseCommand;
import com.cortesgerardo.chess.service.engine.command.ResolvePhaseCommand;
import com.cortesgerardo.chess.service.engine.command.SetupCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Slf4j
@Configuration
@EnableStateMachine
public class StateMachineConfig  extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(final StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(loggingListener());
    }

    @Override
    public void configure(final StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
                .withStates()
                .initial(States.SetUp)
                .state(States.SetUp, createSetupCommand())
                .state(States.PlayGame)
                .end(States.GameOver)
                .and()

                .withStates()
                .parent(States.PlayGame)
                .initial(States.BeginningOfTurn)
                .state(States.BeginningOfTurn, createBeginningOfTurnCommand())
                .state(States.PlayPhase)
                .state(States.ResolvePhase, createResolvePhaseCommand())
                .state(States.EndOfTurnPhase, createEndOfTurnPhaseCommand())
        ;
    }

    @Override
    public void configure(final StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                .source(States.SetUp).event(Events.DONE).target(States.PlayGame)

                .and().withExternal()
                .source(States.PlayGame).event(Events.DONE).target(States.GameOver)

                .and().withExternal()
                .source(States.BeginningOfTurn).event(Events.CYCLE).target(States.PlayPhase)

                .and().withExternal()
                .source(States.PlayPhase).event(Events.PLAY).target(States.ResolvePhase).action(setPlayAction(), errorAction())

                .and().withExternal()
                .source(States.ResolvePhase).event(Events.CYCLE).target(States.EndOfTurnPhase)

                .and().withExternal()
                .source(States.EndOfTurnPhase).event(Events.CYCLE).target(States.BeginningOfTurn)

                .and().withExternal()
                .source(States.EndOfTurnPhase).event(Events.DONE).target(States.GameOver)
        ;
    }

    public StateMachineListener<States, Events> loggingListener() {
        return new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(final State<States, Events> from, final State<States, Events> to) {
                String source = from != null ? from.getId().toString() : "initial";
                log.info("from " + source + " to " + to.getId());
            }
        };
    }

    @Bean
    public Action<States, Events> errorAction() {
        return context -> {
            final String message = context.getException().getMessage();
            log.warn(message);
            context.getExtendedState().getVariables().put("errorMessage", message);
            context.getExtendedState().getVariables().put("hasError", true);
        };
    }

    @Bean
    public MoveAction setPlayAction() {
        return new MoveAction();
    }

    @Bean
    public SetupCommand createSetupCommand() {
        return new SetupCommand();
    }

    @Bean
    public BeginningOfTurnCommand createBeginningOfTurnCommand() {
        return new BeginningOfTurnCommand();
    }

    @Bean
    public ResolvePhaseCommand createResolvePhaseCommand() {
        return new ResolvePhaseCommand();
    }

    @Bean
    public EndOfTurnPhaseCommand createEndOfTurnPhaseCommand() {
        return new EndOfTurnPhaseCommand();
    }

}
