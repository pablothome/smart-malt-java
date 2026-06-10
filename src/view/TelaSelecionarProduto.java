package view;

import controller.ProdutoController;
import model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaSelecionarProduto extends JDialog {

    private static final long serialVersionUID = 1L;
    private JTable tabela;
    private DefaultTableModel modelo;
    private Produto produtoSelecionado;

    public TelaSelecionarProduto(JFrame parent) {
        super(parent, "Selecionar Produto", true);

        setSize(750, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // impede edição
            }
        };

        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("Categoria");
        modelo.addColumn("Preço");
        modelo.addColumn("Estoque");

        tabela.setRowHeight(35);

        tabela.setShowGrid(false);

        tabela.setIntercellSpacing(
                new Dimension(0, 0)
        );

        tabela.getTableHeader()
              .setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            13
                    )
              );
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        JButton btnSelecionar = new JButton("Selecionar");
        add(btnSelecionar, BorderLayout.SOUTH);

        btnSelecionar.addActionListener(e -> selecionarProduto());

        carregarProdutos();
    }

    private void carregarProdutos() {
        modelo.setRowCount(0); // limpa a tabela

        try {
            ProdutoController controller = new ProdutoController();
            List<Produto> lista = controller.listarProdutos();

            for (Produto p : lista) {
                modelo.addRow(new Object[]{
                        p.getIdProduto(),
                        p.getNomeProduto(),
                        p.getCategoriaProduto(),
                        p.getPrecoProduto(),
                        p.getQuantidadeEstoque()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar produtos: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selecionarProduto() {
        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto antes de prosseguir.");
            return;
        }

        produtoSelecionado = new Produto();
        produtoSelecionado.setIdProduto((int) modelo.getValueAt(linha, 0));
        produtoSelecionado.setNomeProduto((String) modelo.getValueAt(linha, 1));
        produtoSelecionado.setCategoriaProduto((String) modelo.getValueAt(linha, 2));
        produtoSelecionado.setPrecoProduto((double) modelo.getValueAt(linha, 3));
        produtoSelecionado.setQuantidadeEstoque((int) modelo.getValueAt(linha, 4));

        System.out.println(">>> PRODUTO SELECIONADO: " + produtoSelecionado.getNomeProduto()
                + " | ID: " + produtoSelecionado.getIdProduto());

        dispose();
    }

    public Produto getProdutoSelecionado() {
        return produtoSelecionado;
    }
}