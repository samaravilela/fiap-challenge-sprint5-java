package br.com.fiap.resource;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.dto.Localizacao;
import br.com.fiap.service.LocalizacaoService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Resource REST para operações com Localização
 * Seguindo princípios RESTful
 * 
 * Endpoints:
 * GET    /localizacoes              - Lista todas as localizações
 * GET    /localizacoes/{id}         - Busca localização por ID
 * POST   /localizacoes              - Cria nova localização
 * PUT    /localizacoes/{id}         - Atualiza localização
 * DELETE /localizacoes/{id}         - Deleta localização
 * GET    /localizacoes/cidade/{cidade} - Lista localizações por cidade
 */
@Path("/localizacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocalizacaoResource {
    
    private final LocalizacaoService localizacaoService;
    
    public LocalizacaoResource() {
        this.localizacaoService = new LocalizacaoService();
    }
    
    /**
     * GET /localizacoes - Lista todas as localizações
     */
    @GET
    public Response listarTodos() {
        try {
            List<Localizacao> localizacoes = localizacaoService.listarTodos();
            return Response.ok(localizacoes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    /**
     * GET /localizacoes/{id} - Busca localização por ID
     */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            Localizacao localizacao = localizacaoService.buscarPorId(id);
            return Response.ok(localizacao).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    /**
     * POST /localizacoes - Cria nova localização
     */
    @POST
    public Response criar(Localizacao localizacao) {
        try {
            Localizacao localizacaoCriada = localizacaoService.criar(localizacao);
            return Response.status(Response.Status.CREATED).entity(localizacaoCriada).build();
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    /**
     * PUT /localizacoes/{id} - Atualiza localização
     */
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Localizacao localizacao) {
        try {
            localizacao.setIdLocalizacao(id);
            localizacaoService.atualizar(localizacao);
            return Response.ok(localizacao).build();
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
     * DELETE /localizacoes/{id} - Deleta localização
     */
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        try {
            localizacaoService.deletar(id);
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
     * GET /localizacoes/cidade/{cidade} - Lista localizações por cidade
     */
    @GET
    @Path("/cidade/{cidade}")
    public Response listarPorCidade(@PathParam("cidade") String cidade) {
        try {
            List<Localizacao> localizacoes = localizacaoService.listarPorCidade(cidade);
            return Response.ok(localizacoes).build();
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
}

