package br.com.fiap.model.dto;

/**
 * Classe modelo para Médico (T_EASEHC_MEDICO)
 * Representa um médico do sistema de saúde
 */
public class Medico {
    private Long idMedico;
    private String nomeCompleto;
    private String crm;
    private String telefone;
    private String email;

    // Construtores
    public Medico() {}

    public Medico(Long idMedico, String nomeCompleto, String crm, String telefone, String email) {
        this.idMedico = idMedico;
        this.nomeCompleto = nomeCompleto;
        this.crm = crm;
        this.telefone = telefone;
        this.email = email;
    }

    // Getters e Setters
    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "idMedico=" + idMedico +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", crm='" + crm + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
