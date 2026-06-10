package model;

import java.util.Date;

public class Pedido {

    private int idPedido;
    private String cpfCliente;
    private Date dataPedido;
    private double valorTotal;

    public Pedido() {
    }

    public Pedido(int idPedido, String cpfCliente, Date dataPedido, double valorTotal) {
        this.idPedido = idPedido;
        this.cpfCliente = cpfCliente;
        this.dataPedido = dataPedido;
        this.valorTotal = valorTotal;
    }

    public Pedido(String cpfCliente, Date dataPedido, double valorTotal) {
        this.cpfCliente = cpfCliente;
        this.dataPedido = dataPedido;
        this.valorTotal = valorTotal;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", cpfCliente='" + cpfCliente + '\'' +
                ", dataPedido=" + dataPedido +
                ", valorTotal=" + valorTotal +
                '}';
    }
}