package ifpr.pgua.eic.colecaoautiagenda.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaoautiagenda.App;
import ifpr.pgua.eic.colecaoautiagenda.models.TarefaDiaria;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioTarefaDiaria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class ListarTarefasDiarias implements Initializable{
    
    @FXML
    private ListView<TarefaDiaria> listaDeAgendamentos;

    private TarefaDiaria opcaoSelecionada;

    private RepositorioTarefaDiaria repositorioTarefaDiaria;
    
    public ListarTarefasDiarias(RepositorioTarefaDiaria repositorioTarefaDiaria) {
        this.repositorioTarefaDiaria = repositorioTarefaDiaria;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        listaDeAgendamentos.getItems().clear();

        listaDeAgendamentos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    
        Resultado resultado = repositorioTarefaDiaria.listarAgendamentosTarefaDiaria();

        if (resultado.foiErro()) {
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        } else {
            List<TarefaDiaria> lista = (List<TarefaDiaria>) resultado.comoSucesso().getObj();
            listaDeAgendamentos.getItems().addAll(lista);
        }
    }

    private void concluirOuDeletarTarefaDiaria(TarefaDiaria tarefa, String tipoOperacao, Resultado resultadoOperacao) {
        if (tarefa != null) {
            if (resultadoOperacao.foiSucesso()) {
                listaDeAgendamentos.getItems().remove(tarefa);
                String mensagem = tipoOperacao.equals("concluir") ? "Parabéns! Tarefa Diária concluída com sucesso!" : "Ótimo! Lembrete de Tarefa Diária deletado com sucesso!";
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
            Resultado resultado = repositorioTarefaDiaria.concluirTarefaDiaria(opcaoSelecionada.getId());
            concluirOuDeletarTarefaDiaria(opcaoSelecionada, "concluir", resultado);
        } else {
            Alert alert = new Alert(AlertType.WARNING, "Nenhum lembrete foi selecionado, tente novamente!");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void selecionar() {
        TarefaDiaria itemSelecionado = listaDeAgendamentos.getSelectionModel().getSelectedItem();
        if (itemSelecionado != null) {
            opcaoSelecionada = itemSelecionado;
        }
    }

    @FXML
    void deletarAgendamento(ActionEvent event) {
        opcaoSelecionada = listaDeAgendamentos.getSelectionModel().getSelectedItem();
    
        if (opcaoSelecionada != null) {
            Resultado resultado = repositorioTarefaDiaria.deletarTarefaDiaria(opcaoSelecionada.getId());
            concluirOuDeletarTarefaDiaria(opcaoSelecionada, "deletar", resultado);
        } else {
            Alert alert = new Alert(AlertType.WARNING, "Nenhum lembrete foi selecionado, tente novamente!");
            alert.showAndWait();
        }
    }

    @FXML
    void editarAgendamento(ActionEvent event) {
        opcaoSelecionada = listaDeAgendamentos.getSelectionModel().getSelectedItem();
        
        if (opcaoSelecionada != null) {
            App.pushScreen("AGENDAMENTOROTINADIARIA", o -> new AgendamentoTarefaDiaria(repositorioTarefaDiaria, opcaoSelecionada));
        } else {
            Alert alert = new Alert(AlertType.WARNING, "Nenhum agendamento selecionado...");
            alert.showAndWait();
        }
    }

    @FXML
    void recarregarLista(ActionEvent event) {
        listaDeAgendamentos.getItems().clear();

        Resultado resultadoLista = repositorioTarefaDiaria.listarAgendamentosTarefaDiaria();

        if (resultadoLista.foiSucesso()) {
           List<TarefaDiaria> listaRecarregada = (List<TarefaDiaria>) resultadoLista.comoSucesso().getObj();
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
