package dao;

import model.Venda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {
	
	

    public boolean registrarVenda(Venda venda) {
    	
    	   System.out.println("=== REGISTRANDO VENDA ===");
    	    System.out.println("Cliente: " + venda.getCpfCliente());
    	    System.out.println("Produto: " + venda.getIdProduto());
    	    System.out.println("Quantidade: " + venda.getQuantidade());

    	    
        String sqlVenda = "INSERT INTO venda (cpf_cliente, id_produto, quantidade, preco_venda, total_venda, data_venda) VALUES (?, ?, ?, ?, ?, NOW())";
        String sqlAtualizarEstoque = "UPDATE produto SET quantidade_estoque = quantidade_estoque - ? WHERE id_produto = ? AND quantidade_estoque >= ?";

        Connection conn = null;
        PreparedStatement stmtVenda = null;
        PreparedStatement stmtEstoque = null;

        try {
            conn = ConexaoDAO.getConnection();
            conn.setAutoCommit(false);

       
            stmtVenda = conn.prepareStatement(sqlVenda);
            stmtVenda.setString(1, venda.getCpfCliente());
            stmtVenda.setInt(2, venda.getIdProduto());
            stmtVenda.setInt(3, venda.getQuantidade());
            stmtVenda.setDouble(4, venda.getPrecoVenda());
            stmtVenda.setDouble(5, venda.getTotalVenda());

            System.out.println("Executando INSERT venda...");
            
            int linhasVenda = stmtVenda.executeUpdate();

            if (linhasVenda == 0) {
                conn.rollback();
                throw new RuntimeException("Falha ao registrar venda.");
            }

          
            stmtEstoque = conn.prepareStatement(sqlAtualizarEstoque);
            stmtEstoque.setInt(1, venda.getQuantidade());
            stmtEstoque.setInt(2, venda.getIdProduto());
            stmtEstoque.setInt(3, venda.getQuantidade());

            int linhasEstoque = stmtEstoque.executeUpdate();

            if (linhasEstoque == 0) {
                conn.rollback();
                throw new RuntimeException("Estoque insuficiente para realizar a venda.");
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            throw new RuntimeException("Erro ao registrar venda: " + e.getMessage());

        } finally {
            try {
                if (stmtVenda != null) stmtVenda.close();
                if (stmtEstoque != null) stmtEstoque.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Venda> listarVendas() {
        List<Venda> lista = new ArrayList<>();

        String sql = "SELECT v.id_venda, c.nome_cliente, p.nome_produto, v.quantidade, v.preco_venda, v.total_venda, v.data_venda " +
                     "FROM venda v " +
                     "INNER JOIN cliente c ON v.cpf_cliente = c.cpf_cliente " +
                     "INNER JOIN produto p ON v.id_produto = p.id_produto " +
                     "ORDER BY v.data_venda DESC";

        try (Connection conn = ConexaoDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Venda venda = new Venda();

                venda.setIdVenda(rs.getInt("id_venda"));
                venda.setNomeCliente(rs.getString("nome_cliente"));
                venda.setNomeProduto(rs.getString("nome_produto"));
                venda.setQuantidade(rs.getInt("quantidade"));
                venda.setPrecoVenda(rs.getDouble("preco_venda"));
                venda.setTotalVenda(rs.getDouble("total_venda"));
                venda.setDataVenda(rs.getTimestamp("data_venda"));
               

                lista.add(venda);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vendas: " + e.getMessage());
        }

        return lista;
	}

    public int contarVendas() {
        String sql = "SELECT COUNT(*) FROM venda";

        try (Connection conn = ConexaoDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}