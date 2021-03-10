package mvvm;

import mvvm.command.Command;
import mvvm.command.CommandManager;
import mvvm.command.CreateColumnCommand;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import model.BoardFacade;
import model.TrelloFacade;

public class TrelloViewModel {
    private final TrelloFacade trelloFacade;
    private final CommandManager commandManager;

    public TrelloViewModel(TrelloFacade trelloFacade) {
        this.trelloFacade = trelloFacade;
        this.commandManager = CommandManager.getInstance();
    }

    public BoardFacade getBoardFacade() {
        return trelloFacade.getBoardFacade();
    }

    public void undo() {
        commandManager.undo();
    }

    public void redo() {
        commandManager.redo();
    }

    public void commandCreateColumn() {
        CommandManager.getInstance().execute(new CreateColumnCommand(this.getBoardFacade()));
    }

    public void quit() {
        Platform.exit();
    }

    public StringProperty nextUndoableProperty() {
        return commandManager.nextUndoableProperty();
    }

    public StringProperty nextRedoableProperty() {
        return commandManager.nextRedoableProperty();
    }

    public BooleanProperty hasNoUndoableProperty() {
        return commandManager.hasNoUndoableProperty();
    }

    public BooleanProperty hasNoRedoableProperty() {
        return commandManager.hasNoRedoableProperty();
    }
}
