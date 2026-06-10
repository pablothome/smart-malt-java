package view;

import javax.swing.*;

import util.Sessao;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import controller.ProdutoController;
import dao.ProdutoDAO;
import model.Produto;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class TelaListagemProduto extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tabela;
    private DefaultTableModel modelo;
    private JTextField txtBuscar;

    public TelaListagemProduto() {
        setTitle("Listagem de Produtos");
        setSize(700, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painelBusca = new JPanel(
        		new FlowLayout(FlowLayout.LEFT)
        		);
        

        

       painelBusca.add(new JLabel("Buscar:"));
       
       txtBuscar = new JTextField(25);
       
       painelBusca.add(txtBuscar);
       
       add(painelBusca, BorderLayout.NORTH);
       
       txtBuscar.getDocument().addDocumentListener(
    	        new javax.swing.event.DocumentListener() {

    	    @Override
    	    public void insertUpdate(
    	            javax.swing.event.DocumentEvent e
    	    ) {
    	        filtrarTabela();
    	    }

    	    @Override
    	    public void removeUpdate(
    	            javax.swing.event.DocumentEvent e
    	    ) {
    	        filtrarTabela();
    	    }

    	    @Override
    	    public void changedUpdate(
    	            javax.swing.event.DocumentEvent e
    	    ) {
    	        filtrarTabela();
    	    }
    	});
       
       
       

        

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("Marca");
        modelo.addColumn("Categoria");
        modelo.addColumn("Preço");
        modelo.addColumn("Quantidade");
        modelo.addColumn("Estoque Mínimo");

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        JButton btnNovo = new JButton("Novo Produto");
        JButton btnEditar = new JButton("Editar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnVoltar = new JButton("Voltar");
        
        if (Sessao.isFuncionario()) {
            btnNovo.setVisible(false);
            btnEditar.setVisible(false);
            btnExcluir.setVisible(false);
        }

        painelBotoes.add(btnNovo);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnVoltar);
        add(painelBotoes, BorderLayout.SOUTH);

        btnNovo.addActionListener(e -> {
            if (Sessao.isFuncionario()) {
            	JOptionPane.showMessageDialog(this, "Você não tem permissão para cadastrar produtos ");
            	return;
            }
            TelaCadastroProduto cadastro = new TelaCadastroProduto();
            cadastro.setVisible(true);
        });
        
        

        btnAtualizar.addActionListener(e -> {
        	
        	txtBuscar.setText("");
        	
        	carregarProdutos();
        });
     
        btnEditar.addActionListener(e -> {
            if (Sessao.isFuncionario()) {
                JOptionPane.showMessageDialog(this, "Você não tem permissão para editar produtos.");
                return;
            }

            editarProduto();
        });
        
        btnExcluir.addActionListener(e -> {
            if (Sessao.isFuncionario()) {
                JOptionPane.showMessageDialog(this, "Você não tem permissão para excluir produtos.");
                return;
            }

            excluirProduto();
        });

        btnVoltar.addActionListener(e -> dispose());
        

      

        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    editarProduto();
                }
            }
        });

        carregarProdutos();
        setVisible(true);
    }
    
    private void filtrarTabela() {

	    String filtro =
	            txtBuscar.getText().trim();

	    ProdutoController controller =
	            new ProdutoController();

	    List<Produto> lista;

	    if (filtro.isEmpty()) {

	        lista = controller.listarProdutos();

	    } else {

	        lista =
	                controller.buscarProdutos(filtro);
	    }

	    modelo.setRowCount(0);

	    for (Produto produto : lista) {

	        modelo.addRow(new Object[] {

	                produto.getIdProduto(),
	                produto.getNomeProduto(),
	                produto.getMarcaProduto(),
	                produto.getCategoriaProduto(),
	                String.format(
	                        "R$ %.2f",
	                        produto.getPrecoProduto()
	                ),
	                produto.getQuantidadeEstoque(),
	                produto.getEstoqueMinimo()
	        });
	    }
	}

    private void atualizarTabela(List<Produto> produtos) {
        modelo.setRowCount(0);
        for (Produto p : produtos) {
            modelo.addRow(new Object[]{
                    p.getIdProduto(),
                    p.getNomeProduto(),
                    p.getMarcaProduto(),
                    p.getCategoriaProduto(),
                    p.getPrecoProduto(),
                    p.getQuantidadeEstoque()
            });
        }
    }

    private void carregarProdutos() {
        modelo.setRowCount(0);
        ProdutoController controller = new ProdutoController();
        List<Produto> lista = controller.listarProdutos();
        atualizarTabela(lista);
    }

    private void editarProduto() {
        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto!");
            return;
        }

        try {
            Produto p = new Produto();
            p.setIdProduto(Integer.parseInt(tabela.getValueAt(linha, 0).toString()));
            p.setNomeProduto(String.valueOf(tabela.getValueAt(linha, 1)));
            p.setMarcaProduto(String.valueOf(tabela.getValueAt(linha, 2)));
            p.setCategoriaProduto(String.valueOf(tabela.getValueAt(linha, 3)));
            p.setPrecoProduto(Double.parseDouble(tabela.getValueAt(linha, 4).toString()));
            p.setQuantidadeEstoque(Integer.parseInt(tabela.getValueAt(linha, 5).toString()));

            TelaEditarProduto tela = new TelaEditarProduto(p);
            tela.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    carregarProdutos();
                }
            });
            tela.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir edição!");
            e.printStackTrace();
        }
    }

    private void excluirProduto() {
        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto!");
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(tabela.getValueAt(linha, 0).toString());
                ProdutoController controller = new ProdutoController();
                controller.excluirProduto(id);
                JOptionPane.showMessageDialog(this, "Produto excluído!");
                carregarProdutos();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir!");
                e.printStackTrace();
            }
        }
    }
}