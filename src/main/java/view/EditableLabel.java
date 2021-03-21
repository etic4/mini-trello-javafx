package view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class EditableLabel extends HBox {

    TextField tf = new TextField();

    BooleanProperty
            tfDisabled = new SimpleBooleanProperty(true),
            editAborted = new SimpleBooleanProperty(false);



    //   CONSTRUCTOR

    public EditableLabel() {
        this.getChildren().add(tf);
        configStyles();
        configActions();
        tf.disableProperty().bind(tfDisabled);
    }


    //   METHODS

    private void configStyles() {
        tf.setOpacity(1);
        tf.setAlignment(Pos.CENTER);
        HBox.setHgrow(tf, Priority.ALWAYS);
    }

    private void configActions() {
        this.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                tfDisabled.set(false);
                editAborted.set(false);
                tf.requestFocus();
                event.consume();
            }
        });

        tf.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                tfDisabled.set(true);
            }

            if (event.getCode().equals(KeyCode.ESCAPE)) {
                editAborted.set(true);
                tfDisabled.set(true);
            }
        });
    }

    public BooleanProperty editAbortedProperty() {
        return editAborted;
    }

}
