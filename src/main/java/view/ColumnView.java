package view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import direction.Direction;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import model.Card;
import model.Column;
import mvvm.ColumnViewModel;
import view.common.EditableLabel;
import view.common.MoveButton;


public class ColumnView extends VBox {
    private static final int LIST_CELL_HEIGHT = 116;

    private final HBox hbHeader = new HBox();
    private final MoveButton
            btLeft = new MoveButton(Direction.LEFT),
            btRight = new MoveButton(Direction.RIGHT);

    private final EditableLabel elTitle = new EditableLabel();
    private final ListView<Card> lvCards = new ListView<>();

    private final ColumnViewModel columnViewModel;

    private final IntegerProperty nbrItems = new SimpleIntegerProperty();


    public ColumnView(ColumnViewModel columnViewModel) {
        this.columnViewModel = columnViewModel;
        buildView();
    }


    ColumnView(Column column) {
        this(new ColumnViewModel(column));
    }

    private void buildView() {
        buildGraphicComponents();
        configCardFactory();
        configBindings();
        configEventsHandling();
    }

    private void buildGraphicComponents() {
        // Add css classes
        getStyleClass().add("col-vbox");
        elTitle.addTextFieldClasses("el-column");
        hbHeader.getStyleClass().add("col-header");
        lvCards.getStyleClass().add("lv-cards");

        // makes components hierarchy
        hbHeader.getChildren().addAll(btLeft, elTitle, btRight);
        getChildren().addAll(hbHeader, lvCards);

        // set hgrow priority
        HBox.setHgrow(elTitle, Priority.ALWAYS);

        // bind prefHeight on number of items
        lvCards.prefHeightProperty().bind(Bindings.multiply(nbrItems, LIST_CELL_HEIGHT).add(10));
    }


    private void configCardFactory() {
        lvCards.setCellFactory(columnView -> new ListCell<>(){
            @Override
            protected void updateItem(Card card, boolean empty) {
                super.updateItem(card, empty);

                CardView cardView = null;
                if (card != null) {
                    cardView = new CardView(card);
                }
                setGraphic(cardView);

                // update nbr items pour màj prefHeight
                nbrItems.set(lvCards.getItems().size());
            }
        });
    }


    private void configBindings() {
        // data bindings
        elTitle.textProperty().bindBidirectional(columnViewModel.columnTitleProperty());
        lvCards.itemsProperty().bind(columnViewModel.cardsListProperty());

        //buttons state bindings
        btRight.disableProperty().bind(columnViewModel.btRightDisabledProperty());
        btLeft.disableProperty().bind(columnViewModel.btLeftDisabledProperty());
    }


    private void configEventsHandling() {
        // Title text changed
        elTitle.addEventHandler(EditableLabel.TEXT_CHANGED, e -> {
            columnViewModel.setTitle(elTitle.textProperty().get());
        });

        // actions on buttons
        btRight.setOnAction(e -> columnViewModel.moveColumn(btRight.getDirection()));
        btLeft.setOnAction((e -> columnViewModel.moveColumn(btLeft.getDirection())));

        // mouse double click
        setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {
                columnViewModel.addCard();
                e.consume();
            }
        });

        // context menu
        var contextMenu = new ColumnDeleteContextMenu(columnViewModel, elTitle.textProperty().get());
        hbHeader.setOnContextMenuRequested(e -> {
            contextMenu.show(hbHeader, e.getScreenX(), e.getScreenY());
            e.consume();
        });

        setOnContextMenuRequested(e -> {
            contextMenu.show(lvCards, e.getScreenX(), e.getScreenY());
            e.consume();
        });

    }
}