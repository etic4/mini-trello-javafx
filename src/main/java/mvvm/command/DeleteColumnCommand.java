package mvvm.command;

import model.BoardFacade;
import model.Column;
import model.ColumnMemento;
import model.MemType;

public class DeleteColumnCommand implements Command {
    private final BoardFacade boardFacade;
    private final Column column;
    private ColumnMemento memento;

    public DeleteColumnCommand(Column column, BoardFacade boardFacade) {
        this.boardFacade = boardFacade;
        this.column = column;
    }

    @Override
    public void execute() {
        memento = column.save(MemType.DELETE);
        boardFacade.delete(column);
    }

    @Override
    public void undo() {
        column.restore(memento);
    }

    @Override
    public String toString() {
        return "Suppression de la " + column;
    }
}
