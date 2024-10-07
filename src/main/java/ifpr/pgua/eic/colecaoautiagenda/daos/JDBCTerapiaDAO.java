package ifpr.pgua.eic.colecaoautiagenda.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaoautiagenda.models.Terapia;

public class JDBCTerapiaDAO implements TerapiaDAO{
    private FabricaConexoes fabrica;

    public JDBCTerapiaDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado criar(Terapia terapia) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("INSERT INTO tb_terapia(titulo, data, horario, detalhes) VALUES (?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, terapia.getTitulo());
            pstm.setDate(2, Date.valueOf(terapia.getData()));
            pstm.setString(3, terapia.getHorario());
            pstm.setString(4, terapia.getDetalhes());
            int ret = pstm.executeUpdate();

            if (ret == 1) {
                ResultSet rs = pstm.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);

                terapia.setId(id);

                return Resultado.sucesso("Agendamento de Terapia realizada com sucesso!", terapia);
            }
            return Resultado.erro("O agendamento de Terapia não deu certo...");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado listar() {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM tb_terapia");

            ResultSet rs = pstm.executeQuery();
            ArrayList<Terapia> lista = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                LocalDate data = rs.getObject("data", LocalDate.class);
                String horario = rs.getString("horario");
                String detalhes = rs.getString("detalhes");

                Terapia terapia = new Terapia(id, titulo, data, horario, detalhes);
                lista.add(terapia);
            }

            return Resultado.sucesso("Lista de Terapias", lista);
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }
}
