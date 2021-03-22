package mvvm.command;

import model.*;

public class CreateColumnCommand extends Command {
    private final BoardFacade boardFacade;

    public CreateColumnCommand(BoardFacade boardFacade) {
        this.boardFacade = boardFacade;
    }

    @Override
    public void execute() {
        var column = boardFacade.addColumn();
        memento = column.save(MemType.ADD);
    }

    @Override
    public String toString() {
        return "Ajout d'une colonne au board " + boardFacade.getBoard();
    }
}
