package view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import mvvm.TrelloViewModel;
import mvvm.command.CommandManager;

public class TrelloMenuBar extends MenuBar {
    private final TrelloViewModel trelloViewModel;
    private final CommandManager commandManager;


    public TrelloMenuBar(TrelloViewModel trelloViewModel){
        super();
        this.trelloViewModel = trelloViewModel;
        this.commandManager = CommandManager.getInstance();
        buildMenus();
    }

    public void buildMenus(){
        buildFilesMenu();
        buildEditMenu();
    }

    private void buildFilesMenu() {
        Menu fileMenu = new Menu("Fichier");

        // --- new column ---
        var createColumn = new MenuItem("Nouvelle Colonne");
        createColumn.setOnAction(e -> trelloViewModel.commandCreateColumn());
        createColumn.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));

        // --- new card ---
        var createCard = new MenuItem("Nouvelle Carte");
        createCard.setOnAction(e -> trelloViewModel.commandCreateCard());
        createCard.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        createCard.disableProperty().bind(trelloViewModel.noColumnSelectedProperty());

        // --- re-seedData
        var reseed = new MenuItem("Reseed et rafraîchit board");
        reseed.setOnAction((e -> trelloViewModel.seedAndRefresh()));


        // --- quit ---
        var quit = new MenuItem("Quitter");
        quit.setOnAction(e -> trelloViewModel.quit());
        quit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));

        // --- add items to menu ---
        fileMenu.getItems().addAll(createColumn, createCard, reseed, quit);
        getMenus().add(fileMenu);
    }

    private void buildEditMenu() {
        Menu editionMenu = new Menu("Edition");

        // --- undo ---
        var undo = new MenuItem("Annuler");
        undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        undo.textProperty().bind(commandManager.firstUndoableStringProperty());
        undo.disableProperty().bind(commandManager.hasNoUndoableProperty());
        undo.setOnAction(e -> commandManager.undo());

        // --- redo ---
        var redo = new MenuItem("Refaire");
        redo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        redo.textProperty().bind(commandManager.firstRedoableStringProperty());
        redo.disableProperty().bind(commandManager.hasNoRedoableProperty());
        redo.setOnAction(e -> commandManager.redo());

        // --- add items to menu ---
        editionMenu.getItems().addAll(undo, redo);
        getMenus().add(editionMenu);
    }
}
