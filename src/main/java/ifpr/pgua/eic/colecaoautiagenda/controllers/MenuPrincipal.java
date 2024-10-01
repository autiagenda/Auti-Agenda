package ifpr.pgua.eic.colecaoautiagenda.controllers;

import ifpr.pgua.eic.colecaoautiagenda.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.application.Platform;

public class MenuPrincipal {
    
  @FXML
  void botaoMedicamentos1(ActionEvent event) {
    App.pushScreen("AGENDAMENTOMEDICAMENTO");
  }

  @FXML
  void botaoMedicamentos2(ActionEvent event) {
    App.pushScreen("AGENDAMENTOMEDICAMENTO");
  }

  @FXML
  void botaoRotinaDiaria1(ActionEvent event) {
    App.pushScreen("AGENDAMENTOROTINADIARIA");
  }

  @FXML
  void botaoRotinaDiaria2(ActionEvent event) {
    App.pushScreen("AGENDAMENTOROTINADIARIA");
  }

  @FXML
  void botaoTerapias1(ActionEvent event) {
    App.pushScreen("AGENDAMENTOTERAPIA");
  }

  @FXML
  void botaoTerapias2(ActionEvent event) {
    App.pushScreen("AGENDAMENTOTERAPIA");
  }

  @FXML
  void abrirTelaPrincipalListar(ActionEvent event) {
    App.pushScreen("MENUPRINCIPALLISTAR");
  }

  @FXML
  void sair(ActionEvent event) {
    Platform.exit();
  }
}
