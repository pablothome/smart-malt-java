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
    private JTextField txtBusca;

    public TelaEstoque() {

        setTitle("Controle de Estoque");
        setSize(750, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        
        JPanel painelBusca = new JPanel(new BorderLayout());

        JLabel lblBusca =
                new JLabel("Buscar:");

        txtBusca =
                new JTextField();

        painelBusca.add(
                lblBusca,
                BorderLayout.WEST
        );

        painelBusca.add(
                txtBusca,
                BorderLayout.CENTER
        );

        add(
                painelBusca,
                BorderLayout.NORTH
        );

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
        
        txtBusca.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {

                    public void insertUpdate(
                            javax.swing.event.DocumentEvent e
                    ) {
                        filtrar();
                    }

                    public void removeUpdate(
                            javax.swing.event.DocumentEvent e
                    ) {
                        filtrar();
                    }

                    public void changedUpdate(
                            javax.swing.event.DocumentEvent e
                    ) {
                        filtrar();
                    }
                }
        );

        carregarProdutos("");
        setVisible(true);
    }
    
  
    
    private void filtrar() {

        String texto =
                txtBusca.getText()
                        .trim();

        carregarProdutos(texto);
    }
    
    

    private void carregarProdutos(String filtro) {
        modelo.setRowCount(0);

        ProdutoController controller = new ProdutoController();
        List<Produto> lista =
                controller.buscarProdutos(filtro);

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