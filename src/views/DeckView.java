package views;

import game.Board;
import game.BoardListener;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DeckView extends HBox implements View, BoardListener {

    private static final String BUTTON_STYLE_NORMAL
            = "-fx-background-color: transparent; -fx-padding: 5,5,5,5;";

    private static final String BUTTON_STYLE_PRESSED
            = "-fx-background-color: transparent; -fx-padding: 6,4,4,6;";

    private static final int FONT_SIZE = 12;

    public DeckView() {
        Board.getInstance().start();
        Button button = new Button();
        button.setGraphic(new ImageView(CardImages.getBackCardImageUrl()));

        button.setStyle(BUTTON_STYLE_NORMAL);

        button.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                ((Button) event.getSource()).setStyle(BUTTON_STYLE_PRESSED);

                // if the deck is empty - resetDeck
                if (Board.getInstance().canDraw(Board.CardDeck.DECK_PILE)) {
                    Board.getInstance().resetDeck();
                }

            }
        });

        button.setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                ((Button) event.getSource()).setStyle(BUTTON_STYLE_NORMAL);
/*
                if (game.Board.getInstance().canDraw(game.Board.CardDeck.DECK_PILE)) {
                    game.Board.getInstance().resetDeck();
                } else {
                    game.Board.getInstance().getDeckMove().moves();
                }
*/
                if (!Board.getInstance().canDraw(Board.CardDeck.DECK_PILE)) {
                    Board.getInstance().getDeckMove().move();
                }
            }
        });

        getChildren().add(button);
        Board.getInstance().addListener(this);
    }

    @Override
    public void gameStateChanged() {
        // if the deck is empty - start
        if (Board.getInstance().canDraw(Board.CardDeck.DECK_PILE)) {
            ((Button) getChildren().get(0)).setGraphic(createNewBoardImage());
        } else {
            ((Button) getChildren().get(0)).setGraphic(new ImageView(CardImages.getBackCardImageUrl()));
        }
    }

    private Canvas createNewBoardImage() {
        double width = (new ImageView(CardImages.getBackCardImageUrl())).getImage().getWidth();
        double height = (new ImageView(CardImages.getBackCardImageUrl())).getImage().getHeight();
        Canvas canvas = new Canvas(width, height);
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.DARKKHAKI);
        context.setFont(Font.font(Font.getDefault().getName(), FONT_SIZE));
        context.fillText("Start Over", 15, 10);
        context.setStroke(Color.DARKGREEN);
        context.setLineWidth(10);
        context.strokeOval(width / 4, height / 2 - width / 4 + FONT_SIZE, width / 2, width / 2);

        return canvas;
    }

    @Override
    public void draw(int columnIndex, int rowIndex) {
        RootGridPane.getInstance().add(this, columnIndex, rowIndex);
    }

    @Override
    public void clear(){
        this.getChildren().clear();
    }
}
