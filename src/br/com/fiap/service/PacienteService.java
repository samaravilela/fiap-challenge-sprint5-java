package br.com.fiap.service;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.dao.PacienteDAO;
import br.com.fiap.model.dto.Paciente;

import java.time.LocalDate;
import java.util.List;

/**
 * Service para regras de negócio da entidade Paciente
 */
public class PacienteService {
    
    private final PacienteDAO pacienteDAO;
    
    public PacienteService() {
        this.pacienteDAO = new PacienteDAO();
    }
    
    /**
     * Cria um novo paciente com validações
     * @param paciente objeto Paciente a ser criado
     * @return Paciente criado
     */
    public Paciente criar(Paciente paciente) {
        validarPaciente(paciente);
        return pacienteDAO.criar(paciente);
    }
    
    /**
     * Busca um paciente por ID
     * @param id ID do paciente
     * @return Paciente encontrado
     * @throws ResourceNotFoundException se não encontrado
     */
    public Paciente buscarPorId(Long id) {
        Paciente paciente = pacienteDAO.buscarPorId(id);
        if (paciente == null) {
            throw new ResourceNotFoundException("Paciente com ID " + id + " não encontrado");
        }
        return paciente;
    }
    
    /**
     * Lista todos os pacientes
     * @return Lista de pacientes
     */
    public List<Paciente> listarTodos() {
        return pacienteDAO.listarTodos();
    }
    
    /**
     * Atualiza um paciente existente
     * @param paciente objeto Paciente com dados atualizados
     * @return true se atualizado com sucesso
     */
    public boolean atualizar(Paciente paciente) {
        validarPaciente(paciente);
        validarIdPaciente(paciente.getIdPaciente());
        
        boolean atualizado = pacienteDAO.atualizar(paciente);
        if (!atualizado) {
            throw new ResourceNotFoundException("Paciente com ID " + paciente.getIdPaciente() + " não encontrado");
        }
        return true;
    }
    
    /**
     * Deleta um paciente
     * @param id ID do paciente
     * @return true se deletado com sucesso
     */
    public boolean deletar(Long id) {
        validarIdPaciente(id);
        
        boolean deletado = pacienteDAO.deletar(id);
        if (!deletado) {
            throw new ResourceNotFoundException("Paciente com ID " + id + " não encontrado");
        }
        return true;
    }
    
    /**
     * Busca pacientes por nome
     * @param nome nome ou parte do nome
     * @return Lista de pacientes encontrados
     */
    public List<Paciente> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidationException("Nome não pode ser vazio");
        }
        return pacienteDAO.buscarPorNome(nome);
    }
    
    /**
     * Valida os dados do paciente
     */
    private void validarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new ValidationException("Paciente não pode ser nulo");
        }
        
        if (paciente.getNomeCompleto() == null || paciente.getNomeCompleto().trim().isEmpty()) {
            throw new ValidationException("Nome completo é obrigatório");
        }
        
        if (paciente.getNomeCompleto().length() > 100) {
            throw new ValidationException("Nome completo não pode ter mais de 100 caracteres");
        }
        
        if (paciente.getDataNascimento() == null) {
            throw new ValidationException("Data de nascimento é obrigatória");
        }
        
        if (paciente.getDataNascimento().isAfter(LocalDate.now())) {
            throw new ValidationException("Data de nascimento não pode ser futura");
        }
        
        if (paciente.getGenero() == null || !paciente.getGenero().matches("[FMO]")) {
            throw new ValidationException("Gênero deve ser F, M ou O");
        }
        
        if (paciente.getTelefone() != null && paciente.getTelefone().length() > 15) {
            throw new ValidationException("Telefone não pode ter mais de 15 caracteres");
        }
        
        if (paciente.getTipoSanguineo() != null && 
            !paciente.getTipoSanguineo().matches("A\\+|A-|AB\\+|AB-|B\\+|B-|O\\+|O-")) {
            throw new ValidationException("Tipo sanguíneo inválido");
        }
    }
    
    /**
     * Valida o ID do paciente
     */
    private void validarIdPaciente(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("ID do paciente inválido");
        }
    }
}

