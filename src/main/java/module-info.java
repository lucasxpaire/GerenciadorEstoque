module com.gerencia.estoque {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gerencia.estoque to javafx.fxml;
    exports com.gerencia.estoque;
    exports com.gerencia.estoque.controller;
    opens com.gerencia.estoque.controller to javafx.fxml;
}