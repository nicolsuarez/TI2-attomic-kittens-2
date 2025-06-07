module game {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;


    opens gui to javafx.fxml;
    exports gui;
    exports model;
    exports controller;
    opens controller to javafx.fxml;
}