package view;

import javax.swing.*;

public class TelaPedidosMenu extends JFrame {

    public TelaPedidosMenu() {

        setTitle("Pedidos");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JButton btnVendas = new JButton("Vendas");
        btnVendas.setBounds(50, 30, 200, 40);
        add(btnVendas);

        JButton btnCompras = new JButton("Compras");
        btnCompras.setBounds(50, 90, 200, 40);
        add(btnCompras);

        // AÇÕES
        btnVendas.addActionListener(e -> new TelaVenda().setVisible(true));

        btnCompras.addActionListener(e -> new TelaCompra().setVisible(true));
                
        

        setVisible(true);
    }
}