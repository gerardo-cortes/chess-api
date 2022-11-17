package com.cortesgerardo.chess.service.game;

public class Knight extends Piece {

    public static Knight of(Player.Color color) {
        return new Knight(color);
    }

    protected Knight(final Player.Color color) {
        super(color, Title.KNIGHT);
    }

    @Override
    protected boolean canDefend(final Board board, final Board.Square start, final Board.Square end) {
        return movesLShape(start, end);
    }

    /**
     * @param start
     * @param end
     * @return
     */
    private static boolean movesLShape(final Board.Square start, final Board.Square end) {
        int rank = Math.abs(start.getRank() - end.getRank());
        int file = Math.abs(start.getFile() - end.getFile());
        return rank * file == 2;
    }

}
