package mvvm.command;

import direction.Direction;
import model.*;

public class MoveColumnCommand extends Command {
    private final Column column;
    private final Direction direction;
    private final BoardFacade boardFacade;
    private Memento<Column> memento;


    public MoveColumnCommand(Column column, Direction direction, BoardFacade boardFacade) {
        this.column = column;
        this.direction = direction;
        this.boardFacade = boardFacade;
    }

    @Override
    public void execute() {
        memento = column.getMemento(MemType.POSITION);
        boardFacade.move(column, direction);
    }

    @Override
    void restore() {
        memento = column.restore(memento);
    }

    @Override
    public String toString() {
        return "Déplacement de la " + column + "vers la " + direction;
    }
}
