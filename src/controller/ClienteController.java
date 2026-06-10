package controller;

import dao.ClienteDAO;
import model.Cliente;

import java.util.List;

public class ClienteController {

	private ClienteDAO clienteDAO;

	public ClienteController() {
		this.clienteDAO = new ClienteDAO();
	}

	public boolean cadastrarCliente(String nome, String cpf, String telefone, String email) {

		if (cpf == null || cpf.trim().isEmpty()) {
			throw new IllegalArgumentException("O CPF é obrigatório.");
		}

		if (nome == null || nome.trim().isEmpty()) {
			throw new IllegalArgumentException("O nome é obrigatório.");
		}

		Cliente cliente = new Cliente();

		cliente.setCpfCliente(cpf.trim());

		cliente.setNomeCliente(nome.trim());

		cliente.setTelefoneCliente(telefone != null ? telefone.trim() : "");

		cliente.setEmailCliente(email != null ? email.trim() : "");

		return clienteDAO.cadastrarCliente(cliente);
	}

	public boolean atualizarCliente(String nome, String cpf, String telefone, String email) {

		if (cpf == null || cpf.trim().isEmpty()) {
			throw new IllegalArgumentException("O CPF é obrigatório.");
		}

		if (nome == null || nome.trim().isEmpty()) {
			throw new IllegalArgumentException("O nome é obrigatório.");
		}

		Cliente cliente = new Cliente();

		cliente.setCpfCliente(cpf.trim());

		cliente.setNomeCliente(nome.trim());

		cliente.setTelefoneCliente(telefone != null ? telefone.trim() : "");

		cliente.setEmailCliente(email != null ? email.trim() : "");

		return clienteDAO.atualizarCliente(cliente);
	}

	public boolean excluirCliente(String cpf) {

		if (cpf == null || cpf.trim().isEmpty()) {

			throw new IllegalArgumentException("CPF inválido.");
		}

		return clienteDAO.excluirCliente(cpf.trim());
	}

	public List<Cliente> buscarPorNomeOuCpf(String filtro) {

		return clienteDAO.buscarPorNomeOuCpf(filtro.trim());
	}

	public List<Cliente> listarClientes() {

		return clienteDAO.listarClientes();
	}

	public Cliente buscarClientePorCpf(String cpf) {

		if (cpf == null || cpf.trim().isEmpty()) {

			throw new IllegalArgumentException("CPF inválido.");
		}

		return clienteDAO.buscarPorCpf(cpf.trim());
	}

	public String[] getClientes() {

		return clienteDAO.listarNomes();
	}

	public String getCpfCliente(String nome) {

		if (nome == null || nome.trim().isEmpty()) {

			return "";
		}

		return clienteDAO.buscarCpfPorNome(nome.trim());
	}
}