module com.project.main {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.project.main to javafx.fxml;
    exports com.project.main;
}