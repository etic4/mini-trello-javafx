package mvvm.command;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


//Singleton
public class CommandManager {
    private static final int CAPACITY = 10;

    private static final CommandManager instance = new CommandManager();

    private final ObservableList<Command>  undoables = FXCollections.observableArrayList();
    private final ObservableList<Command>  redoables = FXCollections.observableArrayList();

    private final ListProperty<Command> undoablesProperty = new SimpleListProperty<>(undoables);
    private final ListProperty<Command> redoablesProperty = new SimpleListProperty<>(redoables);

    private final StringProperty nextUndoableString = new SimpleStringProperty("Annuler");
    private final StringProperty nextRedoableString = new SimpleStringProperty("Refaire");


    public static CommandManager getInstance() {
        return instance;
    }


    private CommandManager() {
        configListeners();
    };


    // change content of string representation of last commands
    private void configListeners() {
        undoablesProperty.addListener((lst, old, newval) -> {
            if (newval.isEmpty()) {
                nextUndoableString.set("Annuler");
            } else {
                nextUndoableString.set("Annuler " + newval.get(newval.size()- 1).toString());
            }
        });

        redoablesProperty.addListener((lst, old, newval) -> {
            if (newval.isEmpty()) {
                nextRedoableString.set("Refaire");
            } else {
                nextRedoableString.set("Refaire " + newval.get(newval.size()- 1).toString());
            }
        });
    }


    // execute command
    public void execute(Command command) {
        command.execute();
        addUndoable(command);
    }

    public void undo() {
        Command command = popCommand(undoables);
        if (command != null) {
            command.undo();
            redoables.add(command);
        }
    }

    public void redo() {
        Command command = popCommand(redoables);
        if (command != null) {
            command.execute();
            addUndoable(command);
        }
    }


    // add element to history
    // remove first if max size reached
    private void addUndoable(Command command) {
        if (undoables.size() == CAPACITY) {
            undoables.remove(0);
        }
        undoables.add(command);
    }


    // pop or nul
    private Command popCommand(ObservableList<Command> lst) {
        if (!lst.isEmpty()) {
            return lst.remove(lst.size() - 1);
        }
        return null;
    }


    // --- Properties ---

    public StringProperty nextUndoableStringProperty() {
        return nextUndoableString;
    }

    public StringProperty nextRedoableStringProperty() {
        return nextRedoableString;
    }

    public ReadOnlyBooleanProperty hasNoUndoableProperty() {
        return undoablesProperty.emptyProperty();
    }

    public ReadOnlyBooleanProperty hasNoRedoableProperty() {
        return redoablesProperty.emptyProperty();
    }
}
