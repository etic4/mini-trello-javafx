package mvvm.command;

import model.Memento;

public abstract class Command {
    protected Memento memento;

    abstract void execute();
    void undo() {
        memento.restore();
    };

}
