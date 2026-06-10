package model;

public class ItemPedido {

    private int idItem;
    private int idPedido;
    private int idProduto;
    private int quantidade;
    private double precoVenda;

    public ItemPedido() {
    }

    public ItemPedido(int idItem, int idPedido, int idProduto, int quantidade, double precoVenda) {
        this.idItem = idItem;
        this.idPedido = idPedido;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.precoVenda = precoVenda;
    }

    public ItemPedido(int idProduto, int quantidade, double precoVenda) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.precoVenda = precoVenda;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
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

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }
    
    public double getSubTotal() {
    	return quantidade * precoVenda;
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "idItem=" + idItem +
                ", idPedido=" + idPedido +
                ", idProduto=" + idProduto +
                ", quantidade=" + quantidade +
                ", precoVenda=" + precoVenda +
                '}';
    }
}