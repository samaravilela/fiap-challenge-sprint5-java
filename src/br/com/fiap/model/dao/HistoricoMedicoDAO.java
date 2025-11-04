package br.com.fiap.model.dao;

import br.com.fiap.exception.DatabaseException;
import br.com.fiap.model.dto.HistoricoMedico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para operações CRUD da entidade HistoricoMedico
 */
public class HistoricoMedicoDAO {
    
    /**
     * Cria um novo histórico médico
     * @param historico objeto HistoricoMedico a ser criado
     * @return HistoricoMedico criado com ID gerado
     */
    public HistoricoMedico criar(HistoricoMedico historico) {
        String sql = "INSERT INTO T_EASEHC_HISTORICO (ID_HIST, ID_PAC, DIAGNOST, TRATAM, MEDICACAO, OBS_HIST, DATA_ACESS) " +
                     "VALUES (SEQ_EASEHC_HISTORICO.NEXTVAL, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_HIST"})) {
            
            stmt.setLong(1, historico.getIdPaciente());
            stmt.setString(2, historico.getDiagnostico());
            stmt.setString(3, historico.getTratamento());
            stmt.setString(4, historico.getMedicacao());
            stmt.setString(5, historico.getObservacoes());
            stmt.setDate(6, Date.valueOf(historico.getDataAcesso()));
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    historico.setIdHistorico(rs.getLong(1));
                }
                conn.commit();
                return historico;
            }
            
            throw new DatabaseException("Falha ao criar histórico médico - nenhuma linha foi afetada");
            
        } catch (SQLException e) {
            ConexaoBD.rollback();
            throw new DatabaseException("Erro ao criar histórico médico: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca um histórico médico pelo ID
     * @param id ID do histórico
     * @return HistoricoMedico encontrado ou null
     */
    public HistoricoMedico buscarPorId(Long id) {
        String sql = "SELECT ID_HIST, ID_PAC, DIAGNOST, TRATAM, MEDICACAO, OBS_HIST, DATA_ACESS " +
                     "FROM T_EASEHC_HISTORICO WHERE ID_HIST = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extrairHistoricoDoResultSet(rs);
            }
            
            return null;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar histórico médico por ID: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lista todos os históricos médicos
     * @return Lista de históricos médicos
     */
    public List<HistoricoMedico> listarTodos() {
        List<HistoricoMedico> historicos = new ArrayList<>();
        String sql = "SELECT ID_HIST, ID_PAC, DIAGNOST, TRATAM, MEDICACAO, OBS_HIST, DATA_ACESS " +
                     "FROM T_EASEHC_HISTORICO ORDER BY DATA_ACESS DESC";
        
        try (Connection conn = ConexaoBD.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                historicos.add(extrairHistoricoDoResultSet(rs));
            }
            
            return historicos;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar históricos médicos: " + e.getMessage(), e);
        }
    }
    
    /**
     * Atualiza um histórico médico existente
     * @param historico objeto HistoricoMedico com dados atualizados
     * @return true se atualizado com sucesso
     */
    public boolean atualizar(HistoricoMedico historico) {
        String sql = "UPDATE T_EASEHC_HISTORICO SET ID_PAC = ?, DIAGNOST = ?, TRATAM = ?, " +
                     "MEDICACAO = ?, OBS_HIST = ?, DATA_ACESS = ? WHERE ID_HIST = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, historico.getIdPaciente());
            stmt.setString(2, historico.getDiagnostico());
            stmt.setString(3, historico.getTratamento());
            stmt.setString(4, historico.getMedicacao());
            stmt.setString(5, historico.getObservacoes());
            stmt.setDate(6, Date.valueOf(historico.getDataAcesso()));
            stmt.setLong(7, historico.getIdHistorico());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                conn.commit();
                return true;
            }
            
            return false;
            
        } catch (SQLException e) {
            ConexaoBD.rollback();
            throw new DatabaseException("Erro ao atualizar histórico médico: " + e.getMessage(), e);
        }
    }
    
    /**
     * Deleta um histórico médico pelo ID
     * @param id ID do histórico
     * @return true se deletado com sucesso
     */
    public boolean deletar(Long id) {
        String sql = "DELETE FROM T_EASEHC_HISTORICO WHERE ID_HIST = ?";
        
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
            throw new DatabaseException("Erro ao deletar histórico médico: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lista históricos médicos por paciente
     * @param idPaciente ID do paciente
     * @return Lista de históricos médicos
     */
    public List<HistoricoMedico> listarPorPaciente(Long idPaciente) {
        List<HistoricoMedico> historicos = new ArrayList<>();
        String sql = "SELECT ID_HIST, ID_PAC, DIAGNOST, TRATAM, MEDICACAO, OBS_HIST, DATA_ACESS " +
                     "FROM T_EASEHC_HISTORICO WHERE ID_PAC = ? ORDER BY DATA_ACESS DESC";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, idPaciente);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                historicos.add(extrairHistoricoDoResultSet(rs));
            }
            
            return historicos;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar históricos por paciente: " + e.getMessage(), e);
        }
    }
    
    /**
     * Método auxiliar para extrair objeto HistoricoMedico do ResultSet
     */
    private HistoricoMedico extrairHistoricoDoResultSet(ResultSet rs) throws SQLException {
        HistoricoMedico historico = new HistoricoMedico();
        historico.setIdHistorico(rs.getLong("ID_HIST"));
        historico.setIdPaciente(rs.getLong("ID_PAC"));
        historico.setDiagnostico(rs.getString("DIAGNOST"));
        historico.setTratamento(rs.getString("TRATAM"));
        historico.setMedicacao(rs.getString("MEDICACAO"));
        historico.setObservacoes(rs.getString("OBS_HIST"));
        
        Date dataAcesso = rs.getDate("DATA_ACESS");
        if (dataAcesso != null) {
            historico.setDataAcesso(dataAcesso.toLocalDate());
        }
        
        return historico;
    }
}

