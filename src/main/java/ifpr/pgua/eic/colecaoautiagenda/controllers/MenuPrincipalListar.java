package ifpr.pgua.eic.colecaoautiagenda.controllers;

import ifpr.pgua.eic.colecaoautiagenda.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MenuPrincipalListar {
    
    @FXML
    void botaoListarMedicamentos(ActionEvent event) {

    }

    @FXML
    void botaoListarRotinaDiaria(ActionEvent event) {

    }

    @FXML
    void botaoListarTerapias(ActionEvent event) {

    }

    @FXML
    void abreTelaPricipalMenu(ActionEvent event) {
        App.pushScreen("MENUPRINCIPAL");
    }

    @FXML
    void sair(ActionEvent event) {
        Platform.exit();
  }
}
