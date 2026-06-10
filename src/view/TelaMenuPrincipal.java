package view;

import controller.CompraController;
import controller.ProdutoController;
import controller.VendaController;
import model.Usuario;
import util.Sessao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaMenuPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    private Usuario usuario;

    // Labels do dashboard
    private JLabel lblTotalProdutos;
    private JLabel lblEstoqueBaixo;
    private JLabel lblTotalVendas;
    private JLabel lblTotalCompras;
    private JLabel lblUsuario;
    private JLabel lblPerfil;

    public TelaMenuPrincipal(Usuario usuario) {
        this.usuario = usuario;

        setTitle("SmartMalte PRO - Sistema de Gestão");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ==========================
        // TOPO
        // ==========================
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(new Color(184, 134, 11));
        painelTopo.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        ImageIcon logo =
                new ImageIcon(
                        getClass().getResource(
                                "/icons/smart-malte-logo.png"
                        )
                );

        Image img =
                logo.getImage().getScaledInstance(
                        60,
                        60,
                        Image.SCALE_SMOOTH
                );

        JLabel lblLogo =
                new JLabel(
                        new ImageIcon(img)
                );

        JLabel lblTitulo =
                new JLabel("SMARTMALTE PRO");

        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        32
                )
        );
        
        JPanel painelMarca =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        painelMarca.setOpaque(false);

        painelMarca.add(lblLogo);
        painelMarca.add(lblTitulo);

        painelTopo.add(
                painelMarca,
                BorderLayout.WEST
        );

        JPanel painelUsuario = new JPanel(new GridLayout(2, 1));
        painelUsuario.setOpaque(false);

        lblUsuario = new JLabel("Usuário: " + usuario.getNomeUsuario());
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        lblPerfil = new JLabel("Perfil: " + usuario.getPerfil());
        lblPerfil.setForeground(new Color(200, 200, 200));
        lblPerfil.setFont(new Font("Segoe UI", Font.BOLD, 14));

        painelUsuario.add(lblUsuario);
        painelUsuario.add(lblPerfil);

        
        painelTopo.add(painelUsuario, BorderLayout.EAST);

        add(painelTopo, BorderLayout.NORTH);
        
        
        

        // ==========================
        // CENTRO PRINCIPAL
        // ==========================
        JPanel painelCentro = new JPanel(new BorderLayout());
        painelCentro.setBackground(new Color(250, 240, 190));
        
        JButton btnProdutos = criarBotao("Produtos", "/icons/produtos.png");
        JButton btnClientes = criarBotao("Clientes", "/icons/cliente.png");
        JButton btnEstoque = criarBotao("Estoque", "/icons/estoque.png");
        JButton btnPedidos = criarBotao("Pedidos", "/icons/pedido.png");
        JButton btnFornecedor = criarBotao("Fornecedores", "/icons/fornecedores.png");
        JButton btnCompras = criarBotao("Compras", "/icons/compras.png");
        JButton btnRelatorios = criarBotao("Relatórios", "/icons/relatorio.png");
        JButton btnVendas = criarBotao("Vendas", "/icons/vendas.png");
        JButton btnLogout = criarBotaoSimples("Sair do Sistema",
                new Color(192, 57, 43));
        
        painelCentro.setBackground(new Color(245, 245, 245));

        JPanel menuLateral = new JPanel();
        menuLateral.setBackground (Color.ORANGE);
        menuLateral.setLayout(new GridLayout(8, 1, 10, 10));
        menuLateral.setPreferredSize(new Dimension(280, 0));
        menuLateral.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        10,
                        15,
                        10
                )
        );

        menuLateral.add(btnProdutos);
        menuLateral.add(btnClientes);
        menuLateral.add(btnEstoque);
        menuLateral.add(btnPedidos);
        menuLateral.add(btnFornecedor);
        menuLateral.add(btnCompras);
        menuLateral.add(btnVendas);
        menuLateral.add(btnRelatorios);

        JPanel painelLateral = new JPanel(
                new BorderLayout()
                
        );
        
        painelLateral.setBackground(
                new Color(160, 82, 45)
        );

        painelLateral.add(
                menuLateral,
                BorderLayout.CENTER
        );

        painelLateral.add(
                btnLogout,
                BorderLayout.SOUTH
        );

        add(
                painelLateral,
                BorderLayout.WEST
        );
        
        // ==========================
        // DASHBOARD (CARDS)
        // ==========================
        JPanel painelDashboard = new JPanel(new GridLayout(1, 4, 20, 20));
        painelDashboard.setBorder(BorderFactory.createEmptyBorder(25, 25, 15, 25));
        painelDashboard.setBackground(new Color(245, 245, 245));

        lblTotalProdutos = new JLabel("0", SwingConstants.CENTER);
        lblEstoqueBaixo = new JLabel("0", SwingConstants.CENTER);
        lblTotalVendas = new JLabel("0", SwingConstants.CENTER);
        lblTotalCompras = new JLabel("0", SwingConstants.CENTER);

        painelDashboard.add(criarCard("Produtos Cadastrados", lblTotalProdutos, new Color(52, 152, 219)));
        painelDashboard.add(criarCard("Estoque Baixo", lblEstoqueBaixo, new Color(231, 76, 60)));
        painelDashboard.add(criarCard("Total de Vendas", lblTotalVendas, new Color(46, 204, 113)));
        painelDashboard.add(criarCard("Total de Compras", lblTotalCompras, new Color(241, 196, 15)));

        painelCentro.add(painelDashboard, BorderLayout.NORTH);
        add(painelCentro, BorderLayout.CENTER);

        

       
      

        // ==========================
        // CONTROLE POR PERFIL
        // ==========================
        if (Sessao.isFuncionario()) {
            btnRelatorios.setEnabled(false);
            btnRelatorios.setToolTipText("Apenas gerente pode acessar relatórios");
        }

        // ==========================
        // AÇÕES DOS BOTÕES
        // ==========================
        btnProdutos.addActionListener(e -> abrirTelaComAtualizacao(new TelaListagemProduto()));
        btnClientes.addActionListener(e -> abrirTelaComAtualizacao(new TelaListagemCliente()));
        btnEstoque.addActionListener(e -> abrirTelaComAtualizacao(new TelaEstoque()));
        btnPedidos.addActionListener(e -> abrirTelaComAtualizacao(new TelaPedidosMenu()));
        btnFornecedor.addActionListener(e -> abrirTelaComAtualizacao(new TelaListarFornecedor()));
        btnCompras.addActionListener(e -> abrirTelaComAtualizacao(new TelaListarCompras()));
        btnRelatorios.addActionListener(e -> abrirTelaComAtualizacao(new TelaRelatorio()));
        btnVendas.addActionListener(e -> abrirTelaComAtualizacao(new TelaListarVendas()));

        btnLogout.addActionListener(e -> sairDoSistema());
        
        

        // ==========================
        // ATUALIZA DASHBOARD AO ABRIR
        // ==========================
        atualizarDashboard();

        // ==========================
        // ATUALIZA DASHBOARD AO VOLTAR FOCO
        // ==========================
        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                atualizarDashboard();
            }
        });

        setVisible(true);
        
        
    }

    
    	private void sairDoSistema() {

    	    int opcao = JOptionPane.showConfirmDialog(
    	            this,
    	            "Deseja realmente sair do sistema?",
    	            "Confirmação",
    	            JOptionPane.YES_NO_OPTION
    	    );

    	    if (opcao == JOptionPane.YES_OPTION) {

    	        Sessao.encerrarSessao();

    	        dispose();

    	        new TelaLogin().setVisible(true);
    	    }
    	
		
	}

	
    private JPanel criarCard(String titulo, JLabel valorLabel, Color cor) {
        JPanel card = new JPanel(new BorderLayout());
        card.putClientProperty(
                "JComponent.arc",
                20
        );
        
        card.setBackground(Color.WHITE);
        card.putClientProperty(
                "JComponent.arc",
                25
        );

        JPanel barra = new JPanel();
        barra.setBackground(cor);
        barra.setPreferredSize(new Dimension(10, 10));

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(60, 60, 60));

        valorLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        valorLabel.setForeground(cor);

        card.add(barra, BorderLayout.NORTH);
        card.add(lblTitulo, BorderLayout.CENTER);
        card.add(valorLabel, BorderLayout.SOUTH);

        return card;
    }

   
    private JButton criarBotao(String texto, String caminhoIcone) {

        JButton botao = new JButton(texto);
        
        botao.putClientProperty(
                "JButton.buttonType",
                "roundRect"
        );

        try {

            java.net.URL location =
                    getClass().getResource(caminhoIcone);

            if (location != null) {

            	ImageIcon icon = new ImageIcon(location);

            	Image img = icon.getImage().getScaledInstance(
            	        32,   
            	        32,   
            	        Image.SCALE_SMOOTH
            	);

            	botao.setIcon(new ImageIcon(img));;
            }

        } catch (Exception e) {
        }

        botao.setHorizontalAlignment(
                SwingConstants.LEFT
        );
        
        botao.setVerticalAlignment(
                SwingConstants.CENTER
        );

        botao.setHorizontalTextPosition(
                SwingConstants.RIGHT
        );

        botao.setVerticalTextPosition(
                SwingConstants.CENTER
        );

        botao.setIconTextGap(20);

        botao.setBackground(
                new Color(176, 109, 39)
        );
        
        botao.setMargin(
                new Insets(5, 15, 5, 15)
        );

        botao.setForeground(Color.WHITE);

        botao.setFocusPainted(false);

        botao.setBorderPainted(false);

        botao.setFont(
                new Font("Segoe UI",
                        Font.BOLD,
                        14)
        );

        return botao;
    }

    
    private JButton criarBotaoSimples(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new Dimension(200, 55));
        botao.setFont(new Font("Segoe UI", Font.BOLD, 15));
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        return botao;
    }

    
    private void abrirTelaComAtualizacao(JFrame tela) {
        tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tela.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                atualizarDashboard();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                atualizarDashboard();
            }
        });

        tela.setVisible(true);
    }

   
    private void atualizarDashboard() {
        try {
            ProdutoController produtoController = new ProdutoController();
            VendaController vendaController = new VendaController();
            CompraController compraController = new CompraController();

            lblTotalProdutos.setText(String.valueOf(produtoController.contarProdutos()));
            lblEstoqueBaixo.setText(String.valueOf(produtoController.contarProdutosCriticos()));
            lblTotalVendas.setText(String.valueOf(vendaController.contarVendas()));
            lblTotalCompras.setText(String.valueOf(compraController.contarCompras()));

        } catch (Exception e) {
            lblTotalProdutos.setText("0");
            lblEstoqueBaixo.setText("0");
            lblTotalVendas.setText("0");
            lblTotalCompras.setText("0");

            System.out.println("Erro ao atualizar dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
}