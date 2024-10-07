package ifpr.pgua.eic.colecaoautiagenda.controllers;

import com.github.hugoperlin.results.Resultado;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

import ifpr.pgua.eic.colecaoautiagenda.App;
import ifpr.pgua.eic.colecaoautiagenda.models.Terapia;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioTerapia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class ListarTerapias implements Initializable{
   
    @FXML
    private ListView<Terapia> listaDeAgendamentos;

    private RepositorioTerapia repositorioTerapia;
    
    public ListarTerapias(RepositorioTerapia repositorioTerapia) {
        this.repositorioTerapia = repositorioTerapia;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        listaDeAgendamentos.getItems().clear();

        listaDeAgendamentos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Resultado resultado = repositorioTerapia.listarAgendamentosTerapia();
        if (resultado.foiErro()) {
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        } else {
            List lista = (List) resultado.comoSucesso().getObj();
            listaDeAgendamentos.getItems().addAll(lista);
        }
    }

    @FXML
    void concluirAgendamento(ActionEvent event) {

    }

    @FXML
    void deletarAgendamento(ActionEvent event) {

    }

    @FXML
    void editarAgendamento(ActionEvent event) {
        App.pushScreen("ATUALIZARTERAPIA");
    }

    @FXML
    void voltar(ActionEvent event) {
        App.pushScreen("MENUPRINCIPALLISTAR");
    }

}
