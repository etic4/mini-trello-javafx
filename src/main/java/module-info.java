module demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires java.sql;
    requires json.simple;

    opens main to javafx.fxml;
    exports main;
    //exports mvvm;
    //exports model;
}