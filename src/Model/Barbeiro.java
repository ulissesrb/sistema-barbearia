package Model;

public class Barbeiro {

    private int id;
    private String nome;
    private String telefone;

    // Construtor vazio
    public Barbeiro() {
    }

    // Construtor com todos os atributos
    public Barbeiro(String nome, String telefone) {
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
    public void setId(int id) {this.id = id;}

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return this.nome; // este método retorna o nome do barbeiro quando chamado
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (!(obj instanceof Barbeiro)) return false;

        Barbeiro outro = (Barbeiro) obj;

        return id == outro.id;

    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }


}

