package model;

public class Usuario {

    private int id_usuario;
    private String nome_usuario;
    private String login_usuario;
    private String senha_usuario;
    private String perfil_usuario;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nomeUsuario, String login, String senha, String perfil) {
        this.id_usuario= idUsuario;
        this.nome_usuario = nomeUsuario;
        this.login_usuario = login;
        this.senha_usuario = senha;
        this.perfil_usuario = perfil;
    }

    public int getIdUsuario() {
        return id_usuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.id_usuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nome_usuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nome_usuario = nomeUsuario;
    }

    public String getLogin() {
        return login_usuario;
    }

    public void setLogin(String login) {
        this.login_usuario = login;
    }

    public String getSenha() {
        return senha_usuario;
    }

    public void setSenha(String senha) {
        this.senha_usuario = senha;
    }

    public String getPerfil() {
        return perfil_usuario;
    }

    public void setPerfil(String perfil) {
        this.perfil_usuario = perfil;
    }
}