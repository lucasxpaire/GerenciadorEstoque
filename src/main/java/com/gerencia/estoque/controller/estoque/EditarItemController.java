package com.gerencia.estoque.controller.estoque;

import com.gerencia.estoque.dao.Database;
import com.gerencia.estoque.model.estoque.Estoque;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditarItemController {

    @FXML
    private TextField campoDescricao;

    @FXML
    private TextField campoPreco;

    private Estoque itemEstoque; // Item que está sendo editado

    /**
     * Define o item do estoque a ser editado e preenche os campos com os valores atuais.
     *
     * @param itemEstoque Item selecionado na tabela.
     */
    public void setItemEstoque(Estoque itemEstoque) {
        this.itemEstoque = itemEstoque;
        campoDescricao.setText(itemEstoque.getDescricao());
        campoPreco.setText(String.valueOf(itemEstoque.getPreco()));
    }

    /**
     * Salva as alterações feitas no item do estoque.
     */
    @FXML
    private void salvarAlteracoes() {
        String novaDescricao = campoDescricao.getText();
        double novoPreco;

        try {
            novoPreco = Double.parseDouble(campoPreco.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "O preço deve ser um valor numérico.");
            return;
        }

        String sqlProduto = "UPDATE Produto SET Descricao = ?, Preco = ? WHERE IdProduto = ?";
        String sqlEstoque = "UPDATE Estoque SET Descricao = ?, Preco = ? WHERE IdProduto = ?";

        try (Connection connection = Database.getConnection()) {
            // Atualiza a tabela Produto
            try (PreparedStatement psProduto = connection.prepareStatement(sqlProduto)) {
                psProduto.setString(1, novaDescricao);
                psProduto.setDouble(2, novoPreco);
                psProduto.setInt(3, itemEstoque.getIdProduto());
                psProduto.executeUpdate();
            }

            // Atualiza a tabela Estoque
            try (PreparedStatement psEstoque = connection.prepareStatement(sqlEstoque)) {
                psEstoque.setString(1, novaDescricao);
                psEstoque.setDouble(2, novoPreco);
                psEstoque.setInt(3, itemEstoque.getIdProduto());
                psEstoque.executeUpdate();
            }

            mostrarAlerta("Sucesso", "Item atualizado com sucesso.");
            fecharJanela();

        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao salvar alterações no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Cancela a edição e fecha a janela.
     */
    @FXML
    private void cancelarEdicao() {
        fecharJanela();
    }

    /**
     * Fecha a janela atual.
     */
    private void fecharJanela() {
        Stage stage = (Stage) campoDescricao.getScene().getWindow();
        stage.close();
    }

    /**
     * Exibe um alerta ao usuário.
     *
     * @param titulo   Título do alerta.
     * @param mensagem Mensagem do alerta.
     */
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        // Obtendo a janela principal para configurar o alerta acima dela
        Stage stage = (Stage) campoDescricao.getScene().getWindow();
        alert.initOwner(stage);  // Define o dono da janela do alerta
        alert.initModality(javafx.stage.Modality.APPLICATION_MODAL);  // Torna o alerta modal (na frente da janela principal)

        alert.showAndWait();  // Exibe o alerta
    }
}
