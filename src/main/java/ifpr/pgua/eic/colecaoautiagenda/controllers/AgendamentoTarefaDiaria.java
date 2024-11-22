package ifpr.pgua.eic.colecaoautiagenda.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaoautiagenda.App;
import ifpr.pgua.eic.colecaoautiagenda.models.TarefaDiaria;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioTarefaDiaria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AgendamentoTarefaDiaria implements Initializable{
    
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

    private TarefaDiaria anterior;

    private boolean agendamentoFeito = false; 

    private RepositorioTarefaDiaria repositorioTarefaDiaria;

    public AgendamentoTarefaDiaria(RepositorioTarefaDiaria repositorioTarefaDiaria) {
        this.repositorioTarefaDiaria = repositorioTarefaDiaria;
    }

    public AgendamentoTarefaDiaria(RepositorioTarefaDiaria repositorioTarefaDiaria, TarefaDiaria anterior){
        this.repositorioTarefaDiaria = repositorioTarefaDiaria;
        this.anterior = anterior;
    }

    @FXML
    void botaoAgendarRotinaDiaria(ActionEvent event) {
        String titulo = labelTitulo.getText();
        LocalDate data = labelData.getValue();
        String horario = labelHorario.getText();
        String detalhes = labelDetalhes.getText(); 
        
        if (titulo.isEmpty() || data == null || horario.isEmpty()) {
            new Alert(AlertType.ERROR, "Por favor, preencha os campos!").showAndWait();
            return;
        }
    
        Resultado resultado;
    
        if (anterior == null) {
            resultado = repositorioTarefaDiaria.agendarTarefaDiaria(titulo, data, horario, detalhes);
            agendamentoFeito = true;
        } else {
            resultado = repositorioTarefaDiaria.editarAgendamentoTarefaDiaria(anterior.getId(), titulo, data, horario, detalhes);
            agendamentoFeito = false;
        }
    
        Alert alert;
        if (resultado != null && resultado.foiSucesso()) {
            alert = new Alert(AlertType.INFORMATION, "Agendamento de Tarefa Diária cadastrado com sucesso!");
            limparCampos();
        } else {
            String mensagemErro = resultado != null ? resultado.getMsg() : "Erro ao cadastrar uma nova Tarefa Diária!";
            alert = new Alert(AlertType.ERROR, mensagemErro);
        }
        alert.showAndWait();
    }
    

    @FXML
    private void limparCampos() {
        labelTitulo.clear();       
        labelData.setValue(null);  
        labelHorario.clear();     
        labelDetalhes.clear();     
    }
    
    @Override
    public void initialize(URL url, ResourceBundle arg1) {
        if (anterior != null) {
            labelTitulo.setText(anterior.getTitulo());
            labelData.setValue(anterior.getData());
            labelHorario.setText(anterior.getHorario());
            labelDetalhes.setText(anterior.getDetalhes());
    
            btatualizar.setText("Atualizar");
        } else {
            limparCampos();
        }
    
        labelHorario.textProperty().addListener((campoHora, horaAnterior, horaAtual) -> {
            if (!horaAtual.matches("\\d{0,2}:?\\d{0,2}")) {
                labelHorario.setText(horaAnterior);
                return;
            }

            if (horaAtual.length() == 2 && !horaAtual.contains(":")) {
                labelHorario.setText(horaAtual + ":");
            }

            if (horaAtual.length() > 5) {
                labelHorario.setText(horaAnterior);
            }
        });

        labelHorario.focusedProperty().addListener((campoHora, horaAnterior, horaAtual) -> {
            if (!horaAtual) { 
                String input = labelHorario.getText();

                if (input.matches("^\\d{2}:\\d{2}$") || input.isEmpty()) {
                    return;
                }

                if (input.matches("^\\d{1,2}$")) {
                    labelHorario.setText(String.format("%02d:00", Integer.parseInt(input)));
                }

                if (input.matches("^\\d{2}:$")) {
                    labelHorario.setText(input + "00");
                }

                if (input.matches("^\\d{2}:\\d{1}$")) {
                    labelHorario.setText(input + "0");
                }
            }
        });
    }
    
    @FXML
    void voltar(ActionEvent event) {
        if (agendamentoFeito) {
            App.pushScreen("MENUPRINCIPAL"); 
        } else {
            App.pushScreen("LISTARROTINADIARIA");  
        }
    }
}
