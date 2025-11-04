package br.com.fiap.service;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.dao.EspecialidadeDAO;
import br.com.fiap.model.dto.Especialidade;

import java.util.List;

/**
 * Service para regras de negócio da entidade Especialidade
 */
public class EspecialidadeService {
    
    private final EspecialidadeDAO especialidadeDAO;
    
    public EspecialidadeService() {
        this.especialidadeDAO = new EspecialidadeDAO();
    }
    
    /**
     * Cria uma nova especialidade com validações
     * @param especialidade objeto Especialidade a ser criado
     * @return Especialidade criada
     */
    public Especialidade criar(Especialidade especialidade) {
        validarEspecialidade(especialidade);
        return especialidadeDAO.criar(especialidade);
    }
    
    /**
     * Busca uma especialidade por ID
     * @param id ID da especialidade
     * @return Especialidade encontrada
     * @throws ResourceNotFoundException se não encontrada
     */
    public Especialidade buscarPorId(Long id) {
        Especialidade especialidade = especialidadeDAO.buscarPorId(id);
        if (especialidade == null) {
            throw new ResourceNotFoundException("Especialidade com ID " + id + " não encontrada");
        }
        return especialidade;
    }
    
    /**
     * Lista todas as especialidades
     * @return Lista de especialidades
     */
    public List<Especialidade> listarTodos() {
        return especialidadeDAO.listarTodos();
    }
    
    /**
     * Atualiza uma especialidade existente
     * @param especialidade objeto Especialidade com dados atualizados
     * @return true se atualizado com sucesso
     */
    public boolean atualizar(Especialidade especialidade) {
        validarEspecialidade(especialidade);
        validarIdEspecialidade(especialidade.getIdEspecialidade());
        
        boolean atualizado = especialidadeDAO.atualizar(especialidade);
        if (!atualizado) {
            throw new ResourceNotFoundException("Especialidade com ID " + especialidade.getIdEspecialidade() + " não encontrada");
        }
        return true;
    }
    
    /**
     * Deleta uma especialidade
     * @param id ID da especialidade
     * @return true se deletado com sucesso
     */
    public boolean deletar(Long id) {
        validarIdEspecialidade(id);
        
        boolean deletado = especialidadeDAO.deletar(id);
        if (!deletado) {
            throw new ResourceNotFoundException("Especialidade com ID " + id + " não encontrada");
        }
        return true;
    }
    
    /**
     * Valida os dados da especialidade
     */
    private void validarEspecialidade(Especialidade especialidade) {
        if (especialidade == null) {
            throw new ValidationException("Especialidade não pode ser nula");
        }
        
        if (especialidade.getNomeEspecialidade() == null || especialidade.getNomeEspecialidade().trim().isEmpty()) {
            throw new ValidationException("Nome da especialidade é obrigatório");
        }
        
        if (especialidade.getNomeEspecialidade().length() > 100) {
            throw new ValidationException("Nome da especialidade não pode ter mais de 100 caracteres");
        }
        
        if (especialidade.getAreaMedica() != null && especialidade.getAreaMedica().length() > 100) {
            throw new ValidationException("Área médica não pode ter mais de 100 caracteres");
        }
        
        if (especialidade.getTempoMedioConsulta() != null && especialidade.getTempoMedioConsulta() <= 0) {
            throw new ValidationException("Tempo médio de consulta deve ser maior que zero");
        }
    }
    
    /**
     * Valida o ID da especialidade
     */
    private void validarIdEspecialidade(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("ID da especialidade inválido");
        }
    }
}

