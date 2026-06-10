package view;

import javax.swing.*;

import controller.ProdutoController;
import model.Produto;

import java.text.NumberFormat;
import java.util.Locale;

public class TelaCadastroProduto extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField txtNome, txtMarca, txtCategoria, txtPreco, txtQuantidade, txtEstoqueMinimo;
	private JButton btnSalvar;

	public TelaCadastroProduto() {
		setTitle("Cadastro de Produto");
		setSize(400, 350);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);

		// NOME
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(20, 20, 100, 25);
		add(lblNome);

		txtNome = new JTextField();
		txtNome.setBounds(120, 20, 200, 25);
		add(txtNome);

		// MARCA
		JLabel lblMarca = new JLabel("Marca:");
		lblMarca.setBounds(20, 60, 100, 25);
		add(lblMarca);

		txtMarca = new JTextField();
		txtMarca.setBounds(120, 60, 200, 25);
		add(txtMarca);

		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setBounds(20, 100, 100, 25);
		add(lblCategoria);

		txtCategoria = new JTextField();
		txtCategoria.setBounds(120, 100, 200, 25);
		add(txtCategoria);

		JLabel lblPreco = new JLabel("Preço:");
		lblPreco.setBounds(20, 140, 100, 25);
		add(lblPreco);

		txtPreco = new JTextField("R$ 0,00");
		txtPreco.setBounds(120, 140, 200, 25);
		add(txtPreco);

		aplicarMascaraMoeda(txtPreco);

		JLabel lblQuantidade = new JLabel("Quantidade:");
		lblQuantidade.setBounds(20, 180, 100, 25);
		add(lblQuantidade);

		txtQuantidade = new JTextField();
		txtQuantidade.setBounds(120, 180, 200, 25);
		add(txtQuantidade);

		txtQuantidade.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				char c = evt.getKeyChar();
				if (!Character.isDigit(c) && c != '\b') {
					evt.consume();
				}
			}
		});

		// ESTOQUE MÍNIMO
		JLabel lblEstoqueMinimo = new JLabel("Estoque Mínimo:");
		lblEstoqueMinimo.setBounds(20, 220, 120, 25);
		add(lblEstoqueMinimo);

		txtEstoqueMinimo = new JTextField();
		txtEstoqueMinimo.setBounds(150, 220, 170, 25);
		add(txtEstoqueMinimo);

		txtEstoqueMinimo.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				char c = evt.getKeyChar();
				if (!Character.isDigit(c) && c != '\b') {
					evt.consume();
				}
			}
		});

		// BOTÃO SALVAR
		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(140, 270, 100, 30);
		add(btnSalvar);

		btnSalvar.addActionListener(e -> salvarProduto());

		setVisible(true);
	}

	private void aplicarMascaraMoeda(JTextField campo) {
		campo.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyReleased(java.awt.event.KeyEvent e) {
				String texto = campo.getText().replaceAll("[^\\d]", "");

				if (texto.isEmpty()) {
					campo.setText("R$ 0,00");
					return;
				}

				double valor = Double.parseDouble(texto) / 100.0;
				NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
				campo.setText(formato.format(valor));
			}
		});
	}

	private double converterPreco(String textoPreco) {
		try {
			String texto = textoPreco.replace("R$", "").replace(".", "").replace(",", ".").replace("\u00A0", "") // remove
																													// espaço
																													// invisível
					.trim();

			System.out.println(">>> TEXTO PREÇO TRATADO PRODUTO: [" + texto + "]");

			return texto.isEmpty() ? 0.0 : Double.parseDouble(texto);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Preço inválido.");
		}
	}

	private void salvarProduto() {
		try {
			String nome = txtNome.getText().trim();
			String marca = txtMarca.getText().trim();
			String categoria = txtCategoria.getText().trim();

			if (nome.isEmpty() || marca.isEmpty() || categoria.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Preencha nome, marca e categoria.");
				return;
			}

			if (txtQuantidade.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Informe a quantidade.");
				return;
			}

			if (txtEstoqueMinimo.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Informe o estoque mínimo.");
				return;
			}

			double preco = converterPreco(txtPreco.getText());
			int quantidade = Integer.parseInt(txtQuantidade.getText().trim());
			int estoqueMinimo = Integer.parseInt(txtEstoqueMinimo.getText().trim());

			if (preco <= 0) {
				JOptionPane.showMessageDialog(this, "Informe um preço válido.");
				return;
			}

			if (quantidade < 0) {
				JOptionPane.showMessageDialog(this, "Quantidade inválida.");
				return;
			}

			if (estoqueMinimo < 0) {
				JOptionPane.showMessageDialog(this, "Estoque mínimo inválido.");
				return;
			}

			Produto produto = new Produto();
			produto.setNomeProduto(nome);
			produto.setMarcaProduto(marca);
			produto.setCategoriaProduto(categoria);
			produto.setPrecoProduto(preco);
			produto.setQuantidadeEstoque(quantidade);
			produto.setEstoqueMinimo(estoqueMinimo);

			ProdutoController controller = new ProdutoController();
			boolean sucesso = controller.cadastrarProduto(produto);

			if (sucesso) {
				JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
				limparCampos();
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Erro ao cadastrar produto.");
			}

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Quantidade ou estoque mínimo inválido.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void limparCampos() {
		txtNome.setText("");
		txtMarca.setText("");
		txtCategoria.setText("");
		txtPreco.setText("R$ 0,00");
		txtQuantidade.setText("");
		txtEstoqueMinimo.setText("");
	}
}