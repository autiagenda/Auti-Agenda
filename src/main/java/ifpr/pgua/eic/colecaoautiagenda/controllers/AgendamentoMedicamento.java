package ifpr.pgua.eic.colecaoautiagenda.controllers;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.colecaoautiagenda.App;
import ifpr.pgua.eic.colecaoautiagenda.models.Medicamento;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioMedicamento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.FileChooser;

public class AgendamentoMedicamento implements Initializable{
    
    @FXML
    private DatePicker labelData;

    @FXML
    private TextField labelDetalhes;

    @FXML
    private TextField labelHorario;

    @FXML
    private TextField labelTitulo;

    @FXML
    private Button foto; 

    private String caminhoFotoSelecionada;

    @FXML
    private Button btatualizar;

    private Medicamento anterior;

    @FXML
    private ToggleButton dia;

    @FXML
    private ToggleButton noite;

    private String periodo;

    private boolean agendamentoFeito = false; 

    private RepositorioMedicamento repositorioMedicamento;

    public AgendamentoMedicamento(RepositorioMedicamento repositorioMedicamento) {
        this.repositorioMedicamento = repositorioMedicamento;
    }

    public AgendamentoMedicamento(RepositorioMedicamento repositorioMedicamento, Medicamento anterior){
        this.repositorioMedicamento = repositorioMedicamento;
        this.anterior = anterior;
    }

    @FXML
    void botaoAgendarMedicamento(ActionEvent event) {
        String titulo = labelTitulo.getText();
        LocalDate data = labelData.getValue();
        String horario = labelHorario.getText();
        String detalhes = labelDetalhes.getText();
    
        if (titulo.isEmpty() || data == null || horario.isEmpty()) {
            new Alert(AlertType.ERROR, "Por favor, preencha todos os campos!").showAndWait();
            return;
        }
    
        Resultado resultado;
    
        if (anterior == null) {
            resultado = repositorioMedicamento.agendarMedicamento(titulo, data, horario, detalhes, caminhoFotoSelecionada, periodo);
            agendamentoFeito = true;
        } else {
            resultado = repositorioMedicamento.editarAgendamentoMedicamento(anterior.getId(), titulo, data, horario, detalhes, caminhoFotoSelecionada, periodo);
            agendamentoFeito = false;
        }
    
        Alert alert;
        if (resultado != null && resultado.foiSucesso()) {
            alert = new Alert(AlertType.INFORMATION, "Agendamento de Medicamento cadastrado com sucesso!");
            limparCampos();
        } else {
            String mensagemErro = resultado != null ? resultado.getMsg() : "Erro ao cadastrar um novo Medicamento!";
            alert = new Alert(AlertType.ERROR, mensagemErro);
        }
        alert.showAndWait();
    }
    
    @FXML
    void botaoTirarFoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione uma foto");
    
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg"),
            new FileChooser.ExtensionFilter("Todos os Arquivos", "*.*")
        );
    
        File selectedFile = fileChooser.showOpenDialog(foto.getScene().getWindow());
    
        if (selectedFile != null) {
            caminhoFotoSelecionada = selectedFile.getAbsolutePath();
    
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Foto Selecionada");
            alert.setHeaderText("Foto carregada com sucesso!");
            alert.setContentText("Caminho: " + caminhoFotoSelecionada);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhum arquivo foi selecionado.");
            alert.setContentText("Por favor, tente novamente.");
            alert.showAndWait();
        }
    }

    @FXML
    private void limparCampos() {
        labelTitulo.clear();       
        labelData.setValue(null);  
        labelHorario.clear();     
        labelDetalhes.clear();     
        caminhoFotoSelecionada = null; 
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
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
    void botaoDia(ActionEvent event) {
        periodo = "Dia"; 
        dia.setSelected(true); 
        noite.setSelected(false);
    }

    @FXML
    void botaoNoite(ActionEvent event) {
        periodo = "Noite"; 
        noite.setSelected(true); 
        dia.setSelected(false);
    }

    @FXML
    void voltar(ActionEvent event) {
        if (agendamentoFeito) {
            App.pushScreen("MENUPRINCIPAL"); 
        } else {
            App.pushScreen("LISTARMEDICAMENTOS");  
        }
    }
}
