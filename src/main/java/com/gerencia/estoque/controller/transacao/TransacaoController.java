package com.gerencia.estoque.controller.transacao;

import com.gerencia.estoque.dao.Database;
import com.gerencia.estoque.model.transacao.Cliente;
import com.gerencia.estoque.model.transacao.Funcionario;
import com.gerencia.estoque.model.transacao.ItemResumo;
import com.gerencia.estoque.model.transacao.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Pos;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransacaoController {

    @FXML
    private ComboBox<Produto> cbProduto;

    @FXML
    private TextField tfQuantidade;

    @FXML
    private ComboBox<Cliente> cbCliente;

    @FXML
    private TableView<ItemResumo> tvResumo;

    @FXML
    private TableColumn<ItemResumo, String> colProduto;

    @FXML
    private TableColumn<ItemResumo, String> colQuantidade;

    @FXML
    private TableColumn<ItemResumo, String> colPreco;

    private ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();
    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
    private ObservableList<ItemResumo> resumoTransacao = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Funcionario> cbFuncionario;

    private ObservableList<Funcionario> listaFuncionarios = FXCollections.observableArrayList();

    @FXML
    private Label lblPontos;
    @FXML
    private Label lblDesconto;

    @FXML
    private Label lblPrecoTotal;

    private Cliente clienteSelecionado;
    private double valorTotal = 0.0;

    @FXML
    private ComboBox<String> cbDesconto;

    @FXML
    public void initialize() {
        carregarProdutos();
        carregarClientes();
        carregarFuncionarios();  // Carregar os funcionários

        // Configuração da tabela de resumo
        colProduto.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescricao()));
        colQuantidade.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getQuantidade())));
        colPreco.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getPrecoTotal())));

        // Centralizar as colunas na tabela
        colProduto.setStyle("-fx-alignment: center;");
        colQuantidade.setStyle("-fx-alignment: center;");
        colPreco.setStyle("-fx-alignment: center;");
        cbDesconto.setValue("Nenhum Desconto");


    }

    private void carregarFuncionarios() {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT IdFuncionario, Nome FROM Funcionario";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    listaFuncionarios.add(new Funcionario(
                            rs.getInt("IdFuncionario"),
                            rs.getString("Nome")
                    ));
                }
                cbFuncionario.setItems(listaFuncionarios);
            }
        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao carregar funcionários: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void carregarProdutos() {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT IdProduto, Descricao, Preco FROM Produto";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    listaProdutos.add(new Produto(
                            rs.getInt("IdProduto"),
                            rs.getString("Descricao"),
                            rs.getDouble("Preco")
                    ));
                }
                cbProduto.setItems(listaProdutos);
            }
        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao carregar produtos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void carregarClientes() {
        listaClientes.clear(); // Limpa a lista antes de recarregar os dados

        try (Connection connection = Database.getConnection()) {
            String query = "SELECT IdCliente, Nome, CPF FROM Cliente";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    listaClientes.add(new Cliente(
                            rs.getInt("IdCliente"),
                            rs.getString("Nome"),
                            rs.getString("CPF")
                    ));
                }
                cbCliente.setItems(listaClientes);
            }
        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao carregar clientes: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void adicionarItem(ActionEvent event) {
        Produto produtoSelecionado = cbProduto.getValue();
        String quantidadeTexto = tfQuantidade.getText();

        if (produtoSelecionado != null && !quantidadeTexto.isEmpty()) {
            try {
                int quantidade = Integer.parseInt(quantidadeTexto);
                double precoTotal = produtoSelecionado.getPreco() * quantidade;

                ItemResumo item = new ItemResumo(produtoSelecionado.getDescricao(), quantidade, precoTotal);

                resumoTransacao.add(item);
                tvResumo.setItems(resumoTransacao);

                // Atualiza o valor total após adicionar o item
                valorTotal += precoTotal;  // Acumula o valor total
                atualizarValorTotal(valorTotal); // Atualiza a label com o valor total

                tfQuantidade.clear();
            } catch (NumberFormatException e) {
                mostrarAlerta("Erro", "A quantidade deve ser um número válido.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Erro", "Selecione um produto e informe a quantidade.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleConfirmarTransacao() {

        Funcionario funcionarioSelecionado = cbFuncionario.getValue(); // Obter o funcionário selecionado

        if (funcionarioSelecionado == null || resumoTransacao.isEmpty()) {
            mostrarAlerta("Erro", "Selecione um funcionário e adicione produtos à transação.", Alert.AlertType.ERROR);
            return;
        }

        Cliente clienteSelecionado = cbCliente.getValue(); // Cliente pode ser nulo (opcional)
        double valorTotalTransacao = valorTotal; // Usa o valor acumulado inicial

        try (Connection connection = Database.getConnection()) {
            // Verifica se é a primeira compra do cliente
            boolean primeiraCompra = true;
            if (clienteSelecionado != null) {
                String queryVerificaPrimeiraCompra = "SELECT COUNT(*) FROM Transacao WHERE IdCliente = ?";
                try (PreparedStatement statementVerifica = connection.prepareStatement(queryVerificaPrimeiraCompra)) {
                    statementVerifica.setInt(1, clienteSelecionado.getIdCliente());
                    ResultSet rsVerifica = statementVerifica.executeQuery();
                    if (rsVerifica.next() && rsVerifica.getInt(1) > 0) {
                        primeiraCompra = false;
                    }
                }
            }

            // Aplica o desconto de 15% se for a primeira compra
            if (primeiraCompra && clienteSelecionado != null) {
                valorTotalTransacao *= 0.85;  // Aplica o desconto de 15%
                mostrarAlerta("Desconto Aplicado", "Foi aplicado um cupom de 15% de desconto por ser a primeira compra do cliente.", Alert.AlertType.INFORMATION);
            }

            // Processa cada item da transação
            for (ItemResumo item : resumoTransacao) {
                Produto produtoSelecionado = listaProdutos.stream()
                        .filter(p -> p.getDescricao().equals(item.getDescricao()))
                        .findFirst()
                        .orElse(null);

                if (produtoSelecionado == null) {
                    continue;
                }

                // Verifica se há estoque suficiente
                String queryEstoque = "SELECT Quantidade FROM Estoque WHERE IdProduto = ?";
                try (PreparedStatement statementEstoque = connection.prepareStatement(queryEstoque)) {
                    statementEstoque.setInt(1, produtoSelecionado.getIdProduto());
                    ResultSet rsEstoque = statementEstoque.executeQuery();
                    if (rsEstoque.next()) {
                        int estoqueAtual = rsEstoque.getInt("Quantidade");
                        if (estoqueAtual < item.getQuantidade()) {
                            mostrarAlerta("Erro", "Quantidade insuficiente de " + item.getDescricao() + " no estoque.", Alert.AlertType.ERROR);
                            return;
                        }
                    }
                }

                // Atualiza o estoque após a venda
                String queryUpdateEstoque = "UPDATE Estoque SET Quantidade = Quantidade - ? WHERE IdProduto = ?";
                try (PreparedStatement statementUpdateEstoque = connection.prepareStatement(queryUpdateEstoque)) {
                    statementUpdateEstoque.setInt(1, item.getQuantidade());
                    statementUpdateEstoque.setInt(2, produtoSelecionado.getIdProduto());
                    statementUpdateEstoque.executeUpdate();
                }

                // Adicionar transação
                String query = "INSERT INTO Transacao (IdProduto, IdFuncionario, IdCliente, Preco, Descricao, Quantidade, Tipo) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, produtoSelecionado.getIdProduto());
                    statement.setInt(2, funcionarioSelecionado.getIdFuncionario());

                    // Define o ID do cliente ou NULL
                    if (clienteSelecionado != null) {
                        statement.setInt(3, clienteSelecionado.getIdCliente());
                    } else {
                        statement.setNull(3, java.sql.Types.INTEGER);
                    }

                    statement.setDouble(4, item.getPrecoTotal());
                    statement.setString(5, item.getDescricao());
                    statement.setInt(6, item.getQuantidade());
                    statement.setString(7, "Venda");
                    statement.executeUpdate();
                }
            }

            // Verifica e aplica desconto adicional, se selecionado
            String descontoSelecionado = cbDesconto.getValue();
            if (descontoSelecionado != null && !descontoSelecionado.isEmpty()) {
                double percentualDesconto = 0.0;
                switch (descontoSelecionado) {
                    case "5% de Desconto":
                        percentualDesconto = 5.0;
                        break;
                    case "10% de Desconto":
                        percentualDesconto = 10.0;
                        break;
                    case "20% de Desconto":
                        percentualDesconto = 20.0;
                        break;
                }
                valorTotalTransacao -= valorTotalTransacao * (percentualDesconto / 100);
            }

            // Atualiza pontos do cliente se ele for cadastrado
            if (clienteSelecionado != null) {
                double pontosAcumulados = (valorTotalTransacao / 10) * 4; // Exemplo: 5 pontos a cada 10 reais
                String updatePontos = "UPDATE Fidelidade SET Pontos = Pontos + ? WHERE IdCliente = ?";
                try (PreparedStatement statementUpdatePontos = connection.prepareStatement(updatePontos)) {
                    statementUpdatePontos.setDouble(1, pontosAcumulados);
                    statementUpdatePontos.setInt(2, clienteSelecionado.getIdCliente());
                    statementUpdatePontos.executeUpdate();
                }
            }

            // Limpa os dados após a transação
            resumoTransacao.clear();
            tvResumo.refresh();
            // Limpa o valor total após a transação e atualiza o label de preço total
            valorTotal = 0.0;  // Resetando o preço total
            lblPrecoTotal.setText("Preço Total: R$ 0,00"); // Atualiza o texto do label
            // Limpa os ComboBoxes após a transação

            // Mensagem de sucesso
            mostrarAlerta("Sucesso", "Transação concluída com sucesso! Valor total: " + valorTotalTransacao, Alert.AlertType.INFORMATION);
            recarregarTela();


        } catch (SQLException e) {
            mostrarAlerta("Erro", "Falha ao processar a transação: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void recarregarTela() {
        try {
            // Obter o stage atual
            Stage stage = (Stage) tvResumo.getScene().getWindow();

            // Carregar a tela novamente (mesma cena)
            Parent root = FXMLLoader.load(getClass().getResource("/com/gerencia/estoque/transacao/transacao.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);  // Substitui a cena atual
            stage.setFullScreen(true);
            stage.setTitle("Nova Transação");  // Opcional: Modificar o título, se necessário
            stage.show();  // Mostra a nova cena
        } catch (IOException e) {
            mostrarAlerta("Erro", "Falha ao recarregar a tela: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleAbrirCadastroCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gerencia/estoque/transacao/CadastroCliente.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Cadastro de Cliente");
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.setResizable(false); // Impede redimensionamento
            Stage ownerStage = (Stage) tvResumo.getScene().getWindow();
            stage.initOwner(ownerStage);
            stage.showAndWait();


            carregarClientes();
        } catch (IOException e) {
            mostrarAlerta("Erro", "Não foi possível abrir o cadastro de cliente: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void verificarFidelidade() {
        clienteSelecionado = cbCliente.getValue();
        if (clienteSelecionado == null) {
            mostrarAlerta("Erro", "Selecione um cliente para verificar fidelidade.", Alert.AlertType.ERROR);
            return;
        }

        try (Connection connection = Database.getConnection()) {
            String queryFidelidade = "SELECT * FROM Fidelidade WHERE IdCliente = ?";
            try (PreparedStatement statement = connection.prepareStatement(queryFidelidade)) {
                statement.setInt(1, clienteSelecionado.getIdCliente());
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    int pontos = rs.getInt("Pontos");
                    lblPontos.setText("Pontos: " + pontos);

                    // Consultar tabela de descontos baseada nos pontos de fidelidade
                    ObservableList<String> descontosDisponiveis = FXCollections.observableArrayList();
                    descontosDisponiveis.add("Nenhum Desconto"); // Opção padrão sem desconto
                    if (pontos >= 20 && pontos < 50) {
                        descontosDisponiveis.add("5% de Desconto");
                    }
                    if (pontos >= 50 && pontos < 100) {
                        descontosDisponiveis.add("5% de Desconto");
                        descontosDisponiveis.add("10% de Desconto");
                    }
                    if (pontos >= 100) {
                        descontosDisponiveis.add("5% de Desconto");
                        descontosDisponiveis.add("10% de Desconto");
                        descontosDisponiveis.add("20% de Desconto");
                    }

                    // Adiciona os descontos ao ComboBox
                    cbDesconto.setItems(descontosDisponiveis);
                    cbDesconto.setVisible(true); // Torna o ComboBox visível

                    // Se nenhum desconto for aplicável
                    if (descontosDisponiveis.isEmpty()) {
                        lblDesconto.setText("Desconto: 0%");
                        cbDesconto.setVisible(false); // Oculta o ComboBox se não houver descontos
                    }

                    // Listener para atualizar o valor total quando o desconto for selecionado
                    cbDesconto.setOnAction(event -> {
                        String descontoSelecionado = cbDesconto.getValue();
                        double percentualDesconto = 0.0;
                        if (descontoSelecionado != null) {
                            switch (descontoSelecionado) {
                                case "5% de Desconto":
                                    percentualDesconto = 5.0;
                                    break;
                                case "10% de Desconto":
                                    percentualDesconto = 10.0;
                                    break;
                                case "20% de Desconto":
                                    percentualDesconto = 20.0;
                                    break;
                                case "Nenhum Desconto":
                                    percentualDesconto = 0.0; // Sem desconto
                                    break;
                            }
                        }

                        // Atualiza o lblDesconto com o desconto selecionado
                        lblDesconto.setText("Desconto: " + percentualDesconto + "%");

                        // Atualiza o valor total com o desconto
                        atualizarValorTotalComDesconto(percentualDesconto);
                    });
                }
            }
        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao verificar fidelidade: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void atualizarValorTotalComDesconto(double percentualDesconto) {
        // Calcula o valor total com desconto
        double valorComDesconto = calcularTotalComDesconto(percentualDesconto);

        // Atualiza o texto da label com o novo valor total
        lblPrecoTotal.setText("Valor Total: R$ " + String.format("%.2f", valorComDesconto));
    }

    private double calcularTotalComDesconto(double percentualDesconto) {
        double total = 0.0;
        for (ItemResumo item : tvResumo.getItems()) {
            total += item.getPrecoTotal();
        }
        return total - (total * (percentualDesconto / 100));
    }

    private void atualizarValorTotal(double total) {
        // Atualiza o texto da label com o valor total formatado
        lblPrecoTotal.setText("Valor Total: R$ " + String.format("%.2f", total));
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Falha ao carregar a tela: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipoAlerta) {
        Alert alert = new Alert(tipoAlerta);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        // Obtém a janela principal para centralizar o alerta
        Stage stage = (Stage) tvResumo.getScene().getWindow();
        alert.initOwner(stage);
        alert.initModality(javafx.stage.Modality.APPLICATION_MODAL);

        // Maximiza a janela principal para garantir que o alerta esteja visível
        //stage.setMaximized(true);

        // Exibe o alerta
        alert.showAndWait();
    }


}