package mvvm.command;

import model.*;

public class DeleteColumnCommand extends Command {
    private final BoardFacade boardFacade;
    private final Column column;
    private Memento<Column> memento;


    public DeleteColumnCommand(Column column, BoardFacade boardFacade) {
        this.boardFacade = boardFacade;
        this.column = column;
    }

    @Override
    public void execute() {
        memento = column.getMemento(MemType.DELETE);
        boardFacade.delete(column);
    }

    @Override
    void restore() {
        memento = column.restore(memento);
    }

    @Override
    public String toString() {
        return "Suppression de la " + column;
    }
}
