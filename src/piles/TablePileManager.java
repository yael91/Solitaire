package piles;

import objects.Card;
import objects.Iterator;
import piles.ClassicTablePile;
import piles.Deck;
import piles.TablePile;
import piles.TablePileFactory;

import java.util.Stack;

public class TablePileManager {

    public enum TablePileEnum {
        Stack1, Stack2, Stack3, Stack4, Stack5, Stack6, Stack7
    }

    private final TablePile[] tableStacks = new ClassicTablePile[TablePileEnum.values().length];

    public TablePileManager(Deck deck, String tablePileType) {
        TablePileFactory tablePileFactory = new TablePileFactory();
        for (int i = 0; i < tableStacks.length; i++) {
            tableStacks[i] = tablePileFactory.getTablePile(deck, TablePileEnum.Stack1.ordinal() + 1 + i, tablePileType);
        }
    }

    public boolean canPushInOneOfThePiles(Card card) {
        for (TablePile tablePile : tableStacks) {
            if (tablePile.canPush(card)) {
                return true;
            }
        }
        return false;
    }

    public void pushToTableStack(Stack<Card> set) {
        for (TablePile tablePile : tableStacks) {
            if (tablePile.getPopedCard() != null && tablePile.getPopedCard().equals(set.peek())) {
                tablePile.setPopedCard(null);
            } else if (tablePile.canPush(set.peek())) {
                while (!set.empty()) {
                    tablePile.push(set.pop());
                }
                return;
            }
        }
    }


    public Card[] getTableStack(TablePileEnum index) {
        Card[] cards = new Card[tableStacks[index.ordinal()].size()];
        int i = 0;

        for (Iterator iter = this.tableStacks[index.ordinal()].getIterator(); iter.hasNext(); ) {
            cards[i] = (Card) iter.next();
            ++i;
        }
        return cards;
    }

    public Card popFromTableStack(TablePileEnum index) {
        return tableStacks[index.ordinal()].pop();
    }
}
