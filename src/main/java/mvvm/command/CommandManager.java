package mvvm.command;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import main.Config;

import java.util.Deque;
import java.util.LinkedList;

//TODO: Revoir fonctionnement memento -> bug: possible de refaire move card après suppression colonne
// => si commande pas restorable, mettre texte à "" et désactiver undo ou redo

//TODO: simplifier CommandManager et relations avec trelloVM et menu -> EN COURS, fixer probs et factoriser
//TODO: voir ce qu'il y a moyen de faire avec la classe Bindings

//Singleton
public class CommandManager {
    private static final int CAPACITY = Config.UNDO_REDO_MAX;

    private static final CommandManager instance = new CommandManager();

    // Dequeu pour pouvoir limiter le nombre de undo / redo
    // Sans limite, un stack le ferait
    private final Deque<Command> undoables = new LinkedList<>();
    private final Deque<Command> redoables = new LinkedList<>();

    private final SimpleStringProperty firstUndoableString = new SimpleStringProperty("Annuler");
    private final SimpleStringProperty firstRedoableString = new SimpleStringProperty("Refaire");

    private final SimpleBooleanProperty hasNoUndoableProperty = new SimpleBooleanProperty(true);
    private final SimpleBooleanProperty hasNoRedoableProperty = new SimpleBooleanProperty(true);


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
        emptyRedoables();
        setUndoRedoProperties();
    }

    public void undo() {
        Command command = popUndoable();

        if (command != null) {
            command.restore();
            pushRedoable(command);
        }
        setUndoRedoProperties();
    }


    public void redo() {
        Command command = popReDoable();

        if (command != null) {
            command.restore();
            pushUndoable(command);
        }
        setUndoRedoProperties();
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

    private void pushCommand(Command command, Deque<Command> commands) {
        if (commands.size() == CAPACITY) {
            commands.pollLast();
        }
        commands.push(command);
    }

    private String peekStringCommand(Deque<Command> commands) {
        return commands.isEmpty() ? "" : commands.peek().toString();
    }

    private void emptyRedoables() {
        redoables.clear();
    }

    private void setUndoRedoProperties() {
        setUndoRedoStrings();
        setUndoRedoStates();
    }

    private void setUndoRedoStrings() {
        firstUndoableString.set("Annuler " + peekStringCommand(undoables));
        firstRedoableString.set("Refaire " + peekStringCommand(redoables));
    }

    private void setUndoRedoStates() {
        hasNoUndoableProperty.set(undoables.isEmpty());
        hasNoRedoableProperty.set(redoables.isEmpty());
    }

    public SimpleStringProperty firstUndoableStringProperty() {
        return firstUndoableString;
    }

    public SimpleStringProperty firstRedoableStringProperty() {
        return firstRedoableString;
    }

    public SimpleBooleanProperty hasNoUndoableProperty() {
        return hasNoUndoableProperty;
    }

    public SimpleBooleanProperty hasNoRedoableProperty() {
        return hasNoRedoableProperty;
    }
}
