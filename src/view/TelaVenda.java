package view;

import controller.ClienteController;
import controller.PedidoController;
import controller.ProdutoController;

import model.ItemPedido;
import model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TelaVenda extends JFrame {

    private static final long serialVersionUID = 1L;

    private JComboBox<String> cbCliente;
    private JComboBox<String> cbProduto;

    private JTextField txtQuantidade;
    private JTextField txtPreco;

    private JTable tabela;
    private DefaultTableModel modelo;

    private JLabel lblTotal;
    private JLabel lblEstoque;
    private JLabel lblSubtotal;

    private List<ItemPedido> carrinho = new ArrayList<>();

    private ProdutoController produtoController =
            new ProdutoController();

    private PedidoController pedidoController =
            new PedidoController();

    private ClienteController clienteController =
            new ClienteController();

    public TelaVenda() {

        setTitle("Registro de Venda");

        setSize(900, 600);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // ============================
        // TOPO
        // ============================
        JPanel topo = new JPanel(new FlowLayout());

        cbCliente = new JComboBox<>(
                clienteController.getClientes()
        );

        cbProduto = new JComboBox<>(
                produtoController.getProdutos()
        );

        txtQuantidade = new JTextField(5);

        txtPreco = new JTextField(10);
        txtPreco.setEditable(false);

        lblEstoque = new JLabel("Estoque: 0");

        lblSubtotal = new JLabel("Subtotal: R$ 0,00");

        JButton btnAdicionar =
                new JButton("Adicionar");

        topo.add(new JLabel("Cliente:"));
        topo.add(cbCliente);

        topo.add(new JLabel("Produto:"));
        topo.add(cbProduto);

        topo.add(lblEstoque);

        topo.add(new JLabel("Qtd:"));
        topo.add(txtQuantidade);

        topo.add(new JLabel("Preço:"));
        topo.add(txtPreco);

        topo.add(lblSubtotal);

        topo.add(btnAdicionar);

        add(topo, BorderLayout.NORTH);

        // ============================
        // TABELA
        // ============================
        String[] colunas = {
                "Produto",
                "Qtd",
                "Preço",
                "Subtotal"
        };

        modelo = new DefaultTableModel(colunas, 0);

        tabela = new JTable(modelo);

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // ============================
        // RODAPÉ
        // ============================
        JPanel rodape = new JPanel(new FlowLayout());

        lblTotal = new JLabel("Total: R$ 0,00");

        JButton btnRemover =
                new JButton("Remover Item");

        JButton btnFinalizar =
                new JButton("Finalizar Venda");

        rodape.add(lblTotal);
        rodape.add(btnRemover);
        rodape.add(btnFinalizar);

        add(rodape, BorderLayout.SOUTH);

        // ============================
        // EVENTOS
        // ============================

        cbProduto.addActionListener(e -> {

            atualizarEstoqueEPreco();

            atualizarSubtotal();
        });

        txtQuantidade.addKeyListener(
                new java.awt.event.KeyAdapter() {

                    @Override
                    public void keyReleased(
                            java.awt.event.KeyEvent e
                    ) {
                        atualizarSubtotal();
                    }
                }
        );

        btnAdicionar.addActionListener(
                e -> adicionarItem()
        );

        btnRemover.addActionListener(
                e -> removerItem()
        );

        btnFinalizar.addActionListener(
                e -> finalizarVenda()
        );

        atualizarEstoqueEPreco();

        setVisible(true);
    }

    // ============================
    // ESTOQUE E PREÇO
    // ============================
    
    private void atualizarEstoqueEPreco() {
    	try {
    		
    		String produtoSelecionado = (String) cbProduto.getSelectedItem();
    		
    		int idProduto = produtoController.getIdProduto(produtoSelecionado);
    		
    		Produto produto = produtoController.buscarPorId(idProduto);
    		
    		if (produto != null) {
    			
    			int quantidadeNoCarrinho = carrinho.stream()
    					.filter(i -> i.getIdProduto() == idProduto)
    					.mapToInt(ItemPedido::getQuantidade)
    					.sum();
    			
    			int estoqueDisponivel = produto.getQuantidadeEstoque() - quantidadeNoCarrinho;
    			
    			if (estoqueDisponivel < 0) {
    				estoqueDisponivel = 0;
    			}
    			
    			lblEstoque.setText("Estoque: " + estoqueDisponivel
    					);
    			
    			txtPreco.setText(String.format("R$%.2f", produto.getPrecoProduto() 
    					)
    					);
    		}
    	} catch (Exception e) {
    	lblEstoque.setText("Estoque: 0");	
    	}
    }
    
    

    // ============================
    // SUBTOTAL
    // ============================
    private void atualizarSubtotal() {

        try {

            String produtoSelecionado =
                    (String) cbProduto.getSelectedItem();

            int idProduto =
                    produtoController.getIdProduto(
                            produtoSelecionado
                    );

            Produto produto =
                    produtoController.buscarPorId(
                            idProduto
                    );

            int quantidade =
                    Integer.parseInt(
                            txtQuantidade.getText()
                    );

            double subtotal =
                    quantidade * produto.getPrecoProduto();

            lblSubtotal.setText(
                    "Subtotal: R$ "
                            + String.format("%.2f", subtotal)
            );

        } catch (Exception e) {

            lblSubtotal.setText(
                    "Subtotal: R$ 0,00"
            );
        }
    }

    // ============================
    // ADICIONAR ITEM
    // ============================
    private void adicionarItem() {

        try {

            String produtoSelecionado =
                    (String) cbProduto.getSelectedItem();

            int idProduto =
                    produtoController.getIdProduto(
                            produtoSelecionado
                    );

            Produto produto =
                    produtoController.buscarPorId(
                            idProduto
                    );
            
            int quantidadeNoCarrinho = carrinho.stream()
            		.filter(i -> i.getIdProduto() == idProduto)
            		.mapToInt(ItemPedido::getQuantidade)
            		.sum();
            
            int estoqueDisponivel = 
            		produto.getQuantidadeEstoque() - quantidadeNoCarrinho;

            if (produto == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Produto não encontrado."
                );

                return;
            }

            int quantidade =
                    Integer.parseInt(
                            txtQuantidade.getText()
                    );

            if (quantidade <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Quantidade inválida."
                );

                return;
            }

            if (estoqueDisponivel <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Produto sem estoque."
                );

                return;
            }

            if (quantidade > estoqueDisponivel) {

                JOptionPane.showMessageDialog(
                        this,
                        "Estoque insuficiente.\nDisponível: " + estoqueDisponivel
                );

                return;
            }

            double preco =
                    produto.getPrecoProduto();

            double subtotal =
                    quantidade * preco;

            ItemPedido item =
                    new ItemPedido();

            item.setIdProduto(idProduto);

            item.setQuantidade(quantidade);

            item.setPrecoVenda(preco);

            carrinho.add(item);

            modelo.addRow(new Object[]{

                    produtoSelecionado,

                    quantidade,

                    String.format(
                            "R$ %.2f",
                            preco
                    ),

                    String.format(
                            "R$ %.2f",
                            subtotal
                    )
            });

            atualizarTotalCarrinho();
            atualizarEstoqueEPreco();

            txtQuantidade.setText("");

            lblSubtotal.setText(
                    "Subtotal: R$ 0,00"
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }

    // ============================
    // REMOVER ITEM
    // ============================
    private void removerItem() {

        int linha =
                tabela.getSelectedRow();

        if (linha >= 0) {

            carrinho.remove(linha);

            modelo.removeRow(linha);

            atualizarTotalCarrinho();
            atualizarEstoqueEPreco();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um item."
            );
        }
    }

    // ============================
    // TOTAL CARRINHO
    // ============================
    private void atualizarTotalCarrinho() {

        double total = 0;

        for (ItemPedido item : carrinho) {

            total +=
                    item.getQuantidade()
                            * item.getPrecoVenda();
        }

        lblTotal.setText(
                "Total: R$ "
                        + String.format("%.2f", total)
        );
    }

    // ============================
    // FINALIZAR VENDA
    // ============================
    private void finalizarVenda() {

        try {

            if (carrinho.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Adicione itens!"
                );

                return;
            }

            String cpfCliente =
                    clienteController.getCpfCliente(
                            (String)
                                    cbCliente.getSelectedItem()
                    );

            boolean sucesso =
                    pedidoController.registrarPedido(
                            carrinho,
                            cpfCliente
                    );

            if (sucesso) {

                JOptionPane.showMessageDialog(
                        this,
                        "Venda realizada com sucesso!"
                );

                carrinho.clear();

                modelo.setRowCount(0);

                atualizarTotalCarrinho();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao registrar venda."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }
}