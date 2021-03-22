package mvvm;

import mvvm.command.*;
import model.Card;
import direction.Direction;
import javafx.beans.property.*;
import model.Column;
import model.BoardFacade;

public class ColumnViewModel {

    private final StringProperty
            columnTitleView = new SimpleStringProperty(),
            columnTitleModel = new SimpleStringProperty();

    private final IntegerProperty selectedCard = new SimpleIntegerProperty();
    private final ObjectProperty<Direction> direction = new SimpleObjectProperty<>();
    private final BooleanProperty
            focusedTitle = new SimpleBooleanProperty(),
            titleEditAborted = new SimpleBooleanProperty();

    private final Column column;
    private final BoardFacade boardFacade;


    public ColumnViewModel(Column column) {
        this.column = column;
        boardFacade = new BoardFacade(column);
        columnTitleModel.bind(column.titleProperty());
        columnTitleView.set(column.getTitle());

        configListeners();
    }


    //   LISTENERS

    private void configListeners() {
        addMoveListener();
        addTitleListener();
    }

    private void addMoveListener() {
        direction.addListener((obj, oldVal, direction) -> {
            CommandManager.getInstance().execute(new MoveColumnCommand(column, direction, boardFacade));
            }
        );
    }

    private void addTitleListener() {
        focusedTitle.addListener((a, oldValue, newValue) -> {
            if (!newValue && !column.getTitle().equals(columnTitleView.get())) {
                CommandManager.getInstance().execute(new EditTitleCommand<>(column, columnTitleView.get(), boardFacade));
            }
        });

        titleEditAborted.addListener((a, oldValue, newValue) -> {
            if(newValue) {
                columnTitleView.set(column.getTitle());
            }
        });

        columnTitleModel.addListener((o, oldVal, newVal) -> columnTitleView.set(column.getTitle()));
    }

    //   PROPERTIES

    public ListProperty<Card> cardsListProperty() {
        return new SimpleListProperty<>(boardFacade.getCards(column));
    }

    public StringProperty columnTitleProperty() {
        return columnTitleView;
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

    public void focusedTitleBinding(ReadOnlyBooleanProperty readOnlyBooleanProperty) {
        focusedTitle.bind(readOnlyBooleanProperty);
    }

    public void bindEditAborted(BooleanProperty titleEditAborted) {
        this.titleEditAborted.bind(titleEditAborted);
    }

    //   ADD & DELETE

    public void addCard() {
        CommandManager.getInstance().execute(new CreateCardCommand(column, boardFacade));
    }

    public void delete() {
        CommandManager.getInstance().execute(new DeleteColumnCommand(column, boardFacade));
    }

    private Card getCard() {
        int index = selectedCard.get();
        return index == -1 ? null : cardsListProperty().get(index);
    }

}