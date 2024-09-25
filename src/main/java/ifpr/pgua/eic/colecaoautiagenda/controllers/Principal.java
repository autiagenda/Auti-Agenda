package ifpr.pgua.eic.colecaoautiagenda.controllers;

import ifpr.pgua.eic.colecaoautiagenda.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Principal {
   
    @FXML
    private Button botaoCadastreSe;

    @FXML
    private Button botaoEntrar;

    @FXML
    void botaoIrParaTelaDeCadastroUsuario(ActionEvent event) {
        App.pushScreen("CADASTROUSUARIO");
    }

    @FXML
    void botaoIrParaTelaDeLogin(ActionEvent event) {
        App.pushScreen("TELALOGIN");

    //botaoIrParaTelaDeCadastroResponsavel era aqui
    }
}



