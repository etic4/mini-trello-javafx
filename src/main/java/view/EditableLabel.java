package view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class EditableLabel extends HBox {

    private TextField tf = new TextField();
    BooleanProperty
            tfDisabled = new SimpleBooleanProperty(true),
            editAborted = new SimpleBooleanProperty(false);

    public EditableLabel(String... cssClasses) {
        getChildren().add(tf);
        tf.disableProperty().bind(tfDisabled);
        HBox.setHgrow(tf, Priority.ALWAYS);

        getStyleClass().add("el-container");

        addTextFieldCssClasses(cssClasses);
        configActions();
    }

    public EditableLabel(){
        this((String) null);
    }

    private void addTextFieldCssClasses(String[] cssClasses) {
        tf.getStyleClass().add("el-text");
        tf.getStyleClass().addAll(cssClasses);
    }

    public void setTextFieldId(String tfId) {
        tf.setId(tfId);
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

    public ReadOnlyBooleanProperty tfFocusedProperty() {
        return tf.focusedProperty();
    }

    public StringProperty textProperty() {
        return tf.textProperty();
    }

}
