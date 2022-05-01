package moves;

import game.Board;
import objects.Location;

public class DiscardPileMove implements Move {

    Board board;
    Location location;

    public DiscardPileMove(Board board, Location location) {
        this.board = board;
        this.location = location;
    }

    @Override
    public void move() {
        if (location.equals(Board.CardDeck.SUIT_PILE)) {
            board.drawInSuit(Board.CardDeck.DISCARD_PILE, 0);
        } else if (location.equals(Board.CardDeck.TABLE_PILE)) {
            board.drawInTable(Board.CardDeck.DISCARD_PILE, 0, null);
        }
    }

}
