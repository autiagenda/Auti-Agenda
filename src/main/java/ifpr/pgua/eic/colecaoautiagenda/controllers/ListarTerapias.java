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

    private Terapia opcaoSelecionada;

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
            List<Terapia> lista = (List<Terapia>) resultado.comoSucesso().getObj();
            listaDeAgendamentos.getItems().addAll(lista);
        }
    }

    private void concluirOuDeletarTerapia(Terapia tarefa, String tipoOperacao, Resultado resultadoOperacao) {
        if (tarefa != null) {
            if (resultadoOperacao.foiSucesso()) {
                listaDeAgendamentos.getItems().remove(tarefa);
                String mensagem = tipoOperacao.equals("concluir") ? "Parabéns! Terapia concluída com sucesso!" : "Ótimo! Lembrete de Terapia deletado com sucesso!";
                Alert alert = new Alert(AlertType.INFORMATION, mensagem);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.ERROR, resultadoOperacao.getMsg());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING, "Nenhum lembrete foi selecionado, tente novamente!");
            alert.showAndWait();
        }
    }

    @FXML
    void concluirAgendamento(ActionEvent event) {
        opcaoSelecionada = listaDeAgendamentos.getSelectionModel().getSelectedItem();
    
        if (opcaoSelecionada != null) {
            Resultado resultado = repositorioTerapia.concluirTerapia(opcaoSelecionada.getId());
            concluirOuDeletarTerapia(opcaoSelecionada, "concluir", resultado);
        } else {
            Alert alert = new Alert(AlertType.WARNING, "Nenhum lembrete foi selecionado, tente novamente!");
            alert.showAndWait();
        }
    }

    @FXML
    private void selecionar() {
        Terapia itemSelecionado = listaDeAgendamentos.getSelectionModel().getSelectedItem();
        if (itemSelecionado != null) {
            opcaoSelecionada = itemSelecionado;
        }
    }

    @FXML
    void deletarAgendamento(ActionEvent event) {
        opcaoSelecionada = listaDeAgendamentos.getSelectionModel().getSelectedItem();

        if (opcaoSelecionada != null) {
            Resultado resultado = repositorioTerapia.deletarTerapia(opcaoSelecionada.getId());

            if (resultado.foiSucesso()) {
                listaDeAgendamentos.getItems().remove(opcaoSelecionada);
                Alert alert = new Alert(AlertType.INFORMATION, "Ótimo! Lembrete de Terapia deletada com sucesso!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING, "Nenhum lembrete foi selecionado, tente novamente!");
            alert.showAndWait();
        }
    }

    @FXML
    void editarAgendamento(ActionEvent event) {
        opcaoSelecionada = listaDeAgendamentos.getSelectionModel().getSelectedItem();
        
        if (opcaoSelecionada != null) {
            App.pushScreen("AGENDAMENTOTERAPIA", o -> new AgendamentoTerapia(repositorioTerapia, opcaoSelecionada));
        } else {
            Alert alert = new Alert(AlertType.WARNING, "Nenhum agendamento selecionado...");
            alert.showAndWait();
        }
    }

    @FXML
    void recarregarLista(ActionEvent event) {
        listaDeAgendamentos.getItems().clear();

        Resultado resultadoLista = repositorioTerapia.listarAgendamentosTerapia();

        if (resultadoLista.foiSucesso()) {
           List<Terapia> listaRecarregada = (List<Terapia>) resultadoLista.comoSucesso().getObj();
           listaDeAgendamentos.getItems().addAll(listaRecarregada); 
        }else{
            Alert alert = new Alert(AlertType.ERROR, resultadoLista.getMsg());
            alert.showAndWait();
        }
    }

    @FXML
    void voltar(ActionEvent event) {
        App.pushScreen("MENUPRINCIPALLISTAR");
    }

}
