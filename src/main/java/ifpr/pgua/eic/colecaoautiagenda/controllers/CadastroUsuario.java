package ifpr.pgua.eic.colecaoautiagenda.controllers;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaoautiagenda.App;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastroUsuario {
    @FXML
    private TextField labelEmailUsuario;

    @FXML
    private TextField labelNomeUsuario;

    @FXML
    private TextField labelSenhaUsuario;

    private RepositorioUsuario repositorio;

    public CadastroUsuario(RepositorioUsuario repositorio) {
        this.repositorio = repositorio;
    }

    private boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";  
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    @FXML
    void botaoCadastreSeUsuario(ActionEvent event) {
        String nome = labelNomeUsuario.getText();
        String email = labelEmailUsuario.getText();
        String senha = labelSenhaUsuario.getText();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            new Alert(AlertType.ERROR, "Por favor, preencha todos os campos!").showAndWait();
            return;
        }

        if (!validarEmail(email)) {
            new Alert(AlertType.ERROR, "O e-mail inserido é invalido... Tente novamente").showAndWait();
            return;
        }

        if (senha.length() < 6) {
            new Alert(AlertType.ERROR, "A senha deve ter no mínimo 6 caracteres! Tente novamente").showAndWait();
            return;
        }
        
        Resultado resultadoCadastro = repositorio.cadastrarUsuario(nome, email, senha);

        Alert alert;
        if (resultadoCadastro != null && resultadoCadastro.foiSucesso()) {
            Resultado resultadoAutenticacao = repositorio.buscarUsuario(email, senha);

            if (resultadoAutenticacao != null && resultadoAutenticacao.foiSucesso()) {
                alert = new Alert(AlertType.INFORMATION, "Usuário cadastrado com sucesso!");
            } else {
                alert = new Alert(AlertType.ERROR, "Erro após o cadastro! Tente novamente");
            }
        } else {
            String mensagemErro = resultadoCadastro != null ? resultadoCadastro.getMsg() : "Erro ao cadastrar um novo usuário!";
            alert = new Alert(AlertType.ERROR, mensagemErro);
        }
        App.popScreen();  
        alert.showAndWait();
    }

    @FXML
    void radioButtonNao(ActionEvent event) {
        //Não realiza nenhuma ação mesmo...
    }

    @FXML
    void radioButtonSim(ActionEvent event) {
        App.pushScreen("CADASTRORESPONSAVEL");  //arrumar para pegar o id do responsável também além do cadastro simples
    }
}
