package ifpr.pgua.eic.colecaoautiagenda.controllers;

import java.time.LocalDate;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaoautiagenda.App;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioTerapia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AgendamentoTerapia {
  
    @FXML
    private DatePicker labelData;

    @FXML
    private TextField labelDetalhes;

    @FXML
    private TextField labelHorario;

    @FXML
    private TextField labelTitulo;

    private RepositorioTerapia repositorioTerapia;

    public AgendamentoTerapia(RepositorioTerapia repositorioTerapia) {
        this.repositorioTerapia = repositorioTerapia;
    }

    @FXML
    void botaoAgendarTerapia(ActionEvent event) {
        String titulo = labelTitulo.getText();
        LocalDate data = labelData.getValue();
        String horario = labelHorario.getText();
        String detalhes = labelDetalhes.getText();

        if (titulo.isEmpty() || data == null || horario.isEmpty() || detalhes.isEmpty()) {
            new Alert(AlertType.ERROR, "Por favor, preencha todos os campos!").showAndWait();
            return;
        }

        Resultado resultadoAgendamento = repositorioTerapia.agendarTerapia(titulo, data, horario, detalhes);

        Alert alert;
        if (resultadoAgendamento != null && resultadoAgendamento.foiSucesso()) {
            alert = new Alert(AlertType.INFORMATION, "Agendamento de Terapia cadastrada com sucesso!");
        } else {
            String mensagemErro = resultadoAgendamento != null ? resultadoAgendamento.getMsg() : "Erro ao agendar uma nova Terapia!";
            alert = new Alert(AlertType.ERROR, mensagemErro);
        }
        alert.showAndWait();
        App.popScreen();  
    }

    @FXML
    void voltar(ActionEvent event) {
        App.pushScreen("MENUPRINCIPAL");
    }
}
