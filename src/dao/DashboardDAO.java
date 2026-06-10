package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.table.DefaultTableModel;

public class DashboardDAO {

    public int contarProdutos() {
        String sql = "SELECT COUNT(*) AS total FROM produto";

        try (Connection conn = ConexaoDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int contarClientes() {
        String sql = "SELECT COUNT(*) AS total FROM cliente";

        try (Connection conn = ConexaoDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int contarFornecedores() {
        String sql = "SELECT COUNT(*) AS total FROM fornecedor";

        try (Connection conn = ConexaoDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int contarProdutosCriticos() {
        String sql = """
            SELECT COUNT(*) AS total
            FROM produto
            WHERE quantidade_estoque <= estoque_minimo
        """;

        try (Connection conn = ConexaoDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public double totalVendas() {
        String sql = "SELECT IFNULL(SUM(total_venda), 0) AS total FROM venda";

        try (Connection conn = ConexaoDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    public double totalCompras() {
        String sql = "SELECT IFNULL(SUM(total_compra), 0) AS total FROM compra";

        try (Connection conn = ConexaoDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    public double lucroBruto() {
        return totalVendas() - totalCompras();
    }

    public DefaultTableModel getEstoqueCritico() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Produto");
        modelo.addColumn("Marca");
        modelo.addColumn("Categoria");
        modelo.addColumn("Quantidade");
        modelo.addColumn("Estoque Mínimo");
        modelo.addColumn("Status");

        String sql = """
            SELECT id_produto, nome_produto, marca_produto, categoria_produto,
                   quantidade_estoque, estoque_minimo
            FROM produto
            WHERE quantidade_estoque <= estoque_minimo
            ORDER BY quantidade_estoque ASC
        """;

        try (Connection conn = ConexaoDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int qtd = rs.getInt("quantidade_estoque");
                int minimo = rs.getInt("estoque_minimo");

                String status;
                if (qtd == 0) {
                    status = "SEM ESTOQUE";
                } else {
                    status = "ESTOQUE BAIXO";
                }

                modelo.addRow(new Object[]{
                    rs.getInt("id_produto"),
                    rs.getString("nome_produto"),
                    rs.getString("marca_produto"),
                    rs.getString("categoria_produto"),
                    qtd,
                    minimo,
                    status
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return modelo;
    }
}