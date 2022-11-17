# Software Architecture Document
## Introduction
### Purpose
The purpose of this project is to showcase the coding skills in Java through a turn-based game.

## Technical Requirements
Some Technical Requirements listed in the document: 
- Made in Java
- It should be a web application
- Use of a familiar Framework

### Functional Requirements
The Functional Requirements listed int the document are:
- It should enable 2 players to play the game
- Follow basic rules regarding figure movements
- game-end determination
- Allow feature different  example scenarios:
  - figure movement validation.
  - game-end determination.

### Non-Functional Requirements
Some Non-Functional Requirements are:
- Readability and comprehensibility of the code (Clean code)
- Testing your solution (Unit testing)
- Conscious design/technical decisions

## Layers
## Presentation layer
The minimal requirements to have a functional interface are:
- ability to present the game status to the user.
- ability to read the user move choice.

For these reasons the minimal interaction can be obtained with two REST endpoints in a first iteration.

> Note: For the next iteration is advice to switch to use Hypermedia as the engine of application state (HATEOAS) as it represent in a better way the manipulation of a State Machine ([Designing a true REST State Machine](https://nordicapis.com/designing-a-true-rest-state-machine/)).

## Logic layer
Chess game is a turn-based game, for this reason the best way to encapsulate the business logic and test each situation that the game can have is using a _Finite State Machine_.

### The State Machine
The State Machine (SM) is designed for a turn-based game, almost each state execute an Action that work as a Command for the SM context


[![](https://mermaid.ink/img/pako:eNptkT1vwyAQhv_K6cYqXjoydGhSdaoSNd1Ch5O5OFYNRBgsWZH_e4HUpmnDwul9n_vguGBtFaPA3pPnTUuNI10Nj9IAHB4-oaqeYM8-nJOQgyztOhpfSTMI2FjDycwFFuOSpOuZyzxz0xrTmmZ7_AjOQCH-OnOH3Yl6FrAe644LvTiZe-fedgP_oG92iGRhf7sZfzHq2uS2dsm4Be4N_m-gOynxzWUzAFO6lp0lIAXbgV2hZmXOxxVqdppaFX8nr1OiP7FmiSKGityXRGmmyFHwdj-aGoV3gVcYzqp8JoojdT1P33Stmic?type=png)](https://mermaid-js.github.io/mermaid-live-editor/edit#pako:eNptkT1vwyAQhv_K6cYqXjoydGhSdaoSNd1Ch5O5OFYNRBgsWZH_e4HUpmnDwul9n_vguGBtFaPA3pPnTUuNI10Nj9IAHB4-oaqeYM8-nJOQgyztOhpfSTMI2FjDycwFFuOSpOuZyzxz0xrTmmZ7_AjOQCH-OnOH3Yl6FrAe644LvTiZe-fedgP_oG92iGRhf7sZfzHq2uS2dsm4Be4N_m-gOynxzWUzAFO6lp0lIAXbgV2hZmXOxxVqdppaFX8nr1OiP7FmiSKGityXRGmmyFHwdj-aGoV3gVcYzqp8JoojdT1P33Stmic)

Each state modify the context in the next way:
- **SetUp**: Initialize the context, and "prepares the board" for the game.
- **BeginningOfTurn**: Cycle between players to assign their turn.
- **PlayPhase**: Waits until the player select a valid move.
- **ResolvePhase**: Get the outcome of the move selected by the player.
- **EndOfTurnPhase**: Checks if a player doesn't have any more moves to finish the game.

### The Parser Generator
To be able to read the moves in a simple way, I'm using a simplified version of the [Algebraic notation (chess)](https://en.wikipedia.org/wiki/Algebraic_notation_(chess)) that **only represents movement but not actions** like _capture_, _check_, or _checkmate_.

Each move of a piece is indicated by the piece's uppercase letter, plus the coordinate of the destination square. For example, Be5 (bishop moves to e5), Nf3 (knight moves to f3). For pawn moves, a letter indicating pawn is not used, only the destination square is given. For example, c5 (pawn moves to c5).

The grammar user is the following
```antlrv4
grammar AlgebraicNotation;

// parser rules
move
    : piece start end
    ;

piece
    : TITLE?
    ;

start
    : FILE? RANK?
    ;

end
    : FILE RANK
    ;

// lexer rules
TITLE
    : [KQRBNP]
    ;

RANK
    : [1-8]
    ;

FILE
    : [a-h]
;

```