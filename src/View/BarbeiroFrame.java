package View;

import Dao.BarbeiroDAO;
import Model.Barbeiro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BarbeiroFrame extends JFrame {

    private JLabel lblPesquisar;
    private JTextField txtPesquisar;
    private JButton btnPesquisar;

    // DAO
    private final BarbeiroDAO BarbeiroDAO;

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
    private JTable tabelaBarbeiros;
    private DefaultTableModel modeloTabela;

    // Selected client
    private int BarbeiroSelecionado = -1;

    public BarbeiroFrame() {

        BarbeiroDAO = new BarbeiroDAO();

        setTitle("Sistema Barbearia - Barbeiros");
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

        lblTitulo = new JLabel("Cadastro de Barbeiros", SwingConstants.CENTER);
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

        tabelaBarbeiros = new JTable(modeloTabela);

        tabelaBarbeiros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaBarbeiros.setRowHeight(24);
        tabelaBarbeiros.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tabelaBarbeiros);

        scroll.setPreferredSize(new Dimension(700, 250));

        JPanel painelTabela = new JPanel(new BorderLayout());

        painelTabela.add(painelPesquisa, BorderLayout.NORTH);
        painelTabela.add(scroll, BorderLayout.CENTER);

        painelPrincipal.add(painelTabela, BorderLayout.SOUTH);

        add(painelPrincipal);

        // ===========================================
        // EVENTS
        // ===========================================

        btnAdicionar.addActionListener(e -> adicionarBarbeiro());

        btnPesquisar.addActionListener(e -> pesquisarBarbeiro());

        txtPesquisar.addActionListener(e -> pesquisarBarbeiro());

        btnAlterar.addActionListener(e -> alterarBarbeiro());

        btnExcluir.addActionListener(e -> excluirBarbeiro());

        btnLimpar.addActionListener(e -> limparCampos());

        btnAtualizar.addActionListener(e -> carregarTabela());

        btnVoltar.addActionListener(e -> {

            dispose();

            new MenuFrame();

        });

        tabelaBarbeiros.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                preencherCampos();

            }

        });

        carregarTabela();

    }

    private void pesquisarBarbeiro() {

        String pesquisa = txtPesquisar.getText().trim().toLowerCase();

        modeloTabela.setRowCount(0);

        for (Barbeiro Barbeiro : BarbeiroDAO.listar()) {

            if (Barbeiro.getNome().toLowerCase().contains(pesquisa)) {

                modeloTabela.addRow(new Object[]{

                        Barbeiro.getId(),
                        Barbeiro.getNome(),
                        Barbeiro.getTelefone()

                });

            }

        }

    }

    private void carregarTabela() {

        modeloTabela.setRowCount(0);

        for (Barbeiro Barbeiro : BarbeiroDAO.listar()) {

            modeloTabela.addRow(new Object[]{

                    Barbeiro.getId(),
                    Barbeiro.getNome(),
                    Barbeiro.getTelefone()

            });

        }

    }

    private void adicionarBarbeiro() {

        String nome = txtNome.getText().trim();
        String telefone = txtTelefone.getText().trim();

        if (nome.isEmpty() || telefone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha todos os campos."
            );

            return;
        }

        Barbeiro Barbeiro = new Barbeiro();

        Barbeiro.setNome(nome);
        Barbeiro.setTelefone(telefone);

        BarbeiroDAO.inserir(Barbeiro);

        carregarTabela();

        limparCampos();

    }

    private void alterarBarbeiro() {

        if (BarbeiroSelecionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um barbeiro."
            );

            return;
        }

        Barbeiro Barbeiro = new Barbeiro();

        Barbeiro.setId(BarbeiroSelecionado);
        Barbeiro.setNome(txtNome.getText().trim());
        Barbeiro.setTelefone(txtTelefone.getText().trim());

        BarbeiroDAO.alterar(Barbeiro);

        carregarTabela();

        limparCampos();

    }

    private void excluirBarbeiro() {

        if (BarbeiroSelecionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um barbeiro."
            );

            return;
        }

        int opcao = JOptionPane.showConfirmDialog(

                this,
                "Excluir Barbeiro?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION

        );

        if (opcao == JOptionPane.YES_OPTION) {

            BarbeiroDAO.excluir(BarbeiroSelecionado);

            carregarTabela();

            limparCampos();

        }

    }

    private void preencherCampos() {

        int linha = tabelaBarbeiros.getSelectedRow();

        if (linha == -1)
            return;

        BarbeiroSelecionado = (Integer) modeloTabela.getValueAt(linha, 0);

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

        BarbeiroSelecionado = -1;

        tabelaBarbeiros.clearSelection();

        txtNome.requestFocus();

    }

}