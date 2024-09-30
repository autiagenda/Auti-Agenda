package ifpr.pgua.eic.colecaoautiagenda.daos;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaoautiagenda.models.Usuario;

public interface UsuarioDAO {
    Resultado criar(Usuario usuario);
    Resultado buscar(String nome, String email, String senha);
}
