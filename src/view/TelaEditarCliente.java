package view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import controller.ClienteController;
import model.Cliente;
import java.awt.*;

public class TelaEditarCliente extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField txtNome;
	private JFormattedTextField txtCpf;
	private JFormattedTextField txtTelefone;
	private JTextField txtEmail;

	private Cliente cliente;
	private ClienteController controller;

	public TelaEditarCliente(Cliente cliente) {
		this.cliente = cliente;
		this.controller = new ClienteController();

		setTitle("Editar Cliente");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("Nome:"), gbc);
		txtNome = new JTextField(cliente.getNomeCliente());
		gbc.gridx = 1;
		add(txtNome, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		add(new JLabel("CPF:"), gbc);
		try {
			MaskFormatter cpfMask = new MaskFormatter("###.###.###-##");
			cpfMask.setPlaceholderCharacter('_');
			txtCpf = new JFormattedTextField(cpfMask);
			txtCpf.setText(cliente.getCpfCliente());
			txtCpf.setEditable(false);
		} catch (Exception e) {
			e.printStackTrace();
			txtCpf = new JFormattedTextField(cliente.getCpfCliente());
			txtCpf.setEditable(false);
		}
		gbc.gridx = 1;
		add(txtCpf, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("Telefone:"), gbc);
		try {
			MaskFormatter telMask = new MaskFormatter("(##) #####-####");
			telMask.setPlaceholderCharacter('_');
			txtTelefone = new JFormattedTextField(telMask);
			txtTelefone.setText(cliente.getTelefoneCliente());
		} catch (Exception e) {
			e.printStackTrace();
			txtTelefone = new JFormattedTextField(cliente.getTelefoneCliente());
		}
		gbc.gridx = 1;
		add(txtTelefone, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		add(new JLabel("Email:"), gbc);
		txtEmail = new JTextField(cliente.getEmailCliente());
		gbc.gridx = 1;
		add(txtEmail, gbc);

		JPanel painelBotoes = new JPanel();
		JButton btnSalvar = new JButton("Salvar");
		JButton btnCancelar = new JButton("Cancelar");
		painelBotoes.add(btnSalvar);
		painelBotoes.add(btnCancelar);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		add(painelBotoes, gbc);

		btnSalvar.addActionListener(e -> salvarCliente());
		btnCancelar.addActionListener(e -> dispose());

		setVisible(true);
	}

	private void salvarCliente() {
		try {
			String nome = txtNome.getText().trim();
			String cpf = txtCpf.getText().replaceAll("[^0-9]", "");
			String telefone = txtTelefone.getText().replaceAll("[^0-9]", "");
			String email = txtEmail.getText().trim();

			if (nome.isEmpty()) {
				JOptionPane.showMessageDialog(this, "O nome é obrigatório!");
				txtNome.requestFocus();
				return;
			}

			if (cpf.length() != 11) {
				JOptionPane.showMessageDialog(this, "CPF inválido!");
				txtCpf.requestFocus();
				return;
			}

			if (telefone.isEmpty() || telefone.length() < 10) {
				JOptionPane.showMessageDialog(this, "Telefone inválido!");
				txtTelefone.requestFocus();
				return;
			}

			if (email.isEmpty() || !email.contains("@")) {
				JOptionPane.showMessageDialog(this, "Email inválido!");
				txtEmail.requestFocus();
				return;
			}

			boolean atualizado = controller.atualizarCliente(nome, cpf, telefone, email);

			if (atualizado) {
				JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Falha ao atualizar cliente!");
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
			e.printStackTrace();
		}
	}
}