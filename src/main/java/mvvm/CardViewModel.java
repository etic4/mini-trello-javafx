package mvvm;

import direction.Direction;
import model.*;
import javafx.beans.property.*;


public class CardViewModel {

    private final StringProperty
            cardTitle = new SimpleStringProperty("");

    private final ObjectProperty<Direction>
            direction = new SimpleObjectProperty<>(null);

    private final Card card;

    private final BoardFacade boardFacade;


    //  CONSTRUCTOR

    public CardViewModel(Card card) {
        this.card = card;
        this.boardFacade = new BoardFacade();
        this.cardTitle.set(card.getTitle());
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
                boardFacade.move(card, direction);
            }
        });
    }

    private void addTitleListener() {
        cardTitle.addListener((a, oldTitle, newTitle) -> {
            if (newTitle != null)
                card.setTitle(newTitle);
        });
    }


    //   BINDING

    public void bindMoveDirection(ObjectProperty<Direction> direction) {
        this.direction.bind(direction);
    }


    //  PROPERTIES

    public StringProperty cardTitleProperty() {
        return cardTitle;
    }

    public BooleanProperty btRightDisabledProperty() {
        return (card.getContainer()).isLastProperty();
    }

    public BooleanProperty btLeftDisabledProperty() {
        return (card.getContainer()).isFirstProperty();
    }

    public BooleanProperty btUpDisabledProperty() {
        return card.isFirstProperty();
    }

    public BooleanProperty btDownDisabledProperty() {
        return card.isLastProperty();
    }

}