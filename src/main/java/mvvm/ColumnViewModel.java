package mvvm;

import model.Card;
import direction.Direction;
import javafx.beans.property.*;
import model.Column;
import model.BoardFacade;


public class ColumnViewModel {

    private final StringProperty
            columnTitle = new SimpleStringProperty("");

    private final IntegerProperty selectedCard = new SimpleIntegerProperty();

    private final ObjectProperty<Direction> direction = new SimpleObjectProperty<>();

    private final Column column;

    private final BoardFacade boardFacade;


    //  CONSTRUCTOR

    public ColumnViewModel(Column column) {
        this.column = column;
        this.boardFacade = new BoardFacade();
        this.columnTitle.set(column.getTitle());
        configListeners();
    }


    //   LISTENERS

    private void configListeners() {
        addMoveListener();
        addTitleListener();
    }

    private void addMoveListener() {
        direction.addListener((obj, oldVal, direction) ->
                boardFacade.move(column, direction)
        );
    }

    private void addTitleListener() {
        columnTitle.addListener((a, oldTitle, newTitle) -> {
            if (newTitle != null)
                column.setTitle(newTitle);
        });
    }


    //   PROPERTIES

    public ListProperty<Card> cardsListProperty() {
        return new SimpleListProperty<>(boardFacade.getCards(column));
    }

    public StringProperty columnTitleProperty() {
        return columnTitle;
    }

    public BooleanProperty btRightDisabledProperty() {
        return column.isLastProperty();
    }

    public BooleanProperty btLeftDisabledProperty() {
        return column.isFirstProperty();
    }


    //   BINDINGS

    public void selectedColumnBinding(ReadOnlyIntegerProperty integerProperty) {
        selectedCard.bind(integerProperty);
    }

    public void directionBinding(ObjectProperty<Direction> direction) {
        this.direction.bind(direction);
    }


    //   ADD & DELETE

    public void addCard() {
        boardFacade.addCard(column);
    }

    public void delete() {
        Card card = getCard();
        if(card != null) {
            boardFacade.delete(card);
        }
    }

    private Card getCard() {
        int index = selectedCard.get();
        return index == -1 ? null : cardsListProperty().get(index);
    }

}