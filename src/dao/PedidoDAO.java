package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Pedido;
import model.ItemPedido;
import model.Produto;

public class PedidoDAO {

	public Produto buscarPorNome(String nomeProduto) {

		String sql = "SELECT * FROM produto WHERE nome_produto = ?";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, nomeProduto);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				Produto p = new Produto();

				p.setIdProduto(rs.getInt("id_produto"));
				p.setNomeProduto(rs.getString("nome_produto"));
				p.setPrecoProduto(rs.getDouble("preco_produto"));
				p.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));

				return p;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void atualizarEstoque(String nomeProduto, int novaQuantidade) {

		String sql = "UPDATE produto SET quantidade_estoque = ? WHERE nome_produto = ?";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, novaQuantidade);
			stmt.setString(2, nomeProduto);

			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int cadastrarPedido(Pedido pedido) {
		String sql = "INSERT INTO pedido (cpf_cliente, data_pedido, valor_total) VALUES (?, NOW(), ?)";

		try (Connection conn = ConexaoDAO.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, pedido.getCpfCliente());
			stmt.setDouble(2, pedido.getValorTotal());

			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1); // retorna id_pedido
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public boolean salvarPedidoCompleto(Pedido pedido, List<ItemPedido> itens) {
		String sqlPedido = "INSERT INTO pedido (cpf_cliente, data_pedido, valor_total) VALUES (?, NOW(), ?)";
		String sqlItem = "INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco_venda) VALUES (?, ?, ?, ?)";
		String sqlBuscarEstoque = "SELECT quantidade_estoque FROM produto WHERE id_produto = ?";
		String sqlBaixarEstoque = "UPDATE produto SET quantidade_estoque = quantidade_estoque - ? WHERE id_produto = ? AND quantidade_estoque >= ?";

		Connection conn = null;

		try {
			conn = ConexaoDAO.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement stmtPedido = conn.prepareStatement(sqlPedido, PreparedStatement.RETURN_GENERATED_KEYS);
			stmtPedido.setString(1, pedido.getCpfCliente());
			stmtPedido.setDouble(2, pedido.getValorTotal());

			int linhasPedido = stmtPedido.executeUpdate();

			if (linhasPedido == 0) {
				conn.rollback();
				return false;
			}

			ResultSet rs = stmtPedido.getGeneratedKeys();
			int idPedidoGerado = 0;

			if (rs.next()) {
				idPedidoGerado = rs.getInt(1);
			} else {
				conn.rollback();
				return false;
			}

			for (ItemPedido item : itens) {

				PreparedStatement stmtBuscaEstoque = conn.prepareStatement(sqlBuscarEstoque);
				stmtBuscaEstoque.setInt(1, item.getIdProduto());
				ResultSet rsEstoque = stmtBuscaEstoque.executeQuery();

				int estoqueAtual = 0;
				if (rsEstoque.next()) {
					estoqueAtual = rsEstoque.getInt("quantidade_estoque");
				}

				if (estoqueAtual < item.getQuantidade()) {
					conn.rollback();
					System.out.println("Estoque insuficiente para o produto ID: " + item.getIdProduto());
					return false;
				}

				PreparedStatement stmtItem = conn.prepareStatement(sqlItem);
				stmtItem.setInt(1, idPedidoGerado);
				stmtItem.setInt(2, item.getIdProduto());
				stmtItem.setInt(3, item.getQuantidade());
				stmtItem.setDouble(4, item.getPrecoVenda());
				stmtItem.executeUpdate();

				PreparedStatement stmtBaixar = conn.prepareStatement(sqlBaixarEstoque);
				stmtBaixar.setInt(1, item.getQuantidade());
				stmtBaixar.setInt(2, item.getIdProduto());
				stmtBaixar.setInt(3, item.getQuantidade());

				int linhasAfetadas = stmtBaixar.executeUpdate();

				if (linhasAfetadas == 0) {
					conn.rollback();
					return false;
				}
			}

			conn.commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();

			try {
				if (conn != null)
					conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} finally {
			try {
				if (conn != null)
					conn.setAutoCommit(true);
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public List<Pedido> listarPedidos() {
		List<Pedido> lista = new java.util.ArrayList<>();

		String sql = """
				    SELECT
				        p.id_pedido,
				        p.cpf_cliente,
				        p.data_pedido,
				        p.valor_total,
				        c.nome_cliente
				    FROM pedido p
				    INNER JOIN cliente c ON p.cpf_cliente = c.cpf_cliente
				    ORDER BY p.data_pedido DESC
				""";

		try (Connection conn = ConexaoDAO.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Pedido pedido = new Pedido();

				pedido.setIdPedido(rs.getInt("id_pedido"));
				pedido.setCpfCliente(rs.getString("cpf_cliente"));
				pedido.setDataPedido(rs.getTimestamp("data_pedido"));
				pedido.setValorTotal(rs.getDouble("valor_total"));
				pedido.setCpfCliente(rs.getString("nome_cliente")); // precisa existir no model Pedido

				lista.add(pedido);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	public Pedido buscarPedidoPorId(int idPedido) {
		String sql = "SELECT * FROM pedido WHERE id_pedido = ?";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, idPedido);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				Pedido pedido = new Pedido();
				pedido.setIdPedido(rs.getInt("id_pedido"));
				pedido.setCpfCliente(rs.getString("cpf_cliente"));
				pedido.setDataPedido(rs.getTimestamp("data_pedido"));
				pedido.setValorTotal(rs.getDouble("valor_total"));

				return pedido;
			}

		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar venda: " + e.getMessage());
		}

		return null;
	}

	public boolean excluirPedido(int idPedido) {
		String sqlItens = "DELETE FROM item_pedido WHERE id_pedido = ?";
		String sqlPedido = "DELETE FROM pedido WHERE id_pedido = ?";

		Connection conn = null;

		try {
			conn = ConexaoDAO.getConnection();
			conn.setAutoCommit(false);

			try (PreparedStatement stmtItens = conn.prepareStatement(sqlItens);
					PreparedStatement stmtPedido = conn.prepareStatement(sqlPedido)) {
				stmtItens.setInt(1, idPedido);
				stmtItens.executeUpdate();

				stmtPedido.setInt(1, idPedido);
				int linhas = stmtPedido.executeUpdate();

				conn.commit();
				return linhas > 0;

			} catch (Exception e) {
				conn.rollback();
				throw new RuntimeException("Erro ao excluir venda: " + e.getMessage());
			}

		} catch (Exception e) {
			throw new RuntimeException("Erro ao excluir venda: " + e.getMessage());
		} finally {
			try {
				if (conn != null)
					conn.setAutoCommit(true);
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}