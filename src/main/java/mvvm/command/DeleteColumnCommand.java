package mvvm.command;

import model.*;

public class DeleteColumnCommand extends Command {
    private final BoardFacade boardFacade;
    private final Column column;


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
    public String toString() {
        return "Suppression de la " + column;
    }
}
