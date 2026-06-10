package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import controller.PedidoController;
import model.ItemPedido;

public class TelaPedido extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtCliente;
    private JTextField txtProduto;     // aqui vamos digitar o ID do produto
    private JTextField txtQuantidade;
    private JTextField txtPreco;

    public TelaPedido() {

        setTitle("Pedido / Venda");
        setSize(400, 320);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblCliente = new JLabel("Cliente (CPF):");
        lblCliente.setBounds(30, 30, 100, 25);
        add(lblCliente);

        txtCliente = new JTextField();
        txtCliente.setBounds(140, 30, 200, 25);
        add(txtCliente);

        JLabel lblProduto = new JLabel("Produto (ID):");
        lblProduto.setBounds(30, 70, 100, 25);
        add(lblProduto);

        txtProduto = new JTextField();
        txtProduto.setBounds(140, 70, 200, 25);
        add(txtProduto);

        JLabel lblQuantidade = new JLabel("Quantidade:");
        lblQuantidade.setBounds(30, 110, 100, 25);
        add(lblQuantidade);

        txtQuantidade = new JTextField();
        txtQuantidade.setBounds(140, 110, 200, 25);
        add(txtQuantidade);

        JLabel lblPreco = new JLabel("Preço Unitário:");
        lblPreco.setBounds(30, 150, 100, 25);
        add(lblPreco);

        txtPreco = new JTextField();
        txtPreco.setBounds(140, 150, 200, 25);
        add(txtPreco);

        JButton btnFinalizar = new JButton("Finalizar Pedido");
        btnFinalizar.setBounds(140, 210, 150, 30);
        add(btnFinalizar);

        btnFinalizar.addActionListener(e -> finalizarPedido());

        setVisible(true);
    }

    private void finalizarPedido() {
        try {
            String cpfCliente = txtCliente.getText().trim();
            String produtoStr = txtProduto.getText().trim();
            String qtdStr = txtQuantidade.getText().trim();
            String precoStr = txtPreco.getText().trim();

            // ✅ VALIDAÇÃO
            if (cpfCliente.isEmpty() || produtoStr.isEmpty() || qtdStr.isEmpty() || precoStr.isEmpty()) {
                throw new IllegalArgumentException("Preencha todos os campos!");
            }

            int idProduto = Integer.parseInt(produtoStr);   // agora converte para int
            int quantidade = Integer.parseInt(qtdStr);
            double preco = Double.parseDouble(precoStr);

            double total = quantidade * preco;

            PedidoController controller = new PedidoController();
            ItemPedido item = new ItemPedido(
            		idProduto,
            		quantidade,
            		preco
            		);
            
            List<ItemPedido> carrinho = new ArrayList<>();
            carrinho.add(item);
            
            boolean sucesso = controller.registrarPedido(carrinho, cpfCliente);

            if (sucesso) {
                JOptionPane.showMessageDialog(this,
                        "Pedido realizado com sucesso!\nTotal: R$ " + total);
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao realizar pedido!");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Produto, quantidade e preço devem ser números!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar pedido!");
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        txtCliente.setText("");
        txtProduto.setText("");
        txtQuantidade.setText("");
        txtPreco.setText("");
        txtCliente.requestFocus();
    }
}