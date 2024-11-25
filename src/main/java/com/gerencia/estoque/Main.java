package com.gerencia.estoque;

import com.gerencia.estoque.dao.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            String sql = "INSERT INTO credenciais (usuario, senha, tipo) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, "prop");
                statement.setString(2, "prop123");
                statement.setString(3, "proprietario");
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}