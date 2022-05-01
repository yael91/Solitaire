package views;

import game.Board;
import game.BoardListener;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import objects.Card;


public class DiscardPileView extends HBox implements View, BoardListener {

    private static final int PADDING = 5;
    private static final String BUTTON_STYLE_NORMAL
            = "-fx-background-color: transparent; -fx-padding: 5,5,5,5;";

    private static final String BUTTON_STYLE_PRESSED
            = "-fx-background-color: transparent; -fx-padding: 6,4,4,6;";

    public DiscardPileView() {
        //setPadding(new Insets(PADDING));
        // ImageView imageView = new ImageView(views.CardImages.getBackCardImageUrl());
        // imageView.setVisible(false);
        // getChildren().add(imageView);

        Button button = new Button();
        button.setGraphic(new ImageView(CardImages.getBackCardImageUrl()));
        button.setVisible(false);
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

                // if can pop on the suitPile
                if (Board.getInstance().canDrawOnPile(Board.CardDeck.SUIT_PILE, Board.getInstance().peekDiscardPile())) {
                    Board.getInstance().getDiscardPileMove(Board.CardDeck.SUIT_PILE).move();

                    // if can pop on the tablePile
                } else if (Board.getInstance().canDrawOnPile(Board.CardDeck.TABLE_PILE, Board.getInstance().peekDiscardPile())) {
                    Board.getInstance().getDiscardPileMove(Board.CardDeck.TABLE_PILE).move();
                }
                // else do nothing / put massage
            }
        });

        getChildren().add(button);

        Board.getInstance().addListener(this);
    }

    @Override
    public void gameStateChanged() {
        Card topCard = Board.getInstance().peekDiscardPile();

        if (Board.getInstance().canDraw(Board.CardDeck.DISCARD_PILE) || topCard == null) {
            getChildren().get(0).setVisible(false);
        } else {
            getChildren().get(0).setVisible(true);
            Button button = (Button) this.getChildren().get(0);
            button.setGraphic((new ImageView(CardImages.getCardImageUrl(topCard))));
        }
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
