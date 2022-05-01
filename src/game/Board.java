package game;

import moves.DeckMove;
import moves.DiscardPileMove;
import moves.Move;
import moves.TablePileMove;
import objects.Card;
import objects.Location;
import piles.Deck;
import piles.SuitPileManager;
import piles.TablePileFactory;
import piles.TablePileManager;

import java.util.*;

// Singleton
public class Board {
    private static Board ourInstance = new Board();
    private TablePileFactory.TablePileType tablePileType = null;

    private Deck deck = new Deck();
    private Stack<Card> discardPile;
    private List<BoardListener> listenersList = new ArrayList<>();
    private TablePileManager tablePileManager;
    private SuitPileManager suitPileManager;

    public static Board getInstance() {
        return ourInstance;
    }

    private Board() {}

    public void addListener(BoardListener listener) {
        listenersList.add(listener);
    }

    private void notifyAllListeners() {
        for (BoardListener boardListener : listenersList) {
            boardListener.gameStateChanged();
        }
    }


    public void init(TablePileFactory.TablePileType tablePileType){
        this.tablePileType = tablePileType;
    }

    public enum CardDeck implements Location {
        DECK_PILE, DISCARD_PILE, SUIT_PILE, TABLE_PILE
    }
    public Move getDeckMove() {
        return new DeckMove(getInstance());
    }

    public Move getDiscardPileMove(Location cardDeck) {
        return new DiscardPileMove(getInstance(), cardDeck);
    }

    public Move getTablePileMove(Location cardDeck, TablePileManager.TablePileEnum index, Card tillCard) {
        return new TablePileMove(getInstance(), cardDeck, index, tillCard);
    }

    public void start() {
        deck.reset();
        deck.shuffle();
        discardPile = new Stack<Card>();
        tablePileManager = new TablePileManager(deck, this.tablePileType.toString());
        suitPileManager = new SuitPileManager();
        notifyAllListeners();
    }

    public void resetDeck() {
        int size = discardPile.size();
        if (deck.empty()) {
            for (int i = 0; i < size; i++) {
                deck.push(discardPile.pop());
            }
        }
        notifyAllListeners();
    }

    public boolean discard() {
        if (!this.deck.empty()) {
            discardPile.add(this.deck.pop());
            notifyAllListeners();
            return true;
        }

        return false;
    }

    public void drawInSuit(Location from, int index) {
        if (from.equals(CardDeck.DISCARD_PILE)) {
            if (!this.discardPile.empty()) {
                suitPileManager.pushToSuitStack(this.discardPile.pop());
            }
        } else if (from.equals(CardDeck.TABLE_PILE)) {
            if (tablePileManager.getTableStack(TablePileManager.TablePileEnum.values()[index]).length > 0) {
                suitPileManager.pushToSuitStack(tablePileManager.popFromTableStack(TablePileManager.TablePileEnum.values()[index]));
            }
        }
        notifyAllListeners();
    }

    public void drawInTable(Location from, int index, Card tillCard) {
        Stack<Card> set = new Stack<>();
        if (from.equals(CardDeck.DISCARD_PILE)) {
            if (!this.discardPile.empty()) {
                set.push(this.discardPile.pop());
            }
        } else if (from.equals(CardDeck.TABLE_PILE)) {
            if (tablePileManager.getTableStack(TablePileManager.TablePileEnum.values()[index]).length > 0) {
                if (tillCard == null) {
                    set.push(tablePileManager.popFromTableStack(TablePileManager.TablePileEnum.values()[index]));
                } else {
                    boolean flag = false;
                    // get the set
                    for (int i = tablePileManager.getTableStack(TablePileManager.TablePileEnum.values()[index]).length; i > 0; i--) {
                        set.push(tablePileManager.popFromTableStack(TablePileManager.TablePileEnum.values()[index]));
                        if (set.peek().equals(tillCard)) {
                            break;
                        }
                    }

                }
            }
        }
        // push set to the right table stack
        tablePileManager.pushToTableStack(set);
        notifyAllListeners();
    }

    public Card peekDiscardPile() {
        if (discardPile.isEmpty()) {
            return null;
        }
        return discardPile.peek();
    }

    public Card peekDeckPile() {
        if (deck.empty()) {
            return null;
        }
        return deck.peek();
    }

    public boolean canDraw(Location location) {
        // if the location is not deck pile or deck is empty return true
        // and if the location is not discardPile pile or deck is empty return true
        // else return you cant pop - false
        return (!location.equals(CardDeck.DECK_PILE) || deck.empty()) && (!location.equals(CardDeck.DISCARD_PILE) || discardPile.isEmpty());
    }

    public boolean canDrawOnPile(Location pile, Card card) {
        if (pile.equals(CardDeck.SUIT_PILE)) {
            return (suitPileManager.canPushInOneOfThePiles(card));
        } else if (pile.equals(CardDeck.TABLE_PILE)) {
            return (tablePileManager.canPushInOneOfThePiles(card));
        }
        return false;
    }

    public Card[] getTableList(TablePileManager.TablePileEnum index) {
        return tablePileManager.getTableStack(index);
    }

    public Stack<Card> getSuitStack(SuitPileManager.SuitPileEnum index) {
        return suitPileManager.getSuitStack(index);
    }

    public boolean isFullHouse() {
        return suitPileManager.fullHouse();
    }

}