package Model;
import java.time.LocalDate;
import java.time.LocalTime;


public class Agendamento {
    private int id;
    private Cliente cliente;
    private Barbeiro barbeiro;
    private LocalDate data;
    private LocalTime hora;

    // Construtor vazio
    public Agendamento() {
    }

    // Construtor com todos os atributos
    public Agendamento(Cliente cliente, Barbeiro barbeiro, LocalDate data, LocalTime hora) {
        this.cliente = cliente;
        this.barbeiro = barbeiro;
        this.data = data;
        this.hora = hora;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Barbeiro getBarbeiro() {
        return barbeiro;
    }

    public LocalDate getData() { return data; }

    public LocalTime getHora() { return hora; }

    // Setters
    public void setId(int id) {this.id = id;}

    public void setCliente(Cliente cliente) {this.cliente = cliente;}

    public void setBarbeiro(Barbeiro barbeiro) {this.barbeiro = barbeiro;}
    public void setData(LocalDate data) {this.data = data;}
    public void setHora(LocalTime hora) {this.hora = hora;}

}

