package view;

import controller.VendaController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TelaProdutosMaisVendidos extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaProdutosMaisVendidos() {

        setTitle("Produtos Mais Vendidos");
        setSize(600, 400);
        setLocationRelativeTo(null);

        modelo = new DefaultTableModel();

        modelo.addColumn("Ranking");
        modelo.addColumn("Produto");
        modelo.addColumn("Quantidade Vendida");
        modelo.addColumn("Faturamento");

        tabela = new JTable(modelo);
        
        tabela.setRowHeight(30);
        tabela.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        tabela.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );
        
        tabela.getColumnModel()
        .getColumn(0)
        .setPreferredWidth(60);

  tabela.getColumnModel()
        .getColumn(1)
        .setPreferredWidth(250);

  tabela.getColumnModel()
        .getColumn(2)
        .setPreferredWidth(120);

  tabela.getColumnModel()
        .getColumn(3)
        .setPreferredWidth(120);

        add(new JScrollPane(tabela));

        carregarDados();

        setVisible(true);
    }

    private void carregarDados() {

        VendaController controller = new VendaController();

        List<Object[]> lista =
                controller.listarProdutosMaisVendidos();

        modelo.setRowCount(0);

        NumberFormat moeda =
                NumberFormat.getCurrencyInstance(
                        new Locale("pt", "BR")
                );

        int ranking = 1;

        for (Object[] linha : lista) {

            modelo.addRow(new Object[]{

                    ranking + "º",

                    linha[0],

                    linha[1],

                    moeda.format(
                            (Double) linha[2]
                    )
            });

            ranking++;
        }
    }
 
}