package br.com.fiap.model.dto;

import java.time.LocalDate;

/**
 * Classe modelo para Histórico Médico (T_EASEHC_HISTORICO)
 * Representa o histórico médico de um paciente
 */
public class HistoricoMedico {
    private Long idHistorico;
    private Long idPaciente;
    private String diagnostico;
    private String tratamento;
    private String medicacao;
    private String observacoes;
    private LocalDate dataAcesso;

    // Construtores
    public HistoricoMedico() {}

    public HistoricoMedico(Long idHistorico, Long idPaciente, String diagnostico, 
                          String tratamento, String medicacao, String observacoes, 
                          LocalDate dataAcesso) {
        this.idHistorico = idHistorico;
        this.idPaciente = idPaciente;
        this.diagnostico = diagnostico;
        this.tratamento = tratamento;
        this.medicacao = medicacao;
        this.observacoes = observacoes;
        this.dataAcesso = dataAcesso;
    }

    // Getters e Setters
    public Long getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(Long idHistorico) {
        this.idHistorico = idHistorico;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamento() {
        return tratamento;
    }

    public void setTratamento(String tratamento) {
        this.tratamento = tratamento;
    }

    public String getMedicacao() {
        return medicacao;
    }

    public void setMedicacao(String medicacao) {
        this.medicacao = medicacao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public LocalDate getDataAcesso() {
        return dataAcesso;
    }

    public void setDataAcesso(LocalDate dataAcesso) {
        this.dataAcesso = dataAcesso;
    }

    @Override
    public String toString() {
        return "HistoricoMedico{" +
                "idHistorico=" + idHistorico +
                ", idPaciente=" + idPaciente +
                ", diagnostico='" + diagnostico + '\'' +
                ", tratamento='" + tratamento + '\'' +
                ", medicacao='" + medicacao + '\'' +
                ", observacoes='" + observacoes + '\'' +
                ", dataAcesso=" + dataAcesso +
                '}';
    }
}

