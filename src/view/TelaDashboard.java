package view;

import controller.DashboardController;
import model.Usuario;
import util.Sessao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class TelaDashboard extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel lblProdutos;
	private JLabel lblClientes;
	private JLabel lblFornecedores;
	private JLabel lblCriticos;
	private JLabel lblVendas;
	private JLabel lblCompras;
	private JLabel lblLucro;

	private JTable tabela;
	private DashboardController controller;

	public TelaDashboard(Usuario usuario) {
		controller = new DashboardController();

		setTitle("Dashboard Gerencial - SmartMalte");
		setSize(1100, 650);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(10, 10));

		JPanel painelTopo = new JPanel(new BorderLayout());
		JLabel titulo = new JLabel("SMARTMALTE - DASHBOARD GERENCIAL", SwingConstants.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 24));
		painelTopo.add(titulo, BorderLayout.CENTER);

		JLabel lblUsuario = new JLabel("Usuário: " + usuario.getNomeUsuario() + " | Perfil: " + usuario.getPerfil());
		lblUsuario.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		painelTopo.add(lblUsuario, BorderLayout.SOUTH);

		add(painelTopo, BorderLayout.NORTH);

		JPanel painelCentro = new JPanel(new BorderLayout(10, 10));

		JPanel painelCards = new JPanel(new GridLayout(2, 4, 10, 10));
		painelCards.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		lblProdutos = criarCard(painelCards, "Produtos");
		lblClientes = criarCard(painelCards, "Clientes");
		lblFornecedores = criarCard(painelCards, "Fornecedores");
		lblCriticos = criarCard(painelCards, "Estoque Crítico");
		lblVendas = criarCard(painelCards, "Total de Vendas");
		lblCompras = criarCard(painelCards, "Total de Compras");
		lblLucro = criarCard(painelCards, "Lucro Bruto");

		JPanel cardVazio = new JPanel();
		painelCards.add(cardVazio);

		painelCentro.add(painelCards, BorderLayout.NORTH);

		tabela = new JTable();
		JScrollPane scroll = new JScrollPane(tabela);
		scroll.setBorder(BorderFactory.createTitledBorder("Produtos com Estoque Crítico"));
		painelCentro.add(scroll, BorderLayout.CENTER);

		add(painelCentro, BorderLayout.CENTER);

		JPanel painelBotoes = new JPanel(new FlowLayout());

		JButton btnAtualizar = new JButton("Atualizar");
		JButton btnProdutos = new JButton("Produtos");
		JButton btnEstoque = new JButton("Estoque");
		JButton btnCompras = new JButton("Compras");
		JButton btnVendas = new JButton("Vendas");
		JButton btnRelatorios = new JButton("Relatórios");
		JButton btnSair = new JButton("Sair");

		painelBotoes.add(btnAtualizar);
		painelBotoes.add(btnProdutos);
		painelBotoes.add(btnEstoque);
		painelBotoes.add(btnCompras);
		painelBotoes.add(btnVendas);
		painelBotoes.add(btnRelatorios);
		painelBotoes.add(btnSair);

		add(painelBotoes, BorderLayout.SOUTH);

		btnAtualizar.addActionListener(e -> carregarDashboard());
		btnProdutos.addActionListener(e -> new TelaListagemProduto().setVisible(true));
		btnEstoque.addActionListener(e -> new TelaEstoque().setVisible(true));
		btnCompras.addActionListener(e -> new TelaListarCompras().setVisible(true));
		btnVendas.addActionListener(e -> new TelaListarVendas().setVisible(true));
		btnRelatorios.addActionListener(e -> new TelaRelatorio().setVisible(true));

		btnSair.addActionListener(e -> {
			int opcao = JOptionPane.showConfirmDialog(this, "Deseja realmente sair?", "Confirmação",
					JOptionPane.YES_NO_OPTION);

			if (opcao == JOptionPane.YES_OPTION) {
				Sessao.setUsuarioLogado(null);
				new TelaLogin().setVisible(true);
				dispose();
			}
		});

		carregarDashboard();
		setVisible(true);
	}

	private JLabel criarCard(JPanel painel, String titulo) {
		JPanel card = new JPanel(new BorderLayout());
		card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		card.setBackground(new Color(245, 245, 245));

		JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

		JLabel lblValor = new JLabel("0", SwingConstants.CENTER);
		lblValor.setFont(new Font("Arial", Font.BOLD, 22));

		card.add(lblTitulo, BorderLayout.NORTH);
		card.add(lblValor, BorderLayout.CENTER);

		painel.add(card);

		return lblValor;
	}

	private void carregarDashboard() {
		NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

		lblProdutos.setText(String.valueOf(controller.contarProdutos()));
		lblClientes.setText(String.valueOf(controller.contarClientes()));
		lblFornecedores.setText(String.valueOf(controller.contarFornecedores()));
		lblCriticos.setText(String.valueOf(controller.contarProdutosCriticos()));
		lblVendas.setText(moeda.format(controller.totalVendas()));
		lblCompras.setText(moeda.format(controller.totalCompras()));
		lblLucro.setText(moeda.format(controller.lucroBruto()));

		DefaultTableModel modelo = controller.getEstoqueCritico();
		tabela.setModel(modelo);
	}
}