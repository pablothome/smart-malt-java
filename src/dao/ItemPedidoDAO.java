package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ItemPedidoDAO {

	public boolean cadastrarItem(int idPedido, int idProduto, int quantidade, double precoVenda) {
		String sql = "INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco_venda) VALUES (?, ?, ?, ?)";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, idPedido);
			stmt.setInt(2, idProduto);
			stmt.setInt(3, quantidade);
			stmt.setDouble(4, precoVenda);

			return stmt.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}