package mvvm;

import javafx.beans.property.*;
import model.Board;
import model.Column;
import model.BoardFacade;

public class BoardViewModel {

    private final ListProperty<Column>
            columnsList = new SimpleListProperty<>();

    private final StringProperty
            boardTitle = new SimpleStringProperty("");

    private final IntegerProperty
            selectedColumn = new SimpleIntegerProperty();

    private final Board board;

    private final BoardFacade boardFacade;


    //   CONSTRUCTOR

    public BoardViewModel(BoardFacade boardFacade) {
        this.boardFacade = boardFacade;
        board = boardFacade.getBoard();
        boardTitle.set(board.getTitle());
        setColumnsList();
        addTitleListener();
    }


    //   SETTER

    private void setColumnsList() {
        columnsList.set(boardFacade.getColumns(board));
    }


    //   LISTENER

    private void addTitleListener() {
        boardTitle.addListener((a, oldTitle, newTitle) -> {
            if (newTitle != null)
                board.setTitle(newTitle);
        });
    }


    //  PROPERTIES

    public StringProperty boardTitleProperty() {
        return boardTitle;
    }

    public ListProperty<Column> columnsListProperty() {
        return columnsList;
    }

    private Column getColumn() {
        int index = selectedColumn.get();
        return index == -1 ? null : columnsListProperty().get(index);
    }

    public void selectedColumnBinding(ReadOnlyIntegerProperty integerProperty) {
        selectedColumn.bind(integerProperty);
    }


    //   ACTIONS

    public void addColumn() {
        boardFacade.addColumn(board);
    }

    public void delete() {
        Column column = getColumn();
        if(column != null) {
            boardFacade.delete(column);
        }
    }
}