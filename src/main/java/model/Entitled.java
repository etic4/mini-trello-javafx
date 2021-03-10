package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Entitled {

    private StringProperty title = new SimpleStringProperty();

    public Entitled(String title) {
        this.title.setValue(title);
    }

    public String getTitle() {
        return title.getValue();
    }

    public void setTitle(String title) {
        this.title.setValue(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    @Override
    public String toString() {
        return title.getValue();
    }

}

