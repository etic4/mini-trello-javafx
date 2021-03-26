package view.common;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.Optional;

/* Base context menu, un seul élément actuellement, pourrait donc s'appeller BaseDeleteContextMenu,
* Mais ça me semble exagérément spécifique
* */

public abstract class BaseContextMenu extends ContextMenu {
    private final String title;

    public BaseContextMenu(String title) {
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
        delete.setOnAction(e -> askDeleteConfirmation());
        getItems().add(delete);
    }

    private void askDeleteConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Delete " + title + " ?");

        Optional<ButtonType> action = alert.showAndWait();

        if(action.isPresent() && action.get() == ButtonType.OK) {
            executeDeleteCommand();
        }
    }

    protected abstract void executeDeleteCommand();
}

