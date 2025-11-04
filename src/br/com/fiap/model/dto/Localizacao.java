package br.com.fiap.model.dto;

/**
 * Classe modelo para Localização (T_EASEHC_LOCALIZACAO)
 * Representa uma unidade de atendimento
 */
public class Localizacao {
    private Long idLocalizacao;
    private String nomeUnidade;
    private String endereco;
    private String estado;
    private String cidade;
    private String pais;
    private String horarioFuncionamento;
    private String telefone;

    // Construtores
    public Localizacao() {}

    public Localizacao(Long idLocalizacao, String nomeUnidade, String endereco, 
                      String estado, String cidade, String pais, 
                      String horarioFuncionamento, String telefone) {
        this.idLocalizacao = idLocalizacao;
        this.nomeUnidade = nomeUnidade;
        this.endereco = endereco;
        this.estado = estado;
        this.cidade = cidade;
        this.pais = pais;
        this.horarioFuncionamento = horarioFuncionamento;
        this.telefone = telefone;
    }

    // Getters e Setters
    public Long getIdLocalizacao() {
        return idLocalizacao;
    }

    public void setIdLocalizacao(Long idLocalizacao) {
        this.idLocalizacao = idLocalizacao;
    }

    public String getNomeUnidade() {
        return nomeUnidade;
    }

    public void setNomeUnidade(String nomeUnidade) {
        this.nomeUnidade = nomeUnidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Localizacao{" +
                "idLocalizacao=" + idLocalizacao +
                ", nomeUnidade='" + nomeUnidade + '\'' +
                ", endereco='" + endereco + '\'' +
                ", estado='" + estado + '\'' +
                ", cidade='" + cidade + '\'' +
                ", pais='" + pais + '\'' +
                ", horarioFuncionamento='" + horarioFuncionamento + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}

