package Controller;

import Dao.AgendamentoDAO;
import Model.Agendamento;
import Exception.NegocioException;
import java.time.LocalDate;
import java.time.LocalTime;

public class AgendamentoController {

    private final AgendamentoDAO agendamentoDAO = new AgendamentoDAO();

    public void agendar(Agendamento agendamento) throws NegocioException {
        LocalDate hoje = LocalDate.now();
        LocalTime inicioFuncionamento = LocalTime.of(9, 0);
        LocalTime fimFuncionamento = LocalTime.of(18, 0);

        // REGRA 1: Não permitir agendamentos em datas passadas
        if (agendamento.getData().isBefore(hoje)) {
            throw new NegocioException("Não é possível realizar agendamentos em datas passadas.");
        }

        // REGRA 2: Não permitir fora do horário de funcionamento (09:00 às 18:00)
        if (agendamento.getHora().isBefore(inicioFuncionamento) || agendamento.getHora().isAfter(fimFuncionamento)) {
            throw new NegocioException("Horário inválido! A barbearia funciona apenas das 09:00 às 18:00.");
        }

        // REGRA 3: Não permitir dois agendamentos no mesmo horário para o mesmo barbeiro
        // Usamos o seu método do DAO para checar se já existe alguém nessa data e hora
        Agendamento agendamentoExistente = agendamentoDAO.buscarPorDataEHora(agendamento.getData(), agendamento.getHora());

        if (agendamentoExistente != null) {
            // Se achou um agendamento e o ID do barbeiro for o mesmo, bloqueia!
            if (agendamentoExistente.getBarbeiro().getId() == agendamento.getBarbeiro().getId()) {
                throw new NegocioException("Este barbeiro já possui um cliente agendado para este horário.");
            }
        }

        // Se passou por todos os "if", o agendamento é válido! Pode salvar no banco.
        agendamentoDAO.inserir(agendamento);
    }
}