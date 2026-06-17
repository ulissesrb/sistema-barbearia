import Dao.BarbeiroDAO;
import Model.Barbeiro;
import Model.Cliente;
import Model.Agendamento;
import Dao.AgendamentoDAO;
import java.time.LocalDate;
import java.time.LocalTime;
import Dao.ClienteDAO;
import java.util.List;
import Controller.UsuarioController;

public class TesteDAO {

    public static void main(String[] args) {

        /* // INSERINDO UM BARBEIRO
        Barbeiro barbeiro = new Barbeiro();

        barbeiro.setNome("robson");
        barbeiro.setTelefone("123456789");

        BarbeiroDAO barbeiroDAO = new BarbeiroDAO();
        barbeiroDAO.inserir(barbeiro);*/

        /*BarbeiroDAO dao = new BarbeiroDAO();

        // LISTANDO TODOS OS BARBEIROS
        List<Barbeiro> lista = dao.listar();

        for (Barbeiro c : lista) {
            System.out.println(
                            c.getId() + " - " +
                            c.getNome() + " - " +
                            c.getTelefone()
            );
        }*/
            // BUSCANDO UM CLIENTE POR ID

        /*Cliente cliente = dao.buscarPorId(3);
        cliente.setNome("Hugo");

        dao.alterar(cliente);*/

        /*  //SIMULANDO CADASTRO

        UsuarioController controle = new UsuarioController();

        // SIMULANDO UM CADASTRO (O usuário digita na tela)
        System.out.println("Cadastrando barbeiro...");
        controle.cadastrarNovoUsuario("marcos_barbeiro", "senha123");
        // Olhe no seu MySQL Workbench: a senha estará salva como um código gigante e seguro!


        // SIMULANDO UMA TELA DE LOGIN
        System.out.println("\nTentando fazer login com senha INCORRETA...");
        boolean tentativa1 = controle.autenticar("marcos_barbeiro", "senha_errada");
        System.out.println("Login autorizado? " + tentativa1); // Vai dar false


        System.out.println("\nTentando fazer login com senha CORRETA...");
        boolean tentativa2 = controle.autenticar("marcos_barbeiro", "senha123");
        System.out.println("Login autorizado? " + tentativa2); // Vai dar true!*/

        /*// INSERINDO UM AGENDAMENTO

        AgendamentoDAO agendamentoDAO = new AgendamentoDAO();

        // 1. Simulamos que buscamos o Cliente ID 3 do banco
        Cliente clienteSelecionado = new Cliente();
        clienteSelecionado.setId(3); // Vamos fingir que o ID dele no banco é 3

        // 2. Simulamos que buscamos o Barbeiro ID 1 do banco
        Barbeiro barbeiroSelecionado = new Barbeiro();
        barbeiroSelecionado.setId(1); // Vamos fingir que o ID dele no banco é 1

        // 3. Criamos o agendamento para o dia 20 de Março às 14:30
        Agendamento novoAgendamento = new Agendamento();
        novoAgendamento.setCliente(clienteSelecionado);
        novoAgendamento.setBarbeiro(barbeiroSelecionado);
        novoAgendamento.setData(LocalDate.of(2026, 3, 20)); // Define a data (Ano, Mês, Dia)
        novoAgendamento.setHora(LocalTime.of(14, 30));       // Define o horário (Hora, Minuto)

        // 4. Mandamos o DAO salvar tudo no banco
        agendamentoDAO.inserir(novoAgendamento);*/

        // LISTANDO TODOS OS AGENDAMENTOS
        AgendamentoDAO agdao = new AgendamentoDAO();
        List<Agendamento> lista = agdao.listar();

        for (Agendamento a : lista) {
            System.out.println(
                    "ID do agendamento: "+ a.getId() + "\n" +
                     "Nome do Cliente: " + a.getCliente().getNome() + "\n" +
                     "Nome do Barbeiro: " + a.getBarbeiro().getNome() + "\n" +
                            a.getData() + " - " +
                            a.getHora()
            );
        }

        // BUSCANDO UM CLIENTE POR ID

        /*Cliente cliente = dao.buscarPorId(3);
        cliente.setNome("Hugo");

        dao.alterar(cliente);*/

    }
}