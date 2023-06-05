module com.project.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    opens com.project.main to javafx.fxml ;
    exports com.project.main;
    exports com.project.controller;
    opens com.project.controller to javafx.fxml;
    exports com.project.model;
    opens com.project.model to javafx.fxml;
}