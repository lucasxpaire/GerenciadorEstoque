package com.gerencia.estoque.controller.transacao;

import com.gerencia.estoque.dao.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

public class AdicionarDemandaController {

    @FXML
    private TextField tfDescricaoProduto;

    @FXML
    private Label lblMensagem;

    private Integer idCliente;

    private Supplier<Integer> obterIdClienteFunc;


    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    // Define a função que será usada para obter o ID do cliente
    public void setObterIdClienteFunc(Supplier<Integer> obterIdClienteFunc) {
        this.obterIdClienteFunc = obterIdClienteFunc;
    }

    @FXML
    private void handleSalvarDemanda() {
        String descricao = tfDescricaoProduto.getText().trim();

        if (descricao.isEmpty()) {
            lblMensagem.setText("A descrição do produto é obrigatória.");
            return;
        }

        try (Connection conn = Database.getConnection()) {
            // Verificar se o produto já existe na tabela ItemDemandado
            Integer idItem = buscarIdItemDemandado(conn, descricao);

            if (idItem == null) {
                // Produto não existe, inserir na tabela ItemDemandado
                idItem = inserirItemDemandado(conn, descricao);
            }

            if (demandaJaExiste(conn, idItem)) {
                incrementarDemanda(conn, idItem);
                mostrarAlerta("Sucesso", "Demanda existente incrementada com sucesso.", Alert.AlertType.INFORMATION);
            } else {
                // Registrar a nova demanda
                inserirDemanda(conn, idItem);
                mostrarAlerta("Sucesso", "Nova demanda registrada com sucesso.", Alert.AlertType.INFORMATION);
            }

            // Fechar a tela após o alerta
            handleCancelar();

        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao salvar a demanda: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipoAlerta) {
        Alert alert = new Alert(tipoAlerta);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        Stage stage = (Stage) tfDescricaoProduto.getScene().getWindow();
        alert.initOwner(stage);
        alert.initModality(javafx.stage.Modality.APPLICATION_MODAL);

        alert.showAndWait();
    }


    private void incrementarDemanda(Connection conn, Integer idItem) throws SQLException {
        String sql = "UPDATE Demanda SET Contagem = Contagem + 1 WHERE IdItem = ? AND IdCliente = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idItem);
            stmt.setInt(2, idCliente);
            stmt.executeUpdate();
        }
    }


    private Integer buscarIdItemDemandado(Connection conn, String descricao) throws SQLException {
        String sql = "SELECT IdItem FROM ItemDemandado WHERE Descricao = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descricao);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("IdItem");
                }
            }
        }
        return null;
    }

    private Integer inserirItemDemandado(Connection conn, String descricao) throws SQLException {
        String sql = "INSERT INTO ItemDemandado (Descricao) VALUES (?) RETURNING IdItem";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descricao);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("IdItem");
                }
            }
        }
        throw new SQLException("Falha ao inserir o item demandado.");
    }

    private boolean demandaJaExiste(Connection conn, Integer idItem) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Demanda WHERE IdItem = ? AND IdCliente = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idItem);
            stmt.setInt(2, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private void inserirDemanda(Connection conn, Integer idItem) throws SQLException {
        String sql = "INSERT INTO Demanda (IdItem, IdCliente) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idItem);
            stmt.setInt(2, idCliente);
            stmt.executeUpdate();
        }
    }

    @FXML
    private void handleCancelar() {
        Stage stage = (Stage) tfDescricaoProduto.getScene().getWindow();
        stage.close();
    }


}
