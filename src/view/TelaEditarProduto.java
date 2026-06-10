package view;

import javax.swing.*;

import controller.ProdutoController;
import model.Produto;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class TelaEditarProduto extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtNome, txtMarca, txtCategoria, txtQuantidade, txtPreco;
    private Produto produto;
    private JTextField txtEstoqueMinimo;

    public TelaEditarProduto(Produto produto) {
        this.produto = produto;

        if (produto == null
                || produto.getNomeProduto() == null || produto.getNomeProduto().trim().isEmpty()
                || produto.getMarcaProduto() == null || produto.getMarcaProduto().trim().isEmpty()
                || produto.getCategoriaProduto() == null || produto.getCategoriaProduto().trim().isEmpty()
                || produto.getPrecoProduto() <= 0
                || produto.getQuantidadeEstoque() < 0) {

            JOptionPane.showMessageDialog(null,
                    "Produto não encontrado ou com dados incompletos!");
            dispose();
            return;
        }

        setTitle("Editar Produto");
        setSize(400, 320);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(30, 30, 100, 25);
        add(lblNome);

        txtNome = new JTextField(produto.getNomeProduto());
        txtNome.setBounds(120, 30, 200, 25);
        add(txtNome);

        JLabel lblMarca = new JLabel("Marca:");
        lblMarca.setBounds(30, 70, 100, 25);
        add(lblMarca);

        txtMarca = new JTextField(produto.getMarcaProduto());
        txtMarca.setBounds(120, 70, 200, 25);
        add(txtMarca);

        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(30, 110, 100, 25);
        add(lblCategoria);

        txtCategoria = new JTextField(produto.getCategoriaProduto());
        txtCategoria.setBounds(120, 110, 200, 25);
        add(txtCategoria);

        JLabel lblPreco = new JLabel("Preço:");
        lblPreco.setBounds(30, 150, 100, 25);
        add(lblPreco);

        txtPreco = new JTextField(formatarMoeda(produto.getPrecoProduto()));
        txtPreco.setBounds(120, 150, 200, 25);
        add(txtPreco);
        
        aplicarMascaraMoeda(txtPreco);

        JLabel lblQuantidade = new JLabel("Quantidade:");
        lblQuantidade.setBounds(30, 190, 100, 25);
        add(lblQuantidade);

        txtQuantidade = new JTextField(String.valueOf(produto.getQuantidadeEstoque()));
        txtQuantidade.setBounds(120, 190, 200, 25);
        add(txtQuantidade);
        
        txtQuantidade.addKeyListener(new java.awt.event.KeyAdapter() {
        	@Override
        	public void keyTyped(java.awt.event.KeyEvent evt) {
        		char c = evt.getKeyChar();
        		if (!Character.isDigit(c) && c != '\b') {
        			evt.consume();
        		}
        	}
        });

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(140, 230, 120, 30);
        add(btnSalvar);

        btnSalvar.addActionListener(e -> salvar());

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

    
    private void salvar() {
        try {
            String nome = txtNome.getText().trim();
            String marca = txtMarca.getText().trim();
            String categoria = txtCategoria.getText().trim();
            String precoTexto = txtPreco.getText().trim();
            String quantidadeTexto = txtQuantidade.getText().trim();

            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome é obrigatório!");
                txtNome.requestFocus();
                return;
            }

            if (marca.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Marca é obrigatória!");
                txtMarca.requestFocus();
                return;
            }

            if (categoria.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Categoria é obrigatória!");
                txtCategoria.requestFocus();
                return;
            }

            if (precoTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preço é obrigatório!");
                txtPreco.requestFocus();
                return;
            }

            double preco;
            try {
                preco = converterMoedaParaDouble(precoTexto);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Preço inválido!");
                txtPreco.requestFocus();
                return;
            }
            
            if (preco <= 0) {
            	JOptionPane.showMessageDialog(this, "Preço inválido");
            	txtPreco.requestFocus();
            	return;
            }

            int quantidade;
            try {
                quantidade = Integer.parseInt(quantidadeTexto);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Quantidade Inválida!");
                txtQuantidade.requestFocus();
                return;
            }
            
            if (quantidade < 0) {
            	JOptionPane.showMessageDialog(this, "Quantidade Inválida!");
            	txtQuantidade.requestFocus();
            	return;
            }

            produto.setNomeProduto(nome);
            produto.setMarcaProduto(marca);
            produto.setCategoriaProduto(categoria);
            produto.setPrecoProduto(preco);
            produto.setQuantidadeEstoque(quantidade);

            ProdutoController controller = new ProdutoController();
            boolean sucesso = controller.atualizarProduto(produto);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar produto!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String formatarMoeda(double valor) {
        NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return formato.format(valor);
    }

    private double converterMoedaParaDouble(String texto) throws ParseException {
        NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        Number numero = formato.parse(texto);
        return numero.doubleValue();
    }
}