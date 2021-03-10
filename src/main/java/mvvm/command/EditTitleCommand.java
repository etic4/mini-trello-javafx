package mvvm.command;

import model.Entitled;
import model.History;
import model.MemType;
import model.Memento;

public class EditTitleCommand<E extends Entitled & History> implements Command {
    private final E entitled;
    private final String text;
    private Memento memento;

    public EditTitleCommand(E entitled, String text) {
            this.entitled = entitled;
            this.text = text;
    }

    @Override
    public void execute() {
        memento = entitled.save(MemType.TITLE);
        entitled.setTitle(text);
    }

    @Override
    public void undo() {
        entitled.restore(memento);
    }

    @Override
    public String toString() {
        return "Édition du titre de " + entitled.getClass().getSimpleName();
    }
}
