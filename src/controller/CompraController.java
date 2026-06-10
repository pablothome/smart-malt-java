package controller;

import dao.CompraDAO;
import dao.FornecedorDAO;
import model.CompraView;
import model.ItemCompra;

import java.util.List;

public class CompraController {

	private CompraDAO compraDAO = new CompraDAO();
	private FornecedorDAO fornecedorDAO = new FornecedorDAO();

	public String[] getFornecedores() {
		return fornecedorDAO.listarNomes();
	}

	public int getIdFornecedor(String nome) {
		int id = fornecedorDAO.buscarIdPorNome(nome);

		if (id == -1) {
			throw new RuntimeException("Fornecedor não encontrado!");
		}

		return id;
	}

	public boolean registrarCompra(List<ItemCompra> itens, int idFornecedor) {
		if (itens == null || itens.isEmpty()) {
			throw new RuntimeException("Nenhum item na compra!");
		}

		return compraDAO.registrarCompra(itens, idFornecedor);
	}

	public int contarCompras() {
		return compraDAO.contarCompras();
	}

	public List<CompraView> listarComprasDetalhado() {
		return compraDAO.listarComprasDetalhado();
	}
}