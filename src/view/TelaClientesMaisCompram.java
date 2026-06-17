package view;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.PedidoController;

public class TelaClientesMaisCompram extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tabela;
    private DefaultTableModel modelo;

    public TelaClientesMaisCompram() {

        setTitle("Clientes que Mais Compram");
        setSize(800, 500);
        setLocationRelativeTo(null);

        modelo = new DefaultTableModel();

        modelo.addColumn("Ranking");
        modelo.addColumn("Cliente");
        modelo.addColumn("Compras");
        modelo.addColumn("Total Gasto");
        modelo.addColumn("Ticket Médio");

        tabela = new JTable(modelo);

        add(new JScrollPane(tabela));

        carregarDados();

        setVisible(true);
    }

    private void carregarDados() {

        PedidoController controller =
                new PedidoController();

        List<Object[]> lista =
                controller.listarClientesMaisCompram();

        modelo.setRowCount(0);

        NumberFormat moeda =
                NumberFormat.getCurrencyInstance(
                        new Locale("pt", "BR")
                );

        int ranking = 1;

        for (Object[] linha : lista) {

            modelo.addRow(new Object[] {

                    ranking + "º",

                    linha[0],

                    linha[1],

                    moeda.format((Double) linha[2]),

                    moeda.format((Double) linha[3])
            });

            ranking++;
        }
    }
}
