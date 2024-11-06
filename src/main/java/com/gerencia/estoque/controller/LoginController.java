package com.gerencia.estoque.controller;

import com.gerencia.estoque.dao.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usuarioTextField;

    @FXML
    private PasswordField senhaPasswordField;

    @FXML
    private Button loginButton;

    @FXML
    public void handleLogin(ActionEvent event) {
        String usuario = usuarioTextField.getText();
        String senha = senhaPasswordField.getText();

        // Verificação de login no banco de dados
        if (usuario.isEmpty() || senha.isEmpty()) {
            mostrarAlerta("Erro", "Por favor, preencha todos os campos.");
        } else if (verificarLogin(usuario, senha)) {
            // Se o usuário contém 'adm', redireciona para a tela de Proprietário
            if (usuario.contains("adm")) {
                carregarTela("/com/gerencia/estoque/painel-adm.fxml", "Visão Geral do Dono");
            } else {
                // Senão, redireciona para a tela de Funcionário
                carregarTela("/com/gerencia/estoque/funcionario-view.fxml", "Tela do Funcionário");
            }
        } else {
            mostrarAlerta("Erro", "Usuário ou senha incorretos.");
        }
    }

    // Método para verificar o login no banco de dados
    private boolean verificarLogin(String usuario, String senha) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM usuarios WHERE usuario = ? AND senha = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, senha);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Retorna true se encontrar um usuário correspondente
        } catch (SQLException e) {
            mostrarAlerta("Erro", "Falha ao conectar ao banco de dados: " + e.getMessage());
            return false;
        }
    }

    // Método para carregar a tela específica com opção de maximização
    private void carregarTela(String caminhoFXML, String titulo) {
        try {
            // Obtém a janela atual e troca a cena para a próxima tela
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(caminhoFXML));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.setFullScreen(true); // Maximiza a janela se solicitado
            stage.setFullScreenExitHint("");
            stage.show();
        } catch (IOException e) {
            mostrarAlerta("Erro", "Falha ao carregar a tela: " + e.getMessage());
        }
    }

    // Método para mostrar alertas
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
