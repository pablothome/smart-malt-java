package view;

import controller.RelatorioController;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TelaRelatorio extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tabela;
    private JScrollPane scroll;
    
    private String dataInicialFiltro;
    private String dataFinalFiltro;

    private JFormattedTextField txtDataInicial;
    private JFormattedTextField txtDataFinal;

    private JLabel lblTotalProdutos;
    private JLabel lblEstoqueCritico;
    private JLabel lblTotalVendas;
    private JLabel lblTotalCompras;
    private JLabel lblFaturamento;
    private JLabel lblGastoCompras;
    private JLabel lblLucroBruto;
    private JLabel lblMargemLucro;
    private JLabel lblTicketMedio;
    private JLabel lblInvestimentoEstoque;
    
    private JLabel lblVendasPeriodo = criarCard("R$ 0,00");
    private JLabel lblComprasPeriodo = criarCard("R$ 0,00");
    private JLabel lblLucroPeriodo = criarCard("R$ 0,00");
    private JLabel lblMargemPeriodo = criarCard("R$ 0,00");
    private JLabel lblTicketPeriodo = criarCard("R$ 0,00");

    private JLabel lblPeriodoAtivo;

    public TelaRelatorio() {
        setTitle("Dashboard Gerencial - SmartMalte");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        RelatorioController controller = new RelatorioController();
        
        

        // ==============================
        // PAINEL SUPERIOR
        // ==============================
        JPanel painelSuperior = new JPanel(new BorderLayout());

        JPanel painelCards = new JPanel(new GridLayout(2, 5, 10, 10));
        painelCards.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblTotalProdutos = criarCard(String.valueOf(controller.contarProdutos()));
        lblEstoqueCritico = criarCard(String.valueOf(controller.contarProdutosCriticos()));
        lblTotalVendas = criarCard(String.valueOf(controller.contarVendas()));
        lblTotalCompras = criarCard(String.valueOf(controller.contarCompras()));
        lblFaturamento = criarCard(formatarMoeda(controller.somarTotalVendas()));
        lblGastoCompras = criarCard(formatarMoeda(controller.somarTotalCompras()));

        lblLucroBruto = criarCard(formatarMoeda(controller.calcularLucroBruto()));
        lblMargemLucro = criarCard(formatarPercentual(controller.calcularMargemLucro()));
        lblTicketMedio = criarCard(formatarMoeda(controller.calcularTicketMedio()));
        lblInvestimentoEstoque = criarCard(formatarMoeda(controller.calcularInvestimentoEstoque()));

        painelCards.add(criarPainelCard("Total de Produtos", lblTotalProdutos));
        painelCards.add(criarPainelCard("Estoque Crítico", lblEstoqueCritico));
        painelCards.add(criarPainelCard("Total de Vendas", lblTotalVendas));
        painelCards.add(criarPainelCard("Total de Compras", lblTotalCompras));
        painelCards.add(criarPainelCard("Faturamento", lblFaturamento));
        painelCards.add(criarPainelCard("Gasto em Compras", lblGastoCompras));
        painelCards.add(criarPainelCard("Lucro Bruto", lblLucroBruto));
        painelCards.add(criarPainelCard("Margem de Lucro", lblMargemLucro));
        painelCards.add(criarPainelCard("Ticket Médio", lblTicketMedio));
        painelCards.add(criarPainelCard("Investimento em Estoque", lblInvestimentoEstoque));
        painelCards.add(criarPainelCard("Vendas (Período)", lblVendasPeriodo));
        painelCards.add(criarPainelCard("Compras (Período)", lblComprasPeriodo));
        painelCards.add(criarPainelCard("Lucro (Período)", lblLucroPeriodo));
        painelCards.add(criarPainelCard("Margem (%)", lblMargemPeriodo));
        painelCards.add(criarPainelCard("Ticket Médio", lblTicketPeriodo));
        
        
        painelSuperior.add(painelCards, BorderLayout.NORTH);

        // ==============================
        // FILTRO + BOTÕES
        // ==============================
        JPanel painelTopo = new JPanel(new FlowLayout());
        
        lblPeriodoAtivo = new JLabel("Período: Nenhum");
        lblPeriodoAtivo.setFont(new Font("Arial", Font.BOLD, 14));

        painelTopo.add(lblPeriodoAtivo);

        try {
            MaskFormatter mask = new MaskFormatter("##/##/####");
            mask.setPlaceholderCharacter('_');

            txtDataInicial = new JFormattedTextField(mask);
            txtDataFinal = new JFormattedTextField(mask);

        } catch (Exception e) {
            txtDataInicial = new JFormattedTextField();
            txtDataFinal = new JFormattedTextField();
        }

        JButton btnFiltrar = new JButton("Filtrar Período");
        JButton btnFinanceiroPeriodo = new JButton("Resumo Financeiro");
        JButton btnVendasPeriodo = new JButton("Vendas no Período");
        JButton btnComprasPeriodo = new JButton("Compras no Período");

        painelTopo.add(new JLabel("Data Inicial:"));
        painelTopo.add(txtDataInicial);

        painelTopo.add(new JLabel("Data Final:"));
        painelTopo.add(txtDataFinal);

        painelTopo.add(btnFiltrar);
        painelTopo.add(btnFinanceiroPeriodo);
        painelTopo.add(btnVendasPeriodo);
        painelTopo.add(btnComprasPeriodo);

        painelSuperior.add(painelTopo, BorderLayout.SOUTH);

        add(painelSuperior, BorderLayout.NORTH);

        // ==============================
        // TABELA
        // ==============================
        tabela = new JTable();
        scroll = new JScrollPane(tabela);

        add(scroll, BorderLayout.CENTER);

        // ==============================
        // EVENTOS
        // ==============================
        btnFiltrar.addActionListener(e -> filtrarPorPeriodo(controller));
        btnFinanceiroPeriodo.addActionListener(e -> carregarResumoFinanceiroPeriodo(controller));
        btnVendasPeriodo.addActionListener(e -> carregarVendasPeriodo(controller));
        btnComprasPeriodo.addActionListener(e -> carregarComprasPeriodo(controller));

        setVisible(true);
    }

    // ==============================
    // VALIDAÇÃO DE DATAS
    // ==============================
    private boolean validarDatas() {
        String dataInicial = txtDataInicial.getText().trim();
        String dataFinal = txtDataFinal.getText().trim();

        if (dataInicial.contains("_") || dataFinal.contains("_")) {
            JOptionPane.showMessageDialog(this, "Preencha as datas corretamente.");
            return false;
        }

        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(dataInicial, formato);
            LocalDate.parse(dataFinal, formato);
            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data inválida! Use dd/MM/yyyy.");
            return false;
        }
    }

    // ==============================
    // CONVERTE DATA PARA MYSQL
    // ==============================
    private String converterDataParaSQL(String dataBR) {
        DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoSQL = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate data = LocalDate.parse(dataBR, formatoBR);
        return data.format(formatoSQL);
    }

    // ==============================
    // AÇÕES
    // ==============================
    private void filtrarPorPeriodo(RelatorioController controller) {
        if (!validarDatas()) return;

        dataInicialFiltro = converterDataParaSQL(txtDataInicial.getText()) + " 00:00:00";
        dataFinalFiltro = converterDataParaSQL(txtDataFinal.getText()) + " 23:59:59";

        // 🔥 ATUALIZA LABEL
        lblPeriodoAtivo.setText("Período: " + txtDataInicial.getText() + " até " + txtDataFinal.getText());

        // 🔥 BUSCAR DADOS
        double vendas = controller.getTotalVendasPorPeriodo(dataInicialFiltro, dataFinalFiltro);
        double compras = controller.getTotalComprasPorPeriodo(dataInicialFiltro, dataFinalFiltro);
        double lucro = controller.getLucroPorPeriodo(dataInicialFiltro, dataFinalFiltro);
        double margem = controller.getMargemPorPeriodo(dataInicialFiltro, dataFinalFiltro);
        double ticket = controller.getTicketMedioPorPeriodo(dataInicialFiltro, dataFinalFiltro);

        // 🔥 ATUALIZA CARDS
        lblVendasPeriodo.setText(formatarMoeda(vendas));
        lblComprasPeriodo.setText(formatarMoeda(compras));
        lblLucroPeriodo.setText(formatarMoeda(lucro));
        lblMargemPeriodo.setText(formatarPercentual(margem));
        lblTicketPeriodo.setText(formatarMoeda(ticket));

        // 🔥 CARREGA TABELA
        tabela.setModel(controller.getResumoFinanceiroPorPeriodo(dataInicialFiltro, dataFinalFiltro));
    }

    private void carregarResumoFinanceiroPeriodo(RelatorioController controller) {
        if (dataInicialFiltro == null || dataFinalFiltro == null) {
            JOptionPane.showMessageDialog(this, "Defina o período primeiro!");
            return;
        }

        tabela.setModel(controller.getResumoFinanceiroPorPeriodo(dataInicialFiltro, dataFinalFiltro));
    }

    private void carregarVendasPeriodo(RelatorioController controller) {
        if (dataInicialFiltro == null || dataFinalFiltro == null) {
            JOptionPane.showMessageDialog(this, "Defina o período primeiro!");
            return;
        }

        tabela.setModel(controller.getTabelaVendasPorPeriodo(dataInicialFiltro, dataFinalFiltro));
    }

    private void carregarComprasPeriodo(RelatorioController controller) {
        if (dataInicialFiltro == null || dataFinalFiltro == null) {
            JOptionPane.showMessageDialog(this, "Defina o período primeiro!");
            return;
        }

        tabela.setModel(controller.getTabelaComprasPorPeriodo(dataInicialFiltro, dataFinalFiltro));
    }

    // ==============================
    // UI AUXILIAR
    // ==============================
    private JLabel criarCard(String valor) {
        JLabel label = new JLabel(valor, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        return label;
    }

    private JPanel criarPainelCard(String titulo, JLabel valorLabel) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        painel.setBackground(new Color(245, 245, 245));
        painel.setPreferredSize(new Dimension(180, 80));

        JLabel tituloLabel = new JLabel(titulo, SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 12));

        valorLabel.setFont(new Font("Arial", Font.BOLD, 16));
        valorLabel.setForeground(new Color(0, 102, 204));

        painel.add(tituloLabel, BorderLayout.NORTH);
        painel.add(valorLabel, BorderLayout.CENTER);

        return painel;
    }

    private String formatarMoeda(double valor) {
        NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return formato.format(valor);
    }

    private String formatarPercentual(double valor) {
        return String.format("%.2f%%", valor);
    }
}