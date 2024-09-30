package ifpr.pgua.eic.colecaoautiagenda.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaoautiagenda.models.Medicamento;


public class JDBCMedicamentoDAO implements MedicamentoDAO{
    private FabricaConexoes fabrica;

    public JDBCMedicamentoDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado criar(Medicamento medicamento) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("INSERT INTO tb_medicamento(titulo, data, horario, detalhes, foto) VALUES (?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, medicamento.getTitulo());
            pstm.setDate(2, Date.valueOf(medicamento.getData()));
            pstm.setString(3, medicamento.getHorario());
            pstm.setString(4, medicamento.getDetalhes());
            pstm.setString(5, medicamento.getFoto());
            int ret = pstm.executeUpdate();

            if (ret == 1) {
                ResultSet rs = pstm.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);

                medicamento.setId(id);

                return Resultado.sucesso("Agendamento de Medicamento realizada com sucesso!", medicamento);
            }
            return Resultado.erro("O agendamento de Medicamento não deu certo...");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }
}
