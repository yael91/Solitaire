package moves;

import game.Board;
import objects.Card;
import objects.Location;
import piles.TablePileManager;

public class TablePileMove implements Move {

    Board board;
    Location location;
    TablePileManager.TablePileEnum fromIndex;
    Card tillCard;

    public TablePileMove(Board board, Location location, TablePileManager.TablePileEnum index, Card tillCard){
         this.board = board;
         this.location = location;
         this.fromIndex = index;
         this.tillCard = tillCard;
    }

    @Override
    public void move() {
        if (location.equals(Board.CardDeck.SUIT_PILE)) {
            board.drawInSuit(Board.CardDeck.TABLE_PILE, fromIndex.ordinal());
        } else if (location.equals(Board.CardDeck.TABLE_PILE)) {
            board.drawInTable(Board.CardDeck.TABLE_PILE, fromIndex.ordinal(), tillCard);
        }
    }
}
