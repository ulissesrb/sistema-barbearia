package Controller;

import Dao.UsuarioDAO;
import Model.Usuario;
import Util.HashUtil;

public class UsuarioController {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    // Regra para Cadastrar
    public void cadastrarNovoUsuario(String login, String senhaPura) {
        // 1. Criptografa a senha antes de qualquer coisa
        String senhaProtegida = HashUtil.gerarHash(senhaPura);

        // 2. Cria o modelo com a senha protegida
        Usuario novoUsuario = new Usuario(login, senhaProtegida);

        // 3. Manda o DAO salvar
        usuarioDAO.inserir(novoUsuario);
    }

    // Regra para fazer Login
    public boolean autenticar(String login, String senhaPura) {
        // 1. Criptografa a senha digitada na tela para poder comparar com o banco
        String senhaProtegida = HashUtil.gerarHash(senhaPura);

        // 2. Pede para o DAO buscar alguém com esse login e essa senha criptografada
        Usuario usuarioEncontrado = usuarioDAO.buscarPorLoginESenha(login, senhaProtegida);

        // 3. Se encontrar o usuário, retorna true (Sucesso), se for null retorna false (Falhou)
        return usuarioEncontrado != null;
    }
}