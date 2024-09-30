package ifpr.pgua.eic.colecaoautiagenda.repositories;

import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaoautiagenda.daos.ResponsavelDAO;
import ifpr.pgua.eic.colecaoautiagenda.models.Responsavel;

public class RepositorioResponsavel {
    private ResponsavelDAO dao;
    private ArrayList<Responsavel> responsaveis;

    public RepositorioResponsavel(ResponsavelDAO dao) {
        responsaveis = new ArrayList<>();
        this.dao = dao;
    }

    public Resultado cadastrarResponsavel(String nome, String email) {
        if (nome.isEmpty() || nome.isBlank()) {
            return Resultado.erro("O nome de responsável escolhido é inválido... Tente outro");
        }

        if (email.isEmpty() || email.isBlank()) {
            return Resultado.erro("O email escolhido é inválido... Tente outro");
        }

        Responsavel novoResponsavel = new Responsavel(0, nome, email);
        return dao.criar(novoResponsavel);
        }

        public Resultado buscarResponsavel(String nome, String email) {
            return dao.buscar(nome, email);
        }
}
