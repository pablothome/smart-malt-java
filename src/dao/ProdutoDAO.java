package dao;

import model.Produto;

import java.sql.*;
//import java.sql.PreparedStatment;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

	public boolean inserir(Produto produto) {
		String sql = "INSERT INTO produto (nome_produto, marca_produto, categoria_produto, preco_produto, quantidade_estoque, estoque_minimo, ativo) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, produto.getNomeProduto());
			stmt.setString(2, produto.getMarcaProduto());
			stmt.setString(3, produto.getCategoriaProduto());
			stmt.setDouble(4, produto.getPrecoProduto());
			stmt.setInt(5, produto.getQuantidadeEstoque());
			stmt.setInt(6, produto.getEstoqueMinimo());
			stmt.setBoolean(7, true);

			return stmt.executeUpdate() > 0;

		} catch (Exception e) {
			System.out.println("Erro ao inserir produto: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public List<Produto> listarTodos() {
		List<Produto> lista = new ArrayList<>();
		String sql = "SELECT * FROM produto WHERE ativo = true";

		try (Connection conn = ConexaoDAO.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setIdProduto(rs.getInt("id_produto"));
				produto.setNomeProduto(rs.getString("nome_produto"));
				produto.setMarcaProduto(rs.getString("marca_produto"));
				produto.setCategoriaProduto(rs.getString("categoria_produto"));
				produto.setPrecoProduto(rs.getDouble("preco_produto"));
				produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
				produto.setEstoqueMinimo(rs.getInt("estoque_minimo"));

				lista.add(produto);
			}

		} catch (Exception e) {
			System.out.println("Erro ao listar produtos: " + e.getMessage());
			e.printStackTrace();
		}

		return lista;
	}

	public int buscarIdPorNome(String nome) {

		String sql = "SELECT id_produto FROM produto WHERE nome_produto = ?";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, nome);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("id_produto");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public Produto buscarPorId(int id) {
		String sql = "SELECT * FROM produto WHERE id_produto = ?";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				Produto produto = new Produto();
				produto.setIdProduto(rs.getInt("id_produto"));
				produto.setNomeProduto(rs.getString("nome_produto"));
				produto.setMarcaProduto(rs.getString("marca_produto"));
				produto.setCategoriaProduto(rs.getString("categoria_produto"));
				produto.setPrecoProduto(rs.getDouble("preco_produto"));
				produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
				produto.setEstoqueMinimo(rs.getInt("estoque_minimo"));

				return produto;
			}

		} catch (Exception e) {
			System.out.println("Erro ao buscar produto por ID: " + e.getMessage());
			e.printStackTrace();
		}

		return null;
	}

	public List<Produto> buscarPorNomeMarcaCategoriaOuId(String filtro) {

		List<Produto> lista = new ArrayList<>();

		String sql = "SELECT * FROM produto " + "WHERE ativo = true AND (" + "nome_produto LIKE ? OR "
				+ "marca_produto LIKE ? OR " + "categoria_produto LIKE ? OR " + "CAST(id_produto AS CHAR) LIKE ?" + ") "
				+ "ORDER BY nome_produto";

		try (Connection conn = ConexaoDAO.getConnection();

				PreparedStatement stmt = conn.prepareStatement(sql)) {

			String busca = "%" + filtro + "%";

			stmt.setString(1, busca);
			stmt.setString(2, busca);
			stmt.setString(3, busca);
			stmt.setString(4, busca);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Produto produto = new Produto();

				produto.setIdProduto(rs.getInt("id_produto"));

				produto.setNomeProduto(rs.getString("nome_produto"));

				produto.setMarcaProduto(rs.getString("marca_produto"));

				produto.setCategoriaProduto(rs.getString("categoria_produto"));

				produto.setPrecoProduto(rs.getDouble("preco_produto"));

				produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));

				produto.setEstoqueMinimo(rs.getInt("estoque_minimo"));

				lista.add(produto);
			}

		} catch (Exception e) {

			System.out.println("Erro ao buscar produtos: " + e.getMessage());

			e.printStackTrace();
		}

		return lista;
	}

	public boolean atualizar(Produto produto) {
		String sql = "UPDATE produto SET nome_produto = ?, marca_produto = ?, categoria_produto = ?, preco_produto = ?, quantidade_estoque = ?, estoque_minimo = ? WHERE id_produto = ?";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, produto.getNomeProduto());
			stmt.setString(2, produto.getMarcaProduto());
			stmt.setString(3, produto.getCategoriaProduto());
			stmt.setDouble(4, produto.getPrecoProduto());
			stmt.setInt(5, produto.getQuantidadeEstoque());
			stmt.setInt(6, produto.getEstoqueMinimo());
			stmt.setInt(7, produto.getIdProduto());

			return stmt.executeUpdate() > 0;

		} catch (Exception e) {
			System.out.println("Erro ao atualizar produto: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public boolean excluir(int id) {
		String sql = "UPDATE produto SET ativo = false WHERE id_produto = ?";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			return stmt.executeUpdate() > 0;

		} catch (Exception e) {
			System.out.println("Erro ao excluir produto: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public List<Produto> buscarComFiltros(String nome, String marca, String categoria) {
		List<Produto> lista = new ArrayList<>();

		String sql = "SELECT * FROM produto WHERE ativo = true AND " + "nome_produto LIKE ? AND "
				+ "marca_produto LIKE ? AND " + "categoria_produto LIKE ? " + "ORDER BY nome_produto";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, "%" + nome + "%");
			stmt.setString(2, "%" + marca + "%");
			stmt.setString(3, "%" + categoria + "%");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setIdProduto(rs.getInt("id_produto"));
				produto.setNomeProduto(rs.getString("nome_produto"));
				produto.setMarcaProduto(rs.getString("marca_produto"));
				produto.setCategoriaProduto(rs.getString("categoria_produto"));
				produto.setPrecoProduto(rs.getDouble("preco_produto"));
				produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
				produto.setEstoqueMinimo(rs.getInt("estoque_minimo"));

				lista.add(produto);
			}

		} catch (Exception e) {
			System.out.println("Erro ao buscar com filtros: " + e.getMessage());
			e.printStackTrace();
		}

		return lista;
	}

	public int contarProdutos() {
		String sql = "SELECT COUNT(*) FROM produto WHERE ativo = true";

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

	public int contarProdutosCriticos() {
		String sql = """
			    SELECT COUNT(*)
			    FROM produto
			    WHERE ativo = true
			    AND quantidade_estoque <= estoque_minimo
			""";

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

	public String[] listarNomes() {
		List<String> nomes = new ArrayList<>();

		String sql = "SELECT nome_produto FROM produto WHERE ativo = true";

		try (Connection conn = ConexaoDAO.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				nomes.add(rs.getString("nome_produto"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return nomes.toArray(new String[0]);
	}

}