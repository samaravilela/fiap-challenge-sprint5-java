package br.com.fiap.service;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.dao.MedicoDAO;
import br.com.fiap.model.dto.Medico;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Service para regras de negócio da entidade Medico
 */
public class MedicoService {
    
    private final MedicoDAO medicoDAO;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    public MedicoService() {
        this.medicoDAO = new MedicoDAO();
    }
    
    /**
     * Cria um novo médico com validações
     * @param medico objeto Medico a ser criado
     * @return Medico criado
     */
    public Medico criar(Medico medico) {
        validarMedico(medico);
        validarCrmUnico(medico.getCrm());
        return medicoDAO.criar(medico);
    }
    
    /**
     * Busca um médico por ID
     * @param id ID do médico
     * @return Medico encontrado
     * @throws ResourceNotFoundException se não encontrado
     */
    public Medico buscarPorId(Long id) {
        Medico medico = medicoDAO.buscarPorId(id);
        if (medico == null) {
            throw new ResourceNotFoundException("Médico com ID " + id + " não encontrado");
        }
        return medico;
    }
    
    /**
     * Lista todos os médicos
     * @return Lista de médicos
     */
    public List<Medico> listarTodos() {
        return medicoDAO.listarTodos();
    }
    
    /**
     * Atualiza um médico existente
     * @param medico objeto Medico com dados atualizados
     * @return true se atualizado com sucesso
     */
    public boolean atualizar(Medico medico) {
        validarMedico(medico);
        validarIdMedico(medico.getIdMedico());
        
        boolean atualizado = medicoDAO.atualizar(medico);
        if (!atualizado) {
            throw new ResourceNotFoundException("Médico com ID " + medico.getIdMedico() + " não encontrado");
        }
        return true;
    }
    
    /**
     * Deleta um médico
     * @param id ID do médico
     * @return true se deletado com sucesso
     */
    public boolean deletar(Long id) {
        validarIdMedico(id);
        
        boolean deletado = medicoDAO.deletar(id);
        if (!deletado) {
            throw new ResourceNotFoundException("Médico com ID " + id + " não encontrado");
        }
        return true;
    }
    
    /**
     * Busca médico por CRM
     * @param crm CRM do médico
     * @return Medico encontrado
     */
    public Medico buscarPorCrm(String crm) {
        if (crm == null || crm.trim().isEmpty()) {
            throw new ValidationException("CRM não pode ser vazio");
        }
        
        Medico medico = medicoDAO.buscarPorCrm(crm);
        if (medico == null) {
            throw new ResourceNotFoundException("Médico com CRM " + crm + " não encontrado");
        }
        return medico;
    }
    
    /**
     * Lista médicos por especialidade
     * @param idEspecialidade ID da especialidade
     * @return Lista de médicos
     */
    public List<Medico> listarPorEspecialidade(Long idEspecialidade) {
        if (idEspecialidade == null || idEspecialidade <= 0) {
            throw new ValidationException("ID da especialidade inválido");
        }
        return medicoDAO.listarPorEspecialidade(idEspecialidade);
    }
    
    /**
     * Valida os dados do médico
     */
    private void validarMedico(Medico medico) {
        if (medico == null) {
            throw new ValidationException("Médico não pode ser nulo");
        }
        
        if (medico.getNomeCompleto() == null || medico.getNomeCompleto().trim().isEmpty()) {
            throw new ValidationException("Nome completo é obrigatório");
        }
        
        if (medico.getNomeCompleto().length() > 100) {
            throw new ValidationException("Nome completo não pode ter mais de 100 caracteres");
        }
        
        if (medico.getCrm() == null || medico.getCrm().trim().isEmpty()) {
            throw new ValidationException("CRM é obrigatório");
        }
        
        if (medico.getCrm().length() > 20) {
            throw new ValidationException("CRM não pode ter mais de 20 caracteres");
        }
        
        if (medico.getEmail() != null && !EMAIL_PATTERN.matcher(medico.getEmail()).matches()) {
            throw new ValidationException("Email inválido");
        }
        
        if (medico.getEmail() != null && medico.getEmail().length() > 100) {
            throw new ValidationException("Email não pode ter mais de 100 caracteres");
        }
        
        if (medico.getTelefone() != null && medico.getTelefone().length() > 15) {
            throw new ValidationException("Telefone não pode ter mais de 15 caracteres");
        }
    }
    
    /**
     * Valida o ID do médico
     */
    private void validarIdMedico(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("ID do médico inválido");
        }
    }
    
    /**
     * Valida se o CRM já está cadastrado
     */
    private void validarCrmUnico(String crm) {
        Medico medicoExistente = medicoDAO.buscarPorCrm(crm);
        if (medicoExistente != null) {
            throw new ValidationException("CRM " + crm + " já está cadastrado");
        }
    }
}

