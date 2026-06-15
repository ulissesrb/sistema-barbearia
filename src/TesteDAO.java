import Model.Cliente;
import Dao.ClienteDAO;
import java.util.List;

public class TesteDAO {

    public static void main(String[] args) {

        ClienteDAO dao = new ClienteDAO();

        List<Cliente> lista = dao.listar();

        for (Cliente c : lista) {
            System.out.println(
                            c.getId() + " - " +
                            c.getNome() + " - " +
                            c.getTelefone()
            );
        }


        /*Cliente cliente = dao.buscarPorId(3);
        cliente.setNome("Hugo");

        dao.alterar(cliente);*/


    }
}