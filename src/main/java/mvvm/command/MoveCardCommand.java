package mvvm.command;

import direction.Direction;
import model.*;

public class MoveCardCommand extends Command {

    private final Card card;
    private final Direction direction;
    private final BoardFacade boardFacade;
    private String commandString = "";


    public MoveCardCommand(Card card, Direction direction, BoardFacade boardFacade) {
        this.card = card;
        this.direction = direction;
        this.boardFacade = boardFacade;
    }

    @Override
    public void execute() {
        memento = card.save(MemType.POSITION);
        setCommandString();
        this.boardFacade.move(card, direction);
    }

    private void setCommandString() {
        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            var sourceColumn = boardFacade.getColumn(card);
            var destColumn = boardFacade.getMoveDestinationColumn(card, direction);
            commandString = "Déplacement de la " + card + "de la " + sourceColumn + " vers la " + destColumn;

        } else {
            commandString = "Déplacement de la " + card + "vers le " + direction;
        }
    }

    @Override
    public String toString() {
        return commandString;
    }
}
