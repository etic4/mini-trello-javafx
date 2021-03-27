package mvvm.command;

import model.*;

public class CreateCardCommand extends Command {
    private final BoardFacade boardFacade;
    private final Column column;

    public CreateCardCommand(Column column, BoardFacade boardFacade) {
        this.boardFacade = boardFacade;
        this.column = column;
    }

    @Override
    public void execute() {
        var card = boardFacade.addCard(column);
        memento = card.save(MemType.ADD);
    }

    @Override
    public String toString() {
        return "Ajout d'une carte à la " + column;
    }
}
