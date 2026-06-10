package model;

public class ProdutoMaisVendido {
    private int idProduto;
    private String nomeProduto;
    private int quantidadeVendida;
    private double totalVendido;

    public ProdutoMaisVendido() {
    }

    public ProdutoMaisVendido(int idProduto, String nomeProduto, int quantidadeVendida, double totalVendido) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.quantidadeVendida = quantidadeVendida;
        this.totalVendido = totalVendido;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public int getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(int quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }

    public double getTotalVendido() {
        return totalVendido;
    }

    public void setTotalVendido(double totalVendido) {
        this.totalVendido = totalVendido;
    }
}