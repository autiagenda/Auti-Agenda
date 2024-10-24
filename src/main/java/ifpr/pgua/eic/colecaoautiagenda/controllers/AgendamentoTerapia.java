package ifpr.pgua.eic.colecaoautiagenda.controllers;

import java.time.LocalDate;

import com.github.hugoperlin.results.Resultado;

import java.net.URL;
import java.util.ResourceBundle;
import ifpr.pgua.eic.colecaoautiagenda.App;
import ifpr.pgua.eic.colecaoautiagenda.models.Terapia;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioTerapia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AgendamentoTerapia implements Initializable{
  
    @FXML
    private DatePicker labelData;

    @FXML
    private TextField labelDetalhes;

    @FXML
    private TextField labelHorario;

    @FXML
    private TextField labelTitulo;

    @FXML
    private Button btatualizar;

    private Terapia anterior;

    private RepositorioTerapia repositorioTerapia;

    public AgendamentoTerapia(RepositorioTerapia repositorioTerapia) {
        this.repositorioTerapia = repositorioTerapia;
    }

    public AgendamentoTerapia(RepositorioTerapia repositorioTerapia, Terapia anterior){
        this.repositorioTerapia = repositorioTerapia;
        this.anterior = anterior;
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

        Resultado resultado;
    
        if (anterior == null) {
            resultado = repositorioTerapia.agendarTerapia(titulo, data, horario, detalhes);
        } else {
            resultado = repositorioTerapia.editarAgendamentoTerapia(anterior.getId(), titulo, data, horario, detalhes);
        }
    
        Alert alert;
        if (resultado != null && resultado.foiSucesso()) {
            alert = new Alert(AlertType.INFORMATION, "Agendamento de Terapia cadastrado com sucesso!");
        } else {
            String mensagemErro = resultado != null ? resultado.getMsg() : "Erro ao cadastrar um novo agendamento de Terapia...";
            alert = new Alert(AlertType.ERROR, mensagemErro);
        }
        alert.showAndWait();
        App.popScreen();  // Retorna à tela anterior após o cadastro, arrumar esta parte e verificar sobre data (coluna diz que está faltante)
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        if (anterior != null) {
            labelTitulo.setText(anterior.getTitulo());
            labelData.setValue(anterior.getData());
            labelHorario.setText(anterior.getHorario());
            labelDetalhes.setText(anterior.getDetalhes());

            btatualizar.setText("Atualizar");
        }
    }

    @FXML
    void voltar(ActionEvent event) {
        App.pushScreen("MENUPRINCIPAL");
    }
}
