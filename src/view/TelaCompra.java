package view;

import controller.CompraController;
import controller.ProdutoController;
import model.ItemCompra;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TelaCompra extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<String> cbFornecedor;
	private JComboBox<String> cbProduto;

	private JTextField txtQuantidade;
	private JTextField txtPreco;

	private JTable tabela;
	private DefaultTableModel modelo;

	private JLabel lblTotal;

	private List<ItemCompra> carrinho = new ArrayList<>();

	private CompraController controller = new CompraController();

	private ProdutoController produtoController = new ProdutoController();

	public TelaCompra() {

		setTitle("Registro de Compra");
		setSize(900, 600);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		JPanel topo = new JPanel(new FlowLayout());

		cbFornecedor = new JComboBox<>(controller.getFornecedores());
		cbProduto = new JComboBox<>(produtoController.getProdutos());

		txtQuantidade = new JTextField(5);

		NumberFormat formatoMoeda = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
		formatoMoeda.setMinimumFractionDigits(2);
		formatoMoeda.setMaximumFractionDigits(2);

		NumberFormatter formatter = new NumberFormatter(formatoMoeda);
		formatter.setValueClass(Double.class);
		formatter.setAllowsInvalid(false);
		formatter.setMinimum(0.0);

		txtPreco = new JTextField(10);

		txtPreco.addKeyListener(new java.awt.event.KeyAdapter() {

			@Override
			public void keyReleased(java.awt.event.KeyEvent e) {

				String texto = txtPreco.getText().replaceAll("[^0-9]", "");

				if (texto.isEmpty()) {
					txtPreco.setText("R$ 0,00");
					return;
				}

				double valor = Double.parseDouble(texto) / 100;

				java.text.NumberFormat nf = java.text.NumberFormat
						.getCurrencyInstance(new java.util.Locale("pt", "BR"));

				txtPreco.setText(nf.format(valor));
			}

		});

		JButton btnAdicionar = new JButton("Adicionar");

		topo.add(new JLabel("Fornecedor:"));
		topo.add(cbFornecedor);

		topo.add(new JLabel("Produto:"));
		topo.add(cbProduto);

		topo.add(new JLabel("Qtd:"));
		topo.add(txtQuantidade);

		topo.add(new JLabel("Preço Compra:"));
		topo.add(txtPreco);

		topo.add(btnAdicionar);

		add(topo, BorderLayout.NORTH);

		String[] colunas = { "Produto", "Qtd", "Preço", "Total" };
		modelo = new DefaultTableModel(colunas, 0);
		tabela = new JTable(modelo);

		add(new JScrollPane(tabela), BorderLayout.CENTER);

		JPanel rodape = new JPanel(new FlowLayout());

		lblTotal = new JLabel("Total: R$ 0.00");

		JButton btnRemover = new JButton("Remover Item");
		JButton btnFinalizar = new JButton("Finalizar Compra");

		rodape.add(lblTotal);
		rodape.add(btnRemover);
		rodape.add(btnFinalizar);

		add(rodape, BorderLayout.SOUTH);

		btnAdicionar.addActionListener(e -> adicionarItem());
		btnRemover.addActionListener(e -> removerItem());
		btnFinalizar.addActionListener(e -> finalizarCompra());

		setVisible(true);
	}

	private void adicionarItem() {
		try {
			String produtoSelecionado = (String) cbProduto.getSelectedItem();
			int idProduto = produtoController.getIdProduto(produtoSelecionado);

			int quantidade = Integer.parseInt(txtQuantidade.getText());
			double preco = converterPreco(txtPreco.getText());

			ItemCompra item = new ItemCompra();
			item.setIdProduto(idProduto);
			item.setQuantidade(quantidade);
			item.setPreco(preco);

			carrinho.add(item);

			modelo.addRow(new Object[] { produtoSelecionado, quantidade, String.format("R$ %.2f", preco),
					String.format("R$ %.2f", item.getTotal()) });

			atualizarTotal();

			txtQuantidade.setText("");
			txtPreco.setText("");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao adicionar item.");
		}
	}

	private void removerItem() {
		int linha = tabela.getSelectedRow();

		if (linha >= 0) {
			carrinho.remove(linha);
			modelo.removeRow(linha);
			atualizarTotal();
		} else {
			JOptionPane.showMessageDialog(this, "Selecione um item.");
		}
	}

	private void atualizarTotal() {
		double total = carrinho.stream().mapToDouble(ItemCompra::getTotal).sum();

		lblTotal.setText("Total: R$ " + String.format("%.2f", total));
	}

	private void finalizarCompra() {

		if (carrinho.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Adicione itens!");
			return;
		}

		int idFornecedor = controller.getIdFornecedor((String) cbFornecedor.getSelectedItem());

		boolean sucesso = controller.registrarCompra(carrinho, idFornecedor);

		if (sucesso) {
			JOptionPane.showMessageDialog(this, "Compra realizada com sucesso!");

			carrinho.clear();
			modelo.setRowCount(0);
			atualizarTotal();
		} else {
			JOptionPane.showMessageDialog(this, "Erro ao registrar compra.");
		}
	}

	private double converterPreco(String textoPreco) {

		try {

			String texto = textoPreco.replace("R$", "").replace(".", "").replace(",", ".").replace("\u00A0", "").trim();

			return texto.isEmpty() ? 0.0 : Double.parseDouble(texto);

		} catch (Exception e) {
			throw new RuntimeException("Preço inválido.");
		}
	}

}