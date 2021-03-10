package mvvm;

import mvvm.command.*;
import javafx.beans.property.*;
import model.Board;
import model.Column;
import model.BoardFacade;

public class BoardViewModel {

    private final ListProperty<Column>
            columnsList = new SimpleListProperty<>();

    private final StringProperty
            boardTitleView = new SimpleStringProperty(),
            boardTitleModel = new SimpleStringProperty();

    private final IntegerProperty
            selectedColumn = new SimpleIntegerProperty();

    private final BooleanProperty
            focusedTitle = new SimpleBooleanProperty(),
            titleEditAborted = new SimpleBooleanProperty();

    private final Board board;

    private final BoardFacade boardFacade;


    //   CONSTRUCTOR

    public BoardViewModel(BoardFacade boardFacade) {
        this.boardFacade = boardFacade;
        board = boardFacade.getBoard();
        boardTitleModel.bind(board.titleProperty());
        boardTitleView.set(board.getTitle());
        setColumnsList();
        addTitleListener();
    }

    //   SETTER

    private void setColumnsList() {
        columnsList.set(boardFacade.getColumns(board));
    }


    //   LISTENER

    private void addTitleListener() {
        focusedTitle.addListener((a, oldValue, newValue) -> {
            if (!newValue && !board.getTitle().equals(boardTitleView.get())) {
                CommandManager.getInstance().execute(new EditTitleCommand<>(board, boardTitleView.get()));
            }
        });

        titleEditAborted.addListener((a, oldValue, newValue) -> {
            if(newValue) {
                boardTitleView.set(board.getTitle());
            }
        });

        boardTitleModel.addListener((o, oldVal, newVal) -> boardTitleView.set(board.getTitle()));
    }


    //  PROPERTIES

    public StringProperty boardTitleProperty() {
        return boardTitleView;
    }

    public ListProperty<Column> columnsListProperty() {
        return columnsList;
    }

    public void focusedTitleBinding(ReadOnlyBooleanProperty readOnlyBooleanProperty) {
        focusedTitle.bind(readOnlyBooleanProperty);
    }

    private Column getColumn() {
        int index = selectedColumn.get();
        return index == -1 ? null : columnsListProperty().get(index);
    }

    public void selectedColumnBinding(ReadOnlyIntegerProperty integerProperty) {
        selectedColumn.bind(integerProperty);
    }


    public void bindEditAborted(BooleanProperty titleEditAborted) {
        this.titleEditAborted.bind(titleEditAborted);
    }


    //   ACTIONS

    public void addColumn() {
        CommandManager.getInstance().execute(new CreateColumnCommand(boardFacade));
    }

    public void delete() {
        Column column = getColumn();

        if(column != null) {
            CommandManager.getInstance().execute(new DeleteColumnCommand(column, boardFacade));
        }
    }
}