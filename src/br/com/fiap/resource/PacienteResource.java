package br.com.fiap.resource;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.dto.Paciente;
import br.com.fiap.service.PacienteService;

import java.util.List;

/**
 * Resource REST para operações com Paciente
 * Seguindo princípios RESTful
 * 
 * Endpoints:
 * GET    /pacientes       - Lista todos os pacientes
 * GET    /pacientes/{id}  - Busca paciente por ID
 * POST   /pacientes       - Cria novo paciente
 * PUT    /pacientes/{id}  - Atualiza paciente
 * DELETE /pacientes/{id}  - Deleta paciente
 */
public class PacienteResource {
    
    private final PacienteService pacienteService;
    
    public PacienteResource() {
        this.pacienteService = new PacienteService();
    }
    
    /**
     * GET /pacientes - Lista todos os pacientes
     * @return Lista de pacientes (200 OK)
     */
    public ResponseEntity<List<Paciente>> listarTodos() {
        try {
            List<Paciente> pacientes = pacienteService.listarTodos();
            return new ResponseEntity<>(pacientes, 200);
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * GET /pacientes/{id} - Busca paciente por ID
     * @param id ID do paciente
     * @return Paciente encontrado (200 OK) ou 404 Not Found
     */
    public ResponseEntity<Paciente> buscarPorId(Long id) {
        try {
            Paciente paciente = pacienteService.buscarPorId(id);
            return new ResponseEntity<>(paciente, 200);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, 404, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * POST /pacientes - Cria novo paciente
     * @param paciente dados do paciente
     * @return Paciente criado (201 Created) ou 400 Bad Request
     */
    public ResponseEntity<Paciente> criar(Paciente paciente) {
        try {
            Paciente pacienteCriado = pacienteService.criar(paciente);
            return new ResponseEntity<>(pacienteCriado, 201);
        } catch (ValidationException e) {
            return new ResponseEntity<>(null, 400, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * PUT /pacientes/{id} - Atualiza paciente
     * @param id ID do paciente
     * @param paciente dados atualizados
     * @return 200 OK ou 404 Not Found ou 400 Bad Request
     */
    public ResponseEntity<Paciente> atualizar(Long id, Paciente paciente) {
        try {
            paciente.setIdPaciente(id);
            pacienteService.atualizar(paciente);
            return new ResponseEntity<>(paciente, 200);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, 404, e.getMessage());
        } catch (ValidationException e) {
            return new ResponseEntity<>(null, 400, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * DELETE /pacientes/{id} - Deleta paciente
     * @param id ID do paciente
     * @return 204 No Content ou 404 Not Found
     */
    public ResponseEntity<Void> deletar(Long id) {
        try {
            pacienteService.deletar(id);
            return new ResponseEntity<>(null, 204);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, 404, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * GET /pacientes/buscar?nome={nome} - Busca pacientes por nome
     * @param nome nome ou parte do nome
     * @return Lista de pacientes encontrados
     */
    public ResponseEntity<List<Paciente>> buscarPorNome(String nome) {
        try {
            List<Paciente> pacientes = pacienteService.buscarPorNome(nome);
            return new ResponseEntity<>(pacientes, 200);
        } catch (ValidationException e) {
            return new ResponseEntity<>(null, 400, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
}

