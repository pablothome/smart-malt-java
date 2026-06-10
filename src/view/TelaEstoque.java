package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controller.ProdutoController;
import model.Produto;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TelaEstoque extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaEstoque() {

        setTitle("Controle de Estoque");
        setSize(750, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modelo.addColumn("Produto");
        modelo.addColumn("Marca");
        modelo.addColumn("Categoria");
        modelo.addColumn("Quantidade");
        modelo.addColumn("Estoque Mínimo");
        modelo.addColumn("Preço");
        modelo.addColumn("Situação");

        tabela = new JTable(modelo);
        tabela.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        carregarEstoque();

        setVisible(true);
    }

    private void carregarEstoque() {
        modelo.setRowCount(0);

        ProdutoController controller = new ProdutoController();
        List<Produto> lista = controller.listarProdutos();

        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        for (Produto p : lista) {
            modelo.addRow(new Object[]{
                    p.getNomeProduto(),
                    p.getMarcaProduto(),
                    p.getCategoriaProduto(),
                    p.getQuantidadeEstoque(),
                    p.getEstoqueMinimo(),
                    formatoMoeda.format(p.getPrecoProduto()),
                    definirSituacao(p.getQuantidadeEstoque(), p.getEstoqueMinimo())
            });
        }
    }

    private String definirSituacao(int quantidade, int estoqueMinimo) {
        if (quantidade == 0) {
            return "CRÍTICO";
        } else if (quantidade <= estoqueMinimo) {
            return "BAIXO";
        } else {
            return "OK";
        }
    }
}