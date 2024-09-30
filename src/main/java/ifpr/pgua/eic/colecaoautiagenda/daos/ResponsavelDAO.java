package ifpr.pgua.eic.colecaoautiagenda.daos;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaoautiagenda.models.Responsavel;

public interface ResponsavelDAO {
    Resultado criar(Responsavel responsavel);
    Resultado buscar(String nome, String email);
}
