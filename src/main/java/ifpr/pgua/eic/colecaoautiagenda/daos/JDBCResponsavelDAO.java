package ifpr.pgua.eic.colecaoautiagenda.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaoautiagenda.models.Responsavel;

public class JDBCResponsavelDAO implements ResponsavelDAO{
     private FabricaConexoes fabrica;

    public JDBCResponsavelDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado criar(Responsavel responsavel) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("INSERT INTO tb_responsavel(nome, email) VALUES (?, ?)",Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, responsavel.getNome());
            pstm.setString(2, responsavel.getEmail());
            int ret = pstm.executeUpdate();

            if (ret == 1) {
                ResultSet rs = pstm.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);

                responsavel.setId(id);

                return Resultado.sucesso("Ótimo! Responsável cadastrado com sucesso!", responsavel);
            }
            return Resultado.erro("O cadastro de responsável não deu certo...");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado buscar(String nome, String email) {
        try (Connection con = fabrica.getConnection()) {
            String sql = "SELECT * FROM tb_responsavel WHERE nome=? AND email=?";
            try (PreparedStatement pstm = con.prepareStatement(sql)) {
                pstm.setString(1, nome);  
                pstm.setString(2, email);

                try (ResultSet rs = pstm.executeQuery()) {
                    if (rs.next()) {
                        String nomeEncontrado = rs.getString("nome");
                        String emailEncontrado = rs.getString("email");
                        int id = rs.getInt("id");

                        Responsavel responsavelEncontradoObj = new Responsavel(nomeEncontrado, emailEncontrado);
                        responsavelEncontradoObj.setId(id);

                        return Resultado.sucesso("Responsável encontrado com sucesso!!!", responsavelEncontradoObj);
                    } else {
                        return Resultado.erro("Responsável não encontrado...");
                    }
                }
            }
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }
}
