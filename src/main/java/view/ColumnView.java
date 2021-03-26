package view;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.ObservableArray;
import javafx.geometry.Pos;
import direction.Direction;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Card;
import model.Column;
import mvvm.ColumnViewModel;

import java.util.Optional;
import java.util.stream.IntStream;

public class ColumnView extends VBox {
    private static final int LIST_CELL_HEIGHT = 116;

    private final HBox hbHeader = new HBox();
    private final Button
            btLeft = new Button(),
            btRight = new Button();

    private final EditableLabel elTitle = new EditableLabel("el-column");
    private final ListView<Card> lvCards = new ListView<>();
    private final ColumnViewModel columnViewModel;

    private final IntegerProperty nbrItems = new SimpleIntegerProperty();
    private final ObjectProperty<Direction> direction = new SimpleObjectProperty<>();


    //  CONSTRUCTORS

    public ColumnView(ColumnViewModel columnViewModel) {
        this.columnViewModel = columnViewModel;
//        lvCards.setPrefHeight(1);
        buildGraphicComponents();
        configBindings();
        configActions();

    }

    ColumnView(Column column) {
        this(new ColumnViewModel(column));
    }

    //  CONFIG GRAPHIC COMPONENTS

    private void buildGraphicComponents() {
        makeComponentsHierarchy();
        configStyles();
        configCardFactory();
    }

    private void makeComponentsHierarchy() {
        hbHeader.getChildren().addAll(btLeft, elTitle, btRight);
        getChildren().addAll(hbHeader, lvCards);
    }

    private void configStyles() {
        getStyleClass().add("col-vbox");
        hbHeader.getStyleClass().add("col-header");
        lvCards.getStyleClass().add("lv-cards");
        HBox.setHgrow(elTitle, Priority.ALWAYS);

//        lvCards.setPrefHeight(lvCards.getItems().size() * LIST_CELL_HEIGHT);
        lvCards.prefHeightProperty().bind(Bindings.multiply(nbrItems, LIST_CELL_HEIGHT).add(10));
        configStyleButtons();
    }


    //  BUTTONS

    private void configStyleButtons() {
//        btRight.getStyleClass().addAll("bt", "bt-right");
//        btLeft.getStyleClass().addAll("bt", "bt-left");


        Button[] buttons = {btLeft, btRight};
        String[] imgName = {"left.png", "right.png"};

        IntStream.range(0, 2).forEach(idx -> {
            var btn = buttons[idx];

            Image image = new Image(getClass().getResourceAsStream(
                    "/icons/" + imgName[idx]), 20, 20, true, false);

            var imgView = new ImageView(image);

            //griser un peu le noir
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(0.4);
            imgView.setEffect(colorAdjust);

            btn.setGraphic(imgView);
            btn.setPrefSize(30, 30);
            btn.setPadding(new Insets(10, 0, 0, 0));
            btn.setStyle("-fx-background-color: #ffff; -fx-border-color: #f1f1f1");
        });
    }

    //   LISTVIEW

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

                // Mettre à jour la taille de la liste pour mettre à jour la taille de la listeView
                nbrItems.set(lvCards.getItems().size());
            }
        });
    }

    //   BINDINGS

    private void configBindings() {
        configDataBindings();
        configViewModelBindings();
        configDisableBindings();

    }

    private void configDataBindings() {
        elTitle.textProperty().bindBidirectional(columnViewModel.columnTitleProperty());
        columnViewModel.focusedTitleBinding(elTitle.tfFocusedProperty());
        columnViewModel.bindEditAborted(elTitle.editAbortedProperty());

        lvCards.itemsProperty().bind(columnViewModel.cardsListProperty());
    }

    private void configViewModelBindings() {
        columnViewModel.directionBinding(direction);
    }

    private void configDisableBindings() {
        btRight.disableProperty().bind(columnViewModel.btRightDisabledProperty());
        btLeft.disableProperty().bind(columnViewModel.btLeftDisabledProperty());
    }


    //   ACTIONS

    private void configActions() {
        configEventsHandling();
        configMouseEvents();
    }

    private void configEventsHandling() {
        btRight.setOnAction(e -> direction.set(Direction.RIGHT));
        btLeft.setOnAction((e -> direction.set(Direction.LEFT)));
    }

    private void configMouseEvents() {
        setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {
                columnViewModel.addCard();
                e.consume();
            }
        });

        // context menu
        var contextMenu = new ColumnContextMenu(columnViewModel, elTitle.textProperty().get());
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