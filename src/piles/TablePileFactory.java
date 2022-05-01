package piles;

import piles.ClassicTablePile;
import piles.Deck;
import piles.TablePile;

// Factory Pattern
public class TablePileFactory {
    public enum TablePileType {
        CLASSICTABLEPILE, ADVANCEDTABLEPILE
    }

    TablePile getTablePile(Deck deck, int number, String pileType) {
        if (pileType == null) {
            return null;
        }
        if (pileType.equalsIgnoreCase(TablePileType.CLASSICTABLEPILE.toString())) {
            return new ClassicTablePile(deck, number);

        } else if (pileType.equalsIgnoreCase(TablePileType.ADVANCEDTABLEPILE.toString())) {
            //return new AdvancedTablePile();
        }

        return null;
    }
}
