package mvvm.command;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.LinkedList;

//Singleton
public class CommandManager {
    private static final int CAPACITY = 10;

    private static final CommandManager instance = new CommandManager();

    LinkedList<Command> undoable = new LinkedList<>();
    LinkedList<Command> redoable = new LinkedList<>();

    private final StringProperty nextUndoable = new SimpleStringProperty("Annuler");
    private final StringProperty nextRedoable = new SimpleStringProperty("Refaire");

    private final BooleanProperty hasNoUndoable = new SimpleBooleanProperty(true);
    private final BooleanProperty hasNoRedoable = new SimpleBooleanProperty(true);


    private CommandManager() {};

    public static CommandManager getInstance() {
        return instance;
    }

    public void execute(Command command) {
        command.execute();
        addUndoable(command);
        updateUndoRedoString();
    }

    public void undo() {
        Command command = popUndoable();
        if (!(command instanceof EmptyCommand)) {
            command.undo();
            addRedoable(command);
        }
        updateUndoRedoString();
    }

    public void redo() {
        Command command = popRedoable();
        if (!(command instanceof EmptyCommand)) {
            command.execute();
            addUndoable(command);
        }
        updateUndoRedoString();
    }

    private void updateUndoRedoString() {
        nextRedoable.setValue("Refaire " + peekRedoable().toString());
        nextUndoable.setValue("Annuler " + peekUndoable().toString());
    }

    // Gestion de l'historique

    // ajout un élément à l'historique
    private void addUndoable(Command command) {
        if (undoable.size() == CAPACITY) {
            undoable.removeLast();
        }
        undoable.push(command);
        hasNoUndoable.set(undoable.isEmpty());
    }


    // pas besoin de checker la taille ici
    private void addRedoable(Command command) {
        redoable.push(command);
        hasNoRedoable.set(redoable.isEmpty());
    }

    private Command popUndoable() {
        Command command = popCommand(undoable);
        hasNoUndoable.set(undoable.isEmpty());
        return command;
    }

    private Command popRedoable() {
        Command command = popCommand(redoable);
        hasNoRedoable.set(redoable.isEmpty());
        return command;
    }

    private Command peekUndoable() {
        return peekCommand(undoable);
    }

    private Command peekRedoable() {
        return peekCommand(redoable);
    }

    private Command peekCommand(LinkedList<Command> lst) {
        if (!lst.isEmpty()) {
            return lst.peek();
        }
        return new EmptyCommand();
    }

    private Command popCommand(LinkedList<Command> lst) {
        if (!lst.isEmpty()) {
            return lst.pop();
        }
        return new EmptyCommand();
    }


    // Properties

    public StringProperty nextUndoableProperty() {
        return nextUndoable;
    }

    public StringProperty nextRedoableProperty() {
        return nextRedoable;
    }

    public BooleanProperty hasNoUndoableProperty() {
        return hasNoUndoable;
    }

    public BooleanProperty hasNoRedoableProperty() {
        return hasNoRedoable;
    }
}
