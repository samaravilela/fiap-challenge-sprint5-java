package br.com.fiap.resource;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.dto.Especialidade;
import br.com.fiap.service.EspecialidadeService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
@Path("/especialidades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EspecialidadeResource {
    
    private final EspecialidadeService especialidadeService;
    
    public EspecialidadeResource() {
        this.especialidadeService = new EspecialidadeService();
    }
    
    /**
     * GET /especialidades - Lista todas as especialidades
     */
    @GET
    public Response listarTodos() {
        try {
            List<Especialidade> especialidades = especialidadeService.listarTodos();
            return Response.ok(especialidades).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    /**
     * GET /especialidades/{id} - Busca especialidade por ID
     */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            Especialidade especialidade = especialidadeService.buscarPorId(id);
            return Response.ok(especialidade).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    /**
     * POST /especialidades - Cria nova especialidade
     */
    @POST
    public Response criar(Especialidade especialidade) {
        try {
            Especialidade especialidadeCriada = especialidadeService.criar(especialidade);
            return Response.status(Response.Status.CREATED).entity(especialidadeCriada).build();
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    /**
     * PUT /especialidades/{id} - Atualiza especialidade
     */
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Especialidade especialidade) {
        try {
            especialidade.setIdEspecialidade(id);
            especialidadeService.atualizar(especialidade);
            return Response.ok(especialidade).build();
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
     * DELETE /especialidades/{id} - Deleta especialidade
     */
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        try {
            especialidadeService.deletar(id);
            return Response.noContent().build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
}

