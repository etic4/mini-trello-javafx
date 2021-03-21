package mvvm.command;

import model.*;

public class CreateCardCommand implements Command {
    private final BoardFacade boardFacade;
    private final Column column;
    private Card card;
    private Memento memento;

    public CreateCardCommand(Column column, BoardFacade boardFacade) {
        this.boardFacade = boardFacade;
        this.column = column;
    }

    @Override
    public void execute() {
        card = boardFacade.addCard(column);
        memento = card.save(MemType.ADD);
    }

    @Override
    public void undo() {
        card.restore(memento);
    }

    @Override
    public String toString() {
        return "Ajout d'une carte à la " + column;
    }
}
