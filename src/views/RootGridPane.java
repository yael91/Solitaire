package views;

import javafx.scene.layout.GridPane;

public class RootGridPane extends GridPane {
    private static RootGridPane ourInstance = new RootGridPane();

    public static RootGridPane getInstance() {
        return ourInstance;
    }

    private RootGridPane() {
    }
}
