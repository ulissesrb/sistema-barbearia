package Model;

public class Usuario {

    private int id;
    private String login;
    private String senha;

    // Construtor vazio
    public Usuario() {
    }

    // Construtor com todos os atributos
    public Usuario( String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

  // Getters e Setters
    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


}