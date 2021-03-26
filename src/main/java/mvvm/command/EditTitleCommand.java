package mvvm.command;

import model.*;

import java.util.Locale;

public class EditTitleCommand<E extends Entitled & History> extends Command {
    private final E entitled;
    private final String title;
    private final BoardFacade boardFacade;
    private String commandString;

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
