package mvvm.command;

import model.*;

public class EditTitleCommand<E extends Entitled & History> extends Command {
    private final E entitled;
    private final String title;
    private final BoardFacade boardFacade;

    public EditTitleCommand(E entitled, String title, BoardFacade boardFacade) {
            this.entitled = entitled;
            this.title = title;
            this.boardFacade = boardFacade;
    }

    @Override
    public void execute() {
        memento = entitled.save(MemType.TITLE);
        boardFacade.setTitle(entitled, title);
    }

    @Override
    public String toString() {
        return "Édition du titre de " + entitled.getClass().getSimpleName();
    }
}
