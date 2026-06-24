package View;

import Dao.ClienteDAO;
import Model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClienteFrame extends JFrame {

    private JLabel lblPesquisar;
    private JTextField txtPesquisar;
    private JButton btnPesquisar;

    // DAO
    private final ClienteDAO clienteDAO;

    // Labels
    private JLabel lblTitulo;
    private JLabel lblNome;
    private JLabel lblTelefone;

    // Text fields
    private JTextField txtNome;
    private JTextField txtTelefone;

    // Buttons
    private JButton btnAdicionar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnLimpar;
    private JButton btnAtualizar;
    private JButton btnVoltar;

    // Table
    private JTable tabelaClientes;
    private DefaultTableModel modeloTabela;

    // Selected client
    private int clienteSelecionado = -1;

    public ClienteFrame() {

        clienteDAO = new ClienteDAO();

        setTitle("Sistema Barbearia - Clientes");
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

        lblTitulo = new JLabel("Cadastro de Clientes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // ============================
        // FORM
        // ============================

        JPanel painelFormulario = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblNome = new JLabel("Nome:");
        lblTelefone = new JLabel("Telefone:");

        txtNome = new JTextField(25);
        txtTelefone = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelFormulario.add(lblNome, gbc);

        gbc.gridx = 1;
        painelFormulario.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        painelFormulario.add(lblTelefone, gbc);

        gbc.gridx = 1;
        painelFormulario.add(txtTelefone, gbc);

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
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Telefone");

        tabelaClientes = new JTable(modeloTabela);

        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaClientes.setRowHeight(24);
        tabelaClientes.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tabelaClientes);

        scroll.setPreferredSize(new Dimension(700, 250));

        JPanel painelTabela = new JPanel(new BorderLayout());

        painelTabela.add(painelPesquisa, BorderLayout.NORTH);
        painelTabela.add(scroll, BorderLayout.CENTER);

        painelPrincipal.add(painelTabela, BorderLayout.SOUTH);

        add(painelPrincipal);

        // ===========================================
        // EVENTS
        // ===========================================

        btnAdicionar.addActionListener(e -> adicionarCliente());

        btnPesquisar.addActionListener(e -> pesquisarCliente());

        txtPesquisar.addActionListener(e -> pesquisarCliente());

        btnAlterar.addActionListener(e -> alterarCliente());

        btnExcluir.addActionListener(e -> excluirCliente());

        btnLimpar.addActionListener(e -> limparCampos());

        btnAtualizar.addActionListener(e -> carregarTabela());

        btnVoltar.addActionListener(e -> {

            dispose();

            new MenuFrame();

        });

        tabelaClientes.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                preencherCampos();

            }

        });

        carregarTabela();

    }

    private void pesquisarCliente() {

        String pesquisa = txtPesquisar.getText().trim().toLowerCase();

        modeloTabela.setRowCount(0);

        for (Cliente cliente : clienteDAO.listar()) {

            if (cliente.getNome().toLowerCase().contains(pesquisa)) {

                modeloTabela.addRow(new Object[]{

                        cliente.getId(),
                        cliente.getNome(),
                        cliente.getTelefone()

                });

            }

        }

    }

    private void carregarTabela() {

        modeloTabela.setRowCount(0);

        for (Cliente cliente : clienteDAO.listar()) {

            modeloTabela.addRow(new Object[]{

                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getTelefone()

            });

        }

    }

    private void adicionarCliente() {

        String nome = txtNome.getText().trim();
        String telefone = txtTelefone.getText().trim();

        if (nome.isEmpty() || telefone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Fill in all fields."
            );

            return;
        }

        Cliente cliente = new Cliente();

        cliente.setNome(nome);
        cliente.setTelefone(telefone);

        clienteDAO.inserir(cliente);

        carregarTabela();

        limparCampos();

    }

    private void alterarCliente() {

        if (clienteSelecionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select a client."
            );

            return;
        }

        Cliente cliente = new Cliente();

        cliente.setId(clienteSelecionado);
        cliente.setNome(txtNome.getText().trim());
        cliente.setTelefone(txtTelefone.getText().trim());

        clienteDAO.alterar(cliente);

        carregarTabela();

        limparCampos();

    }

    private void excluirCliente() {

        if (clienteSelecionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select a client."
            );

            return;
        }

        int opcao = JOptionPane.showConfirmDialog(

                this,
                "Delete selected client?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION

        );

        if (opcao == JOptionPane.YES_OPTION) {

            clienteDAO.excluir(clienteSelecionado);

            carregarTabela();

            limparCampos();

        }

    }

    private void preencherCampos() {

        int linha = tabelaClientes.getSelectedRow();

        if (linha == -1)
            return;

        clienteSelecionado = (Integer) modeloTabela.getValueAt(linha, 0);

        txtNome.setText(

                modeloTabela.getValueAt(linha, 1).toString()

        );

        txtTelefone.setText(

                modeloTabela.getValueAt(linha, 2).toString()

        );

    }

    private void limparCampos() {

        txtNome.setText("");
        txtTelefone.setText("");

        clienteSelecionado = -1;

        tabelaClientes.clearSelection();

        txtNome.requestFocus();

    }

}