package controller;

import java.util.List;

import dao.ProdutoDAO;
import model.Produto;

public class ProdutoController {

	private ProdutoDAO produtoDAO;

	public ProdutoController() {
		produtoDAO = new ProdutoDAO();
	}

	public boolean cadastrarProduto(Produto produto) {
		return produtoDAO.inserir(produto);
	}

	public List<Produto> listarProdutos() {
		return produtoDAO.listarTodos();
	}

	public Produto buscarPorId(int id) {
		return produtoDAO.buscarPorId(id);
	}

	public boolean atualizarProduto(Produto produto) {
		return produtoDAO.atualizar(produto);
	}

	public boolean excluirProduto(int id) {
		return produtoDAO.excluir(id);
	}

	public int contarProdutos() {
		return produtoDAO.contarProdutos();
	}

	public int contarProdutosCriticos() {
		return produtoDAO.contarProdutosCriticos();
	}

	public String[] getProdutos() {
		return produtoDAO.listarNomes();
	}

	public int getIdProduto(String nome) {
		return produtoDAO.buscarIdPorNome(nome);
	}

	public List<Produto> buscarProdutos(String filtro) {

		return produtoDAO.buscarPorNomeMarcaCategoriaOuId(filtro);
	}
}