package ifpr.pgua.eic.colecaoautiagenda.daos;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaoautiagenda.models.Medicamento;

public interface MedicamentoDAO {
    Resultado criar(Medicamento medicamento);
    Resultado listar();
    Resultado editar(int id, Medicamento novo);
    Resultado deletar(int id);
}
