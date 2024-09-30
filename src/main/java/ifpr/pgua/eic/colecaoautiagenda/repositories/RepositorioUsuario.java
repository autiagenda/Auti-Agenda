package ifpr.pgua.eic.colecaoautiagenda.repositories;

import com.github.hugoperlin.results.Resultado;
import java.util.ArrayList;
import ifpr.pgua.eic.colecaoautiagenda.daos.UsuarioDAO;
import ifpr.pgua.eic.colecaoautiagenda.models.Usuario;

public class RepositorioUsuario {
    private UsuarioDAO dao;
    private ArrayList<Usuario> usuarios;

    public RepositorioUsuario(UsuarioDAO dao) {
        usuarios = new ArrayList<>();
        this.dao = dao;
    }

    public Resultado cadastrarUsuario(String nome, String email, String senha) {
        if (nome.isEmpty() || nome.isBlank()) {
            return Resultado.erro("O nome de usuário escolhido é inválido... Tente outra");
        }

        if (email.isEmpty() || email.isBlank()) {
            return Resultado.erro("O email escolhido é inválido... Tente outra");
        }

        if (senha.isEmpty() || senha.isBlank()) {
            return Resultado.erro("A senha escolhida é inválida... Tente outra");
        }

        Usuario novoUsuario = new Usuario(0, nome, email, senha);
        return dao.criar(novoUsuario);
        }

        public Resultado buscarUsuario(String nome, String email, String senha) {
            return dao.buscar(nome, email, senha);
        }
    
}


    
    

