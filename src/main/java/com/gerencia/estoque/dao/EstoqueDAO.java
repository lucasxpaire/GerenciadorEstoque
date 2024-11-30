package com.gerencia.estoque.dao;

import com.gerencia.estoque.model.estoque.Estoque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDAO {

    // Método para buscar todos os itens do estoque
    public List<Estoque> buscarEstoque() throws SQLException {
        List<Estoque> estoqueList = new ArrayList<>();
        String sql = "SELECT * FROM estoque"; // Alterar para o nome da sua tabela de estoque

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int idProduto = resultSet.getInt("idProduto");
                double preco = resultSet.getDouble("preco");
                String descricao = resultSet.getString("descricao");
                int quantidade = resultSet.getInt("quantidade");

                Estoque estoque = new Estoque(idProduto, preco, descricao, quantidade);
                estoqueList.add(estoque);
            }
        }

        return estoqueList;
    }

    public void atualizarEstoque(int idProduto, int quantidade) throws SQLException {
        String atualizarEstoque = "UPDATE Estoque SET Quantidade = Quantidade + ? WHERE IdProduto = ?";
        String buscarPreco = "SELECT Preco, Descricao FROM Estoque WHERE IdProduto = ?";
        String inserirTransacao = "INSERT INTO Transacao (IdProduto, IdFuncionario, IdCliente, Preco, Descricao, Quantidade, DataHora, Tipo) " +
                "VALUES (?, ?, NULL, ?, ?, ?, CURRENT_TIMESTAMP, 'Compra')";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmtEstoque = conn.prepareStatement(atualizarEstoque);
             PreparedStatement stmtBuscarPreco = conn.prepareStatement(buscarPreco);
             PreparedStatement stmtTransacao = conn.prepareStatement(inserirTransacao)) {

            // Atualizar a quantidade no estoque
            stmtEstoque.setInt(1, quantidade);
            stmtEstoque.setInt(2, idProduto);
            stmtEstoque.executeUpdate();

            // Buscar preço e descrição do produto
            stmtBuscarPreco.setInt(1, idProduto);
            ResultSet rs = stmtBuscarPreco.executeQuery();
            if (!rs.next()) {
                throw new SQLException("Produto não encontrado no estoque.");
            }
            double precoUnitario = rs.getDouble("Preco");
            String descricao = rs.getString("Descricao");

            // Calcular o preço total
            double precoTotal = precoUnitario * quantidade;

            // Buscar ID do proprietário
            int idFuncionario = buscarIdProprietario();

            // Inserir na tabela Transacao
            stmtTransacao.setInt(1, idProduto);
            stmtTransacao.setInt(2, idFuncionario);
            stmtTransacao.setDouble(3, precoTotal);
            stmtTransacao.setString(4, descricao);
            stmtTransacao.setInt(5, quantidade);
            stmtTransacao.executeUpdate();
        }
    }


    public void adicionarProduto(Estoque produto) throws SQLException {
        String inserirProduto = "INSERT INTO Produto (Preco, Descricao) VALUES (?, ?) RETURNING IdProduto";
        String inserirEstoque = "INSERT INTO Estoque (IdProduto, Preco, Descricao, Quantidade) VALUES (?, ?, ?, ?)";
        String inserirTransacao = "INSERT INTO Transacao (IdProduto, IdFuncionario, IdCliente, Preco, Descricao, Quantidade, DataHora, Tipo) " +
                "VALUES (?, ?, NULL, ?, ?, ?, CURRENT_TIMESTAMP, 'Compra')";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmtProduto = conn.prepareStatement(inserirProduto);
             PreparedStatement stmtEstoque = conn.prepareStatement(inserirEstoque);
             PreparedStatement stmtTransacao = conn.prepareStatement(inserirTransacao)) {

            // Inserir na tabela Produto
            stmtProduto.setDouble(1, produto.getPreco());
            stmtProduto.setString(2, produto.getDescricao());
            ResultSet rs = stmtProduto.executeQuery();
            int idProduto;
            if (rs.next()) {
                idProduto = rs.getInt("IdProduto");
            } else {
                throw new SQLException("Erro ao obter o ID do produto inserido.");
            }

            // Inserir na tabela Estoque
            stmtEstoque.setInt(1, idProduto);
            stmtEstoque.setDouble(2, produto.getPreco());
            stmtEstoque.setString(3, produto.getDescricao());
            stmtEstoque.setInt(4, produto.getQuantidade());
            stmtEstoque.executeUpdate();

            // Buscar ID do proprietário
            int idFuncionario = buscarIdProprietario();

            // Inserir na tabela Transacao
            double precoTotal = produto.getPreco() * produto.getQuantidade();
            stmtTransacao.setInt(1, idProduto);
            stmtTransacao.setInt(2, idFuncionario);
            stmtTransacao.setDouble(3, precoTotal);
            stmtTransacao.setString(4, produto.getDescricao());
            stmtTransacao.setInt(5, produto.getQuantidade());
            stmtTransacao.executeUpdate();
        }
    }



    // Método para buscar o id de um produto pelo nome ou descrição
    public int buscarIdProduto(String descricao) throws SQLException {
        String sql = "SELECT IdProduto FROM Produto WHERE Descricao = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descricao);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("IdProduto");
            }
        }
        return -1; // Produto não encontrado
    }

    public int buscarIdProprietario() throws SQLException {
        String sql = "SELECT IdCredencial FROM Credenciais WHERE tipo LIKE ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%Proprietário%"); // Ajuste conforme o padrão utilizado para identificar o proprietário
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("IdCredencial");
            } else {
                throw new SQLException("Proprietário não encontrado.");
            }
        }
    }

}
