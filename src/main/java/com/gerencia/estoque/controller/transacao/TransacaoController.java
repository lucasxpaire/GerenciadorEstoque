package com.gerencia.estoque.controller.transacao;

import com.gerencia.estoque.dao.Database;
import com.gerencia.estoque.model.transacao.Cliente;
import com.gerencia.estoque.model.transacao.Funcionario;
import com.gerencia.estoque.model.transacao.ItemResumo;
import com.gerencia.estoque.model.transacao.Produto;
import javafx.application.Platform;
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
import java.util.Comparator;

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
        Funcionario funcionarioSelecionado = cbFuncionario.getValue();
        if (funcionarioSelecionado == null || resumoTransacao.isEmpty()) {
            mostrarAlerta("Erro", "Selecione um funcionário e adicione produtos à transação.", Alert.AlertType.ERROR);
            return;
        }

        Cliente clienteSelecionado = cbCliente.getValue();
        double valorTotalTransacao = valorTotal; // Base inicial para o cálculo.

        try (Connection connection = Database.getConnection()) {
            // Aplica descontos com base no desconto selecionado
            String descontoSelecionado = cbDesconto.getValue();
            double percentualDesconto = 0.0;

            if (descontoSelecionado != null) {
                // Extrai o percentual de desconto da string usando o método de conversão
                percentualDesconto = obterPercentualDesconto(descontoSelecionado);

                // Aplica o desconto ao valor total da transação
                valorTotalTransacao -= valorTotalTransacao * (percentualDesconto / 100);
            }

            // Aplica desconto de 15% para a primeira compra, se aplicável
            if (clienteSelecionado != null && isPrimeiraCompra(connection, clienteSelecionado)) {
                valorTotalTransacao *= 0.85; // Aplica o desconto de 15% para primeira compra
                mostrarAlerta("Desconto Aplicado", "Foi aplicado um cupom de 15% de desconto por ser a primeira compra do cliente.", Alert.AlertType.INFORMATION);
            }

            // Atualiza o rótulo de preço total
            atualizarValorTotal(valorTotalTransacao);

            // Processa os itens da transação
            for (ItemResumo item : resumoTransacao) {
                Produto produtoSelecionado = listaProdutos.stream()
                        .filter(p -> p.getDescricao().equals(item.getDescricao()))
                        .findFirst()
                        .orElse(null);

                if (produtoSelecionado == null) continue;

                if (!verificarEstoque(connection, produtoSelecionado, item)) return;

                atualizarEstoque(connection, produtoSelecionado, item);

                // Atualizar pontos de fidelidade, se necessário
                if (clienteSelecionado != null && valorTotalTransacao >= 10) {
                    double pontosAcumulados = 4; // Exemplo de cálculo de pontos
                    atualizarPontosFidelidade(connection, clienteSelecionado, pontosAcumulados);
                }

                // Adicionar transação com valor ajustado
                adicionarTransacao(connection, produtoSelecionado, funcionarioSelecionado, clienteSelecionado,
                        new ItemResumo(item.getDescricao(), item.getQuantidade(), valorTotalTransacao));
            }


            finalizarTransacao();

            mostrarAlerta("Sucesso", String.format("Transação concluída com sucesso! Valor total: R$ %.2f", valorTotalTransacao), Alert.AlertType.INFORMATION);
            recarregarTela();

        } catch (SQLException e) {
            mostrarAlerta("Erro", "Falha ao processar a transação: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private double obterPercentualDesconto(String descontoSelecionado) {
        double percentualDesconto = 0.0;
        if (descontoSelecionado != null) {
            // Verificar se o desconto contém números
            if (!descontoSelecionado.matches(".*\\d.*")) { // Regex para verificar se contém dígitos
                return 0.0; // Sem desconto
            }

            // Remover o texto extra após o percentual
            String[] partes = descontoSelecionado.split(" - ");
            if (partes.length > 0) {
                // Pega a primeira parte antes do " - ", que contém o valor do percentual
                String percentualString = partes[0].replace("%", "").trim();

                try {
                    // Substitui vírgulas por ponto, se necessário, e converte para double
                    percentualString = percentualString.replace(",", ".");
                    percentualDesconto = Double.parseDouble(percentualString);
                } catch (NumberFormatException e) {
                    System.out.println("Erro ao extrair percentual de desconto: " + e.getMessage());
                }
            }
        }
        return percentualDesconto;
    }
    @FXML
    private void verificarFidelidade() {
        Cliente clienteSelecionado = cbCliente.getValue();
        if (clienteSelecionado == null) {
            mostrarAlerta("Erro", "Selecione um cliente para verificar fidelidade.", Alert.AlertType.ERROR);
            return;
        }
        try (Connection connection = Database.getConnection()) {
            // Consulta os pontos de fidelidade do cliente
            String queryFidelidade = "SELECT * FROM Fidelidade WHERE IdCliente = ?";
            try (PreparedStatement statement = connection.prepareStatement(queryFidelidade)) {
                statement.setInt(1, clienteSelecionado.getIdCliente());
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    int pontos = rs.getInt("Pontos");
                    lblPontos.setText("Pontos: " + pontos);

                    // Consultar descontos disponíveis para o cliente com base nos pontos
                    ObservableList<String> descontosDisponiveis = FXCollections.observableArrayList();
                    descontosDisponiveis.add("Nenhum Desconto"); // Opção padrão sem desconto

                    // Buscar todos os descontos da tabela
                    String queryDescontos = "SELECT * FROM Desconto ORDER BY PontosMinimos ASC";
                    try (PreparedStatement statementDesconto = connection.prepareStatement(queryDescontos)) {
                        ResultSet rsDescontos = statementDesconto.executeQuery();

                        while (rsDescontos.next()) {
                            int pontosMinimos = rsDescontos.getInt("PontosMinimos");
                            double percentualDesconto = rsDescontos.getDouble("Percentual");
                            String descricaoDesconto = rsDescontos.getString("Descricao");

                            // Verifica se o desconto é de "primeira compra"
                            if (descricaoDesconto.toLowerCase().contains("primeira")) {
                                // Só adiciona o desconto de "primeira compra" se o cliente não tiver transações
                                if (isPrimeiraCompra(connection, clienteSelecionado)) {
                                    descontosDisponiveis.add(String.format("%.2f%% - %s", percentualDesconto, descricaoDesconto));
                                }
                            } else if (pontos >= pontosMinimos) {
                                // Adiciona outros descontos com base nos pontos mínimos
                                descontosDisponiveis.add(String.format("%.2f%% - %s", percentualDesconto, descricaoDesconto));
                            }
                        }
                        // Atualizar o ComboBox com os descontos encontrados
                        cbDesconto.setItems(descontosDisponiveis);
                        cbDesconto.setVisible(true); // Torna o ComboBox visível

                        // Caso não haja descontos, ocultar o ComboBox
                        if (descontosDisponiveis.isEmpty()) {
                            lblDesconto.setText("Desconto: 0%");
                            cbDesconto.setVisible(false);
                        }

                        // Listener para atualizar o valor total quando o desconto for selecionado
                        cbDesconto.setOnAction(event -> {
                            String descontoSelecionado = cbDesconto.getValue();
                            double percentualDesconto = obterPercentualDesconto(descontoSelecionado);

                            // Atualiza o lblDesconto com o desconto selecionado
                            lblDesconto.setText("Desconto: " + percentualDesconto + "%");

                            // Atualiza o valor total com o desconto
                            atualizarValorTotalComDesconto(percentualDesconto);
                        });
                    }
                }
            }
        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao verificar fidelidade: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private boolean isPrimeiraCompra(Connection connection, Cliente cliente) throws SQLException {
        // Verificar se o cliente já realizou alguma transação no sistema
        String query = "SELECT COUNT(*) FROM Transacao WHERE IdCliente = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cliente.getIdCliente());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0; // Retorna true se o cliente nunca fez uma transação
            }
        }
        return false; // Caso algo dê errado, assume que não é a primeira compra
    }

    // Função para verificar estoque
    private boolean verificarEstoque(Connection connection, Produto produto, ItemResumo item) throws SQLException {
        String queryEstoque = "SELECT Quantidade FROM Estoque WHERE IdProduto = ?";
        try (PreparedStatement statementEstoque = connection.prepareStatement(queryEstoque)) {
            statementEstoque.setInt(1, produto.getIdProduto());
            ResultSet rsEstoque = statementEstoque.executeQuery();
            if (rsEstoque.next()) {
                int estoqueAtual = rsEstoque.getInt("Quantidade");
                if (estoqueAtual < item.getQuantidade()) {
                    mostrarAlerta("Erro", "Quantidade insuficiente de " + item.getDescricao() + " no estoque.", Alert.AlertType.ERROR);
                    return false;
                }
            }
        }
        return true;
    }

    // Função para atualizar estoque
    private void atualizarEstoque(Connection connection, Produto produto, ItemResumo item) throws SQLException {
        String queryUpdateEstoque = "UPDATE Estoque SET Quantidade = Quantidade - ? WHERE IdProduto = ?";
        try (PreparedStatement statementUpdateEstoque = connection.prepareStatement(queryUpdateEstoque)) {
            statementUpdateEstoque.setInt(1, item.getQuantidade());
            statementUpdateEstoque.setInt(2, produto.getIdProduto());
            statementUpdateEstoque.executeUpdate();
        }
    }

    // Função para adicionar transação
    private void adicionarTransacao(Connection connection, Produto produto, Funcionario funcionario, Cliente cliente, ItemResumo item) throws SQLException {
        String query = "INSERT INTO Transacao (IdProduto, IdFuncionario, IdCliente, Preco, Descricao, Quantidade, Tipo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, produto.getIdProduto());
            statement.setInt(2, funcionario.getIdFuncionario());
            if (cliente != null) {
                statement.setInt(3, cliente.getIdCliente());
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

    // Função para atualizar pontos de fidelidade
    private void atualizarPontosFidelidade(Connection connection, Cliente cliente, double pontosAcumulados) throws SQLException {
        String updatePontos = "UPDATE Fidelidade SET Pontos = Pontos + ? WHERE IdCliente = ?";
        try (PreparedStatement statementUpdatePontos = connection.prepareStatement(updatePontos)) {
            statementUpdatePontos.setDouble(1, pontosAcumulados);
            statementUpdatePontos.setInt(2, cliente.getIdCliente());
            statementUpdatePontos.executeUpdate();
        }
    }

    private void finalizarTransacao() {
        // Limpa os itens da tabela
        resumoTransacao.clear();
        tvResumo.refresh();

        // Limpa os campos de entrada
        tfQuantidade.clear();
        cbProduto.setValue(null);
        cbCliente.setValue(null);
        cbFuncionario.setValue(null);
        cbDesconto.setValue("Nenhum Desconto");

        // Redefine o valor total
        valorTotal = 0.0;
        lblPrecoTotal.setText("Preço Total: R$ 0,00");
        lblDesconto.setText("Desconto: 0%");
        lblPontos.setText("Pontos: 0");

        // Se desejar, pode também redefinir o ComboBox de descontos e clientes
        cbDesconto.setItems(FXCollections.observableArrayList("Nenhum Desconto")); // Resetando os descontos

        // Adiciona o PromptText de volta aos campos de texto
        tfQuantidade.setPromptText("Digite a quantidade");
        cbProduto.setPromptText("Selecione um produto");
        cbCliente.setPromptText("Selecione um cliente");
        cbFuncionario.setPromptText("Selecione um funcionário");
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

    private void atualizarValorTotalComDesconto(double percentualDesconto) {
        double valorComDesconto = calcularTotalComDesconto(percentualDesconto);
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
        // Exibe o alerta
        alert.showAndWait();
    }
}