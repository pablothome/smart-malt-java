package controller;

import dao.DashboardDAO;
import javax.swing.table.DefaultTableModel;

public class DashboardController {

    private DashboardDAO dao;

    public DashboardController() {
        dao = new DashboardDAO();
    }

    public int contarProdutos() {
        return dao.contarProdutos();
    }

    public int contarClientes() {
        return dao.contarClientes();
    }

    public int contarFornecedores() {
        return dao.contarFornecedores();
    }

    public int contarProdutosCriticos() {
        return dao.contarProdutosCriticos();
    }

    public double totalVendas() {
        return dao.totalVendas();
    }

    public double totalCompras() {
        return dao.totalCompras();
    }

    public double lucroBruto() {
        return dao.lucroBruto();
    }

    public DefaultTableModel getEstoqueCritico() {
        return dao.getEstoqueCritico();
    }
}