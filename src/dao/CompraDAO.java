package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.CompraView;
import model.ItemCompra;

public class CompraDAO {

	public boolean registrarCompra(List<ItemCompra> itens, int idFornecedor) {

		double totalCompra = 0;

		for (ItemCompra item : itens) {
			totalCompra += item.getTotal();
		}
		String sqlCompra = "INSERT INTO compra (id_fornecedor, total_compra, data_compra) VALUES (?, ?, NOW())";
		String sqlItem = "INSERT INTO item_compra (id_compra, id_produto, quantidade, preco_compra, total_item) VALUES (?, ?, ?, ?, ?)";
		String sqlEstoque = "UPDATE produto SET quantidade_estoque = quantidade_estoque + ? WHERE id_produto = ?";

		Connection conn = null;

		try {
			conn = ConexaoDAO.getConnection();
			conn.setAutoCommit(false);

			// 1. Inserir compra
			PreparedStatement stmtCompra = conn.prepareStatement(sqlCompra, Statement.RETURN_GENERATED_KEYS);
			stmtCompra.setInt(1, idFornecedor);
			stmtCompra.setDouble(2, totalCompra);
			stmtCompra.executeUpdate();

			ResultSet rs = stmtCompra.getGeneratedKeys();
			if (!rs.next()) {
				throw new RuntimeException("Erro ao gerar ID da compra");
			}

			int idCompra = rs.getInt(1);

			// 2. Inserir itens
			for (ItemCompra item : itens) {

				PreparedStatement stmtItem = conn.prepareStatement(sqlItem);
				stmtItem.setInt(1, idCompra);
				stmtItem.setInt(2, item.getIdProduto());
				stmtItem.setInt(3, item.getQuantidade());
				stmtItem.setDouble(4, item.getPreco());
				stmtItem.setDouble(5, item.getTotal());
				stmtItem.executeUpdate();

				// 3. Atualizar estoque
				PreparedStatement stmtEstoque = conn.prepareStatement(sqlEstoque);
				stmtEstoque.setInt(1, item.getQuantidade());
				stmtEstoque.setInt(2, item.getIdProduto());
				stmtEstoque.executeUpdate();
			}

			conn.commit();
			return true;

		} catch (Exception e) {
			try {
				if (conn != null)
					conn.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			e.printStackTrace();
			return false;

		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<CompraView> listarComprasDetalhado() {

		List<CompraView> lista = new ArrayList<>();

		String sql = "SELECT " + "c.id_compra, " + "f.nome_fornecedor, " + "p.nome_produto, " + "ic.quantidade, "
				+ "ic.preco_compra, " + "ic.total_item, " + "c.data_compra " + "FROM compra c "
				+ "JOIN fornecedor f ON c.id_fornecedor = f.id_fornecedor "
				+ "JOIN item_compra ic ON c.id_compra = ic.id_compra "
				+ "JOIN produto p ON ic.id_produto = p.id_produto " + "ORDER BY c.data_compra DESC";

		try (Connection conn = ConexaoDAO.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {

				CompraView compra = new CompraView();

				compra.setIdCompra(rs.getInt("id_compra"));
				compra.setNomeFornecedor(rs.getString("nome_fornecedor"));
				compra.setNomeProduto(rs.getString("nome_produto"));
				compra.setQuantidade(rs.getInt("quantidade"));
				compra.setPrecoCompra(rs.getDouble("preco_compra"));
				compra.setTotalItem(rs.getDouble("total_item"));
				compra.setDataCompra(rs.getTimestamp("data_compra"));

				lista.add(compra);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	public int contarCompras() {
		String sql = "SELECT COUNT(*) FROM compra";

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