package br.com.fiap.service;

import br.com.fiap.exception.BusinessRuleException;
import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.dao.ConsultaDAO;
import br.com.fiap.model.dto.Consulta;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service para regras de negócio da entidade Consulta
 */
public class ConsultaService {
    
    private final ConsultaDAO consultaDAO;
    private final PacienteService pacienteService;
    private final MedicoService medicoService;
    
    public ConsultaService() {
        this.consultaDAO = new ConsultaDAO();
        this.pacienteService = new PacienteService();
        this.medicoService = new MedicoService();
    }
    
    /**
     * Cria uma nova consulta com validações
     * @param consulta objeto Consulta a ser criado
     * @return Consulta criada
     */
    public Consulta criar(Consulta consulta) {
        validarConsulta(consulta);
        validarEntidadesRelacionadas(consulta);
        validarDisponibilidade(consulta);
        
        return consultaDAO.criar(consulta);
    }
    
    /**
     * Busca uma consulta por ID
     * @param id ID da consulta
     * @return Consulta encontrada
     * @throws ResourceNotFoundException se não encontrada
     */
    public Consulta buscarPorId(Long id) {
        Consulta consulta = consultaDAO.buscarPorId(id);
        if (consulta == null) {
            throw new ResourceNotFoundException("Consulta com ID " + id + " não encontrada");
        }
        return consulta;
    }
    
    /**
     * Lista todas as consultas agendadas
     * @return Lista de consultas com status "Agendada"
     */
    public List<Consulta> listarTodos() {
        return consultaDAO.listarTodos();
    }
    
    /**
     * Atualiza uma consulta existente
     * @param consulta objeto Consulta com dados atualizados
     * @return true se atualizado com sucesso
     */
    public boolean atualizar(Consulta consulta) {
        validarConsulta(consulta);
        validarIdConsulta(consulta.getIdConsulta());
        validarEntidadesRelacionadas(consulta);
        
        boolean atualizado = consultaDAO.atualizar(consulta);
        if (!atualizado) {
            throw new ResourceNotFoundException("Consulta com ID " + consulta.getIdConsulta() + " não encontrada");
        }
        return true;
    }
    
    /**
     * Deleta uma consulta
     * @param id ID da consulta
     * @return true se deletado com sucesso
     */
    public boolean deletar(Long id) {
        validarIdConsulta(id);
        
        boolean deletado = consultaDAO.deletar(id);
        if (!deletado) {
            throw new ResourceNotFoundException("Consulta com ID " + id + " não encontrada");
        }
        return true;
    }
    
    /**
     * Lista consultas por paciente
     * @param idPaciente ID do paciente
     * @return Lista de consultas
     */
    public List<Consulta> listarPorPaciente(Long idPaciente) {
        if (idPaciente == null || idPaciente <= 0) {
            throw new ValidationException("ID do paciente inválido");
        }
        return consultaDAO.listarPorPaciente(idPaciente);
    }
    
    /**
     * Lista consultas por médico
     * @param idMedico ID do médico
     * @return Lista de consultas
     */
    public List<Consulta> listarPorMedico(Long idMedico) {
        if (idMedico == null || idMedico <= 0) {
            throw new ValidationException("ID do médico inválido");
        }
        return consultaDAO.listarPorMedico(idMedico);
    }
    
    /**
     * Lista consultas por status
     * @param status status da consulta
     * @return Lista de consultas
     */
    public List<Consulta> listarPorStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new ValidationException("Status não pode ser vazio");
        }
        
        if (!status.matches("Agendada|Cancelada|Realizada")) {
            throw new ValidationException("Status deve ser: Agendada, Cancelada ou Realizada");
        }
        
        return consultaDAO.listarPorStatus(status);
    }
    
    /**
     * Cancela uma consulta
     * @param id ID da consulta
     * @param motivo motivo do cancelamento
     * @return true se cancelada com sucesso
     */
    public boolean cancelar(Long id, String motivo) {
        Consulta consulta = buscarPorId(id);
        
        if ("Cancelada".equals(consulta.getStatus())) {
            throw new BusinessRuleException("Consulta já está cancelada");
        }
        
        if ("Realizada".equals(consulta.getStatus())) {
            throw new BusinessRuleException("Não é possível cancelar uma consulta já realizada");
        }
        
        consulta.setStatus("Cancelada");
        consulta.setObservacoes(motivo);
        
        return consultaDAO.atualizar(consulta);
    }
    
    /**
     * Valida os dados da consulta
     */
    private void validarConsulta(Consulta consulta) {
        if (consulta == null) {
            throw new ValidationException("Consulta não pode ser nula");
        }
        
        if (consulta.getIdPaciente() == null || consulta.getIdPaciente() <= 0) {
            throw new ValidationException("ID do paciente é obrigatório");
        }
        
        if (consulta.getIdMedico() == null || consulta.getIdMedico() <= 0) {
            throw new ValidationException("ID do médico é obrigatório");
        }
        
        if (consulta.getIdLocalizacao() == null || consulta.getIdLocalizacao() <= 0) {
            throw new ValidationException("ID da localização é obrigatório");
        }
        
        if (consulta.getIdEspecialidade() == null || consulta.getIdEspecialidade() <= 0) {
            throw new ValidationException("ID da especialidade é obrigatório");
        }
        
        if (consulta.getDataHora() == null) {
            throw new ValidationException("Data e hora são obrigatórias");
        }
        
        if (consulta.getDataHora().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Data e hora não podem ser no passado");
        }
        
        if (consulta.getDuracaoMinutos() == null || consulta.getDuracaoMinutos() <= 0) {
            throw new ValidationException("Duração deve ser maior que zero");
        }
        
        if (consulta.getStatus() == null || !consulta.getStatus().matches("Agendada|Cancelada|Realizada")) {
            throw new ValidationException("Status deve ser: Agendada, Cancelada ou Realizada");
        }
        
        if (consulta.getPrioridade() == null || !consulta.getPrioridade().matches("Alta|Baixa|Normal")) {
            throw new ValidationException("Prioridade deve ser: Alta, Baixa ou Normal");
        }
    }
    
    /**
     * Valida o ID da consulta
     */
    private void validarIdConsulta(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("ID da consulta inválido");
        }
    }
    
    /**
     * Valida se as entidades relacionadas existem
     */
    private void validarEntidadesRelacionadas(Consulta consulta) {
        // Verifica se paciente existe
        pacienteService.buscarPorId(consulta.getIdPaciente());
        
        // Verifica se médico existe
        medicoService.buscarPorId(consulta.getIdMedico());
        
        // Aqui poderíamos validar também localização e especialidade
    }
    
    /**
     * Valida disponibilidade de horário para o médico
     */
    private void validarDisponibilidade(Consulta consulta) {
        List<Consulta> consultasMedico = consultaDAO.listarPorMedico(consulta.getIdMedico());
        
        for (Consulta c : consultasMedico) {
            if ("Cancelada".equals(c.getStatus())) {
                continue; // Ignora consultas canceladas
            }
            
            LocalDateTime inicioExistente = c.getDataHora();
            LocalDateTime fimExistente = inicioExistente.plusMinutes(c.getDuracaoMinutos());
            
            LocalDateTime inicioNova = consulta.getDataHora();
            LocalDateTime fimNova = inicioNova.plusMinutes(consulta.getDuracaoMinutos());
            
            // Verifica se há conflito de horário
            if ((inicioNova.isBefore(fimExistente) && fimNova.isAfter(inicioExistente))) {
                throw new BusinessRuleException(
                    "Médico já possui consulta agendada neste horário"
                );
            }
        }
    }
}

