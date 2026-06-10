package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;

public class ClienteDAO {

	public boolean cadastrarCliente(Cliente cliente) {

		String sql = """
				    INSERT INTO cliente
				    (nome_cliente, cpf_cliente, telefone_cliente, email_cliente)
				    VALUES (?, ?, ?, ?)
				""";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, cliente.getNomeCliente());
			stmt.setString(2, cliente.getCpfCliente());
			stmt.setString(3, cliente.getTelefoneCliente());
			stmt.setString(4, cliente.getEmailCliente());

			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {

			throw new RuntimeException("Erro ao cadastrar cliente.", e);
		}
	}

	public List<Cliente> listarClientes() {

		List<Cliente> lista = new ArrayList<>();

		String sql = """
				    SELECT *
				    FROM cliente
				    ORDER BY nome_cliente
				""";

		try (Connection conn = ConexaoDAO.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {

				lista.add(mapearCliente(rs));
			}

		} catch (SQLException e) {

			throw new RuntimeException("Erro ao listar clientes.", e);
		}

		return lista;
	}

	public String[] listarNomes() {

		List<String> lista = new ArrayList<>();

		String sql = """
				    SELECT nome_cliente
				    FROM cliente
				    ORDER BY nome_cliente
				""";

		try (Connection conn = ConexaoDAO.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {

				lista.add(rs.getString("nome_cliente"));
			}

		} catch (SQLException e) {

			throw new RuntimeException("Erro ao listar nomes dos clientes.", e);
		}

		return lista.toArray(new String[0]);
	}

	public Cliente buscarPorCpf(String cpf) {

		String sql = """
				    SELECT *
				    FROM cliente
				    WHERE cpf_cliente = ?
				""";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, cpf);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				return mapearCliente(rs);
			}

		} catch (SQLException e) {

			throw new RuntimeException("Erro ao buscar cliente por CPF.", e);
		}

		return null;
	}

	public List<Cliente> buscarPorNomeOuCpf(String filtro) {

		List<Cliente> lista = new ArrayList<>();

		String sql = """
				    SELECT * FROM cliente
				    WHERE nome_cliente LIKE ?
				    OR cpf_cliente LIKE ?
				    ORDER BY nome_cliente
				""";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, "%" + filtro + "%");
			stmt.setString(2, "%" + filtro + "%");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Cliente cliente = new Cliente();

				cliente.setCpfCliente(rs.getString("cpf_cliente"));

				cliente.setNomeCliente(rs.getString("nome_cliente"));

				cliente.setTelefoneCliente(rs.getString("telefone_cliente"));

				cliente.setEmailCliente(rs.getString("email_cliente"));

				lista.add(cliente);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	public String buscarCpfPorNome(String nome) {

		String sql = """
				    SELECT cpf_cliente
				    FROM cliente
				    WHERE nome_cliente = ?
				""";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, nome);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				return rs.getString("cpf_cliente");
			}

		} catch (SQLException e) {

			throw new RuntimeException("Erro ao buscar CPF do cliente.", e);
		}

		return "";
	}

	public List<Cliente> buscarPorNome(String nome) {

		List<Cliente> lista = new ArrayList<>();

		String sql = """
				    SELECT *
				    FROM cliente
				    WHERE nome_cliente LIKE ?
				    ORDER BY nome_cliente
				""";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, "%" + nome + "%");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				lista.add(mapearCliente(rs));
			}

		} catch (SQLException e) {

			throw new RuntimeException("Erro ao buscar clientes.", e);
		}

		return lista;
	}

	public boolean atualizarCliente(Cliente cliente) {

		String sql = """
				    UPDATE cliente
				    SET
				        nome_cliente = ?,
				        telefone_cliente = ?,
				        email_cliente = ?
				    WHERE cpf_cliente = ?
				""";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, cliente.getNomeCliente());
			stmt.setString(2, cliente.getTelefoneCliente());
			stmt.setString(3, cliente.getEmailCliente());
			stmt.setString(4, cliente.getCpfCliente());

			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {

			throw new RuntimeException("Erro ao atualizar cliente.", e);
		}
	}

	public boolean excluirCliente(String cpfCliente) {

		String sql = """
				    DELETE FROM cliente
				    WHERE cpf_cliente = ?
				""";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, cpfCliente);

			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {

			throw new RuntimeException("Erro ao excluir cliente.", e);
		}
	}

	private Cliente mapearCliente(ResultSet rs) throws SQLException {

		Cliente cliente = new Cliente();

		cliente.setNomeCliente(rs.getString("nome_cliente"));

		cliente.setCpfCliente(rs.getString("cpf_cliente"));

		cliente.setTelefoneCliente(rs.getString("telefone_cliente"));

		cliente.setEmailCliente(rs.getString("email_cliente"));

		return cliente;
	}
}