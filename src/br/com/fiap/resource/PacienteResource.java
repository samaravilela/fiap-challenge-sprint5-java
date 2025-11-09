package br.com.fiap.resource;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.dto.Paciente;
import br.com.fiap.service.PacienteService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
 * GET    /pacientes/buscar?nome={nome} - Busca pacientes por nome
 */
@Path("/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteResource {
    
    private final PacienteService pacienteService;
    
    public PacienteResource() {
        this.pacienteService = new PacienteService();
    }
    
    /**
     * GET /pacientes - Lista todos os pacientes
     */
    @GET
    public Response listarTodos() {
        try {
            List<Paciente> pacientes = pacienteService.listarTodos();
            return Response.ok(pacientes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    /**
     * GET /pacientes/{id} - Busca paciente por ID
     */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            Paciente paciente = pacienteService.buscarPorId(id);
            return Response.ok(paciente).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    /**
     * POST /pacientes - Cria novo paciente
     */
    @POST
    public Response criar(Paciente paciente) {
        try {
            Paciente pacienteCriado = pacienteService.criar(paciente);
            return Response.status(Response.Status.CREATED).entity(pacienteCriado).build();
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    /**
     * PUT /pacientes/{id} - Atualiza paciente
     */
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Paciente paciente) {
        try {
            paciente.setIdPaciente(id);
            pacienteService.atualizar(paciente);
            return Response.ok(paciente).build();
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
     * DELETE /pacientes/{id} - Deleta paciente
     */
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        try {
            pacienteService.deletar(id);
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
     * GET /pacientes/buscar?nome={nome} - Busca pacientes por nome
     */
    @GET
    @Path("/buscar")
    public Response buscarPorNome(@QueryParam("nome") String nome) {
        try {
            List<Paciente> pacientes = pacienteService.buscarPorNome(nome);
            return Response.ok(pacientes).build();
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
}

