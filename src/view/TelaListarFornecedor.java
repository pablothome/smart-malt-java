package view;

import controller.FornecedorController;
import model.Fornecedor;
import util.Sessao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class TelaListarFornecedor extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton btnNovo, btnEditar, btnAtualizar, btnExcluir, btnVoltar;
    private JLabel lblFundo;
    private JTextField txtBuscar;

    private FornecedorController controller;

    public TelaListarFornecedor() {
        controller = new FornecedorController();

        setTitle("Lista de Fornecedores");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
       
        
        
        
        if (!Sessao.getUsuarioLogado().getPerfil().equalsIgnoreCase("gerente")) {
            JOptionPane.showMessageDialog(this, "Acesso negado! Apenas gerente pode acessar relatórios.");
            dispose();
            return;
        }

        // ===== FUNDO =====
        URL caminhoImagem = getClass().getResource("/icons/fundo.jpg");

        if (caminhoImagem != null) {
            ImageIcon imagem = new ImageIcon(caminhoImagem);
            lblFundo = new JLabel(imagem);
        } else {
            lblFundo = new JLabel();
            lblFundo.setOpaque(true);
            lblFundo.setBackground(new Color(240, 240, 240));
        }

        lblFundo.setLayout(new BorderLayout());
        setContentPane(lblFundo);
        
        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));

        painelTopo.setOpaque(false);
        
        painelTopo.add(new JLabel("Buscar:"));
        
        txtBuscar = new JTextField(25);

        

        painelTopo.add(txtBuscar);

        add(painelTopo, BorderLayout.NORTH);
        
        
        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarTabela();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarTabela();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarTabela();
            }
        });
        

        // ===== TABELA =====
        modelo = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;

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

        // ===== PAINEL DE BOTÕES =====
        JPanel painelBotoes = new JPanel();
        painelBotoes.setOpaque(false);

        btnNovo = new JButton("Novo Fornecedor");
        btnEditar = new JButton("Editar");
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir");
        btnVoltar = new JButton("Voltar");
        
        if (Sessao.isFuncionario()) {
            btnEditar.setVisible(false);
            btnExcluir.setVisible(false);
        }

        painelBotoes.add(btnNovo);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnVoltar);

        add(painelBotoes, BorderLayout.SOUTH);

        // ===== EVENTOS =====
        btnNovo.addActionListener(e -> new TelaCadastroFornecedor(this).setVisible(true));
        btnAtualizar.addActionListener(e -> carregarTabela());
        
        btnExcluir.addActionListener(e -> {
            if (Sessao.isFuncionario()) {
                JOptionPane.showMessageDialog(this, "Você não tem permissão para excluir fornecedores.");
                return;
            }

            excluirFornecedor();
        });
        btnEditar.addActionListener(e -> {
            if (Sessao.isFuncionario()) {
                JOptionPane.showMessageDialog(this, "Você não tem permissão para editar fornecedores.");
                return;
            }

            editarFornecedor();
        });
        btnVoltar.addActionListener(e -> dispose());

        carregarTabela();
        setVisible(true);
    }

    private void filtrarTabela() {

        String filtro = txtBuscar.getText().trim();

        List<Fornecedor> lista;

        if (filtro.isEmpty()) {

            lista = controller.listarTodos();

        } else {

            lista = controller.buscar(filtro);
        }

        modelo.setRowCount(0);

        for (Fornecedor fornecedor : lista) {

            modelo.addRow(new Object[]{

                    fornecedor.getIdFornecedor(),
                    fornecedor.getNomeFornecedor(),
                    fornecedor.getCnpjFornecedor(),
                    fornecedor.getTelefoneFornecedor(),
                    fornecedor.getEmailFornecedor()
            });
        }
    }
    
    
    public void carregarTabela() {
        modelo.setRowCount(0);

        try {
            List<Fornecedor> lista;
            
            String filtro = txtBuscar.getText().trim();
            
            if(filtro.isEmpty()) {
            	lista = controller.listarTodos();
            } else {
            	lista = controller.buscar(filtro);
            }

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
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirFornecedor() {
        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um fornecedor.");
            return;
        }

        int idFornecedor = (int) modelo.getValueAt(linha, 0);

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir este fornecedor?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                if (controller.excluir(idFornecedor)) {
                    JOptionPane.showMessageDialog(this, "Fornecedor excluído com sucesso!");
                    carregarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível excluir.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }

    private void editarFornecedor() {
        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um fornecedor.");
            return;
        }

        int idFornecedor = (int) modelo.getValueAt(linha, 0);
        new TelaCadastroFornecedor(this, idFornecedor).setVisible(true);
    }
}