package mvvm;

import mvvm.command.*;
import model.Card;
import direction.Direction;
import javafx.beans.property.*;
import model.Column;
import model.BoardFacade;

public class ColumnViewModel {

    private final StringProperty columnTitleView = new SimpleStringProperty();
    private final Column column;
    private final BoardFacade boardFacade;


    public ColumnViewModel(Column column) {
        this.column = column;
        boardFacade = new BoardFacade(column.getBoard());

        // set title view binded property to column title
        columnTitleView.set(column.getTitle());

        // set title view binding on model value if changed
        column.titleProperty().addListener((o, oldVal, newVal) -> columnTitleView.set(column.getTitle()));
    }


    public StringProperty columnTitleProperty() {
        return columnTitleView;
    }

    public ListProperty<Card> cardsListProperty() {
        return new SimpleListProperty<>(boardFacade.getCards(column));
    }

    public ReadOnlyBooleanProperty btRightDisabledProperty() {
        return column.isLastProperty();
    }

    public ReadOnlyBooleanProperty btLeftDisabledProperty() {
        return column.isFirstProperty();
    }


    // --- Commands ---

    public void addCard() {
        CommandManager.execute(new CreateCardCommand(column, boardFacade));
    }

    public void deleteColumn() {
        CommandManager.execute(new DeleteColumnCommand(column, boardFacade));
    }

    public void moveColumn(Direction direction) {
        CommandManager.execute(new MoveColumnCommand(column, direction, boardFacade));
    }

    public void setTitle(String title) {
        CommandManager.execute(new EditTitleCommand<>(column, columnTitleView.get(), boardFacade));
    }
}