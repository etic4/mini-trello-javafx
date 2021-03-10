module demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;

    opens main to javafx.fxml;
    exports main;
    //exports mvvm;
    //exports model;
}