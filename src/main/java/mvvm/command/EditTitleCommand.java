package mvvm.command;

import model.*;

import java.util.Locale;

public class EditTitleCommand<E extends Entitled & History<T>, T> extends Command {
    private final E entitled;
    private final String title;
    private final BoardFacade boardFacade;
    private String commandString;
    private Memento<T> memento;

    public EditTitleCommand(E entitled, String title, BoardFacade boardFacade) {
            this.entitled = entitled;
            this.title = title;
            this.boardFacade = boardFacade;
    }

    @Override
    public void execute() {
        setCommandString();
        memento = entitled.save(MemType.TITLE);
        boardFacade.setTitle(entitled, title);
    }

    @Override
    void restore() {
        entitled.restore(memento);
    }

    @Override
    boolean isRestorable() {
        return true;
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
