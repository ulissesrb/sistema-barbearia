package Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Usuario;
import Database.Conexao;

public class UsuarioDAO {

    // Método que apenas insere o que recebe
    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO usuarios (login, senha) VALUES (?, ?)";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, usuario.getSenha()); // Já vai salvar o hash criptografado

            stmt.executeUpdate();
            System.out.println("Usuário cadastrado no banco com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método que valida se existe um usuário com aquele login e senha específicos
    // é com esse método que é possivel fazer o login
    public Usuario buscarPorLoginESenha(String login, String senhaCriptografada) {
        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1, login);
            stmt.setString(2, senhaCriptografada);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario user = new Usuario();
                    user.setId(rs.getInt("idusuarios"));
                    user.setLogin(rs.getString("login"));
                    user.setSenha(rs.getString("senha"));
                    return user; // Achou! Login válido.
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Não achou ninguém com essa combinação. Login inválido.
    }


    public List<Usuario> listar() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM usuarios";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
        ) {

            while (rs.next()) {

                Usuario usuario = new Usuario(); // Instancia o objeto

                usuario.setId(rs.getInt("idusuarios")); // Atribui os valores do ResultSet
                usuario.setLogin(rs.getString("login"));

                usuarios.add(usuario); // Adiciona o usuario na lista
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public Usuario buscarPorId(int id) {
        Usuario usuario = null; // Começa como null. Se não achar ninguém no banco, retorna null.
        String sql = "SELECT * FROM usuarios WHERE idusuarios = ?";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            // 1. Primeiro definimos o parâmetro
            stmt.setInt(1, id);

            // 2. Depois executamos a query e guardamos no ResultSet
            try (ResultSet rs = stmt.executeQuery()) {

                // 3. Verificamos se o banco encontrou o usuario
                if (rs.next()) {
                    usuario = new Usuario(); // Instancia o objeto apenas se ele existir
                    usuario.setId(rs.getInt("idusuarios"));
                    usuario.setLogin(rs.getString("login"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuario por ID: " + e.getMessage());
            e.printStackTrace();
        }

        return usuario;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM usuarios WHERE idusuarios = ?";

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

    public void alterar(Usuario usuario) {
        String sql = " UPDATE usuarios SET login = ? WHERE idusuarios = ?";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {

            stmt.setString(1, usuario.getLogin());
            stmt.setInt(2, usuario.getId());



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
