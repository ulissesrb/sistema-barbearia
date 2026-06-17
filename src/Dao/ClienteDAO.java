package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Model.Cliente;
import Database.Conexao;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class ClienteDAO {

    public void inserir(Cliente cliente) {

        String sql = "INSERT INTO clientes (nome, telefone) VALUES (?, ?)";

        try (
                Connection conn = Conexao.conectar(); // Conecta ao banco
                PreparedStatement stmt = conn.prepareStatement(sql); // Prepara a query
        ) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());

            if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
                throw new IllegalArgumentException("Nome é obrigatório.");
            }

            if (cliente.getTelefone() == null || cliente.getTelefone().trim().isEmpty()) {
                throw new IllegalArgumentException("Telefone é obrigatório");
            }

            stmt.executeUpdate();

            System.out.println("Cliente inserido com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> listar() {

        List<Cliente> clientes = new ArrayList<>();

        String sql = "SELECT * FROM clientes";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
        ) {

            while (rs.next()) {

                Cliente cliente = new Cliente(); // Instancia o objeto

                cliente.setId(rs.getInt("idclientes")); // Atribui os valores do ResultSet
                cliente.setNome(rs.getString("nome"));
                cliente.setTelefone(rs.getString("telefone"));

                clientes.add(cliente); // Adiciona o cliente na lista
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    public Cliente buscarPorId(int id){
        Cliente cliente = null; // Começa como null. Se não achar ninguém no banco, retorna null.
        String sql = "SELECT * FROM clientes WHERE idclientes = ?";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            // 1. Primeiro definimos o parâmetro
            stmt.setInt(1, id);

            // 2. Depois executamos a query e guardamos no ResultSet
            try (ResultSet rs = stmt.executeQuery()) {

                // 3. Verificamos se o banco encontrou o cliente
                if (rs.next()) {
                    cliente = new Cliente(); // Instancia o objeto apenas se ele existir
                    cliente.setId(rs.getInt("idclientes"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setTelefone(rs.getString("telefone"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente por ID: " + e.getMessage());
            e.printStackTrace();
        }

        return cliente;
    }

    public void excluir(int id){
        String sql = "DELETE FROM clientes WHERE idclientes = ?";

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

    public void alterar(Cliente cliente){
        String sql = " UPDATE clientes SET nome = ?, telefone = ? WHERE idclientes = ?";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setInt(3, cliente.getId());


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