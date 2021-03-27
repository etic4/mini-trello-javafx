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
        boardFacade = new BoardFacade(column);

        // bind title view property to column title
        columnTitleView.set(column.getTitle());

        // set column's title property on model value if changed
        column.titleProperty().addListener((o, oldVal, newVal) -> columnTitleView.set(column.getTitle()));
    }


    // --- properties ---

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
        CommandManager.getInstance().execute(new CreateCardCommand(column, boardFacade));
    }

    public void deleteColumn() {
        CommandManager.getInstance().execute(new DeleteColumnCommand(column, boardFacade));
    }

    public void moveColumn(Direction direction) {
        CommandManager.getInstance().execute(new MoveColumnCommand(column, direction, boardFacade));
    }

    public void setTitle(String title) {
        CommandManager.getInstance().execute(new EditTitleCommand<>(column, columnTitleView.get(), boardFacade));
    }
}