package br.com.fiap.resource;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.dto.Especialidade;
import br.com.fiap.service.EspecialidadeService;

import java.util.List;

/**
 * Resource REST para operações com Especialidade
 * Seguindo princípios RESTful
 * 
 * Endpoints:
 * GET    /especialidades       - Lista todas as especialidades
 * GET    /especialidades/{id}  - Busca especialidade por ID
 * POST   /especialidades       - Cria nova especialidade
 * PUT    /especialidades/{id}  - Atualiza especialidade
 * DELETE /especialidades/{id}  - Deleta especialidade
 */
public class EspecialidadeResource {
    
    private final EspecialidadeService especialidadeService;
    
    public EspecialidadeResource() {
        this.especialidadeService = new EspecialidadeService();
    }
    
    /**
     * GET /especialidades - Lista todas as especialidades
     */
    public ResponseEntity<List<Especialidade>> listarTodos() {
        try {
            List<Especialidade> especialidades = especialidadeService.listarTodos();
            return new ResponseEntity<>(especialidades, 200);
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * GET /especialidades/{id} - Busca especialidade por ID
     */
    public ResponseEntity<Especialidade> buscarPorId(Long id) {
        try {
            Especialidade especialidade = especialidadeService.buscarPorId(id);
            return new ResponseEntity<>(especialidade, 200);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, 404, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * POST /especialidades - Cria nova especialidade
     */
    public ResponseEntity<Especialidade> criar(Especialidade especialidade) {
        try {
            Especialidade especialidadeCriada = especialidadeService.criar(especialidade);
            return new ResponseEntity<>(especialidadeCriada, 201);
        } catch (ValidationException e) {
            return new ResponseEntity<>(null, 400, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * PUT /especialidades/{id} - Atualiza especialidade
     */
    public ResponseEntity<Especialidade> atualizar(Long id, Especialidade especialidade) {
        try {
            especialidade.setIdEspecialidade(id);
            especialidadeService.atualizar(especialidade);
            return new ResponseEntity<>(especialidade, 200);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, 404, e.getMessage());
        } catch (ValidationException e) {
            return new ResponseEntity<>(null, 400, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * DELETE /especialidades/{id} - Deleta especialidade
     */
    public ResponseEntity<Void> deletar(Long id) {
        try {
            especialidadeService.deletar(id);
            return new ResponseEntity<>(null, 204);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, 404, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
}

