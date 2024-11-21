package ifpr.pgua.eic.colecaoautiagenda.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaoautiagenda.App;
import ifpr.pgua.eic.colecaoautiagenda.models.Medicamento;
import ifpr.pgua.eic.colecaoautiagenda.models.Terapia;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioMedicamento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class ListarMedicamentos implements Initializable{
   
    @FXML
    private ListView<Medicamento> listaDeAgendamentos;

    private Medicamento opcaoSelecionada;

    private RepositorioMedicamento repositorioMedicamento;

    public ListarMedicamentos(RepositorioMedicamento repositorioMedicamento){
        this.repositorioMedicamento = repositorioMedicamento;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        listaDeAgendamentos.getItems().clear();

        listaDeAgendamentos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    
        Resultado resultado = repositorioMedicamento.listarAgendamentosMedicamento();

        if (resultado.foiErro()) {
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        } else {
            List<Medicamento> lista = (List<Medicamento>) resultado.comoSucesso().getObj();
            listaDeAgendamentos.getItems().addAll(lista);
        }
    }

    private void concluirOuDeletarMedicamento(Medicamento tarefa, String tipoOperacao, Resultado resultadoOperacao) {
        if (tarefa != null) {
            if (resultadoOperacao.foiSucesso()) {
                listaDeAgendamentos.getItems().remove(tarefa);
                String mensagem = tipoOperacao.equals("concluir") ? "Parabéns! Tarefa de Medicamento concluído com sucesso!" : "Ótimo! Lembrete de Medicamento deletado com sucesso!";
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
            Resultado resultado = repositorioMedicamento.concluirMedicamento(opcaoSelecionada.getId());
            concluirOuDeletarMedicamento(opcaoSelecionada, "concluir", resultado);
        } else {
            Alert alert = new Alert(AlertType.WARNING, "Nenhum lembrete foi selecionado, tente novamente!");
            alert.showAndWait();
        }
    }

    @FXML
    private void selecionar() {
        Medicamento itemSelecionado = listaDeAgendamentos.getSelectionModel().getSelectedItem();
        if (itemSelecionado != null) {
            opcaoSelecionada = itemSelecionado;
        }
    }

    @FXML
    void deletarAgendamento(ActionEvent event) {
        opcaoSelecionada = listaDeAgendamentos.getSelectionModel().getSelectedItem();

        if (opcaoSelecionada != null) {
            Resultado resultado = repositorioMedicamento.deletarMedicamento(opcaoSelecionada.getId());

            if (resultado.foiSucesso()) {
                listaDeAgendamentos.getItems().remove(opcaoSelecionada);
                Alert alert = new Alert(AlertType.INFORMATION, "Ótimo! Lembrete de Medicamento deletado com sucesso!");
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
            App.pushScreen("AGENDAMENTOMEDICAMENTO", o -> new AgendamentoMedicamento(repositorioMedicamento, opcaoSelecionada));
        } else {
            Alert alert = new Alert(AlertType.WARNING, "Nenhum agendamento selecionado...");
            alert.showAndWait();
        }
    }

    
    @FXML
    void recarregarLista(ActionEvent event) {
        listaDeAgendamentos.getItems().clear();

        Resultado resultadoLista = repositorioMedicamento.listarAgendamentosMedicamento();

        if (resultadoLista.foiSucesso()) {
           List<Medicamento> listaRecarregada = (List<Medicamento>) resultadoLista.comoSucesso().getObj();
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


