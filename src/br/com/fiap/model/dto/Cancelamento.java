package br.com.fiap.model.dto;

import java.time.LocalDateTime;

/**
 * Classe modelo para Cancelamento/Remarcação (T_EASEHC_CANREM)
 * Representa o histórico de cancelamentos e remarcações de consultas
 */
public class Cancelamento {
    private Long idAjuste;
    private Long idConsulta;
    private String tipoAjuste; // Cancelada, Remarcacao
    private String motivoSolicitacao;
    private LocalDateTime novaDataHora;

    // Construtores
    public Cancelamento() {}

    public Cancelamento(Long idAjuste, Long idConsulta, String tipoAjuste, 
                       String motivoSolicitacao, LocalDateTime novaDataHora) {
        this.idAjuste = idAjuste;
        this.idConsulta = idConsulta;
        this.tipoAjuste = tipoAjuste;
        this.motivoSolicitacao = motivoSolicitacao;
        this.novaDataHora = novaDataHora;
    }

    // Getters e Setters
    public Long getIdAjuste() {
        return idAjuste;
    }

    public void setIdAjuste(Long idAjuste) {
        this.idAjuste = idAjuste;
    }

    public Long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getTipoAjuste() {
        return tipoAjuste;
    }

    public void setTipoAjuste(String tipoAjuste) {
        this.tipoAjuste = tipoAjuste;
    }

    public String getMotivoSolicitacao() {
        return motivoSolicitacao;
    }

    public void setMotivoSolicitacao(String motivoSolicitacao) {
        this.motivoSolicitacao = motivoSolicitacao;
    }

    public LocalDateTime getNovaDataHora() {
        return novaDataHora;
    }

    public void setNovaDataHora(LocalDateTime novaDataHora) {
        this.novaDataHora = novaDataHora;
    }

    @Override
    public String toString() {
        return "Cancelamento{" +
                "idAjuste=" + idAjuste +
                ", idConsulta=" + idConsulta +
                ", tipoAjuste='" + tipoAjuste + '\'' +
                ", motivoSolicitacao='" + motivoSolicitacao + '\'' +
                ", novaDataHora=" + novaDataHora +
                '}';
    }
}

