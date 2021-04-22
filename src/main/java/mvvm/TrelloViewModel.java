package mvvm;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import model.Column;
import mvvm.command.CommandManager;
import mvvm.command.CreateCardCommand;
import mvvm.command.CreateColumnCommand;
import javafx.application.Platform;
import model.BoardFacade;
import model.TrelloFacade;

/* Cette classe est "une sorte de singleton" qui est paramétré
lors de l'appel à TrelloViewModel.init(). L'instance peut ensuite être obtenue avec un getInstance();
Permet de binder tout ce qu'on veut depuis d'autres instance de ViewModel, principalement pour gérer des actions
de menus
Est-ce correct de faire ça ? Est-ce que c'est toujours un singleton ?
*/

public class TrelloViewModel {
    public static TrelloViewModel instance = null;

    private final TrelloFacade trelloFacade;
    private final BooleanProperty noColumnSelected = new SimpleBooleanProperty(false);
    private final ObjectProperty<Column> selectedColumn = new SimpleObjectProperty<>();

    public static void init(TrelloFacade trelloFacade) {
        if (instance != null) {
            throw new RuntimeException("TrelloViewModel a déjà été initialisé !");
        }
        instance = new TrelloViewModel(trelloFacade);
    }

    public static TrelloViewModel getInstance() {
        if (instance == null) {
            throw new RuntimeException("TrelloViewModel n'a pas encore été initialisé !");
        }
        return instance;
    }


    private TrelloViewModel(TrelloFacade trelloFacade) {
        this.trelloFacade = trelloFacade;
        noColumnSelected.bind(Bindings.isNull(selectedColumn));
    }

    public BoardFacade getBoardFacade() {
        return trelloFacade.getBoardFacade();
    }

    public void commandCreateColumn() {
        CommandManager.execute(new CreateColumnCommand(getBoardFacade()));
    }

    public void commandCreateCard() {
        CommandManager.execute(new CreateCardCommand(selectedColumn.get(), getBoardFacade()));
    }

    public void commandQuit() {
        Platform.exit();
    }

    public void bindSelectedColumn(ReadOnlyObjectProperty<Column> selectedColumnProperty) {
        selectedColumn.bind(selectedColumnProperty);
    }

    public ReadOnlyBooleanProperty noColumnSelectedProperty() {
        return noColumnSelected;
    }
}
