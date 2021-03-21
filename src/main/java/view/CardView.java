package view;

import javafx.scene.control.*;
import direction.Direction;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Card;
import mvvm.CardViewModel;

import java.util.stream.IntStream;

public class CardView extends BorderPane {

    private static final Color BACKGROUND_COLOR = Color.web("#D8E4F0");

    private final Button
            btUp = new Button(),
            btRight = new Button(),
            btDown = new Button(),
            btLeft = new Button();

    private final EditableLabel
            elTitle = new EditableLabel();

    private final CardViewModel cardViewModel;

    private final ObjectProperty<Direction> direction = new SimpleObjectProperty<>();


    //  CONSTRUCTORS

    public CardView(CardViewModel cardviewModel) {
        this.cardViewModel = cardviewModel;
        buildGraphicComponents();
        configBindings();
        configEventsHandling();
    }

    CardView(Card card) {
        this(new CardViewModel(card));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  CONFIG GRAPHIC COMPONENTS

    private void buildGraphicComponents() {
        makeComponentsHierarchy();
        configStyles();
    }

    private void makeComponentsHierarchy() {
        this.setTop(btUp);
        this.setRight(btRight);
        this.setBottom(btDown);
        this.setLeft(btLeft);
        this.setCenter(elTitle);
    }

    private void configStyles() {
        configStyleBorderPane();
        configStyleEditableLabel();
        configButtons();
    }

    //  BORDER PANE

    private void configStyleBorderPane() {
        CornerRadii corners = new CornerRadii(4);
        BackgroundFill backgroundFill = new BackgroundFill(BACKGROUND_COLOR, corners, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        setBackground(background);
    }

    //  EDITABLE LABEL

    private void configStyleEditableLabel() {
        elTitle.tf.setFont(Font.font("Arial", 15));
        BackgroundFill backgroundFill = new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        elTitle.tf.setBackground(background);
        elTitle.tf.setPrefSize(160, 30);
        elTitle.setMinWidth(70);
    }

    //  BUTTONS

    private void configButtons() {
        Button[] buttons = {btLeft, btUp, btRight, btDown};
        String[] imgName = {"left.png", "up.png", "right.png", "down.png"};

        IntStream.range(0, 4).forEach(idx -> {
            var btn = buttons[idx];
            Image image = new Image(getClass().getResourceAsStream(
                    "/icons/" + imgName[idx]), 20, 20, true, false);
            btn.setGraphic(new ImageView(image));
            btn.setPrefSize(30, 30);
            setBackground(btn);
            setAlignment(btn, Pos.CENTER);
        });
    }

    private void setBackground(Button btn) {
        BackgroundFill backgroundFill = new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        btn.setBackground(background);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //   BINDINGS

    private void configBindings() {
        configViewModelBindings();
        configDisableBindings();
    }

    private void configViewModelBindings() {
        elTitle.tf.textProperty().bindBidirectional(cardViewModel.cardTitleViewProperty());
        cardViewModel.bindEditAborted(elTitle.editAbortedProperty());
        cardViewModel.focusedTitleBinding(elTitle.tf.focusedProperty());
        cardViewModel.bindMoveDirection(direction);
    }

    private void configDisableBindings() {
        btUp.disableProperty().bind(cardViewModel.btUpDisabledProperty());
        btDown.disableProperty().bind(cardViewModel.btDownDisabledProperty());
        btRight.disableProperty().bind(cardViewModel.btRightDisabledProperty());
        btLeft.disableProperty().bind(cardViewModel.btLeftDisabledProperty());
    }

    //  ACTIONS

    private void configEventsHandling() {
        Button[] buttons = {btLeft, btUp, btRight, btDown};
        Direction[] directions = {Direction.LEFT, Direction.UP, Direction.RIGHT, Direction.DOWN};

        IntStream.range(0, 4).forEach(i ->
                buttons[i].setOnAction(e ->
                        direction.set(directions[i])
                )
        );
    }

}