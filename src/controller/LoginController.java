package controller;

import dao.UsuarioDAO;
import model.Usuario;

public class LoginController {

	private UsuarioDAO usuarioDAO;

	public LoginController() {
		this.usuarioDAO = new UsuarioDAO();
	}

	public Usuario autenticar(String login, String senha) {
		if (login == null || login.trim().isEmpty()) {
			throw new IllegalArgumentException("Informe o login.");
		}

		if (senha == null || senha.trim().isEmpty()) {
			throw new IllegalArgumentException("Informe a senha.");
		}

		return usuarioDAO.autenticar(login.trim(), senha.trim());
	}
}