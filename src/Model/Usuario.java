package Model;

public class Usuario {

    private int id;
    private String login;
    private String telefone;

    // Construtor vazio
    public Usuario() {
    }

    // Construtor com todos os atributos
    public Usuario(int id, String login, String telefone) {
        this.id = id;
        this.login = login;
        this.telefone = telefone;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return login;
    }

    public String getTelefone() {
        return telefone;
    }

    // Setters

    public void setNome(String login) {
        this.login = login;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
