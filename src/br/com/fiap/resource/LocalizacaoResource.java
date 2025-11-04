package br.com.fiap.resource;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.dto.Localizacao;
import br.com.fiap.service.LocalizacaoService;

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
public class LocalizacaoResource {
    
    private final LocalizacaoService localizacaoService;
    
    public LocalizacaoResource() {
        this.localizacaoService = new LocalizacaoService();
    }
    
    /**
     * GET /localizacoes - Lista todas as localizações
     */
    public ResponseEntity<List<Localizacao>> listarTodos() {
        try {
            List<Localizacao> localizacoes = localizacaoService.listarTodos();
            return new ResponseEntity<>(localizacoes, 200);
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * GET /localizacoes/{id} - Busca localização por ID
     */
    public ResponseEntity<Localizacao> buscarPorId(Long id) {
        try {
            Localizacao localizacao = localizacaoService.buscarPorId(id);
            return new ResponseEntity<>(localizacao, 200);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, 404, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * POST /localizacoes - Cria nova localização
     */
    public ResponseEntity<Localizacao> criar(Localizacao localizacao) {
        try {
            Localizacao localizacaoCriada = localizacaoService.criar(localizacao);
            return new ResponseEntity<>(localizacaoCriada, 201);
        } catch (ValidationException e) {
            return new ResponseEntity<>(null, 400, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * PUT /localizacoes/{id} - Atualiza localização
     */
    public ResponseEntity<Localizacao> atualizar(Long id, Localizacao localizacao) {
        try {
            localizacao.setIdLocalizacao(id);
            localizacaoService.atualizar(localizacao);
            return new ResponseEntity<>(localizacao, 200);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, 404, e.getMessage());
        } catch (ValidationException e) {
            return new ResponseEntity<>(null, 400, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * DELETE /localizacoes/{id} - Deleta localização
     */
    public ResponseEntity<Void> deletar(Long id) {
        try {
            localizacaoService.deletar(id);
            return new ResponseEntity<>(null, 204);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, 404, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
    
    /**
     * GET /localizacoes/cidade/{cidade} - Lista localizações por cidade
     */
    public ResponseEntity<List<Localizacao>> listarPorCidade(String cidade) {
        try {
            List<Localizacao> localizacoes = localizacaoService.listarPorCidade(cidade);
            return new ResponseEntity<>(localizacoes, 200);
        } catch (ValidationException e) {
            return new ResponseEntity<>(null, 400, e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, 500, "Erro interno: " + e.getMessage());
        }
    }
}

