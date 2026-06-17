package Dao;
import Model.Agendamento;
import Database.Conexao;
import Model.Barbeiro;
import Model.Cliente;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

public class AgendamentoDAO {


    public void inserir(Agendamento agendamento) {
        String sql = "INSERT INTO agendamentos (cliente_id, barbeiro_id, data, hora) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            // 1. Extrai o ID do cliente de dentro do objeto Cliente
            stmt.setInt(1, agendamento.getCliente().getId()); // Garanta que o método no Cliente seja getId() ou getIdclientes()

            // 2. Extrai o ID do barbeiro de dentro do objeto Barbeiro
            stmt.setInt(2, agendamento.getBarbeiro().getId()); // Garanta que o método no Barbeiro seja getId() ou getIdbarbeiros()

            // 3. Converte LocalDate do Java para java.sql.Date do JDBC
            stmt.setDate(3, java.sql.Date.valueOf(agendamento.getData()));

            // 4. Converte LocalTime do Java para java.sql.Time do JDBC
            stmt.setTime(4, java.sql.Time.valueOf(agendamento.getHora()));

            // Executa o comando no banco
            stmt.executeUpdate();
            System.out.println("Agendamento realizado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar o agendamento!");
            e.printStackTrace();
        }
    }

    public List<Agendamento> listar() {

        List<Agendamento> agendamentos = new ArrayList<>();

        String sql = "SELECT * FROM agendamentos";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
        ) {

            while (rs.next()) {

                Agendamento age = new Agendamento(); // Instancia o objeto

                age.setId(rs.getInt("id")); // Atribui os valores do ResultSet
                age.setCliente(new ClienteDAO().buscarPorId(rs.getInt("cliente_id")));
                age.setBarbeiro(new BarbeiroDAO().buscarPorId(rs.getInt("barbeiro_id")));
                age.setData(rs.getDate("data").toLocalDate());
                age.setHora(rs.getTime("hora").toLocalTime());

                agendamentos.add(age); // Adiciona o agendamento na lista
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return agendamentos;
    }

    public Agendamento buscarPorId(int id) {
        Agendamento agendamento = null; // se não encontrar, é null

        // O comando JOIN junta as tabelas pelos IDs correspondentes
        String sql = "SELECT a.*, c.nome AS nome_cliente, b.nome AS nome_barbeiro " +
                "FROM agendamentos a " +
                "INNER JOIN clientes c ON a.cliente_id = c.idclientes " +
                "INNER JOIN barbeiros b ON a.barbeiro_id = b.idbarbeiros " +
                "WHERE a.id = ?";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) { // Executa a query e guarda no ResultSet
                if (rs.next()) {
                    agendamento = new Agendamento();
                    agendamento.setId(rs.getInt("id")); // Nome da PK na tabela agendamentos

                    // 1. Criamos o objeto Cliente e preenchemos os dados que vieram do JOIN
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("cliente_id"));
                    cliente.setNome(rs.getString("nome_cliente"));
                    agendamento.setCliente(cliente); // Coloca o cliente dentro do agendamento

                    // 2. Criamos o objeto Barbeiro e preenchemos os dados que vieram do JOIN
                    Barbeiro barbeiro = new Barbeiro();
                    barbeiro.setId(rs.getInt("barbeiro_id"));
                    barbeiro.setNome(rs.getString("nome_barbeiro"));
                    agendamento.setBarbeiro(barbeiro); // Coloca o barbeiro dentro do agendamento

                    // 3. Convertemos as datas do MySQL (Date e Time) para o Java moderno (LocalDate e LocalTime)
                    agendamento.setData(rs.getDate("data").toLocalDate());
                    agendamento.setHora(rs.getTime("hora").toLocalTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agendamento;
    }

    public Agendamento buscarPorNomeCliente(String nome){
        Agendamento agendamento = null; // se não encontrar, é null

        // O comando JOIN junta as tabelas pelos IDs correspondentes
        String sql = "SELECT a.*, c.nome AS nome_cliente, b.nome AS nome_barbeiro " +
                "FROM agendamentos a " +
                "INNER JOIN clientes c ON a.cliente_id = c.idclientes " +
                "INNER JOIN barbeiros b ON a.barbeiro_id = b.idbarbeiros " +
                "WHERE LOWER(c.nome) = LOWER(?)";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1, nome.toLowerCase());

            try (ResultSet rs = stmt.executeQuery()) { // Executa a query e guarda no ResultSet
                if (rs.next()) {
                    agendamento = new Agendamento();
                    agendamento.setId(rs.getInt("id")); // Nome da PK na tabela agendamentos

                    // 1. Criamos o objeto Cliente e preenchemos os dados que vieram do JOIN
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("idclientes"));
                    cliente.setNome(rs.getString("nome_cliente"));
                    agendamento.setCliente(cliente); // Coloca o cliente dentro do agendamento

                    // 2. Criamos o objeto Barbeiro e preenchemos os dados que vieram do JOIN
                    Barbeiro barbeiro = new Barbeiro();
                    barbeiro.setId(rs.getInt("idbarbeiros"));
                    barbeiro.setNome(rs.getString("nome"));
                    agendamento.setBarbeiro(barbeiro); // Coloca o barbeiro dentro do agendamento

                    // 3. Convertemos as datas do MySQL (Date e Time) para o Java moderno (LocalDate e LocalTime)
                    agendamento.setData(rs.getDate("data").toLocalDate());
                    agendamento.setHora(rs.getTime("hora").toLocalTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agendamento;
    }

    public Agendamento buscarPorDataEHora(LocalDate data, LocalTime hora){
    return null;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM agendamentos WHERE id = ?";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {


            stmt.setInt(1, id);


            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Operação realizada com sucesso.");
            } else {
                System.out.println("Nenhum registro foi encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
