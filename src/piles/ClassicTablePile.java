package piles;

import objects.Card;
import objects.Rank;

import java.util.Iterator;
import java.util.Stack;

public class ClassicTablePile extends TablePile {

    public ClassicTablePile(Deck deck, int number) {
        super(deck, number);
        for (int i = 0; i < number; i++) {
            tableStack.add(deck.pop());
        }
        popedCard = null;
    }
/*
    @Override
    public objects.Iterator<objects.Card> iterator() {
        return tableStack.iterator();
    }
*/

    @Override
    public boolean canPush(Card card) {
        if (tableStack.empty()) {
            // can only put KING
            return card.getRank().equals(Rank.KING);
        }
        // if the top card is one bigger than the card and has the opposite color suit
        return (!card.getSuit().sameColorAs(tableStack.peek().getSuit())) &&
                (card.getRank().ordinal() == (tableStack.peek().getRank().ordinal() - 1));
    }
}

