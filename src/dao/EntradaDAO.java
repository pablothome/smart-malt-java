package dao;

import model.Entrada;
import model.ItemEntrada;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class EntradaDAO {

    public boolean salvarEntradaCompleta(Entrada entrada, List<ItemEntrada> itens) {
        String sqlEntrada = "INSERT INTO entrada (id_fornecedor, data_entrada, valor_total) VALUES (?, ?, ?)";
        String sqlItem = "INSERT INTO item_entrada (id_entrada, id_produto, quantidade, preco_compra) VALUES (?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = ConexaoDAO.getConnection();
            conn.setAutoCommit(false);

            // 1) Inserir entrada
            PreparedStatement stmtEntrada = conn.prepareStatement(sqlEntrada, PreparedStatement.RETURN_GENERATED_KEYS);
            stmtEntrada.setInt(1, entrada.getIdFornecedor());
            stmtEntrada.setTimestamp(2, new java.sql.Timestamp(entrada.getDataEntrada().getTime()));
            stmtEntrada.setDouble(3, entrada.getValorTotal());

            int linhasEntrada = stmtEntrada.executeUpdate();

            if (linhasEntrada == 0) {
                conn.rollback();
                return false;
            }

            ResultSet rs = stmtEntrada.getGeneratedKeys();
            int idEntradaGerada = 0;

            if (rs.next()) {
                idEntradaGerada = rs.getInt(1);
            } else {
                conn.rollback();
                return false;
            }

            // 2) Inserir itens e aumentar estoque
            for (ItemEntrada item : itens) {
                PreparedStatement stmtItem = conn.prepareStatement(sqlItem);
                stmtItem.setInt(1, idEntradaGerada);
                stmtItem.setInt(2, item.getIdProduto());
                stmtItem.setInt(3, item.getQuantidade());
                stmtItem.setDouble(4, item.getPrecoCompra());

                stmtItem.executeUpdate();

                boolean estoqueAumentado = aumentarEstoque(conn, item.getIdProduto(), item.getQuantidade());

                if (!estoqueAumentado) {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();

            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    private boolean aumentarEstoque(Connection conn, int idProduto, int quantidade) throws Exception {
        String sql = "UPDATE produto SET quantidade_estoque = quantidade_estoque + ? WHERE id_produto = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, quantidade);
        stmt.setInt(2, idProduto);

        return stmt.executeUpdate() > 0;
    }
}