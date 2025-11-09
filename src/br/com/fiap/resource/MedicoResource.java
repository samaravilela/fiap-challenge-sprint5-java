package br.com.fiap.resource;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.dto.Medico;
import br.com.fiap.service.MedicoService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
@Path("/medicos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MedicoResource {
    
    private final MedicoService medicoService;
    
    public MedicoResource() {
        this.medicoService = new MedicoService();
    }
    
    /**
     * GET /medicos - Lista todos os médicos
     */
    @GET
    public Response listarTodos() {
        try {
            List<Medico> medicos = medicoService.listarTodos();
            return Response.ok(medicos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    /**
     * GET /medicos/{id} - Busca médico por ID
     */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            Medico medico = medicoService.buscarPorId(id);
            return Response.ok(medico).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    /**
     * POST /medicos - Cria novo médico
     */
    @POST
    public Response criar(Medico medico) {
        try {
            Medico medicoCriado = medicoService.criar(medico);
            return Response.status(Response.Status.CREATED).entity(medicoCriado).build();
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    /**
     * PUT /medicos/{id} - Atualiza médico
     */
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Medico medico) {
        try {
            medico.setIdMedico(id);
            medicoService.atualizar(medico);
            return Response.ok(medico).build();
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
     * DELETE /medicos/{id} - Deleta médico
     */
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        try {
            medicoService.deletar(id);
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
     * GET /medicos/crm/{crm} - Busca médico por CRM
     */
    @GET
    @Path("/crm/{crm}")
    public Response buscarPorCrm(@PathParam("crm") String crm) {
        try {
            Medico medico = medicoService.buscarPorCrm(crm);
            return Response.ok(medico).build();
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
     * GET /medicos/especialidade/{id} - Lista médicos por especialidade
     */
    @GET
    @Path("/especialidade/{id}")
    public Response listarPorEspecialidade(@PathParam("id") Long idEspecialidade) {
        try {
            List<Medico> medicos = medicoService.listarPorEspecialidade(idEspecialidade);
            return Response.ok(medicos).build();
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
}

