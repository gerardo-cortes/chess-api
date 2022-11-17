package com.cortesgerardo.chess.controller.response;

import com.cortesgerardo.chess.service.game.Board;
import com.cortesgerardo.chess.service.game.Player;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class GameResponse extends Response {
    @Getter
    @Setter
    private Player.Color activePlayer;
    @Getter
    @Setter
    private List<String> white;
    @Getter
    @Setter
    private List<String> black;

    public static GameResponse of(final Player player, final Board board) {
        final Player.Color color = player.getColor();
        final List<String> white = board.stream()
                .filter(square -> !square.isEmpty() && square.getPiece().getColor() == Player.Color.WHITE)
                .map(Board.Square::toString)
                .toList();
        final List<String> black = board.stream()
                .filter(square -> !square.isEmpty() && square.getPiece().getColor() == Player.Color.BLACK)
                .map(Board.Square::toString)
                .toList();
        return new GameResponse(color, white, black);
    }

    public GameResponse(final Player.Color activePlayer, final List<String> white, final List<String> black) {
        super(true);
        this.activePlayer = activePlayer;
        this.white = white;
        this.black = black;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("activePlayer", activePlayer)
                .append("white", white)
                .append("black", black)
                .toString();
    }
}
