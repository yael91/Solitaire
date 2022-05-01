package views;

import game.Board;
import game.BoardListener;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import objects.Card;
import piles.TablePileManager;


public class TablePileView extends StackPane implements View, BoardListener {

    //   private static final int PADDING = 5;
    private static final int Y_OFFSET = 19;
    private static final String BUTTON_STYLE_NORMAL
            = "-fx-background-color: transparent; -fx-padding: 5,5,5,5;";

    private static final String BUTTON_STYLE_PRESSED
            = "-fx-background-color: transparent; -fx-padding: 6,4,4,6;";

    private static final String BORDER = "-fx-border-color: darkkhaki;"
            + "-fx-border-width: 3;" + "-fx-border-radius: 10.0";

    private static final String TRANS_BORDER = "-fx-border-color: transparent;"
            + "-fx-border-width: 3;" + "-fx-border-radius: 10.0";

    private TablePileManager.TablePileEnum index;

    private Card[] myTablePile;

    public TablePileView(TablePileManager.TablePileEnum index) {
        this.index = index;

        // setPadding(new Insets(PADDING));
        buildLayout();

        Board.getInstance().addListener(this);
    }

    private void buildLayout() {
        int offset = 0;
        myTablePile = Board.getInstance().getTableList(index);
        for (Card card : myTablePile) {
            Button button = new Button();
            button.setGraphic(new ImageView(CardImages.getCardImageUrl(card)));
            button.setTranslateY(Y_OFFSET * offset);
            ++offset;
            button.setStyle(BUTTON_STYLE_NORMAL);

            button.setOnMousePressed(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    ((Button) event.getSource()).setStyle(BUTTON_STYLE_PRESSED);
                }
            });

            button.setOnMouseReleased(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    ((Button) event.getSource()).setStyle(BUTTON_STYLE_NORMAL);

                    // if is the first card and can pop on the suitPile
                    if (myTablePile[myTablePile.length - 1].equals(card) && Board.getInstance().canDrawOnPile(Board.CardDeck.SUIT_PILE,card)) {
                        Board.getInstance().getTablePileMove(Board.CardDeck.SUIT_PILE, index, null).move();

                        // if is the first card and can pop on the tablePile
                    } else if (myTablePile[myTablePile.length - 1].equals(card) && Board.getInstance().canDrawOnPile(Board.CardDeck.TABLE_PILE, card)) {
                        Board.getInstance().getTablePileMove(Board.CardDeck.TABLE_PILE, index, null).move();

                        // if card and all the cards under it is a "set", check if can push all the set to another table pile
                    } else if (cardHasSet(card) && Board.getInstance().canDrawOnPile(Board.CardDeck.TABLE_PILE,card)) {
                        Board.getInstance().getTablePileMove(Board.CardDeck.TABLE_PILE, index, card).move();
                    }
                    // else do nothing / put massage
                }
            });

            getChildren().add(button);
        }
    }


    @Override
    public void gameStateChanged() {
        getChildren().clear();
        if (Board.getInstance().getTableList(this.index).length == 0) {
            setStyle(BORDER);
            final ImageView imageView = new ImageView(CardImages.getBaseUrlString());
            imageView.setVisible(false);
            getChildren().add(imageView);
        } else {
            setStyle(TRANS_BORDER);
            buildLayout();
        }
    }

    private boolean cardHasSet(Card card) {
        // if card is the first card - there is no set
        if (myTablePile[myTablePile.length - 1].equals(card)) {
            return false;
        }
        for (int i = 0; i < myTablePile.length; i++) {
            if (myTablePile[i].equals(card)) {
                for (int j = i + 1; j < myTablePile.length; j++, i++) {
                    if ((myTablePile[i].getSuit().sameColorAs(myTablePile[j].getSuit())) ||
                            (myTablePile[i].getRank().ordinal() != (myTablePile[j].getRank().ordinal() + 1))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(int columnIndex, int rowIndex) {
        RootGridPane.getInstance().add(this, columnIndex, rowIndex);
    }

    @Override
    public void clear() {
        getChildren().clear();
    }
}
