package com.gerencia.estoque;

import com.gerencia.estoque.dao.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            if (!Database.areCredentialsConfigured()) {
                addDefaultCredentials();
            }
            Parent root = FXMLLoader.load(getClass().getResource("/com/gerencia/estoque/login.fxml"));
            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(root));
            primaryStage.setFullScreen(true);
            primaryStage.setFullScreenExitHint("");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void addDefaultCredentials() {
        try (Connection connection = Database.getConnection()) {
            // Inserir credenciais para o proprietário
            String sql = "INSERT INTO credenciais (usuario, senha, tipo) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, "prop");
                statement.setString(2, "prop123");
                statement.setString(3, "Proprietário");
                statement.executeUpdate();

                // Obter o ID da credencial recém inserida
                ResultSet rsCredenciais = statement.getGeneratedKeys();
                int idCredencial = -1;
                if (rsCredenciais.next()) {
                    idCredencial = rsCredenciais.getInt(1);
                }

                if (idCredencial == -1) {
                    throw new SQLException("Erro ao obter o ID da credencial.");
                }

                // Inserir o proprietário na tabela funcionario
                String sqlFuncionario = "INSERT INTO funcionario (idCredencial, nome) VALUES (?, ?)";
                try (PreparedStatement statementFuncionario = connection.prepareStatement(sqlFuncionario)) {
                    statementFuncionario.setInt(1, idCredencial);
                    statementFuncionario.setString(2, "Lucas");
                    statementFuncionario.executeUpdate();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}