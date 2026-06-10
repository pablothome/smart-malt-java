package controller;

import dao.FornecedorDAO;
import model.Fornecedor;

import java.util.List;

public class FornecedorController {

	private FornecedorDAO fornecedorDAO;

	public FornecedorController() {
		this.fornecedorDAO = new FornecedorDAO();
	}

	public boolean salvar(String nome, String cnpj, String telefone, String email) {
		validarFornecedor(nome, cnpj, telefone, email);

		Fornecedor fornecedor = new Fornecedor(nome.trim(), cnpj.trim(), telefone.trim(), email.trim());

		return fornecedorDAO.salvar(fornecedor);
	}

	public List<Fornecedor> listarTodos() {
		return fornecedorDAO.listarTodos();
	}

	public List<Fornecedor> buscar(String filtro) {

		return fornecedorDAO.buscar(filtro);
	}

	public boolean atualizar(int id, String nome, String cnpj, String telefone, String email) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID inválido.");
		}

		validarFornecedor(nome, cnpj, telefone, email);

		Fornecedor fornecedor = new Fornecedor(id, nome.trim(), cnpj.trim(), telefone.trim(), email.trim());

		return fornecedorDAO.atualizar(fornecedor);
	}

	public boolean excluir(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("Selecione um fornecedor válido.");
		}

		return fornecedorDAO.excluir(id);
	}

	public Fornecedor buscarPorId(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID inválido.");
		}

		return fornecedorDAO.buscarPorId(id);
	}

	private void validarFornecedor(String nome, String cnpj, String telefone, String email) {

		if (nome == null || nome.trim().isEmpty()) {
			throw new IllegalArgumentException("O nome do fornecedor é obrigatório.");
		}

		if (cnpj == null || cnpj.trim().isEmpty()) {
			throw new IllegalArgumentException("O CNPJ é obrigatório.");
		}

		if (telefone == null || telefone.trim().isEmpty()) {
			throw new IllegalArgumentException("O telefone é obrigatório.");
		}

		if (email == null || email.trim().isEmpty()) {
			throw new IllegalArgumentException("O e-mail é obrigatório.");
		}

		if (nome.trim().length() < 3) {
			throw new IllegalArgumentException("O nome deve ter pelo menos 3 caracteres.");
		}

		String cnpjNumeros = cnpj.replaceAll("\\D", "");
		String telefoneNumeros = telefone.replaceAll("\\D", "");

		if (cnpjNumeros.length() != 14) {
			throw new IllegalArgumentException("O CNPJ deve conter 14 números.");
		}

		if (telefoneNumeros.length() < 10 || telefoneNumeros.length() > 11) {
			throw new IllegalArgumentException("O telefone deve conter 10 ou 11 números.");
		}

		if (!email.contains("@") || !email.contains(".")) {
			throw new IllegalArgumentException("Digite um e-mail válido.");
		}
	}
}