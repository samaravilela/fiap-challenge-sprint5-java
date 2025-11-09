package br.com.fiap.resource;

import br.com.fiap.exception.BusinessRuleException;
import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.dto.Consulta;
import br.com.fiap.service.ConsultaService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
@Path("/consultas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsultaResource {
    
    private final ConsultaService consultaService;
    
    public ConsultaResource() {
        this.consultaService = new ConsultaService();
    }
    
    /**
     * GET /consultas - Lista todas as consultas agendadas
     */
    @GET
    public Response listarTodos() {
        try {
            List<Consulta> consultas = consultaService.listarTodos();
            return Response.ok(consultas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    /**
     * GET /consultas/{id} - Busca consulta por ID
     */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            Consulta consulta = consultaService.buscarPorId(id);
            return Response.ok(consulta).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    /**
     * POST /consultas - Cria nova consulta
     */
    @POST
    public Response criar(Consulta consulta) {
        try {
            Consulta consultaCriada = consultaService.criar(consulta);
            return Response.status(Response.Status.CREATED).entity(consultaCriada).build();
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (BusinessRuleException e) {
            return Response.status(422)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    /**
     * PUT /consultas/{id} - Atualiza consulta
     */
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Consulta consulta) {
        try {
            consulta.setIdConsulta(id);
            consultaService.atualizar(consulta);
            return Response.ok(consulta).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    /**
     * DELETE /consultas/{id} - Deleta consulta
     */
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        try {
            consultaService.deletar(id);
            return Response.noContent().build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    /**
     * GET /consultas/paciente/{id} - Lista consultas do paciente
     */
    @GET
    @Path("/paciente/{id}")
    public Response listarPorPaciente(@PathParam("id") Long idPaciente) {
        try {
            List<Consulta> consultas = consultaService.listarPorPaciente(idPaciente);
            return Response.ok(consultas).build();
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    /**
     * GET /consultas/medico/{id} - Lista consultas do médico
     */
    @GET
    @Path("/medico/{id}")
    public Response listarPorMedico(@PathParam("id") Long idMedico) {
        try {
            List<Consulta> consultas = consultaService.listarPorMedico(idMedico);
            return Response.ok(consultas).build();
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    /**
     * GET /consultas/status/{status} - Lista consultas por status
     */
    @GET
    @Path("/status/{status}")
    public Response listarPorStatus(@PathParam("status") String status) {
        try {
            List<Consulta> consultas = consultaService.listarPorStatus(status);
            return Response.ok(consultas).build();
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    /**
     * PUT /consultas/{id}/cancelar - Cancela consulta
     */
    @PUT
    @Path("/{id}/cancelar")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response cancelar(@PathParam("id") Long id, String motivo) {
        try {
            consultaService.cancelar(id, motivo);
            return Response.ok("Consulta cancelada com sucesso").build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (BusinessRuleException e) {
            return Response.status(422)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
}

