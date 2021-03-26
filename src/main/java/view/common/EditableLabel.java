package view.common;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/*
* Editable Label fire event when text changed
* */
public class EditableLabel extends HBox {
    //text changed event type
    public static EventType<Event> TEXT_CHANGED = new EventType<>("TEXT_CHANGED");

    // text changed event
    static class TextChangedEvent extends Event {
        public TextChangedEvent() {
            super(TEXT_CHANGED);
        }
    }

    // texte field & text field backup
    private final TextField tf = new TextField();
    private String tfBackup;

    // disabled state of inner textfield
    private final BooleanProperty tfDisabled = new SimpleBooleanProperty(true);



    public EditableLabel() {
        buildView();
    }


    private void buildView() {
        //set css classes
        getStyleClass().add("el-container");
        tf.getStyleClass().add("el-text");

        // add textfield
        getChildren().add(tf);

        // set text field grow priority
        HBox.setHgrow(tf, Priority.ALWAYS);

        //bind text field disabled property
        tf.disableProperty().bind(tfDisabled);

        // Fire event when focus lose if text changed
        configEventFiring();

        configEventsHandling();
    }


    private void configEventFiring() {
        tf.focusedProperty().addListener((a, oldValue, newValue) -> {
            if (!newValue && !tf.getText().equals(tfBackup)) {
                fireEvent(new TextChangedEvent());
            }
        });
    }

    private void configEventsHandling() {
        /*
        * on double click edit start
        * if ENTER (or lose focus) tf.disabled
        * on ESC text field's text is restored to previous text
        * */
        this.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                tfBackup = tf.getText();
                tfDisabled.set(false);
                tf.requestFocus();
                event.consume();
            }
        });

        tf.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                tfDisabled.set(true);
            }

            if (event.getCode().equals(KeyCode.ESCAPE)) {
                tfDisabled.set(true);
                tf.setText(tfBackup);
            }
        });
    }

    public void setTextFieldId(String tfId) {
        tf.setId(tfId);
    }

    public void addTextFieldClasses(String... cssClasses) {
        tf.getStyleClass().addAll(cssClasses);
    }

    public String getText() {
        return tf.getText();
    }

    public StringProperty textProperty() {
        return tf.textProperty();
    }

}
