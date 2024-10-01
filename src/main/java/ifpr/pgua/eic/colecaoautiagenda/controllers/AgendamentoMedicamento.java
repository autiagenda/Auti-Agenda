package ifpr.pgua.eic.colecaoautiagenda.controllers;

import java.io.File;
import java.time.LocalDate;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaoautiagenda.App;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioMedicamento;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioTarefaDiaria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class AgendamentoMedicamento {
    
    @FXML
    private DatePicker labelData;

    @FXML
    private TextField labelDetalhes;

    @FXML
    private TextField labelHorario;

    @FXML
    private TextField labelTitulo;

    @FXML
    private Button foto; //Verificar se devo colocar como label

    private RepositorioMedicamento repositorioMedicamento;

    public AgendamentoMedicamento(RepositorioMedicamento repositorioMedicamento) {
        this.repositorioMedicamento = repositorioMedicamento;
    }

    @FXML
    void botaoAgendarMedicamento(ActionEvent event) {
      /*   String titulo = labelTitulo.getText();
        LocalDate data = labelData.getValue();
        String horario = labelHorario.getText();
        String detalhes = labelDetalhes.getText();
       // String foto = foto.getText(); 

        if (titulo.isEmpty() || data == null || horario.isEmpty() || detalhes.isEmpty()) {
            new Alert(AlertType.ERROR, "Por favor, preencha todos os campos!").showAndWait();
            return;
        }

        Resultado resultadoAgendamento = repositorioMedicamento.agendarMedicamento(titulo, data, horario, detalhes, foto);

        Alert alert;
        if (resultadoAgendamento != null && resultadoAgendamento.foiSucesso()) {
            alert = new Alert(AlertType.INFORMATION, "Agendamento de Medicamento cadastrado com sucesso!");
        } else {
            String mensagemErro = resultadoAgendamento != null ? resultadoAgendamento.getMsg() : "Erro ao agendar uma nova Medicamento!";
            alert = new Alert(AlertType.ERROR, mensagemErro);
        }
        alert.showAndWait();
        App.popScreen();  
   */ }

    @FXML
    void botaoTirarFoto(ActionEvent event) {
       // Verificar como pegar arquivos
    }

    @FXML
    void voltar(ActionEvent event) {
        App.pushScreen("MENUPRINCIPAL");
    }
}
