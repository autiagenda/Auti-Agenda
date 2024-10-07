package ifpr.pgua.eic.colecaoautiagenda.repositories;

import java.time.LocalDate;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaoautiagenda.daos.TarefaDiariaDAO;
import ifpr.pgua.eic.colecaoautiagenda.models.TarefaDiaria;

public class RepositorioTarefaDiaria {
    private ArrayList<TarefaDiaria> tarefasDiarias;

    private TarefaDiariaDAO dao;

    public RepositorioTarefaDiaria(TarefaDiariaDAO dao) {
        tarefasDiarias = new ArrayList<>();
        this.dao = dao;
    }

     public Resultado agendarTarefaDiaria(String titulo, LocalDate data, String horario, String detalhes) {

        if (titulo == null || titulo.isEmpty() || titulo.isBlank()) {
            return Resultado.erro("Informe o título do seu agendamento!");
        }

        if (data == null) {
            return Resultado.erro("Data não selecionada! Tente novamente");
        }

        if (horario == null || horario.isEmpty() || horario.isBlank()) {
            return Resultado.erro("Informe o horário do seu agendamento!");
        }

        if (detalhes == null || detalhes.isEmpty() || detalhes.isBlank()) {
            return Resultado.erro("Informe os detalhes do seu agendamento!");
        }

        TarefaDiaria novaTarefaDiaria = new TarefaDiaria(titulo, data, horario, detalhes);
        return dao.criar(novaTarefaDiaria);
    } 

    public Resultado listarAgendamentosTarefaDiaria() {
        return dao.listar();
    }

    public Resultado deletarTarefaDiaria(int id) {
        return dao.deletar(id);
    }
}
