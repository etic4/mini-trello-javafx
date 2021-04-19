package mvvm.command;

import model.Memento;

public abstract class Command {

    abstract void execute();

    abstract void restore();

    abstract boolean isRestorable();
}
