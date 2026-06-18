package Dao;

import Database.Conexao;
import Model.Barbeiro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BarbeiroDAO {

    public void inserir(Barbeiro barbeiro) {

        String sql = "INSERT INTO barbeiros (nome, telefone) VALUES (?, ?)";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {

            stmt.setString(1, barbeiro.getNome());
            stmt.setString(2, barbeiro.getTelefone());

            if (barbeiro.getNome() == null || barbeiro.getNome().trim().isEmpty()) {
                throw new IllegalArgumentException("Nome é obrigatório.");
            }

            if (barbeiro.getTelefone() == null || barbeiro.getTelefone().trim().isEmpty()) {
                throw new IllegalArgumentException("Telefone é obrigatório");
            }

            stmt.executeUpdate();

            System.out.println("Barbeiro inserido com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Barbeiro> listar() {

        List<Barbeiro> barbeiros = new ArrayList<>();

        String sql = "SELECT * FROM barbeiros";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
        ) {

            while (rs.next()) {

                Barbeiro barbeiro = new Barbeiro(); // Instancia o objeto

                barbeiro.setId(rs.getInt("idbarbeiros")); // Atribui os valores do ResultSet
                barbeiro.setNome(rs.getString("nome"));
                barbeiro.setTelefone(rs.getString("telefone"));

                barbeiros.add(barbeiro); // Adiciona o barbeiro na lista
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return barbeiros;
    }

    public Barbeiro buscarPorId(int id){
        Barbeiro barbeiro = null; // Começa como null. Se não achar ninguém no banco, retorna null.
        String sql = "SELECT * FROM barbeiros WHERE idbarbeiros = ?";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            // 1. Primeiro definimos o parâmetro
            stmt.setInt(1, id);

            // 2. Depois executamos a query e guardamos no ResultSet
            try (ResultSet rs = stmt.executeQuery()) {

                // 3. Verificamos se o banco encontrou o barbeiro
                if (rs.next()) {
                    barbeiro = new Barbeiro(); // Instancia o objeto apenas se ele existir
                    barbeiro.setId(rs.getInt("idbarbeiros"));
                    barbeiro.setNome(rs.getString("nome"));
                    barbeiro.setTelefone(rs.getString("telefone"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar barbeiro por ID: " + e.getMessage());
            e.printStackTrace();
        }

        return barbeiro;
    }



    public void excluir(int id){
        String sql = "DELETE FROM barbeiros WHERE idbarbeiros = ?";

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

    public void alterar(Barbeiro barbeiro){
        String sql = " UPDATE barbeiros SET nome = ?, telefone = ? WHERE idbarbeiros = ?";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {

            stmt.setString(1, barbeiro.getNome());
            stmt.setString(2, barbeiro.getTelefone());
            stmt.setInt(3, barbeiro.getId());



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