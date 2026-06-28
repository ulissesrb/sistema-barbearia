package View;

import Dao.UsuarioDAO;
import Model.Usuario;
import Controller.UsuarioController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import javax.swing.*;
import java.awt.*;

public class UsuarioFrame extends JFrame {

    private JLabel lblPesquisar;
    private JTextField txtPesquisar;
    private JButton btnPesquisar;

    // DAO
    private final UsuarioDAO usuarioDAO;

    // Labels
    private JLabel lblTitulo;
    private JLabel lblLogin;
    private JLabel lblSenha;

    // Text fields
    private JTextField txtLogin;
    private JTextField txtSenha;

    // Buttons
    private JButton btnAdicionar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnLimpar;
    private JButton btnAtualizar;
    private JButton btnVoltar;

    // Table
    private JTable tabelaUsuarios;
    private DefaultTableModel modeloTabela;

    // Selected client
    private int usuarioSelecionado = -1;

    public UsuarioFrame() {

        usuarioDAO = new UsuarioDAO();

        setTitle("Sistema Barbearia - Usuários");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        inicializarComponentes();

        setVisible(true);
    }

    private void inicializarComponentes() {

        JPanel painelPrincipal = new JPanel(new BorderLayout(15, 15));
        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // ============================
        // TITLE
        // ============================

        lblTitulo = new JLabel("Cadastro de Usuários", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // ============================
        // FORM
        // ============================

        JPanel painelFormulario = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblLogin = new JLabel("Login:");
        lblSenha = new JLabel("Senha:");

        txtLogin = new JTextField(25);
        txtSenha = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelFormulario.add(lblLogin, gbc);

        gbc.gridx = 1;
        painelFormulario.add(txtLogin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        painelFormulario.add(lblSenha, gbc);

        gbc.gridx = 1;
        painelFormulario.add(txtSenha, gbc);

        // ============================
        // BUTTONS
        // ============================

        JPanel painelBotoes = new JPanel(new FlowLayout());

        btnAdicionar = new JButton("Adicionar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Limpar");
        btnAtualizar = new JButton("Atualizar");
        btnVoltar = new JButton("Voltar");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnVoltar);

        JPanel painelSuperior = new JPanel(new BorderLayout());

        painelSuperior.add(painelFormulario, BorderLayout.CENTER);
        painelSuperior.add(painelBotoes, BorderLayout.SOUTH);

        painelPrincipal.add(painelSuperior, BorderLayout.CENTER);

        // ============================
        // SEARCH PANEL
        // ============================

        JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));

        lblPesquisar = new JLabel("Pesquisar:");

        txtPesquisar = new JTextField(20);

        btnPesquisar = new JButton("Pesquisar");

        painelPesquisa.add(lblPesquisar);
        painelPesquisa.add(txtPesquisar);
        painelPesquisa.add(btnPesquisar);

        // ============================
        // TABLE
        // ============================

        modeloTabela = new DefaultTableModel();

        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Login");
        //modeloTabela.addColumn("Telefone");

        tabelaUsuarios = new JTable(modeloTabela);

        tabelaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaUsuarios.setRowHeight(24);
        tabelaUsuarios.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tabelaUsuarios);

        scroll.setPreferredSize(new Dimension(700, 250));

        JPanel painelTabela = new JPanel(new BorderLayout());

        painelTabela.add(painelPesquisa, BorderLayout.NORTH);
        painelTabela.add(scroll, BorderLayout.CENTER);

        painelPrincipal.add(painelTabela, BorderLayout.SOUTH);

        add(painelPrincipal);

        // ===========================================
        // EVENTS
        // ===========================================

        btnAdicionar.addActionListener(e -> adicionarUsuario());

        btnPesquisar.addActionListener(e -> pesquisarUsuario());

        txtPesquisar.addActionListener(e -> pesquisarUsuario());

        btnAlterar.addActionListener(e -> alterarUsuario());

        btnExcluir.addActionListener(e -> excluirUsuario());

        btnLimpar.addActionListener(e -> limparCampos());

        btnAtualizar.addActionListener(e -> carregarTabela());

        btnVoltar.addActionListener(e -> {

            dispose();

            new MenuFrame();

        });

        tabelaUsuarios.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                preencherCampos();

            }

        });

        carregarTabela();

    }

    private void pesquisarUsuario() {

        String pesquisa = txtPesquisar.getText().trim().toLowerCase();

        modeloTabela.setRowCount(0);

        for (Usuario usuario : usuarioDAO.listar()) {

            if (usuario.getLogin().toLowerCase().contains(pesquisa)) {

                modeloTabela.addRow(new Object[]{

                        usuario.getId(),
                        usuario.getLogin()
                });

            }

        }

    }

    private void carregarTabela() {

        modeloTabela.setRowCount(0);

        for (Usuario usuario : usuarioDAO.listar()) {

            modeloTabela.addRow(new Object[]{

                    usuario.getId(),
                    usuario.getLogin(),

            });

        }

    }

    private void adicionarUsuario() {

        String login = txtLogin.getText().trim();
        String senha = txtSenha.getText().trim();

        if (login.isEmpty() || senha.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha todos os campos."
            );

            return;
        }
        UsuarioController controller = new UsuarioController();

        controller.cadastrarNovoUsuario(login, senha);

        carregarTabela();

        limparCampos();

    }

    private void alterarUsuario() {

        if (usuarioSelecionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um usuário."
            );

            return;
        }

        UsuarioController controller = new UsuarioController();

        Usuario usuario = new Usuario();
        usuario.setId(usuarioSelecionado);
        usuario.setLogin(txtLogin.getText().trim());

        String senha = !txtSenha.getText().trim().isEmpty() ? txtSenha.getText().trim() : null;

        controller.alterarUsuario(usuario, senha);

        carregarTabela();

        limparCampos();

    }

    private void excluirUsuario() {

        if (usuarioSelecionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um usuário."
            );

            return;
        }

        int opcao = JOptionPane.showConfirmDialog(

                this,
                "Excluir usuário?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION

        );

        if (opcao == JOptionPane.YES_OPTION) {

            usuarioDAO.excluir(usuarioSelecionado);

            carregarTabela();

            limparCampos();

        }

    }

    private void preencherCampos() {

        int linha = tabelaUsuarios.getSelectedRow();

        if (linha == -1)
            return;

        usuarioSelecionado = (Integer) modeloTabela.getValueAt(linha, 0);

        txtLogin.setText(

                modeloTabela.getValueAt(linha, 1).toString()

        );

//        txtSenha.setText(
//
//                modeloTabela.getValueAt(linha, 2).toString()
//
//        );

    }

    private void limparCampos() {

        txtLogin.setText("");
        txtSenha.setText("");

        usuarioSelecionado = -1;

        tabelaUsuarios.clearSelection();

        txtLogin.requestFocus();

    }

}