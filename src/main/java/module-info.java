module com.project.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    opens com.project.main to javafx.fxml ;
    exports com.project.main;
}