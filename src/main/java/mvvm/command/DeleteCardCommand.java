package mvvm.command;

import model.*;

public class DeleteCardCommand extends Command {
    private final BoardFacade boardFacade;
    private final Card card;
    private Memento<Card> memento;

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
    void restore() {
        card.restore(memento);
    }

    @Override
    boolean isRestorable() {
        return card.isRestorable(memento);
    }

    @Override
    public String toString() {
        return "Suppression de la " + card;
    }
}
