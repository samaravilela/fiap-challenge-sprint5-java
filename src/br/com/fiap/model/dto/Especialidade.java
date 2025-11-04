package br.com.fiap.model.dto;

/**
 * Classe modelo para Especialidade (T_EASEHC_ESPECIALIDADE)
 * Representa uma especialidade médica
 */
public class Especialidade {
    private Long idEspecialidade;
    private String nomeEspecialidade;
    private String areaMedica;
    private Integer tempoMedioConsulta; // em minutos

    // Construtores
    public Especialidade() {}

    public Especialidade(Long idEspecialidade, String nomeEspecialidade, 
                        String areaMedica, Integer tempoMedioConsulta) {
        this.idEspecialidade = idEspecialidade;
        this.nomeEspecialidade = nomeEspecialidade;
        this.areaMedica = areaMedica;
        this.tempoMedioConsulta = tempoMedioConsulta;
    }

    // Getters e Setters
    public Long getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(Long idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }

    public String getNomeEspecialidade() {
        return nomeEspecialidade;
    }

    public void setNomeEspecialidade(String nomeEspecialidade) {
        this.nomeEspecialidade = nomeEspecialidade;
    }

    public String getAreaMedica() {
        return areaMedica;
    }

    public void setAreaMedica(String areaMedica) {
        this.areaMedica = areaMedica;
    }

    public Integer getTempoMedioConsulta() {
        return tempoMedioConsulta;
    }

    public void setTempoMedioConsulta(Integer tempoMedioConsulta) {
        this.tempoMedioConsulta = tempoMedioConsulta;
    }

    @Override
    public String toString() {
        return "Especialidade{" +
                "idEspecialidade=" + idEspecialidade +
                ", nomeEspecialidade='" + nomeEspecialidade + '\'' +
                ", areaMedica='" + areaMedica + '\'' +
                ", tempoMedioConsulta=" + tempoMedioConsulta +
                '}';
    }
}

