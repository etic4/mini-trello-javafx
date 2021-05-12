package mvvm.command;

import model.*;

public class CreateColumnCommand extends Command {
    private final BoardFacade boardFacade;
    private Column column;
    private Memento<Column> memento;


    public CreateColumnCommand(BoardFacade boardFacade) {
        this.boardFacade = boardFacade;
    }

    @Override
    public void execute() {
        column = boardFacade.newColumn();
        memento = column.getMemento(MemType.ADD);
    }

    @Override
    void restore() {
        memento = column.restore(memento);
    }

    @Override
    public String toString() {
        return "Ajout d'une colonne au board " + boardFacade.getBoard();
    }
}
