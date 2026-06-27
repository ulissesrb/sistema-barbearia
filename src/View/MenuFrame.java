package View;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame {

    private JButton btnClientes;
    private JButton btnBarbeiros;
    private JButton btnAgendamentos;
    private JButton btnUsuarios;
    private JButton btnLogout;
    private JButton btnSair;

    public MenuFrame() {

        setTitle("Sistema Barbearia - Menu Principal");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        inicializarComponentes();

        setVisible(true);
    }

    private void inicializarComponentes() {

        JPanel painelPrincipal = new JPanel(new BorderLayout(20, 20));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ===== Título =====

        JLabel lblTitulo = new JLabel("MENU PRINCIPAL", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // ===== Botões centrais =====

        JPanel painelCentro = new JPanel();
        painelCentro.setLayout(new GridLayout(3, 1, 15, 15));

        btnClientes = new JButton("Clientes");
        btnBarbeiros = new JButton("Barbeiros");
        btnAgendamentos = new JButton("Agendamentos");
        btnUsuarios = new JButton("Usuários");

        painelCentro.add(btnClientes);
        painelCentro.add(btnBarbeiros);
        painelCentro.add(btnAgendamentos);
        painelCentro.add(btnUsuarios);

        painelPrincipal.add(painelCentro, BorderLayout.CENTER);

        // ===== Botões inferiores =====

        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnLogout = new JButton("Logout");
        btnSair = new JButton("Sair");

        painelInferior.add(btnLogout);
        painelInferior.add(btnSair);

        painelPrincipal.add(painelInferior, BorderLayout.SOUTH);

        add(painelPrincipal);

        // ===== Eventos =====

        btnClientes.addActionListener(e -> {

            dispose();
            new ClienteFrame();

        });

        btnBarbeiros.addActionListener(e -> {

            dispose();
            new BarbeiroFrame();

        });

        btnAgendamentos.addActionListener(e -> {

            dispose();
            new AgendamentoFrame();

        });

        btnUsuarios.addActionListener(e -> {

            dispose();
            new UsuarioFrame();

        });

        btnLogout.addActionListener(e -> {

            dispose();
            new LoginFrame();

        });

        btnSair.addActionListener(e -> System.exit(0));

    }

}