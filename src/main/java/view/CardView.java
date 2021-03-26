package view;

import javafx.scene.control.*;
import direction.Direction;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.BoardFacade;
import model.Card;
import mvvm.CardViewModel;

import java.util.Optional;
import java.util.stream.IntStream;

public class CardView extends BorderPane {

    private final Button
            btUp = new Button(),
            btRight = new Button(),
            btDown = new Button(),
            btLeft = new Button();

    private final EditableLabel elTitle = new EditableLabel("el-card");
    private final CardViewModel cardViewModel;
    private final ObjectProperty<Direction> direction = new SimpleObjectProperty<>();


    public CardView(CardViewModel cardviewModel) {
        this.cardViewModel = cardviewModel;
        buildGraphicComponents();
        configBindings();
        configEventsHandling();
    }

    CardView(Card card) {
        this(new CardViewModel(card));
    }

    //  CONFIG GRAPHIC COMPONENTS

    private void buildGraphicComponents() {
        makeComponentsHierarchy();
        configStyles();
    }

    private void makeComponentsHierarchy() {
        setTop(btUp);
        setRight(btRight);
        setBottom(btDown);
        setLeft(btLeft);
        setCenter(elTitle);
    }

    private void configStyles() {
        getStyleClass().add("card-bp");
        configButtons();
    }

    //  BUTTONS

    private void configButtons() {
//        btRight.getStyleClass().addAll("bt", "bt-right");
//        btLeft.getStyleClass().addAll("bt", "bt-left");
//        btUp.getStyleClass().addAll("bt", "bt-up");
//        btDown.getStyleClass().addAll("bt", "bt-down");

        Button[] buttons = {btLeft, btUp, btRight, btDown};
        String[] imgName = {"left.png", "up.png", "right.png", "down.png"};

        IntStream.range(0, 4).forEach(idx -> {
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
            btn.setStyle("-fx-background-color: #ffffff; -fx-border-color: #f1f1f1");
            setAlignment(btn, Pos.CENTER);
        });
    }

    //   BINDINGS

    private void configBindings() {
        configViewModelBindings();
        configDisableBindings();
    }

    private void configViewModelBindings() {
        elTitle.textProperty().bindBidirectional(cardViewModel.cardTitleViewProperty());
        cardViewModel.bindEditAborted(elTitle.editAbortedProperty());
        cardViewModel.focusedTitleBinding(elTitle.tfFocusedProperty());
        cardViewModel.bindMoveDirection(direction);
    }

    private void configDisableBindings() {
        btUp.disableProperty().bind(cardViewModel.btUpDisabledProperty());
        btDown.disableProperty().bind(cardViewModel.btDownDisabledProperty());
        btRight.disableProperty().bind(cardViewModel.btRightDisabledProperty());
        btLeft.disableProperty().bind(cardViewModel.btLeftDisabledProperty());
    }

    // --- EventsHandling ---

    private void configEventsHandling() {

        Button[] buttons = {btLeft, btUp, btRight, btDown};
        Direction[] directions = {Direction.LEFT, Direction.UP, Direction.RIGHT, Direction.DOWN};

        // set context menu
        var contextMenu = new CardContextMenu(cardViewModel, elTitle.textProperty().get());

        setOnContextMenuRequested(e -> {
            contextMenu.show(this, e.getScreenX(), e.getScreenY());
            e.consume();
        });
    }
}