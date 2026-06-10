package model;

public class Fornecedor {

    private int idFornecedor;
    private String nomeFornecedor;
    private String cnpjFornecedor;
    private String telefoneFornecedor;
    private String emailFornecedor;

    public Fornecedor() {
    }

    public Fornecedor(String nomeFornecedor, String cnpjFornecedor, String telefoneFornecedor, String emailFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
        this.cnpjFornecedor = cnpjFornecedor;
        this.telefoneFornecedor = telefoneFornecedor;
        this.emailFornecedor = emailFornecedor;
    }

    public Fornecedor(int idFornecedor, String nomeFornecedor, String cnpjFornecedor, String telefoneFornecedor, String emailFornecedor) {
        this.idFornecedor = idFornecedor;
        this.nomeFornecedor = nomeFornecedor;
        this.cnpjFornecedor = cnpjFornecedor;
        this.telefoneFornecedor = telefoneFornecedor;
        this.emailFornecedor = emailFornecedor;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public String getCnpjFornecedor() {
        return cnpjFornecedor;
    }

    public void setCnpjFornecedor(String cnpjFornecedor) {
        this.cnpjFornecedor = cnpjFornecedor;
    }

    public String getTelefoneFornecedor() {
        return telefoneFornecedor;
    }

    public void setTelefoneFornecedor(String telefoneFornecedor) {
        this.telefoneFornecedor = telefoneFornecedor;
    }

    public String getEmailFornecedor() {
        return emailFornecedor;
    }

    public void setEmailFornecedor(String emailFornecedor) {
        this.emailFornecedor = emailFornecedor;
    }

    @Override
    public String toString() {
        return nomeFornecedor;
    }
}