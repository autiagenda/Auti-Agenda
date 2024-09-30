package ifpr.pgua.eic.colecaoautiagenda.models;

import java.time.LocalDate;

public class Terapia {
    private int id;
    private String titulo;
    private LocalDate data;
    private String horario;
    private String detalhes;

    public Terapia(String titulo, LocalDate data, String horario, String detalhes) {
        this.titulo = titulo;
        this.data = data;
        this.horario = horario;
        this.detalhes = detalhes;
    }

    public Terapia(int id, String titulo, LocalDate data, String horario, String detalhes) {
        this.id = id;
        this.titulo = titulo;
        this.data = data;
        this.horario = horario;
        this.detalhes = detalhes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDetalhes() {
        return detalhes;
    }
    
    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }
}
