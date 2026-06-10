package model;

public class Produto {

    private int idProduto;
    private String nomeProduto;
    private String marcaProduto;
    private String categoriaProduto;
    private double precoProduto;
    private int quantidadeEstoque;
    private int estoqueMinimo;

    public Produto() {
    }

    public Produto(int idProduto, String nomeProduto, String marcaProduto, String categoriaProduto,
                   double precoProduto, int quantidadeEstoque, int estoqueMinimo) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.marcaProduto = marcaProduto;
        this.categoriaProduto = categoriaProduto;
        this.precoProduto = precoProduto;
        this.quantidadeEstoque = quantidadeEstoque;
        this.estoqueMinimo = estoqueMinimo;
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

    public String getMarcaProduto() {
        return marcaProduto;
    }

    public void setMarcaProduto(String marcaProduto) {
        this.marcaProduto = marcaProduto;
    }

    public String getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(String categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    public double getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(double precoProduto) {
        this.precoProduto = precoProduto;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(int estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    @Override
    public String toString() {
        return nomeProduto;
    }
}