package View;

import Dao.AgendamentoDAO;
import Dao.BarbeiroDAO;
import Dao.ClienteDAO;

import Model.Agendamento;
import Model.Barbeiro;
import Model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class AgendamentoFrame extends JFrame {

    // DAOs
    private final AgendamentoDAO agendamentoDAO;
    private final ClienteDAO clienteDAO;
    private final BarbeiroDAO barbeiroDAO;

    // Labels
    private JLabel lblTitulo;
    private JLabel lblCliente;
    private JLabel lblBarbeiro;
    private JLabel lblData;
    private JLabel lblHora;

    // Components
    private JComboBox<Cliente> cbClientes;
    private JComboBox<Barbeiro> cbBarbeiros;

    private JTextField txtData;
    private JTextField txtHora;

    // Buttons
    private JButton btnAdicionar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnLimpar;
    private JButton btnAtualizar;
    private JButton btnVoltar;

    // Table
    private JTable tabelaAgendamentos;
    private DefaultTableModel modeloTabela;

    // Selected appointment
    private int agendamentoSelecionado = -1;

    public AgendamentoFrame() {

        agendamentoDAO = new AgendamentoDAO();
        clienteDAO = new ClienteDAO();
        barbeiroDAO = new BarbeiroDAO();

        setTitle("Sistema Barbearia - Agendamentos");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        inicializarComponentes();

        setVisible(true);

    }

    private void inicializarComponentes() {

        JPanel painelPrincipal = new JPanel(new BorderLayout(15,15));

        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15,15,15,15)
        );

        // ===========================
        // TITLE
        // ===========================

        lblTitulo = new JLabel(
                "Agendamentos",
                SwingConstants.CENTER
        );

        lblTitulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // ===========================
        // FORM
        // ===========================

        JPanel painelFormulario = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblCliente = new JLabel("Cliente:");
        lblBarbeiro = new JLabel("Barbeiro:");
        lblData = new JLabel("Data (AAAA-MM-DD):");
        lblHora = new JLabel("Hora (HH:MM):");

        cbClientes = new JComboBox<>();
        cbBarbeiros = new JComboBox<>();

        txtData = new JTextField(12);
        txtHora = new JTextField(8);

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelFormulario.add(lblCliente, gbc);

        gbc.gridx = 1;
        painelFormulario.add(cbClientes, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        painelFormulario.add(lblBarbeiro, gbc);

        gbc.gridx = 1;
        painelFormulario.add(cbBarbeiros, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        painelFormulario.add(lblData, gbc);

        gbc.gridx = 1;
        painelFormulario.add(txtData, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        painelFormulario.add(lblHora, gbc);

        gbc.gridx = 1;
        painelFormulario.add(txtHora, gbc);

        // ===========================
        // BUTTONS
        // ===========================

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

        // ===========================
        // TABLE
        // ===========================

        modeloTabela = new DefaultTableModel();

        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Cliente");
        modeloTabela.addColumn("Barbeiro");
        modeloTabela.addColumn("Data");
        modeloTabela.addColumn("Hora");

        tabelaAgendamentos = new JTable(modeloTabela);

        tabelaAgendamentos.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tabelaAgendamentos.setRowHeight(24);

        tabelaAgendamentos.getTableHeader()
                .setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tabelaAgendamentos);

        scroll.setPreferredSize(
                new Dimension(850,260)
        );

        painelPrincipal.add(scroll, BorderLayout.SOUTH);

        add(painelPrincipal);

        // ===========================
        // EVENTS
        // ===========================

        btnAdicionar.addActionListener(e -> adicionarAgendamento());

        btnAlterar.addActionListener(e -> alterarAgendamento());

        btnExcluir.addActionListener(e -> excluirAgendamento());

        btnLimpar.addActionListener(e -> limparCampos());

        btnAtualizar.addActionListener(e -> carregarTabela());

        btnVoltar.addActionListener(e -> {

            dispose();

            new MenuFrame();

        });

        tabelaAgendamentos.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                preencherCampos();

            }

        });

        // Load data
        carregarClientes();

        carregarBarbeiros();

        carregarTabela();

        btnAlterar.setEnabled(false);
        btnExcluir.setEnabled(false);

    }

    private void carregarClientes() {

        cbClientes.removeAllItems();

        for (Cliente cliente : clienteDAO.listar()) {

            cbClientes.addItem(cliente);

        }

    }

    private void carregarBarbeiros() {

        cbBarbeiros.removeAllItems();

        for (Barbeiro barbeiro : barbeiroDAO.listar()) {

            cbBarbeiros.addItem(barbeiro);

        }

    }

    private void carregarTabela() {

        modeloTabela.setRowCount(0);

        for (Agendamento agendamento : agendamentoDAO.listar()) {

            modeloTabela.addRow(new Object[]{

                    agendamento.getId(),

                    agendamento.getCliente().getNome(),

                    agendamento.getBarbeiro().getNome(),

                    agendamento.getData(),

                    agendamento.getHora()

            });

        }

    }

    private void preencherCampos() {

        int linha = tabelaAgendamentos.getSelectedRow();

        if (linha == -1)
            return;

        agendamentoSelecionado =
                (Integer) modeloTabela.getValueAt(linha, 0);

        Agendamento agendamento =
                agendamentoDAO.buscarPorId(agendamentoSelecionado);

        if (agendamento == null)
            return;

        cbClientes.setSelectedItem(
                agendamento.getCliente()
        );

        cbBarbeiros.setSelectedItem(
                agendamento.getBarbeiro()
        );

        txtData.setText(
                agendamento.getData().toString()
        );

        txtHora.setText(
                agendamento.getHora().toString()
        );

        btnAlterar.setEnabled(true);
        btnExcluir.setEnabled(true);

    }

    private void limparCampos() {

        cbClientes.setSelectedIndex(0);

        cbBarbeiros.setSelectedIndex(0);

        txtData.setText("");

        txtHora.setText("");

        agendamentoSelecionado = -1;

        tabelaAgendamentos.clearSelection();

        btnAlterar.setEnabled(false);

        btnExcluir.setEnabled(false);

    }

    private void adicionarAgendamento() {

        try {

            Cliente cliente = (Cliente) cbClientes.getSelectedItem();
            Barbeiro barbeiro = (Barbeiro) cbBarbeiros.getSelectedItem();

            if (cliente == null || barbeiro == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Selecione um cliente e um barbeiro."
                );

                return;
            }

            LocalDate data = LocalDate.parse(txtData.getText().trim());
            LocalTime hora = LocalTime.parse(txtHora.getText().trim());

            Agendamento agendamento = new Agendamento();

            agendamento.setCliente(cliente);
            agendamento.setBarbeiro(barbeiro);
            agendamento.setData(data);
            agendamento.setHora(hora);

            agendamentoDAO.inserir(agendamento);

            JOptionPane.showMessageDialog(
                    this,
                    "Agendamento realizado com sucesso!"
            );

            carregarTabela();
            limparCampos();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Data ou hora inválida.\n\n" +
                            "Data: AAAA-MM-DD\n" +
                            "Hora: HH:MM",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }

    private void alterarAgendamento() {

        if (agendamentoSelecionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um agendamento."
            );

            return;
        }

        try {

            Cliente cliente = (Cliente) cbClientes.getSelectedItem();
            Barbeiro barbeiro = (Barbeiro) cbBarbeiros.getSelectedItem();

            LocalDate data = LocalDate.parse(txtData.getText().trim());
            LocalTime hora = LocalTime.parse(txtHora.getText().trim());

            Agendamento agendamento = new Agendamento();

            agendamento.setId(agendamentoSelecionado);
            agendamento.setCliente(cliente);
            agendamento.setBarbeiro(barbeiro);
            agendamento.setData(data);
            agendamento.setHora(hora);

            agendamentoDAO.alterar(agendamento);

            JOptionPane.showMessageDialog(
                    this,
                    "Agendamento alterado com sucesso!"
            );

            carregarTabela();

            limparCampos();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Data ou hora inválida.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }

    private void excluirAgendamento() {

        if (agendamentoSelecionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um agendamento."
            );

            return;
        }

        int resposta = JOptionPane.showConfirmDialog(

                this,
                "Deseja realmente excluir este agendamento?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION

        );

        if (resposta == JOptionPane.YES_OPTION) {

            agendamentoDAO.excluir(agendamentoSelecionado);

            JOptionPane.showMessageDialog(
                    this,
                    "Agendamento excluído."
            );

            carregarTabela();

            limparCampos();

        }

    }

}