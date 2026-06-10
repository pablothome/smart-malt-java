package view;

import controller.ClienteController;
import model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaSelecionarCliente extends JDialog {

    private static final long serialVersionUID = 1L;

    private JTable tabela;
    private DefaultTableModel modelo;
    private Cliente clienteSelecionado;

    public TelaSelecionarCliente(JFrame parent) {
        super(parent, "Selecionar Cliente", true);

        setSize(500, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modelo.addColumn("CPF");
        modelo.addColumn("Nome");

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        JButton btnSelecionar = new JButton("Selecionar");
        add(btnSelecionar, BorderLayout.SOUTH);

        btnSelecionar.addActionListener(e -> selecionarCliente());

        // Duplo clique também seleciona
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    selecionarCliente();
                }
            }
        });

        carregarClientes();
    }

    private void carregarClientes() {
        ClienteController controller = new ClienteController();
        List<Cliente> lista = controller.listarClientes();

        modelo.setRowCount(0); // evita duplicar

        for (Cliente c : lista) {
            modelo.addRow(new Object[]{
                    c.getCpfCliente(),
                    c.getNomeCliente()
            });
        }
    }

    private void selecionarCliente() {
        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente!");
            return;
        }

        String cpf = tabela.getValueAt(linha, 0).toString();
        String nome = tabela.getValueAt(linha, 1).toString();

        clienteSelecionado = new Cliente();
        clienteSelecionado.setCpfCliente(cpf);
        clienteSelecionado.setNomeCliente(nome);

        dispose();
    }

    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }
}