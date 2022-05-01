package piles;

import objects.Card;
import objects.Rank;

import java.util.Iterator;
import java.util.Stack;

public class SuitPile extends Stack<Card> implements Iterable<Card> {
    private final Stack<Card> suitStack = new Stack<>();

    public SuitPile() {}

    @Override
    public Iterator<Card> iterator() {
        return suitStack.iterator();
    }

    @Override
    public Card push(Card card) {
        suitStack.push(card);
        return card;
    }

    @Override
    public boolean empty() {
        return this.suitStack.empty();
    }

    @Override
    public int size() {
        return suitStack.size();
    }

    public boolean canPush(Card card) {
        // if it's the first card and the card is Ace
        if (suitStack.empty() && card.getRank().equals(Rank.ACE)) {
            return true;
        }
        // if the top card is one smaller than the card and has the same suit
        return (!suitStack.empty() && (suitStack.peek().getRank().ordinal() + 1) == card.getRank().ordinal()
                && suitStack.peek().getSuit().equals(card.getSuit()));
    }

}
