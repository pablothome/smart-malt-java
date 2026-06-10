package controller;

import java.util.List;

import dao.VendaDAO;
import model.Venda;

public class VendaController {

	private VendaDAO vendaDAO;

	public VendaController() {
		vendaDAO = new VendaDAO();
	}

	public boolean registrarVenda(String cpfCliente, int idProduto, int quantidade, double precoVenda) {
		double totalVenda = quantidade * precoVenda;

		Venda venda = new Venda(cpfCliente, idProduto, quantidade, precoVenda, totalVenda);
		return vendaDAO.registrarVenda(venda);
	}

	public List<Venda> listarVendas() {
		return vendaDAO.listarVendas();
	}

	public int contarVendas() {
		return vendaDAO.contarVendas();
	}
}
