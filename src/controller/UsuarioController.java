package controller;

import dao.UsuarioDAO;

public class UsuarioController {

    public boolean login(String login, String senha) {
    	System.out.println("Login digitado: " + login);
    	System.out.println("Senha digitada: " + senha);
    	return true;
    	
    	//return login.equals("admin") && senha.equals("123");
    }
}