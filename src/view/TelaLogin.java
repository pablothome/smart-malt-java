package view;

import controller.LoginController;
import model.Usuario;
import util.Sessao;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnEntrar;

    private boolean autenticando = false; // evita login duplicado

    public TelaLogin() {

        setTitle("SmartMalte");
        setSize(550, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        JPanel painelFundo = new JPanel(new GridBagLayout());
        painelFundo.setBackground(new Color(250, 240, 190));
//
        // Card central
        JPanel card = new JPanel();
        card.putClientProperty(
                "JComponent.arc",
                25
        );
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        //card.setBackground(new Color(193, 154, 107));
        card.setBackground(Color.orange);
        card.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Logo
        JLabel lblLogo = new JLabel();
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        try {

            ImageIcon logoOriginal = new ImageIcon(
                    getClass().getResource("/icons/smart-malte-logo.png")
            );

            Image imagemRedimensionada =
                    logoOriginal.getImage().getScaledInstance(
                            140,
                            140,
                            Image.SCALE_SMOOTH
                    );

            lblLogo.setIcon(
                    new ImageIcon(imagemRedimensionada)
            );

        } catch (Exception e) {
            System.out.println("Logo não encontrada");
        }

        // Título
        JLabel lblTitulo = new JLabel("SMARTMALTE");
        lblTitulo.setFont(
                new Font("Segoe UI", Font.BOLD, 28)
                
        );
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        //lblTitulo.setForeground(new Color(211, 84, 0));
        lblTitulo.setForeground(new Color(92, 51, 23));
        
        // Subtítulo
        JLabel lblSubtitulo =
                new JLabel("Sistema de Gestão de Bebidas");

        lblSubtitulo.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        lblSubtitulo.setForeground(Color.GRAY);

        lblSubtitulo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // Login
        JLabel lblLogin = new JLabel("Login");

        txtLogin = new JTextField();
        txtLogin.setMaximumSize(
                new Dimension(300, 40)
        );

        // Senha
        JLabel lblSenha = new JLabel("Senha");

        txtSenha = new JPasswordField();
        txtSenha.setMaximumSize(
                new Dimension(300, 40)
        );

        // Botão
        btnEntrar = new JButton("ENTRAR");
        
        btnEntrar.putClientProperty(
                "JButton.buttonType",
                "roundRect"
        );
        
        
        //btnEntrar.setBackground(new Color(230, 126, 34));
        btnEntrar.setBackground(new Color(92, 51, 23));
        btnEntrar.setForeground(Color.WHITE);

        btnEntrar.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        btnEntrar.setMaximumSize(
                new Dimension(300, 45)
        );

        // Espaçamentos
        card.add(lblLogo);
        card.add(Box.createVerticalStrut(15));

        card.add(lblTitulo);
        card.add(Box.createVerticalStrut(5));

        card.add(lblSubtitulo);
        card.add(Box.createVerticalStrut(25));

        card.add(lblLogin);
        card.add(Box.createVerticalStrut(5));

        card.add(txtLogin);
        card.add(Box.createVerticalStrut(15));

        card.add(lblSenha);
        card.add(Box.createVerticalStrut(5));

        card.add(txtSenha);
        card.add(Box.createVerticalStrut(25));

        card.add(btnEntrar);

        painelFundo.add(card);

        add(painelFundo);

        btnEntrar.addActionListener(e -> autenticar());

        getRootPane().setDefaultButton(btnEntrar);

        setVisible(true);
    }
    	
     

    private void autenticar() {
        if (autenticando) return; // impede executar duas vezes

        autenticando = true;
        btnEntrar.setEnabled(false);

        try {
            String login = txtLogin.getText().trim();
            String senha = new String(txtSenha.getPassword()).trim();

            System.out.println("===== DEBUG LOGIN =====");
            System.out.println("Login digitado: " + login);
            System.out.println("Senha digitada: " + senha);

            if (login.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha login e senha.");
                return;
            }

            LoginController controller = new LoginController();
            Usuario usuario = controller.autenticar(login, senha);

            if (usuario != null) {
                Sessao.setUsuarioLogado(usuario);

                System.out.println("Usuário: " + usuario.getNomeUsuario());
                System.out.println("Perfil: [" + usuario.getPerfil() + "]");

                JOptionPane.showMessageDialog(this,
                        "Bem-vindo, " + usuario.getNomeUsuario() + "!\nPerfil: " + usuario.getPerfil());

                TelaMenuPrincipal menu = new TelaMenuPrincipal(Sessao.getUsuarioLogado());
                menu.setVisible(true);

                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Login ou senha inválidos.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            autenticando = false;
            btnEntrar.setEnabled(true);
        }
    }
}