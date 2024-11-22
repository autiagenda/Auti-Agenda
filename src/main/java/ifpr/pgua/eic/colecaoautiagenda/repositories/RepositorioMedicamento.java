package ifpr.pgua.eic.colecaoautiagenda.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.colecaoautiagenda.daos.MedicamentoDAO;
import ifpr.pgua.eic.colecaoautiagenda.models.Medicamento;

public class RepositorioMedicamento {
    private ArrayList<Medicamento> medicamentos;

    private MedicamentoDAO dao;

    public RepositorioMedicamento(MedicamentoDAO dao) {
        medicamentos = new ArrayList<>();
        this.dao = dao;
    }

     public Resultado agendarMedicamento(String titulo, LocalDate data, String horario, String detalhes, String foto, String periodo) {

        if (titulo == null || titulo.isEmpty() || titulo.isBlank()) {
            return Resultado.erro("Informe o título do seu agendamento!");
        }

        if (data == null) {
            return Resultado.erro("Data não selecionada! Tente novamente");
        }

        if (horario == null || horario.isEmpty() || horario.isBlank()) {
            return Resultado.erro("Informe o horário do seu agendamento!");
        }

        Medicamento novaMedicamento = new Medicamento(titulo, data, horario, detalhes, foto, periodo);
        return dao.criar(novaMedicamento);
    } 

    public Resultado listarAgendamentosMedicamento() {
        return dao.listar();
    }

    public Resultado concluirMedicamento(int id) {
        return dao.deletar(id);
    }

    public Resultado deletarMedicamento(int id) {
        return dao.deletar(id);
    }
    public Resultado editarAgendamentoMedicamento(int id, String titulo, LocalDate data, String horario, String detalhes, String foto, String periodo){
        Medicamento novo = new Medicamento(id, titulo, data, horario, detalhes, foto, periodo);

        return dao.editar(id, novo);
    }
}
