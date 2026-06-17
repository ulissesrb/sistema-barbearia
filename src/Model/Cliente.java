package Model;

public class Cliente {

    private int id;
    private String nome;
    private String telefone;

    // Construtor vazio
    public Cliente() {
    }

    // Construtor com todos os atributos
    public Cliente(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    // Setters

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setId(int id) {this.id = id;}
}