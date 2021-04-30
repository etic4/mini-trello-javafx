package view;

import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import mvvm.BoardViewModel;
import mvvm.TrelloViewModel;


public class TrelloView extends VBox {
    private final TrelloViewModel trelloViewModel;

    public TrelloView(Stage primaryStage) {
        trelloViewModel = TrelloViewModel.getInstance();

        Scene scene = new Scene(this);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

        primaryStage.setScene(scene);
        buildView(primaryStage);
    }


    public void buildView(Stage primaryStage) {
        configStage(primaryStage);
        configComponents();
        addNeedsRefreshListener();
    }

    private void configStage(Stage primaryStage) {
        primaryStage.setTitle("Trello");
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(750);
    }

    private void configComponents() {
        setStyles();
        setComponentsHierarchy();
    }

    private void resetScene() {
        getChildren().clear();
        setComponentsHierarchy();
    }

    private void setStyles() {
        setId("vb-trello");
    }

    private void setComponentsHierarchy() {
        MenuBar menuBar = new TrelloMenuBar(trelloViewModel);
        BoardViewModel boardViewModel = new BoardViewModel(trelloViewModel.getBoardFacade());
        BoardView boardView = new BoardView(boardViewModel);

        getChildren().addAll(menuBar, boardView);

        setVgrow(boardView, Priority.ALWAYS);
    }

    private void addNeedsRefreshListener() {
        trelloViewModel.boardNeedsRefreshProperty().addListener((o, oldVal, newVal) -> {
            resetScene();
            trelloViewModel.boardNeedsRefreshProperty().set(false);
        });
    }
}