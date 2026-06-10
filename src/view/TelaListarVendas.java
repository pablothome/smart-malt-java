package view;

import controller.VendaController;
import model.Venda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TelaListarVendas extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaListarVendas() {
        setTitle("Histórico de Vendas");
        setSize(950, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Cliente");
        modelo.addColumn("Produto");
        modelo.addColumn("Quantidade");
        modelo.addColumn("Preço Venda");
        modelo.addColumn("Total");
        modelo.addColumn("Data");

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        carregarVendas();

        setVisible(true);
    }

    private void carregarVendas() {
        try {
            VendaController controller = new VendaController();
            List<Venda> lista = controller.listarVendas();

            modelo.setRowCount(0);

            NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            for (Venda v : lista) {
                modelo.addRow(new Object[]{
                        v.getIdVenda(),
                        v.getNomeCliente(),
                        v.getNomeProduto(),
                        v.getQuantidade(),
                        moeda.format(v.getPrecoVenda()),
                        moeda.format(v.getTotalVenda()),
                        v.getDataVenda() != null ? sdf.format(v.getDataVenda()) : ""
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar vendas: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}