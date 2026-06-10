package controller;

import dao.PedidoDAO;
import dao.ProdutoDAO;
import model.ItemPedido;
import model.Pedido;
import model.Produto;

import java.util.Date;
import java.util.List;

public class PedidoController {

	private PedidoDAO pedidoDAO = new PedidoDAO();
	private ProdutoDAO produtoDAO = new ProdutoDAO();

	public boolean registrarPedido(List<ItemPedido> itens, String cpfCliente) {

		if (cpfCliente == null || cpfCliente.trim().isEmpty()) {
			throw new RuntimeException("CPF do cliente é obrigatório!");
		}

		if (itens == null || itens.isEmpty()) {
			throw new RuntimeException("O carrinho está vazio!");
		}

		double totalVenda = 0;

		for (ItemPedido item : itens) {

			if (item.getQuantidade() <= 0) {
				throw new RuntimeException("Quantidade inválida!");
			}

			Produto produto = produtoDAO.buscarPorId(item.getIdProduto());

			if (produto == null) {
				throw new RuntimeException("Produto não encontrado!");
			}

			if (produto.getQuantidadeEstoque() <= 0) {
				throw new RuntimeException("Produto sem estoque: " + produto.getNomeProduto());
			}

			if (item.getQuantidade() > produto.getQuantidadeEstoque()) {

				throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNomeProduto()
						+ " | Estoque disponível: " + produto.getQuantidadeEstoque());
			}

			totalVenda += item.getQuantidade() * item.getPrecoVenda();
		}

		Pedido pedido = new Pedido();

		pedido.setCpfCliente(cpfCliente);
		pedido.setDataPedido(new Date());
		pedido.setValorTotal(totalVenda);

		return pedidoDAO.salvarPedidoCompleto(pedido, itens);
	}
}