package ifpr.pgua.eic.colecaoautiagenda.controllers;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaoautiagenda.App;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioResponsavel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class CadastroResponsavel {
    
    @FXML
    private TextField labelEmail;

    @FXML
    private TextField labelNomeResponsavel;

    private RepositorioResponsavel repositorio;

    public CadastroResponsavel(RepositorioResponsavel repositorio) {
        this.repositorio = repositorio;
    }

    @FXML
    void botaoCadastarResponsavel(ActionEvent event) {
        String nome = labelNomeResponsavel.getText();
        String email = labelEmail.getText();

        if (nome.isEmpty() || email.isEmpty()) {
            new Alert(AlertType.ERROR, "Por favor, preencha todos os campos!").showAndWait();
            return;
        }

        Resultado resultadoCadastro = repositorio.cadastrarResponsavel(nome, email);

        Alert alert;
        if (resultadoCadastro != null && resultadoCadastro.foiSucesso()) {
            Resultado resultadoAutenticacao = repositorio.buscarResponsavel(nome, email);

            if (resultadoAutenticacao != null && resultadoAutenticacao.foiSucesso()) {
                alert = new Alert(AlertType.INFORMATION, "Responsável cadastrado com sucesso!");
            } else {
                alert = new Alert(AlertType.ERROR, "Erro após o cadastro! Tente novamente");
            }
        } else {
            String mensagemErro = resultadoCadastro != null ? resultadoCadastro.getMsg() : "Erro ao cadastrar um novo responsável!";
            alert = new Alert(AlertType.ERROR, mensagemErro);
        }
        App.popScreen();  
        alert.showAndWait();
    }
}
