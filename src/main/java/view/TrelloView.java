package view;

import mvvm.command.CommandManager;
import javafx.geometry.Insets;
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


    public TrelloView(Stage primaryStage, TrelloViewModel trelloViewModel) {
        this.trelloViewModel = trelloViewModel;
        Scene scene = new Scene(this);

        //config stage
        primaryStage.setTitle("Trello");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(750);

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
        menuBar = new MenuBar();

        // Menu fichier

        Menu fileMenu = new Menu("Fichier");
        
        var createColumn = new MenuItem("Nouvelle Colonne");
        createColumn.setOnAction(e -> trelloViewModel.commandCreateColumn());
        createColumn.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));

        var quit = new MenuItem("Quitter");
        quit.setOnAction(e -> trelloViewModel.quit());
        quit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));

        fileMenu.getItems().add(createColumn);
        fileMenu.getItems().add(quit);

        // Menu edition

        Menu editionMenu = new Menu("Edition");
        
        var undo = new MenuItem("Annuler");
        undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        undo.textProperty().bind(trelloViewModel.nextUndoableProperty());
        undo.disableProperty().bind(trelloViewModel.hasNoUndoableProperty());
        undo.setOnAction(e -> trelloViewModel.undo());
        
        var redo = new MenuItem("Refaire");
        redo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        redo.textProperty().bind(trelloViewModel.nextRedoableProperty());
        redo.disableProperty().bind(trelloViewModel.hasNoRedoableProperty());
        redo.setOnAction(e -> trelloViewModel.redo());
        
        editionMenu.getItems().add(undo);
        editionMenu.getItems().add(redo);
        
        // Menu bar
        menuBar.getMenus().addAll(fileMenu, editionMenu);
    }

    private void makeComponentsHierarchy() {
        this.getChildren().addAll(menuBar, boardView);
    }

    private void configStylesVBow() {
        BackgroundFill backgroundFill = new BackgroundFill(BACKGROUND_COLOR, null, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        setBackground(background);
        setPrefSize(1000, 750);
        setVgrow(boardView, Priority.ALWAYS);
    }
}