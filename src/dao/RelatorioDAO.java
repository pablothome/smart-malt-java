package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.table.DefaultTableModel;

public class RelatorioDAO {

	public DefaultTableModel relatorioProdutosMaisVendidos() {
		String[] colunas = { "Produto", "Total Vendido" };
		DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

		String sql = """
				    SELECT
				        p.nome_produto,
				        COALESCE(SUM(ic.quantidade), 0) AS total_comprado
				    FROM produto p
				    LEFT JOIN item_compra ic
				        ON p.id_produto = ic.id_produto
				    GROUP BY p.id_produto, p.nome_produto
				    ORDER BY total_comprado DESC
				""";

		try (Connection conn = ConexaoDAO.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				modelo.addRow(new Object[] { rs.getString("nome_produto"), rs.getInt("total_vendido") });
			}

		} catch (Exception e) {
			throw new RuntimeException("Erro: " + e.getMessage());
		}

		return modelo;
	}

	public DefaultTableModel relatorioProdutosMaisComprados() {
		String[] colunas = { "Produto", "Total Comprado" };
		DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

		String sql = """
				    SELECT p.nome_produto, COALESCE(SUM(c.quantidade), 0) AS total_comprado
				    FROM produto p
				    LEFT JOIN compra c ON p.id_produto = c.id_produto
				    GROUP BY p.id_produto, p.nome_produto
				    ORDER BY total_comprado DESC
				""";

		try (Connection conn = ConexaoDAO.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				modelo.addRow(new Object[] { rs.getString("nome_produto"), rs.getInt("total_comprado") });
			}

		} catch (Exception e) {
			throw new RuntimeException("Erro: " + e.getMessage());
		}

		return modelo;
	}

	public DefaultTableModel relatorioEstoqueAtual() {
		String[] colunas = { "ID", "Produto", "Preço", "Estoque" };
		DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

		String sql = "SELECT * FROM produto";

		try (Connection conn = ConexaoDAO.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				modelo.addRow(new Object[] { rs.getInt("id_produto"), rs.getString("nome_produto"),
						rs.getDouble("preco_produto"), rs.getInt("quantidade_estoque") });
			}

		} catch (Exception e) {
			throw new RuntimeException("Erro: " + e.getMessage());
		}

		return modelo;
	}

	public DefaultTableModel relatorioResumoFinanceiro() {
		String[] colunas = { "Descrição", "Valor" };
		DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

		double vendas = somarTotalVendas();
		double compras = somarTotalCompras();
		double lucro = vendas - compras;

		modelo.addRow(new Object[] { "Total Vendas", vendas });
		modelo.addRow(new Object[] { "Total Compras", compras });
		modelo.addRow(new Object[] { "Lucro", lucro });

		return modelo;
	}

	public DefaultTableModel relatorioResumoFinanceiroPorPeriodo(String dataInicial, String dataFinal) {
		String[] colunas = { "Descrição", "Valor" };
		DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

		String sqlVendas = "SELECT IFNULL(SUM(total_venda),0) FROM venda WHERE DATE(data_venda) BETWEEN ? AND ?";
		String sqlCompras = "SELECT IFNULL(SUM(total_compra),0) FROM compra WHERE DATE(data_compra) BETWEEN ? AND ?";

		double vendas = 0;
		double compras = 0;

		try (Connection conn = ConexaoDAO.getConnection()) {

			try (PreparedStatement stmt = conn.prepareStatement(sqlVendas)) {
				stmt.setString(1, dataInicial);
				stmt.setString(2, dataFinal);
				ResultSet rs = stmt.executeQuery();
				if (rs.next())
					vendas = rs.getDouble(1);
			}

			try (PreparedStatement stmt = conn.prepareStatement(sqlCompras)) {
				stmt.setString(1, dataInicial);
				stmt.setString(2, dataFinal);
				ResultSet rs = stmt.executeQuery();
				if (rs.next())
					compras = rs.getDouble(1);
			}

		} catch (Exception e) {
			throw new RuntimeException("Erro: " + e.getMessage());
		}

		double lucro = vendas - compras;

		modelo.addRow(new Object[] { "Período", dataInicial + " até " + dataFinal });
		modelo.addRow(new Object[] { "Vendas", vendas });
		modelo.addRow(new Object[] { "Compras", compras });
		modelo.addRow(new Object[] { "Lucro", lucro });

		return modelo;
	}

	public int contarProdutos() {
		return executarCount("SELECT COUNT(*) FROM produto");
	}

	public int contarProdutosCriticos() {
		return executarCount("SELECT COUNT(*) FROM produto WHERE quantidade_estoque <= estoque_minimo");
	}

	public int contarVendas() {
		return executarCount("SELECT COUNT(*) FROM venda");
	}

	public int contarCompras() {
		return executarCount("SELECT COUNT(*) FROM compra");
	}

	private int executarCount(String sql) {
		try (Connection conn = ConexaoDAO.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			if (rs.next())
				return rs.getInt(1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public double somarTotalVendas() {
		return executarSoma("SELECT IFNULL(SUM(total_venda),0) FROM venda");
	}

	public double somarTotalCompras() {
		return executarSoma("SELECT IFNULL(SUM(total_compra),0) FROM compra");
	}

	private double executarSoma(String sql) {
		try (Connection conn = ConexaoDAO.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			if (rs.next())
				return rs.getDouble(1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public double somarVendasPorPeriodo(String dataInicial, String dataFinal) {
		String sql = "SELECT IFNULL(SUM(total_venda), 0) FROM venda WHERE data_venda BETWEEN ? AND ?";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, dataInicial);
			stmt.setString(2, dataFinal);

			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return rs.getDouble(1);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public double somarComprasPorPeriodo(String dataInicial, String dataFinal) {
		String sql = "SELECT IFNULL(SUM(total_compra), 0) FROM compra WHERE data_compra BETWEEN ? AND ?";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, dataInicial);
			stmt.setString(2, dataFinal);

			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return rs.getDouble(1);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public DefaultTableModel relatorioVendasPorPeriodo(String dataInicial, String dataFinal) {
		String[] colunas = { "ID", "Produto", "Quantidade", "Preço Venda", "Total", "Data" };
		DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

		String sql = """
				    SELECT
				        v.id_venda,
				        p.nome_produto,
				        v.quantidade,
				        v.preco_venda,
				        v.total_venda,
				        v.data_venda
				    FROM venda v
				    INNER JOIN produto p ON v.id_produto = p.id_produto
				    WHERE v.data_venda BETWEEN ? AND ?
				    ORDER BY v.data_venda DESC
				""";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, dataInicial);
			stmt.setString(2, dataFinal);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					modelo.addRow(new Object[] { rs.getInt("id_venda"), rs.getString("nome_produto"),
							rs.getInt("quantidade"), rs.getDouble("preco_venda"), rs.getDouble("total_venda"),
							rs.getString("data_venda") });
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Erro no relatório de vendas por período: " + e.getMessage());
		}

		return modelo;
	}

	public DefaultTableModel relatorioComprasPorPeriodo(String dataInicial, String dataFinal) {
		String[] colunas = { "ID", "Fornecedor", "Produto", "Quantidade", "Preço Compra", "Total", "Data" };
		DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

		String sql = """
				    SELECT
				        c.id_compra,
				        f.nome_fornecedor,
				        p.nome_produto,
				        ic.quantidade,
				        ic.preco_compra,
				        ic.total_item,
				        c.data_compra
				    FROM compra c
				    INNER JOIN fornecedor f
				        ON c.id_fornecedor = f.id_fornecedor
				    INNER JOIN item_compra ic
				        ON c.id_compra = ic.id_compra
				    INNER JOIN produto p
				        ON ic.id_produto = p.id_produto
				    WHERE c.data_compra BETWEEN ? AND ?
				    ORDER BY c.data_compra DESC
				""";

		try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, dataInicial);
			stmt.setString(2, dataFinal);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					modelo.addRow(new Object[] { rs.getInt("id_compra"), rs.getString("nome_fornecedor"),
							rs.getString("nome_produto"), rs.getInt("quantidade"), rs.getDouble("preco_compra"),
							rs.getDouble("total_item"), rs.getString("data_compra") });
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Erro no relatório de compras por período: " + e.getMessage());
		}

		return modelo;
	}

	public double calcularInvestimentoEstoque() {
		return executarSoma("SELECT IFNULL(SUM(quantidade_estoque * preco_produto),0) FROM produto");
	}

	public DefaultTableModel relatorioProdutosMaisLucrativos() {
		String[] colunas = { "Produto", "Receita", "Custo", "Lucro" };
		DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

		String sql = """
				    SELECT
				        p.nome_produto,
				        IFNULL(SUM(v.total_venda),0) AS receita,
				        IFNULL(SUM(ic.total_item),0) AS custo
				    FROM produto p
				    LEFT JOIN venda v
				        ON p.id_produto = v.id_produto
				    LEFT JOIN item_compra ic
				        ON p.id_produto = ic.id_produto
				    GROUP BY p.nome_produto
				    ORDER BY receita DESC
				""";

		try (Connection conn = ConexaoDAO.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				double receita = rs.getDouble("receita");
				double custo = rs.getDouble("custo");

				modelo.addRow(new Object[] { rs.getString("nome_produto"), receita, custo, receita - custo });
			}

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		return modelo;
	}

	public DefaultTableModel relatorioFornecedoresMaiorGasto() {
		String[] colunas = { "Fornecedor", "Total Gasto" };
		DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

		String sql = """
				    SELECT
				        f.nome_fornecedor,
				        IFNULL(SUM(ic.total_item),0) AS total
				    FROM fornecedor f
				    LEFT JOIN compra c
				        ON f.id_fornecedor = c.id_fornecedor
				    LEFT JOIN item_compra ic
				        ON c.id_compra = ic.id_compra
				    GROUP BY f.nome_fornecedor
				    ORDER BY total DESC
				""";

		try (Connection conn = ConexaoDAO.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				modelo.addRow(new Object[] { rs.getString("nome_fornecedor"), rs.getDouble("total") });
			}

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		return modelo;
	}
}