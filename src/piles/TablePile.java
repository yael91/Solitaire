package piles;

import objects.Card;
import objects.Container;
import objects.Iterator;

import java.util.Stack;

public abstract class TablePile extends Stack<Card> implements Container {

    protected final Stack<Card> tableStack = new Stack<>();
    protected Card popedCard;

    public TablePile(Deck deck, int number) {
        popedCard = null;
    }

    public void setPopedCard(Card popedCard) {
        this.popedCard = popedCard;
    }

    public Card getPopedCard() {
        return popedCard;
    }

    @Override
    public Card push(Card card) {
        tableStack.push(card);
        return card;
    }

    @Override
    public Card pop() {
        popedCard = tableStack.pop();
        return popedCard;
    }

    @Override
    public int size() {
        return tableStack.size();
    }

    abstract boolean canPush(Card card);

    @Override
    public Iterator getIterator(){
        return new MyIterator();
    }

    private class MyIterator implements Iterator {

        int index;

        @Override
        public boolean hasNext() {
            return index < tableStack.size();
        }

        @Override
        public Object next() {

            if(this.hasNext()){
                return tableStack.get(index++);
            }
            return null;
        }
    }
}
