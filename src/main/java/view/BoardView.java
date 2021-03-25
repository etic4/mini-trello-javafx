package view;

import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import model.Column;
import mvvm.BoardViewModel;
import mvvm.TrelloViewModel;

public class BoardView extends VBox {
    private final EditableLabel elTitle = new EditableLabel();
    private final ListView<Column> lvColumns = new ListView<>();
    private final BoardViewModel boardViewModel;


    public BoardView(BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
        build();
    }

    public void build() {
        buildGraphicComponents();
        configBindings();
        configActions();
    }

    public void buildGraphicComponents() {
        makeComponentsHierarchy();
        configStyles();
        configColumnFactory();
    }

    //  CONFIG GRAPHIC COMPONENTS

    public void makeComponentsHierarchy() {
        getChildren().addAll(elTitle, lvColumns);
    }

    public void configStyles() {
        VBox.setVgrow(lvColumns, Priority.ALWAYS);
        elTitle.setTextFieldId("el-title");
        lvColumns.getStyleClass().add("lv-columns");
    }


    private void configColumnFactory() {
        lvColumns.setCellFactory(columnView -> new ListCell<>(){
            @Override
            protected void updateItem(Column column, boolean empty) {
                super.updateItem(column, empty);
                ColumnView columnView = null;
                if (column != null) {
                    columnView = new ColumnView(column);

                }
                setGraphic(columnView);
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  CONFIG BINDINGS & LISTENERS

    private void configBindings() {
        elTitle.textProperty().bindBidirectional(boardViewModel.boardTitleProperty());
        lvColumns.itemsProperty().bindBidirectional(boardViewModel.columnsListProperty());
        boardViewModel.bindEditAborted(elTitle.editAbortedProperty());
        boardViewModel.bindFfocusedTitle(elTitle.tfFocusedProperty());

        TrelloViewModel.getInstance().bindSelectedColumn(lvColumns.getSelectionModel().selectedItemProperty());
    }

    // CONFIG MOUSE EVENT

    private void configActions() {
        configMouseEvents();
    }

    private void configMouseEvents() {
        lvColumns.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                boardViewModel.addColumn();
            }
        });
    }
}