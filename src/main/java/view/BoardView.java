package view;

import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import model.Column;
import mvvm.BoardViewModel;
import mvvm.TrelloViewModel;
import view.common.EditableLabel;


public class BoardView extends VBox {
    private final EditableLabel elTitle = new EditableLabel();
    private final ListView<Column> lvColumns = new ListView<>();
    private final BoardViewModel boardViewModel;


    public BoardView(BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
        buildView();
    }

    public void buildView() {
        configColumnFactory();
        configGraphicComponents();
        configBindings();
        configEventsHandling();
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

    public void configGraphicComponents() {
        setStyles();
        configComponentsHierarchy();
    }

    private void setStyles() {
        elTitle.setTextFieldId("el-title");
        lvColumns.getStyleClass().add("lv-columns");
    }

    private void configComponentsHierarchy() {
        getChildren().addAll(elTitle, lvColumns);
        VBox.setVgrow(lvColumns, Priority.ALWAYS);
    }

    private void configBindings() {
        elTitle.textProperty().bindBidirectional(boardViewModel.boardTitleProperty());
        lvColumns.itemsProperty().bindBidirectional(boardViewModel.columnsListProperty());

        // binding pour obtenir la colonne sélectionnée dans menu principal
        TrelloViewModel.getInstance().bindSelectedColumn(lvColumns.getSelectionModel().selectedItemProperty());
    }

    private void configEventsHandling() {
        // Title text changed
        elTitle.addEventHandler(EditableLabel.TEXT_CHANGED, e -> {
            boardViewModel.setTitle(elTitle.getText());
        });

        lvColumns.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                boardViewModel.addColumn();
            }
        });
    }
}