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
                CommandManager.getInstance().execute(new EditTitleCommand<>(board, boardTitleView.get(), boardFacade));
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

    public void bindFfocusedTitle(ReadOnlyBooleanProperty readOnlyBooleanProperty) {
        focusedTitle.bind(readOnlyBooleanProperty);
    }


    public void bindEditAborted(BooleanProperty titleEditAborted) {
        this.titleEditAborted.bind(titleEditAborted);
    }


    //   ACTIONS

    public void addColumn() {
        CommandManager.getInstance().execute(new CreateColumnCommand(boardFacade));
    }
}