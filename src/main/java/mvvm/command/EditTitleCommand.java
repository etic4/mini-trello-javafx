package mvvm.command;

import model.*;

public class EditTitleCommand<E extends Entitled & History<E>> extends Command {
    private final E entitled;
    private final String title;
    private final BoardFacade boardFacade;
    private String commandString;
    private Memento<E> memento;


    public EditTitleCommand(E entitled, String title, BoardFacade boardFacade) {
            this.entitled = entitled;
            this.title = title;
            this.boardFacade = boardFacade;
    }

    @Override
    public void execute() {
        setCommandString();
        memento = entitled.getMemento(MemType.TITLE);
        boardFacade.setTitle(entitled, title);
    }

    @Override
    void restore() {
        memento = entitled.restore(memento);
    }

    private void setCommandString() {
        String objName = entitled.getClass().getSimpleName().toLowerCase();
        String oldTitle = entitled.getTitle();

        commandString = "Édition du titre de la " + objName + " \"" + oldTitle + "\" en \"" + title + "\"";
    }

    @Override
    public String toString() {
        return commandString;
    }
}
