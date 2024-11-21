package ifpr.pgua.eic.colecaoautiagenda.controllers;

import java.io.File;
import java.time.LocalDate;
import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.colecaoautiagenda.App;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioMedicamento;
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
    private Button foto; 

    private String caminhoFotoSelecionada;

    private RepositorioMedicamento repositorioMedicamento;

    public AgendamentoMedicamento(RepositorioMedicamento repositorioMedicamento) {
        this.repositorioMedicamento = repositorioMedicamento;
    }

    @FXML
    void botaoAgendarMedicamento(ActionEvent event) {
        String titulo = labelTitulo.getText();
        LocalDate data = labelData.getValue();
        String horario = labelHorario.getText();
        String detalhes = labelDetalhes.getText();
    
        if (titulo.isEmpty() || data == null || horario.isEmpty() || detalhes.isEmpty() || caminhoFotoSelecionada == null) {
            new Alert(AlertType.ERROR, "Por favor, preencha todos os campos!").showAndWait();
            return;
        }
    
        Resultado resultadoAgendamento = repositorioMedicamento.agendarMedicamento(titulo, data, horario, detalhes, caminhoFotoSelecionada);
    
        Alert alert;
        if (resultadoAgendamento != null && resultadoAgendamento.foiSucesso()) {
            alert = new Alert(AlertType.INFORMATION, "Agendamento de Medicamento cadastrado com sucesso!");
        } else {
            String mensagemErro = resultadoAgendamento != null ? resultadoAgendamento.getMsg() : "Erro ao agendar uma nova Medicamento!";
            alert = new Alert(AlertType.ERROR, mensagemErro);
        }
        alert.showAndWait();
        App.popScreen();
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
    void voltar(ActionEvent event) {
        App.pushScreen("MENUPRINCIPAL");
    }
}
