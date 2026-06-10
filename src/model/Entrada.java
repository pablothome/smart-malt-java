package model;

import java.util.Date;

public class Entrada {
    private int idEntrada;
    private int idFornecedor;
    private Date dataEntrada;
    private double valorTotal;

    public Entrada() {
    }

    public Entrada(int idFornecedor, Date dataEntrada, double valorTotal) {
        this.idFornecedor = idFornecedor;
        this.dataEntrada = dataEntrada;
        this.valorTotal = valorTotal;
    }

    public int getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(int idEntrada) {
        this.idEntrada = idEntrada;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}