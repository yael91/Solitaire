package game;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import piles.TablePileFactory;
import views.RootGridPane;
import views.ViewInitializer;


public class Solitaire extends Application implements BoardListener {
    // public static game.Board mBoard;
    private static final int WIDTH = 850;
    private static final int HEIGHT = 600;
    private static final String TITLE = "game.Solitaire";
    private Stage mPrimaryStage;

    private ViewInitializer viewInitializer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mPrimaryStage = primaryStage;
        mPrimaryStage.setTitle(TITLE);

        Board board = Board.getInstance();
        board.init(TablePileFactory.TablePileType.CLASSICTABLEPILE);

        // use views.ViewInitializer Facade
        viewInitializer = new ViewInitializer();
        viewInitializer.initDeckView(0, 0);
        viewInitializer.initDiscardPileView(1,0);
        viewInitializer.initSuitPilesView(3,0);
        viewInitializer.initTablePilesView(0, 1);

        RootGridPane.getInstance().setStyle("-fx-background-color: green;");
        RootGridPane.getInstance().setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(final KeyEvent pEvent) {
                System.out.println("key pressed");
                if (pEvent.getCode() == KeyCode.ESCAPE) {
                    mPrimaryStage.close();
                }
                pEvent.consume();
            }

        });

        Board.getInstance().addListener(this);

        mPrimaryStage.setResizable(false); // user could not change the stage
        mPrimaryStage.setScene(new Scene(RootGridPane.getInstance(), WIDTH, HEIGHT));
        mPrimaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void gameStateChanged() {
        if (Board.getInstance().isFullHouse()) {
            viewInitializer.clearAllViews();
            Background background = new Background(
                    new BackgroundImage(
                            new Image(this.getClass().getResource("images/win.gif").toExternalForm()),
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.DEFAULT,
                            new BackgroundSize(WIDTH, HEIGHT, false, false, false, false)));

            RootGridPane.getInstance().setBackground(background);
        }
    }
}
