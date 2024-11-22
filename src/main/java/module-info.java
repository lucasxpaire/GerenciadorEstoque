module com.gerencia.estoque {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    // Pacote principal
    exports com.gerencia.estoque;
    opens com.gerencia.estoque to javafx.fxml;

    // Controladores
    exports com.gerencia.estoque.controller;
    opens com.gerencia.estoque.controller to javafx.fxml;

    exports com.gerencia.estoque.controller.relatorio;
    opens com.gerencia.estoque.controller.relatorio to javafx.fxml;

    exports com.gerencia.estoque.controller.vender;
    opens com.gerencia.estoque.controller.vender to javafx.fxml;

    exports com.gerencia.estoque.controller.estoque;
    opens com.gerencia.estoque.controller.estoque to javafx.fxml;

    exports com.gerencia.estoque.controller.funcionarios;
    opens com.gerencia.estoque.controller.funcionarios to javafx.fxml;

    exports com.gerencia.estoque.controller.paineladm;
    opens com.gerencia.estoque.controller.paineladm to javafx.fxml;


}
