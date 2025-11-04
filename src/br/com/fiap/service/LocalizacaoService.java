package br.com.fiap.service;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.dao.LocalizacaoDAO;
import br.com.fiap.model.dto.Localizacao;

import java.util.List;

/**
 * Service para regras de negócio da entidade Localizacao
 */
public class LocalizacaoService {
    
    private final LocalizacaoDAO localizacaoDAO;
    
    public LocalizacaoService() {
        this.localizacaoDAO = new LocalizacaoDAO();
    }
    
    /**
     * Cria uma nova localização com validações
     * @param localizacao objeto Localizacao a ser criado
     * @return Localizacao criada
     */
    public Localizacao criar(Localizacao localizacao) {
        validarLocalizacao(localizacao);
        return localizacaoDAO.criar(localizacao);
    }
    
    /**
     * Busca uma localização por ID
     * @param id ID da localização
     * @return Localizacao encontrada
     * @throws ResourceNotFoundException se não encontrada
     */
    public Localizacao buscarPorId(Long id) {
        Localizacao localizacao = localizacaoDAO.buscarPorId(id);
        if (localizacao == null) {
            throw new ResourceNotFoundException("Localização com ID " + id + " não encontrada");
        }
        return localizacao;
    }
    
    /**
     * Lista todas as localizações
     * @return Lista de localizações
     */
    public List<Localizacao> listarTodos() {
        return localizacaoDAO.listarTodos();
    }
    
    /**
     * Atualiza uma localização existente
     * @param localizacao objeto Localizacao com dados atualizados
     * @return true se atualizado com sucesso
     */
    public boolean atualizar(Localizacao localizacao) {
        validarLocalizacao(localizacao);
        validarIdLocalizacao(localizacao.getIdLocalizacao());
        
        boolean atualizado = localizacaoDAO.atualizar(localizacao);
        if (!atualizado) {
            throw new ResourceNotFoundException("Localização com ID " + localizacao.getIdLocalizacao() + " não encontrada");
        }
        return true;
    }
    
    /**
     * Deleta uma localização
     * @param id ID da localização
     * @return true se deletado com sucesso
     */
    public boolean deletar(Long id) {
        validarIdLocalizacao(id);
        
        boolean deletado = localizacaoDAO.deletar(id);
        if (!deletado) {
            throw new ResourceNotFoundException("Localização com ID " + id + " não encontrada");
        }
        return true;
    }
    
    /**
     * Lista localizações por cidade
     * @param cidade nome da cidade
     * @return Lista de localizações
     */
    public List<Localizacao> listarPorCidade(String cidade) {
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new ValidationException("Cidade não pode ser vazia");
        }
        return localizacaoDAO.listarPorCidade(cidade);
    }
    
    /**
     * Valida os dados da localização
     */
    private void validarLocalizacao(Localizacao localizacao) {
        if (localizacao == null) {
            throw new ValidationException("Localização não pode ser nula");
        }
        
        if (localizacao.getNomeUnidade() == null || localizacao.getNomeUnidade().trim().isEmpty()) {
            throw new ValidationException("Nome da unidade é obrigatório");
        }
        
        if (localizacao.getNomeUnidade().length() > 100) {
            throw new ValidationException("Nome da unidade não pode ter mais de 100 caracteres");
        }
        
        if (localizacao.getEndereco() != null && localizacao.getEndereco().length() > 200) {
            throw new ValidationException("Endereço não pode ter mais de 200 caracteres");
        }
        
        if (localizacao.getEstado() != null && localizacao.getEstado().length() > 2) {
            throw new ValidationException("Estado deve ter no máximo 2 caracteres");
        }
        
        if (localizacao.getCidade() != null && localizacao.getCidade().length() > 100) {
            throw new ValidationException("Cidade não pode ter mais de 100 caracteres");
        }
        
        if (localizacao.getPais() != null && localizacao.getPais().length() > 100) {
            throw new ValidationException("País não pode ter mais de 100 caracteres");
        }
        
        if (localizacao.getTelefone() != null && localizacao.getTelefone().length() > 15) {
            throw new ValidationException("Telefone não pode ter mais de 15 caracteres");
        }
    }
    
    /**
     * Valida o ID da localização
     */
    private void validarIdLocalizacao(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("ID da localização inválido");
        }
    }
}

