module game {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens gui to javafx.fxml;
    exports gui;
    exports model;
    exports controller;
    opens controller to javafx.fxml;
    exports resources;
}