package mvvm.command;

import model.*;

public class DeleteCardCommand implements Command {
    private final BoardFacade boardFacade;
    private final Card card;
    private CardMemento memento;

    public DeleteCardCommand(Card card,  BoardFacade boardFacade) {
        this.boardFacade = boardFacade;
        this.card = card;
    }

    @Override
    public void execute() {
        memento = card.save(MemType.DELETE);
        boardFacade.delete(card);
    }

    @Override
    public void undo() {
        card.restore(memento);
    }

    @Override
    public String toString() {
        return "Suppression de la " + card;
    }
}
