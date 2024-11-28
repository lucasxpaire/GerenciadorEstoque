package com.gerencia.estoque.controller.descontos;

import com.gerencia.estoque.dao.Database;
import com.gerencia.estoque.model.desconto.Desconto;
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

public class ManterDescontosController {

    @FXML
    private TextField campoTipo;
    @FXML
    private TextField campoPercentual;
    @FXML
    private TextField campoDescricao;

    @FXML
    private TableView<Desconto> tableDescontos;
    @FXML
    private TableColumn<Desconto, String> colunaTipo;
    @FXML
    private TableColumn<Desconto, String> colunaPercentual;
    @FXML
    private TableColumn<Desconto, String> colunaDescricao;

    private ObservableList<Desconto> listaDescontos;

    @FXML
    public void initialize() {
        listaDescontos = FXCollections.observableArrayList();

        colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colunaTipo.setStyle("-fx-alignment: CENTER;");
        colunaPercentual.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPercentual() + "%"));
        colunaPercentual.setStyle("-fx-alignment: CENTER;");
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaDescricao.setStyle("-fx-alignment: CENTER;");

        tableDescontos.setItems(listaDescontos);
        carregarDescontos();

        tableDescontos.setOnMouseClicked(event -> {
            Desconto descontoSelecionado = tableDescontos.getSelectionModel().getSelectedItem();
            if (descontoSelecionado != null) {
                campoTipo.setText(descontoSelecionado.getTipo());
                campoPercentual.setText(String.valueOf(descontoSelecionado.getPercentual()));
                campoDescricao.setText(descontoSelecionado.getDescricao());
            }
        });
    }

    private void carregarDescontos() {
        listaDescontos.clear();
        String sql = "SELECT * FROM Desconto";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Desconto desconto = new Desconto(
                        rs.getInt("IdDesconto"),
                        rs.getString("Tipo"),
                        rs.getDouble("Percentual"),
                        rs.getString("Descricao")
                );
                listaDescontos.add(desconto);
            }
        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao carregar descontos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void adicionarDesconto() {
        String tipo = campoTipo.getText();
        String percentualTexto = campoPercentual.getText();
        String descricao = campoDescricao.getText();

        if (tipo.isEmpty() || percentualTexto.isEmpty()) {
            mostrarAlerta("Erro", "Os campos 'Tipo' e 'Percentual' são obrigatórios.", Alert.AlertType.WARNING);
            return;
        }

        try {
            double percentual = Double.parseDouble(percentualTexto);
            String sql = "INSERT INTO Desconto (Tipo, Percentual, Descricao) VALUES (?, ?, ?)";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, tipo);
                stmt.setDouble(2, percentual);
                stmt.setString(3, descricao);
                stmt.executeUpdate();
                mostrarAlerta("Sucesso", "Desconto adicionado com sucesso.", Alert.AlertType.INFORMATION);
                carregarDescontos();
                limparCampos();
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "O campo 'Percentual' deve ser um número.", Alert.AlertType.WARNING);
        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao adicionar desconto: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void editarDesconto() {
        Desconto descontoSelecionado = tableDescontos.getSelectionModel().getSelectedItem();
        if (descontoSelecionado == null) {
            mostrarAlerta("Erro", "Selecione um desconto para editar.", Alert.AlertType.WARNING);
            return;
        }

        String tipo = campoTipo.getText();
        String percentualTexto = campoPercentual.getText();
        String descricao = campoDescricao.getText();

        if (tipo.isEmpty() || percentualTexto.isEmpty()) {
            mostrarAlerta("Erro", "Os campos 'Tipo' e 'Percentual' são obrigatórios.", Alert.AlertType.WARNING);
            return;
        }

        try {
            double percentual = Double.parseDouble(percentualTexto);
            String sql = "UPDATE Desconto SET Tipo = ?, Percentual = ?, Descricao = ? WHERE IdDesconto = ?";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, tipo);
                stmt.setDouble(2, percentual);
                stmt.setString(3, descricao);
                stmt.setInt(4, descontoSelecionado.getIdDesconto());
                stmt.executeUpdate();
                mostrarAlerta("Sucesso", "Desconto editado com sucesso.", Alert.AlertType.INFORMATION);
                carregarDescontos();
                limparCampos();
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "O campo 'Percentual' deve ser um número.", Alert.AlertType.WARNING);
        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao editar desconto: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void removerDesconto() {
        Desconto descontoSelecionado = tableDescontos.getSelectionModel().getSelectedItem();
        if (descontoSelecionado == null) {
            mostrarAlerta("Erro", "Selecione um desconto para remover.", Alert.AlertType.WARNING);
            return;
        }

        String sql = "DELETE FROM Desconto WHERE IdDesconto = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, descontoSelecionado.getIdDesconto());
            stmt.executeUpdate();
            mostrarAlerta("Sucesso", "Desconto removido com sucesso.", Alert.AlertType.INFORMATION);
            carregarDescontos();
            limparCampos();
        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao remover desconto: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void voltar(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/painel-prop/painel-prop.fxml", "Painel do Proprietário", event);
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
            mostrarAlerta("Erro", "Falha ao carregar a tela: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipoAlerta) {
        Alert alert = new Alert(tipoAlerta);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        Stage stage = (Stage) campoTipo.getScene().getWindow();
        alert.initOwner(stage);
        alert.initModality(javafx.stage.Modality.APPLICATION_MODAL);

        alert.showAndWait();
    }

    private void limparCampos() {
        campoTipo.clear();
        campoPercentual.clear();
        campoDescricao.clear();
    }
}
