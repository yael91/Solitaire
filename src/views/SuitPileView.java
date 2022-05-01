package views;

import game.Board;
import game.BoardListener;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import objects.Card;
import piles.SuitPileManager;

import java.util.Stack;


public class SuitPileView extends StackPane implements View, BoardListener {

    private static final int PADDING = 5;
    private static final String BORDER = "-fx-border-color: lightgray;"
            + "-fx-border-width: 3;" + "-fx-border-radius: 10.0";
    private SuitPileManager.SuitPileEnum index;


    public SuitPileView(SuitPileManager.SuitPileEnum index) {
        this.index = index;

        setPadding(new Insets(PADDING));
        setStyle(BORDER);
        final ImageView imageView = new ImageView(CardImages.getBaseUrlString());
        imageView.setVisible(false);
        getChildren().add(imageView);
        Board.getInstance().addListener(this);
    }

    @Override
    public void gameStateChanged() {
        /*
        if(game.Board.getInstance().isFullHouse()){
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(this.getClass().getResource("images/yay.gif").toExternalForm()));
        }
        */
        Stack<Card> mySuitPile = Board.getInstance().getSuitStack(this.index);
        if (mySuitPile.empty()) {
            getChildren().get(0).setVisible(false);
        } else {
            getChildren().get(0).setVisible(true);
            Card topCard = mySuitPile.peek();
            ImageView imageView = (ImageView) this.getChildren().get(0);
            imageView.setImage(new Image(CardImages.getCardImageUrl(topCard)));
        }
    }

    @Override
    public void draw(int columnIndex, int rowIndex) {
        RootGridPane.getInstance().add(this, columnIndex, rowIndex);
    }

    @Override
    public void clear() {
        getChildren().clear();
        setStyle("-fx-background-color: transparent;");
    }
}
