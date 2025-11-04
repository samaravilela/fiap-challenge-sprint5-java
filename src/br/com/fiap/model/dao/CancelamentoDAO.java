package br.com.fiap.model.dao;

import br.com.fiap.exception.DatabaseException;
import br.com.fiap.model.dto.Cancelamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para operações CRUD da entidade Cancelamento
 */
public class CancelamentoDAO {
    
    /**
     * Cria um novo registro de cancelamento/remarcação
     * @param cancelamento objeto Cancelamento a ser criado
     * @return Cancelamento criado com ID gerado
     */
    public Cancelamento criar(Cancelamento cancelamento) {
        String sql = "INSERT INTO T_EASEHC_CANREM (ID_AJUSTE, ID_CONSULTA, TP_AJUSTE, MOT_SOLIC, NV_DT_HORA) " +
                     "VALUES (SEQ_EASEHC_CANREM.NEXTVAL, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_AJUSTE"})) {
            
            stmt.setLong(1, cancelamento.getIdConsulta());
            stmt.setString(2, cancelamento.getTipoAjuste());
            stmt.setString(3, cancelamento.getMotivoSolicitacao());
            
            if (cancelamento.getNovaDataHora() != null) {
                stmt.setTimestamp(4, Timestamp.valueOf(cancelamento.getNovaDataHora()));
            } else {
                stmt.setNull(4, Types.TIMESTAMP);
            }
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    cancelamento.setIdAjuste(rs.getLong(1));
                }
                conn.commit();
                return cancelamento;
            }
            
            throw new DatabaseException("Falha ao criar cancelamento - nenhuma linha foi afetada");
            
        } catch (SQLException e) {
            ConexaoBD.rollback();
            throw new DatabaseException("Erro ao criar cancelamento: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca um cancelamento pelo ID
     * @param id ID do cancelamento
     * @return Cancelamento encontrado ou null
     */
    public Cancelamento buscarPorId(Long id) {
        String sql = "SELECT ID_AJUSTE, ID_CONSULTA, TP_AJUSTE, MOT_SOLIC, NV_DT_HORA " +
                     "FROM T_EASEHC_CANREM WHERE ID_AJUSTE = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extrairCancelamentoDoResultSet(rs);
            }
            
            return null;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar cancelamento por ID: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lista todos os cancelamentos
     * @return Lista de cancelamentos
     */
    public List<Cancelamento> listarTodos() {
        List<Cancelamento> cancelamentos = new ArrayList<>();
        String sql = "SELECT ID_AJUSTE, ID_CONSULTA, TP_AJUSTE, MOT_SOLIC, NV_DT_HORA " +
                     "FROM T_EASEHC_CANREM ORDER BY ID_AJUSTE DESC";
        
        try (Connection conn = ConexaoBD.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                cancelamentos.add(extrairCancelamentoDoResultSet(rs));
            }
            
            return cancelamentos;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar cancelamentos: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lista cancelamentos por consulta
     * @param idConsulta ID da consulta
     * @return Lista de cancelamentos
     */
    public List<Cancelamento> listarPorConsulta(Long idConsulta) {
        List<Cancelamento> cancelamentos = new ArrayList<>();
        String sql = "SELECT ID_AJUSTE, ID_CONSULTA, TP_AJUSTE, MOT_SOLIC, NV_DT_HORA " +
                     "FROM T_EASEHC_CANREM WHERE ID_CONSULTA = ? ORDER BY ID_AJUSTE DESC";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, idConsulta);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                cancelamentos.add(extrairCancelamentoDoResultSet(rs));
            }
            
            return cancelamentos;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar cancelamentos por consulta: " + e.getMessage(), e);
        }
    }
    
    /**
     * Deleta um cancelamento pelo ID
     * @param id ID do cancelamento
     * @return true se deletado com sucesso
     */
    public boolean deletar(Long id) {
        String sql = "DELETE FROM T_EASEHC_CANREM WHERE ID_AJUSTE = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                conn.commit();
                return true;
            }
            
            return false;
            
        } catch (SQLException e) {
            ConexaoBD.rollback();
            throw new DatabaseException("Erro ao deletar cancelamento: " + e.getMessage(), e);
        }
    }
    
    /**
     * Método auxiliar para extrair objeto Cancelamento do ResultSet
     */
    private Cancelamento extrairCancelamentoDoResultSet(ResultSet rs) throws SQLException {
        Cancelamento cancelamento = new Cancelamento();
        cancelamento.setIdAjuste(rs.getLong("ID_AJUSTE"));
        cancelamento.setIdConsulta(rs.getLong("ID_CONSULTA"));
        cancelamento.setTipoAjuste(rs.getString("TP_AJUSTE"));
        cancelamento.setMotivoSolicitacao(rs.getString("MOT_SOLIC"));
        
        Timestamp novaDataHora = rs.getTimestamp("NV_DT_HORA");
        if (novaDataHora != null) {
            cancelamento.setNovaDataHora(novaDataHora.toLocalDateTime());
        }
        
        return cancelamento;
    }
}

