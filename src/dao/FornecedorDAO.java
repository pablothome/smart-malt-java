package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Fornecedor;

public class FornecedorDAO {

	public String[] listarNomes() {
		List<String> lista = new ArrayList<>();

		String sql = "SELECT nome_fornecedor FROM fornecedor";

		try (Connection conn = ConexaoDAO.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				lista.add(rs.getString("nome_fornecedor"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista.toArray(new String[0]);
	}

	public int buscarIdPorNome(String nome) {
		String sql = "SELECT id_fornecedor FROM fornecedor WHERE nome_fornecedor = ?";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, nome);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("id_fornecedor");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	public Fornecedor buscarPorId(int id) {

		String sql = "SELECT * FROM fornecedor WHERE id_fornecedor = ?";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				Fornecedor fornecedor = new Fornecedor();

				fornecedor.setIdFornecedor(rs.getInt("id_fornecedor"));
				fornecedor.setNomeFornecedor(rs.getString("nome_fornecedor"));
				fornecedor.setTelefoneFornecedor(rs.getString("telefone_fornecedor"));
				fornecedor.setEmailFornecedor(rs.getString("email_fornecedor"));
				fornecedor.setCnpjFornecedor(rs.getString("cnpj_fornecedor"));

				return fornecedor;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Fornecedor> listarTodos() {

		List<Fornecedor> lista = new ArrayList<>();

		String sql = "SELECT * FROM fornecedor";

		try (Connection conn = ConexaoDAO.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {

				Fornecedor fornecedor = new Fornecedor();

				fornecedor.setIdFornecedor(rs.getInt("id_fornecedor"));
				fornecedor.setNomeFornecedor(rs.getString("nome_fornecedor"));
				fornecedor.setTelefoneFornecedor(rs.getString("telefone_fornecedor"));
				fornecedor.setEmailFornecedor(rs.getString("email_fornecedor"));
				fornecedor.setCnpjFornecedor(rs.getString("cnpj_fornecedor"));

				lista.add(fornecedor);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	public boolean atualizar(Fornecedor fornecedor) {

		String sql = "UPDATE fornecedor SET nome_fornecedor = ?, cnpj_fornecedor = ?, telefone_fornecedor = ?, email_fornecedor = ? WHERE id_fornecedor = ?";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, fornecedor.getNomeFornecedor());
			stmt.setString(2, fornecedor.getCnpjFornecedor());
			stmt.setString(3, fornecedor.getTelefoneFornecedor());
			stmt.setString(4, fornecedor.getEmailFornecedor());
			stmt.setInt(5, fornecedor.getIdFornecedor());

			return stmt.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean excluir(int id) {

		String sql = "DELETE FROM fornecedor WHERE id_fornecedor = ?";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);

			return stmt.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean salvar(Fornecedor fornecedor) {

		String sql = "INSERT INTO fornecedor (nome_fornecedor, telefone_fornecedor, email_fornecedor, cnpj_fornecedor) VALUES (?, ?, ?, ?)";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, fornecedor.getNomeFornecedor());
			stmt.setString(2, fornecedor.getTelefoneFornecedor());
			stmt.setString(3, fornecedor.getEmailFornecedor());
			stmt.setString(4, fornecedor.getCnpjFornecedor());

			return stmt.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public List<Fornecedor> buscar(String filtro) {
		List<Fornecedor> lista = new ArrayList<>();

		String sql = """
				SELECT *
				FROM fornecedor
				WHERE nome_fornecedor LIKE?
				or cnpj_fornecedor LIKE ?
				ORDER BY nome_fornecedor
				""";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, "%" + filtro + "%");
			stmt.setString(2, "%" + filtro + "%");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Fornecedor fornecedor = new Fornecedor();

				fornecedor.setIdFornecedor(rs.getInt("id_fornecedor"));

				fornecedor.setNomeFornecedor(rs.getString("nome_fornecedor"));

				fornecedor.setCnpjFornecedor(rs.getString("cnpj_fornecedor"));

				fornecedor.setTelefoneFornecedor(rs.getString("telefone_fornecedor"));

				lista.add(fornecedor);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;

	}
}