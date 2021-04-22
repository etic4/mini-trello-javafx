package mvvm;

import mvvm.command.*;
import javafx.beans.property.*;
import model.Board;
import model.Column;
import model.BoardFacade;

public class BoardViewModel {

    private final StringProperty boardTitleView = new SimpleStringProperty();
    private final Board board;
    private final BoardFacade boardFacade;


    //   CONSTRUCTOR

    public BoardViewModel(BoardFacade boardFacade) {
        this.boardFacade = boardFacade;
        board = boardFacade.getBoard();

        // set title view binded property tot board title
        boardTitleView.set(board.getTitle());

        // set title view binding on model value if changed
        board.titleProperty().addListener((o, oldVal, newVal) -> boardTitleView.set(board.getTitle()));
    }


    public StringProperty boardTitleProperty() {
        return boardTitleView;
    }

    public ListProperty<Column> columnsListProperty() {
        return new SimpleListProperty<>(boardFacade.getColumns(board));
    }

    // --- commands ---

    public void setTitle(String title) {
        CommandManager.execute(new EditTitleCommand<>(board, title, boardFacade));
    }

    public void addColumn() {
        CommandManager.execute(new CreateColumnCommand(boardFacade));
    }
}