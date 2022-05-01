package views;

import objects.Suit;
import piles.SuitPileManager;
import piles.TablePileManager;

public class ViewInitializer {
    private View deckView;
    private View discardPileView;
    private View[] suitsStacks;
    private View[] tableStacks;

    public ViewInitializer() {
        this.deckView = null;
        this.discardPileView = null;
        this.suitsStacks = null;
        this.tableStacks = null;
    }

    public void initDeckView(int columnIndex, int rowIndex) {
        this.deckView = new DeckView();
        this.deckView.draw(columnIndex, rowIndex);
    }

    public void initDiscardPileView(int columnIndex, int rowIndex) {
        this.discardPileView = new DiscardPileView();
        this.discardPileView.draw(columnIndex, rowIndex);
    }

    public void initSuitPilesView(int columnIndex, int rowIndex) {
        this.suitsStacks = new SuitPileView[Suit.values().length];
        for (SuitPileManager.SuitPileEnum index : SuitPileManager.SuitPileEnum.values()) {
            this.suitsStacks[index.ordinal()] = new SuitPileView(index);
            this.suitsStacks[index.ordinal()].draw(columnIndex + index.ordinal(), rowIndex);
        }
    }

    public void initTablePilesView(int columnIndex, int rowIndex) {
        this.tableStacks = new TablePileView[TablePileManager.TablePileEnum.values().length];

        for (TablePileManager.TablePileEnum index : TablePileManager.TablePileEnum.values()) {
            this.tableStacks[index.ordinal()] = new TablePileView(index);
            this.tableStacks[index.ordinal()].draw(columnIndex + index.ordinal(), rowIndex);
        }
    }

    public void clearAllViews() {
        if (this.deckView != null) {
            this.deckView.clear();
        }
        if (this.discardPileView != null) {
            this.discardPileView.clear();

        }
        if (this.suitsStacks != null) {
            for (View suitPileView : this.suitsStacks) {
                suitPileView.clear();
            }
        }

        if (this.tableStacks != null) {
            for (View tablePileView : this.tableStacks) {
                tablePileView.clear();
            }
        }

    }
}
