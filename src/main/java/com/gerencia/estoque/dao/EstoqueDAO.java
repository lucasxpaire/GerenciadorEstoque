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

    // Método para atualizar a quantidade de um produto no estoque
    public void atualizarEstoque(int idProduto, int quantidade) throws SQLException {
        String sql = "UPDATE Estoque SET Quantidade = Quantidade + ? WHERE IdProduto = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantidade);
            stmt.setInt(2, idProduto);
            stmt.executeUpdate();
        }
    }

    // Método para adicionar um novo produto ao estoque
    public void adicionarProduto(Estoque produto) throws SQLException {
        String inserirProduto = "INSERT INTO Produto (Preco, Descricao) VALUES (?, ?) RETURNING IdProduto";
        String inserirEstoque = "INSERT INTO Estoque (IdProduto, Preco, Descricao, Quantidade) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmtProduto = conn.prepareStatement(inserirProduto);
             PreparedStatement stmtEstoque = conn.prepareStatement(inserirEstoque)) {

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
}
