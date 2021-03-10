package mvvm.command;

import model.*;

public class CreateColumnCommand implements Command {
    private final BoardFacade boardFacade;
    private final Board board;
    private Column column;
    private ColumnMemento memento;

    public CreateColumnCommand(BoardFacade boardFacade) {
        this.boardFacade = boardFacade;
        this.board = boardFacade.getBoard();
    }

    @Override
    public void execute() {
        column = boardFacade.addColumn(board);
        memento = column.save(MemType.ADD);
    }

    @Override
    public void undo() {
        column.restore(memento);
    }

    @Override
    public String toString() {
        return "Ajout d'une colonne au board " + board;
    }
}
