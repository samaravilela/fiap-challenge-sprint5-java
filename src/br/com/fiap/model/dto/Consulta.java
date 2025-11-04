package br.com.fiap.model.dto;

import java.time.LocalDateTime;

/**
 * Classe modelo para Consulta (T_EASEHC_CONSULTA)
 * Representa uma consulta médica agendada
 */
public class Consulta {
    private Long idConsulta;
    private Long idPaciente;
    private Long idMedico;
    private Long idLocalizacao;
    private Long idEspecialidade;
    private LocalDateTime dataHora;
    private Integer duracaoMinutos;
    private String status; // Agendada, Cancelada, Realizada
    private String observacoes;
    private String prioridade; // Alta, Baixa, Normal

    // Construtores
    public Consulta() {}

    public Consulta(Long idConsulta, Long idPaciente, Long idMedico, Long idLocalizacao,
                   Long idEspecialidade, LocalDateTime dataHora, Integer duracaoMinutos,
                   String status, String observacoes, String prioridade) {
        this.idConsulta = idConsulta;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.idLocalizacao = idLocalizacao;
        this.idEspecialidade = idEspecialidade;
        this.dataHora = dataHora;
        this.duracaoMinutos = duracaoMinutos;
        this.status = status;
        this.observacoes = observacoes;
        this.prioridade = prioridade;
    }

    // Getters e Setters
    public Long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }

    public Long getIdLocalizacao() {
        return idLocalizacao;
    }

    public void setIdLocalizacao(Long idLocalizacao) {
        this.idLocalizacao = idLocalizacao;
    }

    public Long getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(Long idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "idConsulta=" + idConsulta +
                ", idPaciente=" + idPaciente +
                ", idMedico=" + idMedico +
                ", idLocalizacao=" + idLocalizacao +
                ", idEspecialidade=" + idEspecialidade +
                ", dataHora=" + dataHora +
                ", duracaoMinutos=" + duracaoMinutos +
                ", status='" + status + '\'' +
                ", observacoes='" + observacoes + '\'' +
                ", prioridade='" + prioridade + '\'' +
                '}';
    }
}
