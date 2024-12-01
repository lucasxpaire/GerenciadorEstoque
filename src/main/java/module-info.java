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

    exports com.gerencia.estoque.controller.descontos;
    opens com.gerencia.estoque.controller.descontos to javafx.fxml;

    exports com.gerencia.estoque.controller.estoque;
    opens com.gerencia.estoque.controller.estoque to javafx.fxml;

    exports com.gerencia.estoque.controller.funcionarios;
    opens com.gerencia.estoque.controller.funcionarios to javafx.fxml;

    exports com.gerencia.estoque.controller.paineladm;
    opens com.gerencia.estoque.controller.paineladm to javafx.fxml;

    // Pacote de transação e cadastro de cliente
    exports com.gerencia.estoque.controller.transacao;
    opens com.gerencia.estoque.controller.transacao to javafx.fxml;

    exports com.gerencia.estoque.controller.clientes;
    opens com.gerencia.estoque.controller.clientes to javafx.fxml;

    // Modelos
    exports com.gerencia.estoque.model.funcionarios;
    opens com.gerencia.estoque.model.funcionarios to javafx.fxml;

    exports com.gerencia.estoque.model.estoque;
    opens com.gerencia.estoque.model.estoque to javafx.base;

    exports com.gerencia.estoque.model.transacao;
    opens com.gerencia.estoque.model.transacao to javafx.base;

    exports com.gerencia.estoque.model.desconto;
    opens com.gerencia.estoque.model.desconto to javafx.base;

    exports com.gerencia.estoque.model.clientes;
    opens com.gerencia.estoque.model.clientes to javafx.base;

    exports com.gerencia.estoque.model.relatorio;
    opens com.gerencia.estoque.model.relatorio to javafx.base;

}
