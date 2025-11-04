package br.com.fiap.model.dao;

import br.com.fiap.exception.DatabaseException;
import br.com.fiap.model.dto.Orientacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para operações CRUD da entidade Orientacao
 */
public class OrientacaoDAO {
    
    /**
     * Cria uma nova orientação
     * @param orientacao objeto Orientacao a ser criado
     * @return Orientacao criada com ID gerado
     */
    public Orientacao criar(Orientacao orientacao) {
        String sql = "INSERT INTO T_EASEHC_ORIENTACAO (ID_ORIENT, ID_CONSULTA, TP_EXAME, INS_PREP, REC_POS) " +
                     "VALUES (SEQ_EASEHC_ORIENTACAO.NEXTVAL, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_ORIENT"})) {
            
            stmt.setLong(1, orientacao.getIdConsulta());
            stmt.setString(2, orientacao.getTipoExame());
            stmt.setString(3, orientacao.getInstrucoesPreparacao());
            stmt.setString(4, orientacao.getRecomendacoesPosExame());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    orientacao.setIdOrientacao(rs.getLong(1));
                }
                conn.commit();
                return orientacao;
            }
            
            throw new DatabaseException("Falha ao criar orientação - nenhuma linha foi afetada");
            
        } catch (SQLException e) {
            ConexaoBD.rollback();
            throw new DatabaseException("Erro ao criar orientação: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca uma orientação pelo ID
     * @param id ID da orientação
     * @return Orientacao encontrada ou null
     */
    public Orientacao buscarPorId(Long id) {
        String sql = "SELECT ID_ORIENT, ID_CONSULTA, TP_EXAME, INS_PREP, REC_POS " +
                     "FROM T_EASEHC_ORIENTACAO WHERE ID_ORIENT = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extrairOrientacaoDoResultSet(rs);
            }
            
            return null;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar orientação por ID: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lista todas as orientações
     * @return Lista de orientações
     */
    public List<Orientacao> listarTodos() {
        List<Orientacao> orientacoes = new ArrayList<>();
        String sql = "SELECT ID_ORIENT, ID_CONSULTA, TP_EXAME, INS_PREP, REC_POS " +
                     "FROM T_EASEHC_ORIENTACAO ORDER BY ID_ORIENT";
        
        try (Connection conn = ConexaoBD.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                orientacoes.add(extrairOrientacaoDoResultSet(rs));
            }
            
            return orientacoes;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar orientações: " + e.getMessage(), e);
        }
    }
    
    /**
     * Atualiza uma orientação existente
     * @param orientacao objeto Orientacao com dados atualizados
     * @return true se atualizado com sucesso
     */
    public boolean atualizar(Orientacao orientacao) {
        String sql = "UPDATE T_EASEHC_ORIENTACAO SET ID_CONSULTA = ?, TP_EXAME = ?, INS_PREP = ?, REC_POS = ? " +
                     "WHERE ID_ORIENT = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, orientacao.getIdConsulta());
            stmt.setString(2, orientacao.getTipoExame());
            stmt.setString(3, orientacao.getInstrucoesPreparacao());
            stmt.setString(4, orientacao.getRecomendacoesPosExame());
            stmt.setLong(5, orientacao.getIdOrientacao());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                conn.commit();
                return true;
            }
            
            return false;
            
        } catch (SQLException e) {
            ConexaoBD.rollback();
            throw new DatabaseException("Erro ao atualizar orientação: " + e.getMessage(), e);
        }
    }
    
    /**
     * Deleta uma orientação pelo ID
     * @param id ID da orientação
     * @return true se deletado com sucesso
     */
    public boolean deletar(Long id) {
        String sql = "DELETE FROM T_EASEHC_ORIENTACAO WHERE ID_ORIENT = ?";
        
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
            throw new DatabaseException("Erro ao deletar orientação: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lista orientações por consulta
     * @param idConsulta ID da consulta
     * @return Lista de orientações
     */
    public List<Orientacao> listarPorConsulta(Long idConsulta) {
        List<Orientacao> orientacoes = new ArrayList<>();
        String sql = "SELECT ID_ORIENT, ID_CONSULTA, TP_EXAME, INS_PREP, REC_POS " +
                     "FROM T_EASEHC_ORIENTACAO WHERE ID_CONSULTA = ? ORDER BY ID_ORIENT";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, idConsulta);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                orientacoes.add(extrairOrientacaoDoResultSet(rs));
            }
            
            return orientacoes;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar orientações por consulta: " + e.getMessage(), e);
        }
    }
    
    /**
     * Método auxiliar para extrair objeto Orientacao do ResultSet
     */
    private Orientacao extrairOrientacaoDoResultSet(ResultSet rs) throws SQLException {
        Orientacao orientacao = new Orientacao();
        orientacao.setIdOrientacao(rs.getLong("ID_ORIENT"));
        orientacao.setIdConsulta(rs.getLong("ID_CONSULTA"));
        orientacao.setTipoExame(rs.getString("TP_EXAME"));
        orientacao.setInstrucoesPreparacao(rs.getString("INS_PREP"));
        orientacao.setRecomendacoesPosExame(rs.getString("REC_POS"));
        
        return orientacao;
    }
}

