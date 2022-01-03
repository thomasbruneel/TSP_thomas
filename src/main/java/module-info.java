module com.example.tsp_thomas {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.tsp_thomas to javafx.fxml;
    exports com.example.tsp_thomas;
}