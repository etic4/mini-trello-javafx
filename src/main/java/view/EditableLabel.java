package view;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public class EditableLabel extends Label {

    TextField tf = new TextField();
    String backup = "";

    public EditableLabel(){
        this("");
    }

    public EditableLabel(String str){
        super(str);
        this.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                tf.setText(backup = this.getText());
                this.setGraphic(tf);
                this.setText("");
                tf.requestFocus();
            }
        });

        tf.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if(!newVal){
                toLabel();
            }
        });

        tf.setOnKeyReleased(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                toLabel();
            }
            else if(event.getCode().equals(KeyCode.ESCAPE)){
                tf.setText(backup);
                toLabel();
            }
        });

    }

    private void toLabel(){
        this.setGraphic(null);
        this.setText(tf.getText());
    }

}