package util;

import model.Usuario;

public class Sessao {
	
	private static Usuario usuarioLogado;
	
	public static void setUsuarioLogado(Usuario usuario) {
		usuarioLogado = usuario;
	}
	
	public static Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
	
	public static boolean isGerente() {
		return usuarioLogado != null && usuarioLogado.getPerfil() != null && usuarioLogado.getPerfil().trim().toUpperCase().equals("GERENTE");
		
	}
	
	public static boolean isFuncionario() {
		return usuarioLogado != null && usuarioLogado.getPerfil() != null && usuarioLogado.getPerfil().trim().toUpperCase().equals("FUNCIONARIO");
	}
	
	public static void encerrarSessao() {
		usuarioLogado = null;
	}
}