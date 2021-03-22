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

    private final EditableLabel elTitle = new EditableLabel();
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
        setStyle("-fx-padding: 2 ;-fx-border-color: #dddddd ;-fx-border-radius: 4; -fx-background-color: #ffffff");
    }

    //  EDITABLE LABEL

    private void configStyleEditableLabel() {
        elTitle.tf.setFont(Font.font("Arial", 14));
        elTitle.tf.setStyle("-fx-background-color: #ffffff");
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

        setOnContextMenuRequested(e -> {
            var contextMenu = getContextMenu();
            contextMenu.show(this, e.getScreenX(), e.getScreenY());
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
                cardViewModel.delete();
            }
        });
        contextMenu.getItems().add(delete);
        return contextMenu;
    }
}