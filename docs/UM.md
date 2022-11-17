# User Manual
The API allows you to play a chess game

# How to Play the game
1. First you need to have a server running through an IDE or a `.jar` file as instructed in the [README](../README.md)
2. As instructed in the [API documentation](API.md) there are 2 endpoints to play the game, one for get the game status (`/game`) and other to play a move (`/game/move`).
3. Is recommended to look at the logs of the running server, for a "graphical" representation of the game status.

Playing the Scholar's Mate will look like this:
```shell
# WHITE turn 
# game status
$ curl http://localhost:8080/game
# Move e4
$ curl http://localhost:8080/game/move?move=e4 \
-X PUT

# BLACK turn 
# game status
$ curl http://localhost:8080/game
# Move e5
$ curl http://localhost:8080/game/move?move=e5 \
-X PUT

# WHITE turn 
# game status
$ curl http://localhost:8080/game
# Move Bc4 (targeting f7)
$ curl http://localhost:8080/game/move?move=Bc4 \
-X PUT

# BLACK turn 
# game status
$ curl http://localhost:8080/game
# Move Nc6
$ curl http://localhost:8080/game/move?move=Nc6 \
-X PUT

# WHITE turn 
# game status
$ curl http://localhost:8080/game
# Move Qh5 (adding another attacker to the f7-pawn)
$ curl http://localhost:8080/game/move?move=Qh5 \
-X PUT

# BLACK turn 
# game status
$ curl http://localhost:8080/game
# Move Nf6
$ curl http://localhost:8080/game/move?move=Nf6 \
-X PUT

# WHITE turn 
# game status
$ curl http://localhost:8080/game
# Move Qxf7#
$ curl http://localhost:8080/game/move?move=Qf7 \
-X PUT
```
