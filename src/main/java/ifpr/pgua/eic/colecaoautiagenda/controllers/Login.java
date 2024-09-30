package ifpr.pgua.eic.colecaoautiagenda.controllers;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaoautiagenda.App;
import ifpr.pgua.eic.colecaoautiagenda.models.Usuario;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login {
    @FXML
    private TextField labelEmail;

    @FXML
    private PasswordField labelSenha;

    private RepositorioUsuario repositorio;

    public Login(RepositorioUsuario repositorio) {
        this.repositorio = repositorio;
    }

    @FXML
    void botaoRealizarLogin(ActionEvent event) {
        String email = labelEmail.getText();
        String senha = labelSenha.getText();
    
        if (email.isEmpty() || email.isBlank() || senha.isEmpty() || senha.isBlank()) {
            exibirAlerta("ERRO", "Por favor, preencha usuário e senha!");
        } else {
            Resultado resultadoBusca = repositorio.buscarUsuario(email, senha);
    
            if (resultadoBusca != null && resultadoBusca.foiSucesso()) {
                Usuario usuarioAutenticado = (Usuario) resultadoBusca.comoSucesso().getObj();
                App.pushScreen("MENUPRINCIPAL");
            } else {
                exibirAlerta("ERRO", resultadoBusca != null ? resultadoBusca.getMsg() : "Ocorreu um erro na busca do usuário...");
            }
        }  //Chamar a tela de menu principal
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
