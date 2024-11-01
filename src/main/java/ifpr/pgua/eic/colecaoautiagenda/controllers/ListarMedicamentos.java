package ifpr.pgua.eic.colecaoautiagenda.controllers;

import ifpr.pgua.eic.colecaoautiagenda.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ListarMedicamentos {
   
    @FXML
    private ListView<?> listaDeAgendamentos;

    @FXML
    void concluirAgendamento(ActionEvent event) {

    }

    @FXML
    void deletarAgendamento(ActionEvent event) {

    }

    @FXML
    void editarAgendamento(ActionEvent event) {
        App.pushScreen("ATUALIZARMEDICAMENTO");
    }

    
    @FXML
    void recarregarLista(ActionEvent event) {

    }

    @FXML
    void voltar(ActionEvent event) {
        App.pushScreen("MENUPRINCIPALLISTAR");
    }
}
