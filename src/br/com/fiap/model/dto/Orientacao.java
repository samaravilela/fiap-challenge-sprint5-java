package br.com.fiap.model.dto;

/**
 * Classe modelo para Orientação (T_EASEHC_ORIENTACAO)
 * Representa orientações e exames recomendados em uma consulta
 */
public class Orientacao {
    private Long idOrientacao;
    private Long idConsulta;
    private String tipoExame;
    private String instrucoesPreparacao;
    private String recomendacoesPosExame;

    // Construtores
    public Orientacao() {}

    public Orientacao(Long idOrientacao, Long idConsulta, String tipoExame, 
                     String instrucoesPreparacao, String recomendacoesPosExame) {
        this.idOrientacao = idOrientacao;
        this.idConsulta = idConsulta;
        this.tipoExame = tipoExame;
        this.instrucoesPreparacao = instrucoesPreparacao;
        this.recomendacoesPosExame = recomendacoesPosExame;
    }

    // Getters e Setters
    public Long getIdOrientacao() {
        return idOrientacao;
    }

    public void setIdOrientacao(Long idOrientacao) {
        this.idOrientacao = idOrientacao;
    }

    public Long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getTipoExame() {
        return tipoExame;
    }

    public void setTipoExame(String tipoExame) {
        this.tipoExame = tipoExame;
    }

    public String getInstrucoesPreparacao() {
        return instrucoesPreparacao;
    }

    public void setInstrucoesPreparacao(String instrucoesPreparacao) {
        this.instrucoesPreparacao = instrucoesPreparacao;
    }

    public String getRecomendacoesPosExame() {
        return recomendacoesPosExame;
    }

    public void setRecomendacoesPosExame(String recomendacoesPosExame) {
        this.recomendacoesPosExame = recomendacoesPosExame;
    }

    @Override
    public String toString() {
        return "Orientacao{" +
                "idOrientacao=" + idOrientacao +
                ", idConsulta=" + idConsulta +
                ", tipoExame='" + tipoExame + '\'' +
                ", instrucoesPreparacao='" + instrucoesPreparacao + '\'' +
                ", recomendacoesPosExame='" + recomendacoesPosExame + '\'' +
                '}';
    }
}

