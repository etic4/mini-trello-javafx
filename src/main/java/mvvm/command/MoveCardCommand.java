package mvvm.command;

import direction.Direction;
import model.*;

public class MoveCardCommand extends Command {

    private final Card card;
    private final Direction direction;
    private final BoardFacade boardFacade;
    private Memento<Card> memento;
    private Column column;

    public MoveCardCommand(Card card, Direction direction, BoardFacade boardFacade) {
        this.card = card;
        this.direction = direction;
        this.boardFacade = boardFacade;
    }

    @Override
    public void execute() {
        getCommandString();
        memento = card.save(MemType.POSITION);
        column = boardFacade.move(card, direction);
    }

    @Override
    void restore() {
        card.restore(memento);
    }

    @Override
    boolean isUndoable() {
        return card.isUndoable(memento);
    }

    //Faut que colonne existe
    @Override
    boolean isRedoable() {
        return boardFacade.isInBoard(column);
    }

    private String getCommandString() {
        var commandString = "";
        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            var sourceColumn = boardFacade.getColumn(card);
            var destColumn = boardFacade.getMoveDestinationColumn(card, direction);
            commandString = "Déplacement de la " + card + "de la " + sourceColumn + " vers la " + destColumn;
        } else {
            commandString = "Déplacement de la " + card + "vers le " + direction;
        }
        return commandString;
    }

    @Override
    public String toString() {
        return getCommandString();
    }
}
