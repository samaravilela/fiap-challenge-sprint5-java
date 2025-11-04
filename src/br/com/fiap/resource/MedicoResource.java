package br.com.fiap.resource;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.dto.Medico;
import br.com.fiap.service.MedicoService;

import java.util.List;

/**
 * Resource REST para operações com Médico
 * Seguindo princípios RESTful
 * 
 * Endpoints:
 * GET    /medicos                      - Lista todos os médicos
 * GET    /medicos/{id}                 - Busca médico por ID
 * POST   /medicos                      - Cria novo médico
 * PUT    /medicos/{id}                 - Atualiza médico
 * DELETE /medicos/{id}                 - Deleta médico
 * GET    /medicos/crm/{crm}            - Busca médico por CRM
 * GET    /medicos/especialidade/{id}   - Lista médicos por especialidade
 */
public class MedicoResource {
    
    private final MedicoService medicoService;
    
    public MedicoResource() {
        this.medicoService = new MedicoService();
    }
    
    /**
     * GET /medicos - Lista todos os médicos
     */
    public ResponseEntity<List<Medico>> listarTodos() {
        try {
            List<Medico> medicos = medicoService.listarTodos();
            return new ResponseEntity<>(medicos, 200);
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * GET /medicos/{id} - Busca médico por ID
     */
    public ResponseEntity<Medico> buscarPorId(Long id) {
        try {
            Medico medico = medicoService.buscarPorId(id);
            return new ResponseEntity<>(medico, 200);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, 404, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * POST /medicos - Cria novo médico
     */
    public ResponseEntity<Medico> criar(Medico medico) {
        try {
            Medico medicoCriado = medicoService.criar(medico);
            return new ResponseEntity<>(medicoCriado, 201);
        } catch (ValidationException e) {
            return new ResponseEntity<>(null, 400, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * PUT /medicos/{id} - Atualiza médico
     */
    public ResponseEntity<Medico> atualizar(Long id, Medico medico) {
        try {
            medico.setIdMedico(id);
            medicoService.atualizar(medico);
            return new ResponseEntity<>(medico, 200);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, 404, e.getMessage());
        } catch (ValidationException e) {
            return new ResponseEntity<>(null, 400, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * DELETE /medicos/{id} - Deleta médico
     */
    public ResponseEntity<Void> deletar(Long id) {
        try {
            medicoService.deletar(id);
            return new ResponseEntity<>(null, 204);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, 404, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * GET /medicos/crm/{crm} - Busca médico por CRM
     */
    public ResponseEntity<Medico> buscarPorCrm(String crm) {
        try {
            Medico medico = medicoService.buscarPorCrm(crm);
            return new ResponseEntity<>(medico, 200);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, 404, e.getMessage());
        } catch (ValidationException e) {
            return new ResponseEntity<>(null, 400, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * GET /medicos/especialidade/{id} - Lista médicos por especialidade
     */
    public ResponseEntity<List<Medico>> listarPorEspecialidade(Long idEspecialidade) {
        try {
            List<Medico> medicos = medicoService.listarPorEspecialidade(idEspecialidade);
            return new ResponseEntity<>(medicos, 200);
        } catch (ValidationException e) {
            return new ResponseEntity<>(null, 400, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
}

