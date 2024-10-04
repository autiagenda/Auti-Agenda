package ifpr.pgua.eic.colecaoautiagenda.controllers;

import ifpr.pgua.eic.colecaoautiagenda.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ListarTerapias {
   
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
        App.pushScreen("ATUALIZARTERAPIA");
    }

    @FXML
    void voltar(ActionEvent event) {
        App.pushScreen("MENUPRINCIPALLISTAR");
    }

}
