package br.com.fiap.model.dto;

import java.time.LocalDate;

/**
 * Classe modelo para Paciente (T_EASEHC_PACIENTE)
 * Representa um paciente do sistema de saúde
 */
public class Paciente {
    private Long idPaciente;
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String genero; // F, M, O
    private String telefone;
    private String tipoSanguineo; // A+, A-, AB+, AB-, B+, B-, O+, O-
    private String alergias;

    // Construtores
    public Paciente() {}

    public Paciente(Long idPaciente, String nomeCompleto, LocalDate dataNascimento, 
                    String genero, String telefone, String tipoSanguineo, String alergias) {
        this.idPaciente = idPaciente;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.telefone = telefone;
        this.tipoSanguineo = tipoSanguineo;
        this.alergias = alergias;
    }

    // Getters e Setters
    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "idPaciente=" + idPaciente +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", genero='" + genero + '\'' +
                ", telefone='" + telefone + '\'' +
                ", tipoSanguineo='" + tipoSanguineo + '\'' +
                ", alergias='" + alergias + '\'' +
                '}';
    }
}
