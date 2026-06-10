package view;

import controller.CompraController;
import model.CompraView;
import util.Sessao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TelaListarCompras extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tabela;
    private DefaultTableModel modelo;
    private CompraController controller;

    public TelaListarCompras() {
        controller = new CompraController();

        setTitle("Histórico de Compras");
        setSize(950, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // =============================
        // CONTROLE DE ACESSO
        // =============================
        if (!Sessao.getUsuarioLogado().getPerfil().equalsIgnoreCase("gerente")) {
            JOptionPane.showMessageDialog(this,
                    "Acesso negado! Apenas gerente pode acessar relatórios.");
            dispose();
            return;
        }

        // =============================
        // TABELA
        // =============================
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modelo.addColumn("ID");
        modelo.addColumn("Fornecedor");
        modelo.addColumn("Produto");
        modelo.addColumn("Quantidade");
        modelo.addColumn("Preço Compra");
        modelo.addColumn("Subtotal");
        modelo.addColumn("Data");

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        // =============================
        // BOTÕESN
        // =============================
        JPanel painelBotoes = new JPanel();

        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnFechar = new JButton("Fechar");

        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnFechar);

        add(painelBotoes, BorderLayout.SOUTH);

        btnAtualizar.addActionListener(e -> carregarCompras());
        btnFechar.addActionListener(e -> dispose());

        carregarCompras();
    }

    // =============================
    // CARREGAR DADOS
    // =============================
    private void carregarCompras() {
        modelo.setRowCount(0);

        try {
            List<CompraView> lista = controller.listarComprasDetalhado();

            NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            for (CompraView c : lista) {
                modelo.addRow(new Object[]{
                        c.getIdCompra(),
                        c.getNomeFornecedor(),
                        c.getNomeProduto(),
                        c.getQuantidade(),
                        moeda.format(c.getPrecoCompra()),
                        moeda.format(c.getTotalItem()),
                        c.getDataCompra() != null ? sdf.format(c.getDataCompra()) : ""
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar compras: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    
    
    
    
    
}