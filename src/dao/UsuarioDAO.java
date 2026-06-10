package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Usuario;

public class UsuarioDAO {

	public Usuario autenticar(String login_usuario, String senha_usuario) {

	    String sql = "SELECT * FROM usuario WHERE login_usuario = ? AND senha_usuario = ?";

	    try (Connection conn = ConexaoDAO.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, login_usuario);
	        stmt.setString(2, senha_usuario);
	        
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNomeUsuario(rs.getString("nome_usuario"));
                usuario.setLogin(rs.getString("login_usuario"));
                usuario.setSenha(rs.getString("senha_usuario"));
                usuario.setPerfil(rs.getString("perfil_usuario"));
                return usuario;
            }
	        

	    } catch (Exception e) {
	        throw new RuntimeException("Erro ao autenticar usuário", e);
	    }

	    return null;
	}
}