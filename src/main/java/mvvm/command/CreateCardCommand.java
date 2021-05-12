package mvvm.command;

import model.*;

public class CreateCardCommand extends Command {
    private final BoardFacade boardFacade;
    private final Column column;
    private Card card;
    private Memento<Card> memento;


    public CreateCardCommand(Column column, BoardFacade boardFacade) {
        this.boardFacade = boardFacade;
        this.column = column;
    }

    @Override
    public void execute() {
        card = boardFacade.newCard(column);
        memento = card.getMemento(MemType.ADD);
    }

    @Override
    void restore() {
        memento = card.restore(memento);
    }

    @Override
    public String toString() {
        return "Ajout d'une carte à la " + column;
    }
}
