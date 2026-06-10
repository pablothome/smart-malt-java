package view;

import controller.FornecedorController;
import model.Fornecedor;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;

public class TelaCadastroFornecedor extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField txtNome, txtEmail;
	private JFormattedTextField txtCnpj, txtTelefone;
	private JButton btnSalvar, btnVoltar;

	private FornecedorController controller;
	private TelaListarFornecedor telaPai;
	private int idFornecedor = 0;

	public TelaCadastroFornecedor(TelaListarFornecedor telaPai) {
		this(telaPai, 0);
	}

	public TelaCadastroFornecedor(TelaListarFornecedor telaPai, int idFornecedor) {
		this.telaPai = telaPai;
		this.idFornecedor = idFornecedor;
		this.controller = new FornecedorController();

		setTitle(idFornecedor == 0 ? "Cadastro de Fornecedor" : "Editar Fornecedor");
		setSize(500, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout(10, 10));

		JPanel painelCampos = new JPanel(new GridLayout(4, 2, 10, 10));
		painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		painelCampos.add(new JLabel("Nome:"));
		txtNome = new JTextField();
		painelCampos.add(txtNome);

		painelCampos.add(new JLabel("CNPJ:"));
		txtCnpj = criarCampoFormatado("##.###.###/####-##");
		painelCampos.add(txtCnpj);

		painelCampos.add(new JLabel("Telefone:"));
		txtTelefone = criarCampoFormatado("(##) #####-####");
		painelCampos.add(txtTelefone);

		painelCampos.add(new JLabel("Email:"));
		txtEmail = new JTextField();
		painelCampos.add(txtEmail);

		add(painelCampos, BorderLayout.CENTER);

		JPanel painelBotoes = new JPanel(new FlowLayout());

		btnSalvar = new JButton("Salvar");
		btnVoltar = new JButton("Voltar");

		painelBotoes.add(btnSalvar);
		painelBotoes.add(btnVoltar);

		add(painelBotoes, BorderLayout.SOUTH);

		btnSalvar.addActionListener(e -> salvarFornecedor());
		btnVoltar.addActionListener(e -> dispose());

		if (idFornecedor != 0) {
			carregarFornecedor();
		}

		setVisible(true);
	}

	private JFormattedTextField criarCampoFormatado(String mascara) {
		try {
			MaskFormatter formatter = new MaskFormatter(mascara);
			formatter.setPlaceholderCharacter('_');
			return new JFormattedTextField(formatter);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao criar campo formatado.");
		}
	}

	private void carregarFornecedor() {
		try {
			Fornecedor fornecedor = controller.buscarPorId(idFornecedor);

			if (fornecedor != null) {
				txtNome.setText(fornecedor.getNomeFornecedor());
				txtCnpj.setText(fornecedor.getCnpjFornecedor());
				txtTelefone.setText(fornecedor.getTelefoneFornecedor());
				txtEmail.setText(fornecedor.getEmailFornecedor());
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage());
		}
	}

	private void salvarFornecedor() {
		try {
			String nome = txtNome.getText().trim();
			String cnpj = txtCnpj.getText().trim();
			String telefone = txtTelefone.getText().trim();
			String email = txtEmail.getText().trim();

			if (nome.isEmpty() || cnpj.contains("_") || telefone.contains("_") || email.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Preencha todos os campos corretamente.", "Campos obrigatórios",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			boolean sucesso;

			if (idFornecedor == 0) {
				sucesso = controller.salvar(nome, cnpj, telefone, email);
			} else {
				sucesso = controller.atualizar(idFornecedor, nome, cnpj, telefone, email);
			}

			if (sucesso) {
				JOptionPane.showMessageDialog(this, "Fornecedor salvo com sucesso!");

				if (telaPai != null) {
					telaPai.carregarTabela();
				}

				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Não foi possível salvar.");
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de validação", JOptionPane.ERROR_MESSAGE);
		}
	}
}