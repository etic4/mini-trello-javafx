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

    private static final Color BACKGROUND_COLOR = Color.web("#E5E5E5");

    private final TrelloViewModel trelloViewModel;

    private BoardView boardView;

    private MenuBar menuBar;

    private final CommandManager commandManager;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  CONSTRUCTOR

    public TrelloView(Stage primaryStage, TrelloViewModel trelloViewModel) {
        this.trelloViewModel = trelloViewModel;
        this.commandManager = CommandManager.getInstance();
        Scene scene = new Scene(this);
        primaryStage.setTitle("Trello");
        primaryStage.setScene(scene);

        //TODO: refactoriser
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(750);
        build();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

        var quit = new MenuItem("Quitter");
        quit.setOnAction(e -> trelloViewModel.quit());

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
        
        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(editionMenu);
    }

    private void makeComponentsHierarchy() {
        this.getChildren().add(menuBar);
        this.getChildren().add(boardView);
    }

    private void configStylesVBow() {
        CornerRadii corners = new CornerRadii(0);
        BackgroundFill backgroundFill = new BackgroundFill(BACKGROUND_COLOR, corners, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        setBackground(background);
        setPrefSize(1000, 750);
        setVgrow(boardView, Priority.ALWAYS);
    }

}