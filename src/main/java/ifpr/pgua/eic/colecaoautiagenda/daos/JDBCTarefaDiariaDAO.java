package ifpr.pgua.eic.colecaoautiagenda.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaoautiagenda.models.TarefaDiaria;
import java.sql.Date;

public class JDBCTarefaDiariaDAO implements TarefaDiariaDAO{
    private FabricaConexoes fabrica;

    public JDBCTarefaDiariaDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado criar(TarefaDiaria tarefaDiaria) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("INSERT INTO tb_rotina_diaria(titulo, data, horario, detalhes) VALUES (?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, tarefaDiaria.getTitulo());
            pstm.setDate(2, Date.valueOf(tarefaDiaria.getData()));
            pstm.setString(3, tarefaDiaria.getHorario());
            pstm.setString(4, tarefaDiaria.getDetalhes());
            int ret = pstm.executeUpdate();

            if (ret == 1) {
                ResultSet rs = pstm.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);

                tarefaDiaria.setId(id);

                return Resultado.sucesso("Agendamento de Tarefa Diária realizada com sucesso!", tarefaDiaria);
            }
            return Resultado.erro("O agendamento de Tarefa Diária não deu certo...");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado listar() {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM tb_rotina_diaria");

            ResultSet rs = pstm.executeQuery();
            ArrayList<TarefaDiaria> lista = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                LocalDate data = rs.getObject("data", LocalDate.class);
                String horario = rs.getString("horario");
                String detalhes = rs.getString("detalhes");

                TarefaDiaria tarefaDiaria = new TarefaDiaria(id, titulo, data, horario, detalhes);
                lista.add(tarefaDiaria);
            }

            return Resultado.sucesso("Lista de Tarefas Diárias", lista);
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }
}

