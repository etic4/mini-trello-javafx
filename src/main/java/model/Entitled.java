package model;

import javafx.beans.property.*;

public abstract class Entitled {

    private final ReadOnlyStringWrapper title = new ReadOnlyStringWrapper();

    public Entitled(String title) {
        this.title.set(title);
    }

    public String getTitle() {
        return title.getValue();
    }

    void setTitle(String title) {
        this.title.set(title);
    }

    public ReadOnlyStringProperty titleProperty() {
        return title.getReadOnlyProperty() ;
    }

    @Override
    public String toString() {
        return title.getValue();
    }
}

