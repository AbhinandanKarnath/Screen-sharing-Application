module com.project.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires javafx.media;

    opens com.project.controller to javafx.fxml ;
    exports com.project.controller;
    exports com.project.model;
    opens com.project.model to javafx.fxml;
}