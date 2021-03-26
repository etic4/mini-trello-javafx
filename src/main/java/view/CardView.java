package view;

import direction.Direction;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import model.Card;
import mvvm.CardViewModel;
import view.common.EditableLabel;
import view.common.MoveButton;


public class CardView extends BorderPane {

    private final MoveButton
            btUp = new MoveButton(Direction.UP),
            btRight = new MoveButton(Direction.RIGHT),
            btDown = new MoveButton(Direction.DOWN),
            btLeft = new MoveButton(Direction.LEFT);

    private final EditableLabel elTitle = new EditableLabel();
    private final CardViewModel cardViewModel;


    CardView(Card card) {
        this(new CardViewModel(card));
    }


    public CardView(CardViewModel cardviewModel) {
        this.cardViewModel = cardviewModel;
        buildView();
    }


    private void buildView() {
        buildGraphicComponents();
        configBindings();
        configEventsHandling();
    }


    private void buildGraphicComponents() {
        // set css classes
        getStyleClass().add("card-bp");
        elTitle.addTextFieldClasses("el-card");

        // make components hierarchy
        setTop(btUp);
        setRight(btRight);
        setBottom(btDown);
        setLeft(btLeft);
        setCenter(elTitle);

        // set buttons
        for (var btn : new MoveButton[] {btLeft, btUp, btRight, btDown}) {
            setAlignment(btn, Pos.CENTER);
        }
    }


    private void configBindings() {
        // ViewModel bindings
        elTitle.textProperty().bindBidirectional(cardViewModel.cardTitleViewProperty());

        // Buttons disable bindings
        btUp.disableProperty().bind(cardViewModel.btUpDisabledProperty());
        btDown.disableProperty().bind(cardViewModel.btDownDisabledProperty());
        btRight.disableProperty().bind(cardViewModel.btRightDisabledProperty());
        btLeft.disableProperty().bind(cardViewModel.btLeftDisabledProperty());
    }


    private void configEventsHandling() {
        // Title text changed
        elTitle.addEventHandler(EditableLabel.TEXT_CHANGED, e -> {
            cardViewModel.setTitle(elTitle.textProperty().get());
        });

        // buttons clicks
        for(var button : new MoveButton[]{btLeft, btUp, btRight, btDown}) {
            button.setOnAction(e -> cardViewModel.moveCard(button.getDirection()));
        }

        // set context menu
        var contextMenu = new CardContextMenu(cardViewModel, elTitle.textProperty().get());

        setOnContextMenuRequested(e -> {
            contextMenu.show(this, e.getScreenX(), e.getScreenY());
            e.consume();
        });
    }
}