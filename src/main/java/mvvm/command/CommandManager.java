package mvvm.command;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Deque;
import java.util.LinkedList;

//TODO: Revoir fonctionnement memento -> bug: possible de refaire move card après suppression colonne
// => problème de conception
//TODO: simplifier CommandManager et relations avec trelloVM et menu (en cours)

//Singleton
public class CommandManager {
    private static final int CAPACITY = 10;

    private static final CommandManager instance = new CommandManager();

    private final Deque<Command> undoables = new LinkedList<>();
    private final Deque<Command> redoables = new LinkedList<>();

    private final SimpleStringProperty firstUndoable = new SimpleStringProperty();
    private final SimpleStringProperty firstRedoable = new SimpleStringProperty();

    private final SimpleBooleanProperty hasUndoable = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty hasRedoable = new SimpleBooleanProperty(false);


    public static void execute(Command command) {
        instance.executeCommand(command);
    }

    public static CommandManager getInstance() {
        return instance;
    }

    private CommandManager() { };

    private void executeCommand(Command command) {
        command.execute();
        pushUndoable(command);
        firstRedoable.set(command.toString());
        hasRedoable.set(true);
    }

    public void undo() {
        Command command = popUndoable();
        var commandString = "";

        if (command != null) {
            command.restore();
            pushRedoable(command);
            commandString = command.toString();
        }
        firstRedoable.set(commandString);
        hasRedoable.set(command != null);
    }

    public void redo() {
        Command command = popReDoable();
        var commandString = "";

        if (command != null) {
            command.execute();
            pushUndoable(command);
            commandString = command.toString();
        }
        firstUndoable.set(commandString);
        hasUndoable.set(command != null);
    }

    private Command popUndoable(){
        return popCommand(undoables);
    }

    private Command popReDoable(){
        return popCommand(redoables);
    }

    private void pushUndoable(Command command) {
        pushCommand(command, undoables);
    }

    private void pushRedoable(Command command) {
        pushCommand(command, redoables);
    }

    private Command popCommand(Deque<Command> deque) {
        if (deque.size() != 0) {
            return deque.pop();
        }
        return null;
    }

    private void pushCommand(Command command, Deque<Command> deque) {
        if (deque.size() == CAPACITY) {
            deque.pollLast();
        }
        deque.push(command);
    }

    public SimpleStringProperty firstUndoableProperty() {
        return firstUndoable;
    }

    public SimpleStringProperty firstRedoableProperty() {
        return firstRedoable;
    }

    public SimpleBooleanProperty hasNoUndoableProperty() {
        return hasUndoable;
    }

    public SimpleBooleanProperty hasNoRedoableProperty() {
        return hasRedoable;
    }
}
