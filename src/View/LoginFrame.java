package View;

import Controller.UsuarioController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnSair;

    private final UsuarioController usuarioController;

    public LoginFrame() {

        usuarioController = new UsuarioController();

        setTitle("Sistema Barbearia - Login");
        setSize(420, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        inicializarComponentes();

        setVisible(true);
    }

    private void inicializarComponentes() {

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Sistema Barbearia", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));

        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel painelCampos = new JPanel(new GridLayout(2, 2, 10, 10));

        painelCampos.add(new JLabel("Login:"));
        txtLogin = new JTextField();
        painelCampos.add(txtLogin);

        painelCampos.add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        painelCampos.add(txtSenha);

        painelPrincipal.add(painelCampos, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));

        btnEntrar = new JButton("Entrar");
        btnSair = new JButton("Sair");

        painelBotoes.add(btnEntrar);
        painelBotoes.add(btnSair);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);

        btnEntrar.addActionListener(this::fazerLogin);

        btnSair.addActionListener(e -> System.exit(0));

        txtSenha.addActionListener(this::fazerLogin);
    }

    private void fazerLogin(ActionEvent e) {

        String login = txtLogin.getText().trim();
        String senha = String.valueOf(txtSenha.getPassword());

        if (login.isEmpty() || senha.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Informe o login e a senha.",
                    "Campos obrigatórios",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        boolean autenticado = usuarioController.autenticar(login, senha);

        if (autenticado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login realizado com sucesso!"
            );

            dispose();

            new MenuFrame();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Login ou senha inválidos.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

            txtSenha.setText("");
            txtSenha.requestFocus();
        }
    }
}