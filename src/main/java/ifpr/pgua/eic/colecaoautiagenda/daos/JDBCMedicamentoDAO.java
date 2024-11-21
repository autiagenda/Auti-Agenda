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
import ifpr.pgua.eic.colecaoautiagenda.models.Medicamento;

public class JDBCMedicamentoDAO implements MedicamentoDAO{
    private FabricaConexoes fabrica;

    public JDBCMedicamentoDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado criar(Medicamento medicamento) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("INSERT INTO tb_medicamento(titulo, data, horario, detalhes, foto, periodo) VALUES (?, ?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, medicamento.getTitulo());
            pstm.setDate(2, Date.valueOf(medicamento.getData()));
            pstm.setString(3, medicamento.getHorario());
            pstm.setString(4, medicamento.getDetalhes());
            pstm.setString(5, medicamento.getFoto());
            pstm.setString(6, medicamento.getPeriodo());
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

    @Override
    public Resultado listar() {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM tb_medicamento");

            ResultSet rs = pstm.executeQuery();
            ArrayList<Medicamento> lista = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                LocalDate data = rs.getObject("data", LocalDate.class);
                String horario = rs.getString("horario");
                String detalhes = rs.getString("detalhes");
                String foto = rs.getString("foto");
                String periodo = rs.getString("periodo");

                Medicamento medicamento = new Medicamento(id, titulo, data, horario, detalhes, foto, periodo);
                lista.add(medicamento);
            }

            return Resultado.sucesso("Lista de Medicamentos", lista);
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado deletar(int id) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("DELETE FROM tb_medicamento WHERE id = ?");
            pstm.setInt(1, id);

            int ret = pstm.executeUpdate();

            if (ret == 1) {
                return Resultado.sucesso("Ótimo! Lembrete de Medicamento deletado com sucesso", con);
            }
            return Resultado.erro("Lembrete de Medicamento não encontrado...");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado editar(int id, Medicamento novo) {
        try (Connection con = fabrica.getConnection();) {

            PreparedStatement pstm = con.prepareStatement(
                    "UPDATE tb_medicamento SET titulo=?, data=?, horario=?, detalhes=?, foto=?, periodo=? WHERE id=?");
            pstm.setString(1, novo.getTitulo());
            pstm.setDate(2, Date.valueOf(novo.getData()));
            pstm.setString(3, novo.getHorario());
            pstm.setString(4, novo.getDetalhes());
            pstm.setString(5, novo.getFoto());
            pstm.setString(6, novo.getPeriodo());
            pstm.setInt(7, id);

            int ret = pstm.executeUpdate();

            if (ret == 1) {
                return Resultado.sucesso("Agendamento de Medicamento Atualizado!", novo);
            }
            return Resultado.erro("Erro não identificado...");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }
}
