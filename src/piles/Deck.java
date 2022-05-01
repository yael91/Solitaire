package piles;

import objects.Card;
import objects.Rank;
import objects.Suit;

import java.util.Collections;
import java.util.Stack;

public class Deck {
    private Stack<Card> cards = new Stack<>();

    public void reset() {
        this.cards.clear();
        for(Suit suit : Suit.values()) {
            for(Rank rank : Rank.values()) {
                cards.push(Card.getCard(rank, suit));
            }
        }
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public boolean empty() {
        return this.cards.empty();
    }

    public void push(Card card){
        cards.push(card);
    }

    public Card peek(){
        return cards.peek();
    }

    public Card pop() {
        assert !empty();
        return this.cards.pop();
    }
}
