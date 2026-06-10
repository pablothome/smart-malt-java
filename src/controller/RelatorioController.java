package controller;

import dao.RelatorioDAO;
import javax.swing.table.DefaultTableModel;

public class RelatorioController {

	private RelatorioDAO dao;

	public RelatorioController() {
		dao = new RelatorioDAO();
	}

	public DefaultTableModel getProdutosMaisVendidos() {
		return dao.relatorioProdutosMaisVendidos();
	}

	public DefaultTableModel getProdutosMaisComprados() {
		return dao.relatorioProdutosMaisComprados();
	}

	public DefaultTableModel getEstoqueAtual() {
		return dao.relatorioEstoqueAtual();
	}

	public DefaultTableModel getResumoFinanceiro() {
		return dao.relatorioResumoFinanceiro();
	}

	public DefaultTableModel getProdutosMaisLucrativos() {
		return dao.relatorioProdutosMaisLucrativos();
	}

	public DefaultTableModel getFornecedoresMaiorGasto() {
		return dao.relatorioFornecedoresMaiorGasto();
	}

	public DefaultTableModel getResumoFinanceiroPorPeriodo(String dataInicial, String dataFinal) {
		return dao.relatorioResumoFinanceiroPorPeriodo(dataInicial, dataFinal);
	}

	public DefaultTableModel getTabelaVendasPorPeriodo(String dataInicial, String dataFinal) {
		return dao.relatorioVendasPorPeriodo(dataInicial, dataFinal);
	}

	public DefaultTableModel getTabelaComprasPorPeriodo(String dataInicial, String dataFinal) {
		return dao.relatorioComprasPorPeriodo(dataInicial, dataFinal);
	}

	public int contarProdutos() {
		return dao.contarProdutos();
	}

	public int contarProdutosCriticos() {
		return dao.contarProdutosCriticos();
	}

	public int contarVendas() {
		return dao.contarVendas();
	}

	public int contarCompras() {
		return dao.contarCompras();
	}

	public double somarTotalVendas() {
		return dao.somarTotalVendas();
	}

	public double somarTotalCompras() {
		return dao.somarTotalCompras();
	}

	public double calcularLucroBruto() {
		return somarTotalVendas() - somarTotalCompras();
	}

	public double calcularMargemLucro() {
		double totalVendas = somarTotalVendas();
		return totalVendas == 0 ? 0 : (calcularLucroBruto() / totalVendas) * 100;
	}

	public double calcularTicketMedio() {
		int totalVendas = contarVendas();
		return totalVendas == 0 ? 0 : somarTotalVendas() / totalVendas;
	}

	public double calcularInvestimentoEstoque() {
		return dao.calcularInvestimentoEstoque();
	}

	public double getTotalVendasPorPeriodo(String dataInicial, String dataFinal) {
		return dao.somarVendasPorPeriodo(dataInicial, dataFinal);
	}

	public double getTotalComprasPorPeriodo(String dataInicial, String dataFinal) {
		return dao.somarComprasPorPeriodo(dataInicial, dataFinal);
	}

	public double getLucroPorPeriodo(String dataInicial, String dataFinal) {
		return getTotalVendasPorPeriodo(dataInicial, dataFinal) - getTotalComprasPorPeriodo(dataInicial, dataFinal);
	}

	public double getMargemPorPeriodo(String dataInicial, String dataFinal) {
		double vendas = getTotalVendasPorPeriodo(dataInicial, dataFinal);
		return vendas == 0 ? 0 : (getLucroPorPeriodo(dataInicial, dataFinal) / vendas) * 100;
	}

	public double getTicketMedioPorPeriodo(String dataInicial, String dataFinal) {
		double vendas = getTotalVendasPorPeriodo(dataInicial, dataFinal);
		int totalVendas = contarVendas(); // pode melhorar depois

		return totalVendas == 0 ? 0 : vendas / totalVendas;
	}
}