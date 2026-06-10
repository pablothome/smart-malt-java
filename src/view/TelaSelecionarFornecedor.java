package view;

import controller.FornecedorController;
import model.Fornecedor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaSelecionarFornecedor extends JDialog {

    private static final long serialVersionUID = 1L;
    private JTable tabela;
    private DefaultTableModel modelo;
    private Fornecedor fornecedorSelecionado;

    public TelaSelecionarFornecedor(JFrame parent) {
        super(parent, "Selecionar Fornecedor", true);

        setSize(750, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("CNPJ");
        modelo.addColumn("Telefone");
        modelo.addColumn("Email");

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        JButton btnSelecionar = new JButton("Selecionar");
        add(btnSelecionar, BorderLayout.SOUTH);

        btnSelecionar.addActionListener(e -> selecionarFornecedor());

        carregarFornecedores();
    }

    private void carregarFornecedores() {
        modelo.setRowCount(0);

        try {
            FornecedorController controller = new FornecedorController();
            List<Fornecedor> lista = controller.listarTodos();

            for (Fornecedor f : lista) {
                modelo.addRow(new Object[]{
                        f.getIdFornecedor(),
                        f.getNomeFornecedor(),
                        f.getCnpjFornecedor(),
                        f.getTelefoneFornecedor(),
                        f.getEmailFornecedor()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar fornecedores: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selecionarFornecedor() {
        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um fornecedor.");
            return;
        }

        fornecedorSelecionado = new Fornecedor();
        fornecedorSelecionado.setIdFornecedor((int) modelo.getValueAt(linha, 0));
        fornecedorSelecionado.setNomeFornecedor((String) modelo.getValueAt(linha, 1));
        fornecedorSelecionado.setCnpjFornecedor((String) modelo.getValueAt(linha, 2));
        fornecedorSelecionado.setTelefoneFornecedor((String) modelo.getValueAt(linha, 3));
        fornecedorSelecionado.setEmailFornecedor((String) modelo.getValueAt(linha, 4));

        dispose();
    }

    public Fornecedor getFornecedorSelecionado() {
        return fornecedorSelecionado;
    }
}