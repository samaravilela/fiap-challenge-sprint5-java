package br.com.fiap.resource;

import br.com.fiap.exception.BusinessRuleException;
import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.dto.Consulta;
import br.com.fiap.service.ConsultaService;

import java.util.List;

/**
 * Resource REST para operações com Consulta
 * Seguindo princípios RESTful
 * 
 * Endpoints:
 * GET    /consultas              - Lista todas as consultas agendadas
 * GET    /consultas/{id}         - Busca consulta por ID
 * POST   /consultas              - Cria nova consulta
 * PUT    /consultas/{id}         - Atualiza consulta
 * DELETE /consultas/{id}         - Deleta consulta
 * GET    /consultas/paciente/{id} - Lista consultas do paciente
 * GET    /consultas/medico/{id}   - Lista consultas do médico
 * GET    /consultas/status/{status} - Lista consultas por status
 * PUT    /consultas/{id}/cancelar - Cancela consulta
 */
public class ConsultaResource {
    
    private final ConsultaService consultaService;
    
    public ConsultaResource() {
        this.consultaService = new ConsultaService();
    }
    
    /**
     * GET /consultas - Lista todas as consultas agendadas
     */
    public ResponseEntity<List<Consulta>> listarTodos() {
        try {
            List<Consulta> consultas = consultaService.listarTodos();
            return new ResponseEntity<>(consultas, 200);
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * GET /consultas/{id} - Busca consulta por ID
     */
    public ResponseEntity<Consulta> buscarPorId(Long id) {
        try {
            Consulta consulta = consultaService.buscarPorId(id);
            return new ResponseEntity<>(consulta, 200);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, 404, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * POST /consultas - Cria nova consulta
     */
    public ResponseEntity<Consulta> criar(Consulta consulta) {
        try {
            Consulta consultaCriada = consultaService.criar(consulta);
            return new ResponseEntity<>(consultaCriada, 201);
        } catch (ValidationException e) {
            return new ResponseEntity<>(null, 400, e.getMessage());
        } catch (BusinessRuleException e) {
            return new ResponseEntity<>(null, 422, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * PUT /consultas/{id} - Atualiza consulta
     */
    public ResponseEntity<Consulta> atualizar(Long id, Consulta consulta) {
        try {
            consulta.setIdConsulta(id);
            consultaService.atualizar(consulta);
            return new ResponseEntity<>(consulta, 200);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, 404, e.getMessage());
        } catch (ValidationException e) {
            return new ResponseEntity<>(null, 400, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * DELETE /consultas/{id} - Deleta consulta
     */
    public ResponseEntity<Void> deletar(Long id) {
        try {
            consultaService.deletar(id);
            return new ResponseEntity<>(null, 204);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, 404, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * GET /consultas/paciente/{id} - Lista consultas do paciente
     */
    public ResponseEntity<List<Consulta>> listarPorPaciente(Long idPaciente) {
        try {
            List<Consulta> consultas = consultaService.listarPorPaciente(idPaciente);
            return new ResponseEntity<>(consultas, 200);
        } catch (ValidationException e) {
            return new ResponseEntity<>(null, 400, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * GET /consultas/medico/{id} - Lista consultas do médico
     */
    public ResponseEntity<List<Consulta>> listarPorMedico(Long idMedico) {
        try {
            List<Consulta> consultas = consultaService.listarPorMedico(idMedico);
            return new ResponseEntity<>(consultas, 200);
        } catch (ValidationException e) {
            return new ResponseEntity<>(null, 400, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * GET /consultas/status/{status} - Lista consultas por status
     */
    public ResponseEntity<List<Consulta>> listarPorStatus(String status) {
        try {
            List<Consulta> consultas = consultaService.listarPorStatus(status);
            return new ResponseEntity<>(consultas, 200);
        } catch (ValidationException e) {
            return new ResponseEntity<>(null, 400, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * PUT /consultas/{id}/cancelar - Cancela consulta
     */
    public ResponseEntity<Void> cancelar(Long id, String motivo) {
        try {
            consultaService.cancelar(id, motivo);
            return new ResponseEntity<>(null, 200, "Consulta cancelada com sucesso");
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, 404, e.getMessage());
        } catch (BusinessRuleException e) {
            return new ResponseEntity<>(null, 422, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
}

