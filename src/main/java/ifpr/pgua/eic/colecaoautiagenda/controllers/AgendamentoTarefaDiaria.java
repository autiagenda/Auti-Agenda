package ifpr.pgua.eic.colecaoautiagenda.controllers;

import java.time.LocalDate;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaoautiagenda.App;
import ifpr.pgua.eic.colecaoautiagenda.models.TarefaDiaria;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioTarefaDiaria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AgendamentoTarefaDiaria {
    
    @FXML
    private DatePicker labelData;

    @FXML
    private TextField labelDetalhes;

    @FXML
    private TextField labelHorario;

    @FXML
    private TextField labelTitulo;

    private RepositorioTarefaDiaria repositorioTarefaDiaria;

    public AgendamentoTarefaDiaria(RepositorioTarefaDiaria repositorioTarefaDiaria) {
        this.repositorioTarefaDiaria = repositorioTarefaDiaria;
    }

    @FXML
    void botaoAgendarRotinaDiaria(ActionEvent event) {
        String titulo = labelTitulo.getText();
        LocalDate data = labelData.getValue();
        String horario = labelHorario.getText();
        String detalhes = labelDetalhes.getText();

        if (titulo.isEmpty() || data == null || horario.isEmpty() || detalhes.isEmpty()) {
            new Alert(AlertType.ERROR, "Por favor, preencha todos os campos!").showAndWait();
            return;
        }

        Resultado resultadoAgendamento = repositorioTarefaDiaria.agendarTarefaDiaria(titulo, data, horario, detalhes);

        Alert alert;
        if (resultadoAgendamento != null && resultadoAgendamento.foiSucesso()) {
            alert = new Alert(AlertType.INFORMATION, "Agendamento de Tarefa Diária cadastrada com sucesso!");
        } else {
            String mensagemErro = resultadoAgendamento != null ? resultadoAgendamento.getMsg() : "Erro ao cadastrar uma nova Tarefa Diária!";
            alert = new Alert(AlertType.ERROR, mensagemErro);
        }
        alert.showAndWait();
        App.popScreen();  // Retorna à tela anterior após o cadastro
    }

    @FXML
    void voltar(ActionEvent event) {
        App.pushScreen("MENUPRINCIPAL");
    }

}
