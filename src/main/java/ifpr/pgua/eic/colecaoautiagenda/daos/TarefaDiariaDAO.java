package ifpr.pgua.eic.colecaoautiagenda.daos;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaoautiagenda.models.TarefaDiaria;

public interface TarefaDiariaDAO {
    Resultado criar(TarefaDiaria tarefaDiaria);
    Resultado listar();
   // Resultado editar(int id, TarefaDiaria novo);
   // Resultado deletar(int id);
}
