package model;

public class ItemEntrada {
    private int idItem;
    private int idEntrada;
    private int idProduto;
    private int quantidade;
    private double precoCompra;

    public ItemEntrada() {
    }

    public ItemEntrada(int idProduto, int quantidade, double precoCompra) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.precoCompra = precoCompra;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public int getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(int idEntrada) {
        this.idEntrada = idEntrada;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(double precoCompra) {
        this.precoCompra = precoCompra;
    }
}