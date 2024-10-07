package ifpr.pgua.eic.colecaoautiagenda.repositories;

import java.time.LocalDate;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.colecaoautiagenda.daos.TerapiaDAO;
import ifpr.pgua.eic.colecaoautiagenda.models.Terapia;

public class RepositorioTerapia {
    private ArrayList<Terapia> terapias;

    private TerapiaDAO dao;

    public RepositorioTerapia(TerapiaDAO dao) {
        terapias = new ArrayList<>();
        this.dao = dao;
    }

     public Resultado agendarTerapia(String titulo, LocalDate data, String horario, String detalhes) {

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

        Terapia novaTerapia = new Terapia(titulo, data, horario, detalhes);
        return dao.criar(novaTerapia);
    } 

    public Resultado listarAgendamentosTerapia() {
        return dao.listar();
    }
}
