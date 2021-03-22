package view;

import javafx.beans.binding.Bindings;
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
    private static final int CARD_CELL_HEIGHT = 100;

    private final HBox hbHeader = new HBox();
    private final Button
            btLeft = new Button(),
            btRight = new Button();

    private final EditableLabel elTitle = new EditableLabel();
    private final ListView<Card> lvCards = new ListView<>();
    private final ColumnViewModel columnViewModel;
    private final ObjectProperty<Direction> direction = new SimpleObjectProperty<>();


    //  CONSTRUCTORS

    public ColumnView(ColumnViewModel columnViewModel) {
        this.columnViewModel = columnViewModel;
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
        configStyleVBox();
        configStyleHeader();
        configStyleButtons();
        configStyleEditableLabel();
        configStyleListView();
    }

    //  VBOX

    private void configStyleVBox() {
        setStyle("-fx-border-color: #dddddd;");
        VBox.setVgrow(lvCards, Priority.ALWAYS);
    }

    private void configStyleHeader() {
        hbHeader.setStyle("-fx-background-color: #fcfcfc");
        elTitle.tf.setStyle("-fx-background-color: #fcfcfc");
    }
    //  BUTTONS

    private void configStyleButtons() {
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

    //  EDITABLE LABEL

    private void configStyleEditableLabel() {
        elTitle.tf.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        elTitle.tf.setStyle("-fx-background-color: #fcfcfc");
        elTitle.setPrefHeight(40);
        elTitle.setMinWidth(190);
        elTitle.setPadding(new Insets(3, 0, 0, 0));
        elTitle.setAlignment(Pos.CENTER);
        HBox.setHgrow(elTitle, Priority.ALWAYS);
    }

    //   LISTVIEW

    private void configStyleListView() {
        lvCards.setStyle("-fx-background-color: #fcfcfc; -fx-background-insets: 0; -fx-padding: 0;");
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
                setStyle("-fx-background-color: #fcfcfc");
                setGraphic(cardView);
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
        elTitle.tf.textProperty().bindBidirectional(columnViewModel.columnTitleProperty());
        columnViewModel.focusedTitleBinding(elTitle.tf.focusedProperty());
        columnViewModel.bindEditAborted(elTitle.editAbortedProperty());

        lvCards.itemsProperty().bind(columnViewModel.cardsListProperty());
    }

    private void configViewModelBindings() {
        columnViewModel.directionBinding(direction);
        columnViewModel.selectedColumnBinding(lvCards.getSelectionModel().selectedIndexProperty());
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
        lvCards.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {
                columnViewModel.addCard();
                e.consume();
            }
        });

        lvCards.setOnContextMenuRequested(e -> {
            var contextMenu = getContextMenu();
            contextMenu.show(lvCards, e.getScreenX(), e.getScreenY());
            e.consume();
        });
    }

    private ContextMenu getContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem delete = new MenuItem("Delete " + elTitle.tf.getText());
        delete.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Delete " + elTitle.tf.getText() + " ?");
            Optional<ButtonType> action = alert.showAndWait();

            if(action.isPresent() && action.get() == ButtonType.OK) {
                columnViewModel.delete();
            }
        });
        contextMenu.getItems().add(delete);
        return contextMenu;
    }


    private Card selectedCard() {
        return lvCards.getSelectionModel().getSelectedItem();
    }

}