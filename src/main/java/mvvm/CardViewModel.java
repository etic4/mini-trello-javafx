package mvvm;

import mvvm.command.CommandManager;
import mvvm.command.EditTitleCommand;
import mvvm.command.MoveCardCommand;
import direction.Direction;
import model.*;
import javafx.beans.property.*;


public class CardViewModel {

    private final StringProperty
            cardTitleView = new SimpleStringProperty(),
            cardTitleModel = new SimpleStringProperty();

    private final ObjectProperty<Direction>
            direction = new SimpleObjectProperty<>(null);

    private final BooleanProperty
            focusedTitle = new SimpleBooleanProperty(),
            titleEditAborted = new SimpleBooleanProperty();

    private final Card card;

    private final BoardFacade boardFacade;


    //  CONSTRUCTOR

    public CardViewModel(Card card) {
        this.card = card;
        boardFacade = new BoardFacade(card);
        cardTitleModel.bind(card.titleProperty());
        cardTitleView.set(card.getTitle());
        configListeners();
    }


    //   LISTENERS

    private void configListeners() {
        addMoveListener();
        addTitleListener();
    }

    private void addMoveListener() {
        direction.addListener((obj, oldVal, direction) -> {
            if (direction != null) {
                CommandManager.getInstance().execute(new MoveCardCommand(card, direction, boardFacade));
            }
        });
    }

    private void addTitleListener() {
        focusedTitle.addListener((a, oldValue, newValue) -> {
            if (!newValue && !card.getTitle().equals(cardTitleView.get())) {
                CommandManager.getInstance().execute(new EditTitleCommand<>(card, cardTitleView.get()));
            }
        });

        titleEditAborted.addListener((a, oldValue, newValue) -> {
            if(newValue) {
                cardTitleView.set(card.getTitle());
            }
        });

        cardTitleModel.addListener((o, oldVal, newVal) -> cardTitleView.set(card.getTitle()));
    }

    //   BINDING

    public void bindMoveDirection(ObjectProperty<Direction> direction) {
        this.direction.bind(direction);
    }

    public void focusedTitleBinding(ReadOnlyBooleanProperty readOnlyBooleanProperty) {
        focusedTitle.bind(readOnlyBooleanProperty);
    }

    public void bindEditAborted(BooleanProperty titleEditAborted) {
        this.titleEditAborted.bind(titleEditAborted);
    }

    //  PROPERTIES

    public StringProperty cardTitleViewProperty() {
        return cardTitleView;
    }

    public BooleanProperty btRightDisabledProperty() {
        return card.isColumnLastProperty();
    }

    public BooleanProperty btLeftDisabledProperty() {
        return card.isColumnFirstProperty();
    }

    public BooleanProperty btUpDisabledProperty() {
        return card.isFirstProperty();
    }

    public BooleanProperty btDownDisabledProperty() {
        return card.isLastProperty();
    }

}