package view;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mvvm.BoardViewModel;
import mvvm.TrelloViewModel;

public class TrelloView extends VBox {

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private final TrelloViewModel trelloViewModel;
    private BoardView boardView;
    private MenuBar menuBar;


    public TrelloView(Stage primaryStage) {
        trelloViewModel = TrelloViewModel.getInstance();
        Scene scene = new Scene(this);

        //config stage
        primaryStage.setTitle("Trello");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(750);

        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

        build();

    }

    //  CONFIG GRAPHIC COMPONENTS

    public void build() {
        initBoard();
        buildMainMenuBar();
        makeComponentsHierarchy();
        configStylesVBow();
    }

    private void initBoard() {
        BoardViewModel boardViewModel = new BoardViewModel(trelloViewModel.getBoardFacade());
        boardView = new BoardView(boardViewModel);
    }

    private void buildMainMenuBar() {
        menuBar = new TrelloMenuBar(trelloViewModel);
    }

    private void makeComponentsHierarchy() {
        this.getChildren().addAll(menuBar, boardView);
    }

    private void configStylesVBow() {
        setId("vb-trello");
        setVgrow(boardView, Priority.ALWAYS);
    }
}