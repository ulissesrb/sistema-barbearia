import Model.Cliente;

public class testeCliente {
    public static void main(String[] args) {
        Cliente cliente = new Cliente();


        cliente.setNome("Ulisses");
        cliente.setTelefone("11999999999");


        System.out.println(cliente.getNome());
        System.out.println(cliente.getTelefone());
    }
}