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
        configViewComponents();
    }


    // taille et titre de la fenêtre
    private void configStage(Stage primaryStage) {
        primaryStage.setTitle("Trello");
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(750);
    }


    private void configViewComponents() {
        // set view id
        setId("vb-trello");

        // create main menubar
        MenuBar menuBar = new TrelloMenuBar(trelloViewModel);

        // create boardView & board viewmodel
        BoardViewModel boardViewModel = new BoardViewModel(trelloViewModel.getBoardFacade());
        BoardView boardView = new BoardView(boardViewModel);

        // add components to view
        getChildren().addAll(menuBar, boardView);

        // set boardview vgrow priority
        setVgrow(boardView, Priority.ALWAYS);
    }
}