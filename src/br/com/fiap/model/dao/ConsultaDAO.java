package br.com.fiap.model.dao;

import br.com.fiap.exception.DatabaseException;
import br.com.fiap.model.dto.Consulta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para operações CRUD da entidade Consulta
 */
public class ConsultaDAO {
    
    /**
     * Cria uma nova consulta no banco de dados
     * @param consulta objeto Consulta a ser criado
     * @return Consulta criada com ID gerado
     */
    public Consulta criar(Consulta consulta) {
        String sql = "INSERT INTO T_EASEHC_CONSULTA (ID_CONSULTA, ID_PACIENTE, ID_MEDICO, ID_LOCAL, ID_ESP, " +
                     "DT_HORA, DUR_MINUTOS, STATUS, OBSERVACOES, PRIORIDADE) " +
                     "VALUES (SEQ_EASEHC_CONSULTA.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_CONSULTA"})) {
            
            stmt.setLong(1, consulta.getIdPaciente());
            stmt.setLong(2, consulta.getIdMedico());
            stmt.setLong(3, consulta.getIdLocalizacao());
            stmt.setLong(4, consulta.getIdEspecialidade());
            stmt.setTimestamp(5, Timestamp.valueOf(consulta.getDataHora()));
            stmt.setInt(6, consulta.getDuracaoMinutos());
            stmt.setString(7, consulta.getStatus());
            stmt.setString(8, consulta.getObservacoes());
            stmt.setString(9, consulta.getPrioridade());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    consulta.setIdConsulta(rs.getLong(1));
                }
                conn.commit();
                return consulta;
            }
            
            throw new DatabaseException("Falha ao criar consulta - nenhuma linha foi afetada");
            
        } catch (SQLException e) {
            ConexaoBD.rollback();
            throw new DatabaseException("Erro ao criar consulta: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca uma consulta pelo ID
     * @param id ID da consulta
     * @return Consulta encontrada ou null
     */
    public Consulta buscarPorId(Long id) {
        String sql = "SELECT ID_CONSULTA, ID_PACIENTE, ID_MEDICO, ID_LOCAL, ID_ESP, DT_HORA, " +
                     "DUR_MINUTOS, STATUS, OBSERVACOES, PRIORIDADE " +
                     "FROM T_EASEHC_CONSULTA WHERE ID_CONSULTA = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extrairConsultaDoResultSet(rs);
            }
            
            return null;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar consulta por ID: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lista todas as consultas
     * @return Lista de consultas
     */
    public List<Consulta> listarTodos() {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT ID_CONSULTA, ID_PACIENTE, ID_MEDICO, ID_LOCAL, ID_ESP, DT_HORA, " +
                     "DUR_MINUTOS, STATUS, OBSERVACOES, PRIORIDADE " +
                     "FROM T_EASEHC_CONSULTA ORDER BY DT_HORA DESC";
        
        try (Connection conn = ConexaoBD.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                consultas.add(extrairConsultaDoResultSet(rs));
            }
            
            return consultas;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar consultas: " + e.getMessage(), e);
        }
    }
    
    /**
     * Atualiza uma consulta existente
     * @param consulta objeto Consulta com dados atualizados
     * @return true se atualizado com sucesso
     */
    public boolean atualizar(Consulta consulta) {
        String sql = "UPDATE T_EASEHC_CONSULTA SET ID_PACIENTE = ?, ID_MEDICO = ?, ID_LOCAL = ?, " +
                     "ID_ESP = ?, DT_HORA = ?, DUR_MINUTOS = ?, STATUS = ?, OBSERVACOES = ?, PRIORIDADE = ? " +
                     "WHERE ID_CONSULTA = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, consulta.getIdPaciente());
            stmt.setLong(2, consulta.getIdMedico());
            stmt.setLong(3, consulta.getIdLocalizacao());
            stmt.setLong(4, consulta.getIdEspecialidade());
            stmt.setTimestamp(5, Timestamp.valueOf(consulta.getDataHora()));
            stmt.setInt(6, consulta.getDuracaoMinutos());
            stmt.setString(7, consulta.getStatus());
            stmt.setString(8, consulta.getObservacoes());
            stmt.setString(9, consulta.getPrioridade());
            stmt.setLong(10, consulta.getIdConsulta());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                conn.commit();
                return true;
            }
            
            return false;
            
        } catch (SQLException e) {
            ConexaoBD.rollback();
            throw new DatabaseException("Erro ao atualizar consulta: " + e.getMessage(), e);
        }
    }
    
    /**
     * Deleta uma consulta pelo ID
     * @param id ID da consulta
     * @return true se deletado com sucesso
     */
    public boolean deletar(Long id) {
        String sql = "DELETE FROM T_EASEHC_CONSULTA WHERE ID_CONSULTA = ?";
        
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
            throw new DatabaseException("Erro ao deletar consulta: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lista consultas por paciente
     * @param idPaciente ID do paciente
     * @return Lista de consultas
     */
    public List<Consulta> listarPorPaciente(Long idPaciente) {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT ID_CONSULTA, ID_PACIENTE, ID_MEDICO, ID_LOCAL, ID_ESP, DT_HORA, " +
                     "DUR_MINUTOS, STATUS, OBSERVACOES, PRIORIDADE " +
                     "FROM T_EASEHC_CONSULTA WHERE ID_PACIENTE = ? ORDER BY DT_HORA DESC";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, idPaciente);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                consultas.add(extrairConsultaDoResultSet(rs));
            }
            
            return consultas;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar consultas por paciente: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lista consultas por médico
     * @param idMedico ID do médico
     * @return Lista de consultas
     */
    public List<Consulta> listarPorMedico(Long idMedico) {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT ID_CONSULTA, ID_PACIENTE, ID_MEDICO, ID_LOCAL, ID_ESP, DT_HORA, " +
                     "DUR_MINUTOS, STATUS, OBSERVACOES, PRIORIDADE " +
                     "FROM T_EASEHC_CONSULTA WHERE ID_MEDICO = ? ORDER BY DT_HORA DESC";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, idMedico);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                consultas.add(extrairConsultaDoResultSet(rs));
            }
            
            return consultas;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar consultas por médico: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lista consultas por status
     * @param status status da consulta (Agendada, Cancelada, Realizada)
     * @return Lista de consultas
     */
    public List<Consulta> listarPorStatus(String status) {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT ID_CONSULTA, ID_PACIENTE, ID_MEDICO, ID_LOCAL, ID_ESP, DT_HORA, " +
                     "DUR_MINUTOS, STATUS, OBSERVACOES, PRIORIDADE " +
                     "FROM T_EASEHC_CONSULTA WHERE STATUS = ? ORDER BY DT_HORA DESC";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                consultas.add(extrairConsultaDoResultSet(rs));
            }
            
            return consultas;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar consultas por status: " + e.getMessage(), e);
        }
    }
    
    /**
     * Método auxiliar para extrair objeto Consulta do ResultSet
     */
    private Consulta extrairConsultaDoResultSet(ResultSet rs) throws SQLException {
        Consulta consulta = new Consulta();
        consulta.setIdConsulta(rs.getLong("ID_CONSULTA"));
        consulta.setIdPaciente(rs.getLong("ID_PACIENTE"));
        consulta.setIdMedico(rs.getLong("ID_MEDICO"));
        consulta.setIdLocalizacao(rs.getLong("ID_LOCAL"));
        consulta.setIdEspecialidade(rs.getLong("ID_ESP"));
        
        Timestamp dataHora = rs.getTimestamp("DT_HORA");
        if (dataHora != null) {
            consulta.setDataHora(dataHora.toLocalDateTime());
        }
        
        consulta.setDuracaoMinutos(rs.getInt("DUR_MINUTOS"));
        consulta.setStatus(rs.getString("STATUS"));
        consulta.setObservacoes(rs.getString("OBSERVACOES"));
        consulta.setPrioridade(rs.getString("PRIORIDADE"));
        
        return consulta;
    }
}
