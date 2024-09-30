package ifpr.pgua.eic.colecaoautiagenda.daos;

import com.github.hugoperlin.results.Resultado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ifpr.pgua.eic.colecaoautiagenda.models.Usuario;

public class JDBCUsuarioDAO implements UsuarioDAO{
    
    private FabricaConexoes fabrica;

    public JDBCUsuarioDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado criar(Usuario usuario) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("INSERT INTO tb_usuario(nome, email, senha) VALUES (?, ?, ?)",Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getEmail());
            pstm.setString(3, usuario.getSenha());
            int ret = pstm.executeUpdate();

            if (ret == 1) {
                ResultSet rs = pstm.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);

                usuario.setId(id);

                return Resultado.sucesso("Ótimo! Usuário cadastrado com sucesso!", usuario);
            }
            return Resultado.erro("O cadastro de usuário não deu certo...");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado buscar(String nome, String email, String senha) {
        try (Connection con = fabrica.getConnection()) {
            String sql = "SELECT * FROM tb_usuario WHERE nome=? AND email=? AND senha=?";
            try (PreparedStatement pstm = con.prepareStatement(sql)) {
                pstm.setString(1, nome);
                pstm.setString(2, email);  
                pstm.setString(3, senha);

                try (ResultSet rs = pstm.executeQuery()) {
                    if (rs.next()) {
                        String usuarioEncontrado = rs.getString("nome");
                        String emailEncontrado = rs.getString("email");
                        String senhaEncontrada = rs.getString("senha");
                        int id = rs.getInt("id");

                        Usuario usuarioEncontradoObj = new Usuario(id, usuarioEncontrado, emailEncontrado, senhaEncontrada);
                        usuarioEncontradoObj.setId(id);

                        return Resultado.sucesso("Usuário encontrado com sucesso!!!", usuarioEncontradoObj);
                    } else {
                        return Resultado.erro("Usuário não encontrado...");
                    }
                }
            }
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }
}