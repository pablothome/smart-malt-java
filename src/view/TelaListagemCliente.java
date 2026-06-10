package view;

import javax.swing.*;

import util.Sessao;
import javax.swing.table.DefaultTableModel;

import controller.ClienteController;

import model.Cliente;


import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class TelaListagemCliente extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTable tabela;
	private DefaultTableModel modelo;
	private JTextField txtBusca;

	public TelaListagemCliente() {
		setTitle("Listagem de Clientes");
		setSize(750, 420);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel painelBusca = new JPanel(
				new FlowLayout(FlowLayout.LEFT)
				);
		
		painelBusca.add(new JLabel(
				"Buscar:"
				));
		
		txtBusca = new JTextField(25);
		
		painelBusca.add(txtBusca);
		add(painelBusca, BorderLayout.NORTH);
				
		
		txtBusca.getDocument().addDocumentListener(
		        new javax.swing.event.DocumentListener() {

		    @Override
		    public void insertUpdate(
		            javax.swing.event.DocumentEvent e
		    ) {
		        filtrarClientes();
		    }

		    @Override
		    public void removeUpdate(
		            javax.swing.event.DocumentEvent e
		    ) {
		        filtrarClientes();
		    }

		    @Override
		    public void changedUpdate(
		            javax.swing.event.DocumentEvent e
		    ) {
		        filtrarClientes();
		    }
		});
		

		// ==============================
		// TABELA
		// ==============================
		modelo = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		modelo.addColumn("CPF");
		modelo.addColumn("Nome");
		modelo.addColumn("Telefone");
		modelo.addColumn("Email");

		tabela = new JTable(modelo);
		JScrollPane scroll = new JScrollPane(tabela);
		add(scroll, BorderLayout.CENTER);

		// ==============================
		// PAINEL DE BOTÕES
		// ==============================
		JPanel painelBotoes = new JPanel();

		JButton btnNovo = new JButton("Novo Cliente");
		JButton btnEditar = new JButton("Editar");
		JButton btnAtualizar = new JButton("Atualizar");
		JButton btnExcluir = new JButton("Excluir");
		JButton btnVoltar = new JButton("Voltar");

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

		// ==============================
		// EVENTOS
		// ==============================

		// Buscar
		

		

		// Novo Cliente
		btnNovo.addActionListener(e -> {
			TelaCadastroCliente tela = new TelaCadastroCliente();
			tela.setVisible(true);

			tela.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					carregarClientes();
				}
			});
		});

		// Atualizar lista
		btnAtualizar.addActionListener(e -> {
			txtBusca.setText("");
			carregarClientes();
		});

		// Editar
		
		btnEditar.addActionListener(e -> {
			if (Sessao.isFuncionario()) {
				JOptionPane.showMessageDialog(this, "Você não tem permissão para editar clientes.");
				return;
			}

			editarCliente();
		});

		// Excluir
		
		btnExcluir.addActionListener(e -> {
			if (Sessao.isFuncionario()) {
				JOptionPane.showMessageDialog(this, "Você não tem permissão para excluir clientes.");
				return;
			}

			excluirCliente();
		});

		// Voltar

		btnVoltar.addActionListener(e -> dispose());

		// Duplo clique para editar
		tabela.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					editarCliente();
				}
			}
		});

		carregarClientes();
		setVisible(true);
	}

	
	
	private void filtrarClientes() {

	    String filtro = txtBusca.getText().trim();

	    ClienteController controller =
	            new ClienteController();

	    try {

	        List<Cliente> clientes;

	        if (filtro.isEmpty()) {

	            clientes = controller.listarClientes();

	        } else {

	            clientes =
	                    controller.buscarPorNomeOuCpf(filtro);
	        }

	        atualizarTabela(clientes);

	    } catch (Exception e) {

	        modelo.setRowCount(0);
	    }
	}


	// ==============================
	// ATUALIZAR TABELA
	// ==============================
	private void atualizarTabela(List<Cliente> clientes) {
		modelo.setRowCount(0);

		for (Cliente c : clientes) {
			modelo.addRow(new Object[] { c.getCpfCliente(), c.getNomeCliente(), c.getTelefoneCliente(),
					c.getEmailCliente() });
		}
	}

	// ==============================
	// CARREGAR TODOS OS CLIENTES
	// ==============================
	public void carregarClientes() {
		try {
			ClienteController controller = new ClienteController();
			List<Cliente> clientes = controller.listarClientes();
			atualizarTabela(clientes);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + e.getMessage());
		}
	}

	// ==============================
	// EDITAR CLIENTE
	// ==============================
	private void editarCliente() {
		int linha = tabela.getSelectedRow();

		if (linha == -1) {
			JOptionPane.showMessageDialog(this, "Selecione um cliente!");
			return;
		}

		String cpf = tabela.getValueAt(linha, 0).toString();

		try {
			ClienteController controller = new ClienteController();
			Cliente cliente = controller.buscarClientePorCpf(cpf);

			if (cliente != null) {
				TelaEditarCliente tela = new TelaEditarCliente(cliente);

				tela.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						carregarClientes();
					}
				});

				tela.setVisible(true);

			} else {
				JOptionPane.showMessageDialog(this, "Cliente não encontrado!");
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao abrir edição: " + e.getMessage());
		}
	}

	// ==============================
	// EXCLUIR CLIENTE
	// ==============================
	private void excluirCliente() {
		int linha = tabela.getSelectedRow();

		if (linha == -1) {
			JOptionPane.showMessageDialog(this, "Selecione um cliente!");
			return;
		}

		String cpf = tabela.getValueAt(linha, 0).toString();

		int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o cliente?",
				"Confirmação", JOptionPane.YES_NO_OPTION);

		if (confirmacao == JOptionPane.YES_OPTION) {
			try {
				ClienteController controller = new ClienteController();
				boolean excluido = controller.excluirCliente(cpf);

				if (excluido) {
					JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
					carregarClientes();
				} else {
					JOptionPane.showMessageDialog(this, "Erro ao excluir cliente!");
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Erro ao excluir cliente: " + e.getMessage());
			}
		}
	}
}