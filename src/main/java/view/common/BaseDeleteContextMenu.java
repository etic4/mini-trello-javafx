package view.common;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.Optional;

// L'essentiel du delete contextuel est partagé par card et column
public abstract class BaseDeleteContextMenu extends ContextMenu {
    // title's object
    private final String title;

    protected abstract void executeDeleteCommand();


    public BaseDeleteContextMenu(String title) {
        super();
        this.title = title;
        buildMenu();
    }

    private void buildMenu() {
        // build and add menu delete
        buildMenuDelete();
    }

    private void buildMenuDelete() {
        MenuItem delete = new MenuItem("Delete " + title);
        delete.setOnAction(e -> askDeleteConfirmationAndDo());
        getItems().add(delete);
    }

    private void askDeleteConfirmationAndDo() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Delete " + title + " ?");

        Optional<ButtonType> action = alert.showAndWait();

        if(action.isPresent() && action.get() == ButtonType.OK) {
            executeDeleteCommand();
        }
    }
}

