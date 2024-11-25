package com.gerencia.estoque.controller.funcionarios;

import com.gerencia.estoque.dao.Database;
import com.gerencia.estoque.model.funcionarios.Funcionario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManterFuncionariosController {

    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoUsuario;
    @FXML
    private PasswordField campoSenha;
    @FXML
    private TextField campoTipo;

    @FXML
    private TableView<Funcionario> tableFuncionarios;
    @FXML
    private TableColumn<Funcionario, String> colunaNome;
    @FXML
    private TableColumn<Funcionario, String> colunaUsuario;
    @FXML
    private TableColumn<Funcionario, String> colunaTipo;
    @FXML
    private TableColumn<Funcionario, String> colunaSenha;

    private ObservableList<Funcionario> listaFuncionarios;

    @FXML
    public void initialize() {
        listaFuncionarios = FXCollections.observableArrayList();

        // Configurar o alinhamento de cada coluna
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setStyle("-fx-alignment: CENTER;");

        colunaUsuario.setCellValueFactory(param -> new SimpleStringProperty(getCredencialUsuario(param.getValue().getIdCredencial())));
        colunaUsuario.setStyle("-fx-alignment: CENTER;");

        colunaTipo.setCellValueFactory(param -> new SimpleStringProperty(getCredencialTipo(param.getValue().getIdCredencial())));
        colunaTipo.setStyle("-fx-alignment: CENTER;");

        colunaSenha.setCellValueFactory(param -> new SimpleStringProperty(getCredencialSenha(param.getValue().getIdCredencial())));
        colunaSenha.setStyle("-fx-alignment: CENTER;");

        tableFuncionarios.setItems(listaFuncionarios);
        carregarFuncionarios();

        tableFuncionarios.setOnMouseClicked(event -> {
            Funcionario funcionarioSelecionado = tableFuncionarios.getSelectionModel().getSelectedItem();
            if (funcionarioSelecionado != null) {
                campoNome.setText(funcionarioSelecionado.getNome());
                campoUsuario.setText(getCredencialUsuario(funcionarioSelecionado.getIdCredencial()));
                campoSenha.setText(getCredencialSenha(funcionarioSelecionado.getIdCredencial())); // Mostra a senha
                campoTipo.setText(getCredencialTipo(funcionarioSelecionado.getIdCredencial())); // Mostra o tipo
            }
        });
    }



    private String getCredencialTipo(int idCredencial) {
        String tipo = "";
        String sql = "SELECT tipo FROM credenciais WHERE idCredencial = ?";  // Alterado para 'credenciais'
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCredencial);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tipo = rs.getString("tipo");
            }
        } catch (SQLException e) {
            showAlert("Erro", "Erro ao buscar tipo: " + e.getMessage());
        }
        return tipo;
    }


    // Método para obter o usuário a partir da credencial
    private String getCredencialUsuario(int idCredencial) {
        String usuario = "";
        String sql = "SELECT usuario FROM credenciais WHERE idCredencial = ?";  // Alterado para 'credenciais'
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCredencial);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = rs.getString("usuario");
            }
        } catch (SQLException e) {
            showAlert("Erro", "Erro ao buscar usuário: " + e.getMessage());
        }
        return usuario;
    }


    // Método para obter a senha a partir da credencial (usado apenas para exibição segura)
    private String getCredencialSenha(int idCredencial) {
        String senha = "";
        String sql = "SELECT senha FROM credenciais WHERE idCredencial = ?";  // Alterado para 'credenciais'
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCredencial);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                senha = rs.getString("senha");
            }
        } catch (SQLException e) {
            showAlert("Erro", "Erro ao buscar senha: " + e.getMessage());
        }
        return senha;
    }


    private void carregarFuncionarios() {
        listaFuncionarios.clear();
        String sql = "SELECT f.idFuncionario, f.idCredencial, f.nome, c.usuario, c.tipo, c.senha FROM funcionario f " +
                "JOIN credenciais c ON f.idCredencial = c.idCredencial";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Funcionario funcionario = new Funcionario(
                        rs.getInt("idFuncionario"),
                        rs.getInt("idCredencial"),
                        rs.getString("nome")
                );
                // Aqui você pode definir a credencial como parte do objeto Funcionario se for necessário
                listaFuncionarios.add(funcionario);
            }
        } catch (SQLException e) {
            showAlert("Erro", "Erro ao carregar funcionários: " + e.getMessage());
        }
    }


    @FXML
    public void editarFuncionario() {
        Funcionario funcionarioSelecionado = tableFuncionarios.getSelectionModel().getSelectedItem();

        if (funcionarioSelecionado != null) {
            funcionarioSelecionado.setNome(campoNome.getText());
            String novoUsuario = campoUsuario.getText();
            String novaSenha = campoSenha.getText();

            atualizarCredencial(funcionarioSelecionado.getIdCredencial(), novoUsuario, novaSenha);
            atualizarFuncionario(funcionarioSelecionado);
            showAlert("Sucesso", "Funcionário editado com sucesso.");
            carregarFuncionarios();
            limparCampos(); // Limpa os campos após editar
        } else {
            showAlert("Erro", "Selecione um funcionário para editar.");
        }
    }

    @FXML
    public void removerFuncionario() {
        Funcionario funcionarioSelecionado = tableFuncionarios.getSelectionModel().getSelectedItem();

        if (funcionarioSelecionado != null) {
            int idFuncionario = funcionarioSelecionado.getIdFuncionario();
            int idCredencial = funcionarioSelecionado.getIdCredencial();

            String sqlFuncionario = "DELETE FROM funcionario WHERE idFuncionario = ?";
            String sqlCredencial = "DELETE FROM credenciais WHERE idCredencial = ?";

            try (Connection conn = Database.getConnection()) {
                conn.setAutoCommit(false); // Inicia a transação

                // Remove o funcionário
                try (PreparedStatement stmtFuncionario = conn.prepareStatement(sqlFuncionario)) {
                    stmtFuncionario.setInt(1, idFuncionario);
                    stmtFuncionario.executeUpdate();
                }

                // Remove a credencial
                try (PreparedStatement stmtCredencial = conn.prepareStatement(sqlCredencial)) {
                    stmtCredencial.setInt(1, idCredencial);
                    stmtCredencial.executeUpdate();
                }

                conn.commit(); // Confirma a transação
                showAlert("Sucesso", "Funcionário removido com sucesso.");
                carregarFuncionarios(); // Atualiza a lista de funcionários
                limparCampos(); // Limpa os campos após remover

            } catch (SQLException e) {
                showAlert("Erro", "Erro ao remover funcionário: " + e.getMessage());
            }
        } else {
            showAlert("Erro", "Selecione um funcionário para remover.");
        }
    }

    @FXML
    public void adicionarFuncionario() {
        String nome = campoNome.getText();
        String usuario = campoUsuario.getText();
        String senha = campoSenha.getText();
        String tipo = campoTipo.getText();

        if (nome.isEmpty() || usuario.isEmpty() || senha.isEmpty() || tipo.isEmpty()) {
            showAlert("Erro", "Todos os campos devem ser preenchidos.");
            return;
        }

        String sqlCredencial = "INSERT INTO credenciais (usuario, senha, tipo) VALUES (?, ?, ?)";
        String sqlFuncionario = "INSERT INTO funcionario (idCredencial, nome) VALUES (?, ?)";

        try (Connection conn = Database.getConnection()) {
            conn.setAutoCommit(false); // Inicia uma transação

            // Inserir na tabela credencial
            try (PreparedStatement stmtCredencial = conn.prepareStatement(sqlCredencial, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmtCredencial.setString(1, usuario);
                stmtCredencial.setString(2, senha);
                stmtCredencial.setString(3, tipo);
                stmtCredencial.executeUpdate();

                // Recupera o ID gerado para credencial
                ResultSet rsCredencial = stmtCredencial.getGeneratedKeys();
                int idCredencial = -1;
                if (rsCredencial.next()) {
                    idCredencial = rsCredencial.getInt(1);
                }

                if (idCredencial == -1) {
                    throw new SQLException("Falha ao recuperar ID da credencial.");
                }

                // Inserir na tabela funcionario usando o idCredencial gerado
                try (PreparedStatement stmtFuncionario = conn.prepareStatement(sqlFuncionario)) {
                    stmtFuncionario.setInt(1, idCredencial);
                    stmtFuncionario.setString(2, nome);
                    stmtFuncionario.executeUpdate();
                }

                conn.commit(); // Confirma a transação
                showAlert("Sucesso", "Funcionário adicionado com sucesso.");
                carregarFuncionarios();
                limparCampos(); // Limpa os campos após adicionar

            } catch (SQLException e) {
                conn.rollback(); // Reverte a transação em caso de erro
                showAlert("Erro", "Erro ao adicionar funcionário: " + e.getMessage());
            }

        } catch (SQLException e) {
            showAlert("Erro", "Erro de conexão: " + e.getMessage());
        }
    }


    private void atualizarCredencial(int idCredencial, String usuario, String senha) {
        String sql = "UPDATE credenciais SET usuario = ?, senha = ? WHERE idCredencial = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            stmt.setString(2, senha);
            stmt.setInt(3, idCredencial);
            stmt.executeUpdate();
        } catch (SQLException e) {
            showAlert("Erro", "Erro ao atualizar credencial: " + e.getMessage());
        }
    }

    private void atualizarFuncionario(Funcionario funcionario) {
        String sql = "UPDATE funcionario SET nome = ? WHERE idFuncionario = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setInt(2, funcionario.getIdFuncionario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            showAlert("Erro", "Erro ao atualizar funcionário: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Obtendo a janela principal para configurar o alerta acima dela
        Stage stage = (Stage) campoNome.getScene().getWindow();
        alert.initOwner(stage);  // Define o dono da janela do alerta
        alert.initModality(javafx.stage.Modality.APPLICATION_MODAL);  // Torna o alerta modal (na frente da janela principal)

        alert.showAndWait();  // Exibe o alerta
    }

    private void limparCampos() {
        campoNome.clear();
        campoUsuario.clear();
        campoSenha.clear();
        campoTipo.clear();
    }

    @FXML
    public void voltar(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/painel-prop/painel-prop.fxml", "Painel do Administrador", event);
    }

    private void carregarTela(String caminhoFXML, String titulo, ActionEvent event) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(caminhoFXML));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Falha ao carregar a tela: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
