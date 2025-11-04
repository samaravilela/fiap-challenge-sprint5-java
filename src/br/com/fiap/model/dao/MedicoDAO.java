package br.com.fiap.model.dao;

import br.com.fiap.exception.DatabaseException;
import br.com.fiap.model.dto.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para operações CRUD da entidade Medico
 */
public class MedicoDAO {
    
    /**
     * Cria um novo médico no banco de dados
     * @param medico objeto Medico a ser criado
     * @return Medico criado com ID gerado
     */
    public Medico criar(Medico medico) {
        String sql = "INSERT INTO T_EASEHC_MEDICO (ID_MEDICO, NM_COMPLETO, CRM, TELEFONE, EMAIL) " +
                     "VALUES (SEQ_EASEHC_MEDICO.NEXTVAL, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_MEDICO"})) {
            
            stmt.setString(1, medico.getNomeCompleto());
            stmt.setString(2, medico.getCrm());
            stmt.setString(3, medico.getTelefone());
            stmt.setString(4, medico.getEmail());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    medico.setIdMedico(rs.getLong(1));
                }
                conn.commit();
                return medico;
            }
            
            throw new DatabaseException("Falha ao criar médico - nenhuma linha foi afetada");
            
        } catch (SQLException e) {
            ConexaoBD.rollback();
            throw new DatabaseException("Erro ao criar médico: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca um médico pelo ID
     * @param id ID do médico
     * @return Medico encontrado ou null
     */
    public Medico buscarPorId(Long id) {
        String sql = "SELECT ID_MEDICO, NM_COMPLETO, CRM, TELEFONE, EMAIL " +
                     "FROM T_EASEHC_MEDICO WHERE ID_MEDICO = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extrairMedicoDoResultSet(rs);
            }
            
            return null;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar médico por ID: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lista todos os médicos
     * @return Lista de médicos
     */
    public List<Medico> listarTodos() {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT ID_MEDICO, NM_COMPLETO, CRM, TELEFONE, EMAIL " +
                     "FROM T_EASEHC_MEDICO ORDER BY NM_COMPLETO";
        
        try (Connection conn = ConexaoBD.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                medicos.add(extrairMedicoDoResultSet(rs));
            }
            
            return medicos;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar médicos: " + e.getMessage(), e);
        }
    }
    
    /**
     * Atualiza um médico existente
     * @param medico objeto Medico com dados atualizados
     * @return true se atualizado com sucesso
     */
    public boolean atualizar(Medico medico) {
        String sql = "UPDATE T_EASEHC_MEDICO SET NM_COMPLETO = ?, CRM = ?, TELEFONE = ?, EMAIL = ? " +
                     "WHERE ID_MEDICO = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, medico.getNomeCompleto());
            stmt.setString(2, medico.getCrm());
            stmt.setString(3, medico.getTelefone());
            stmt.setString(4, medico.getEmail());
            stmt.setLong(5, medico.getIdMedico());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                conn.commit();
                return true;
            }
            
            return false;
            
        } catch (SQLException e) {
            ConexaoBD.rollback();
            throw new DatabaseException("Erro ao atualizar médico: " + e.getMessage(), e);
        }
    }
    
    /**
     * Deleta um médico pelo ID
     * @param id ID do médico
     * @return true se deletado com sucesso
     */
    public boolean deletar(Long id) {
        String sql = "DELETE FROM T_EASEHC_MEDICO WHERE ID_MEDICO = ?";
        
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
            throw new DatabaseException("Erro ao deletar médico: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca médico por CRM
     * @param crm CRM do médico
     * @return Medico encontrado ou null
     */
    public Medico buscarPorCrm(String crm) {
        String sql = "SELECT ID_MEDICO, NM_COMPLETO, CRM, TELEFONE, EMAIL " +
                     "FROM T_EASEHC_MEDICO WHERE CRM = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, crm);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extrairMedicoDoResultSet(rs);
            }
            
            return null;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar médico por CRM: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lista médicos por especialidade
     * @param idEspecialidade ID da especialidade
     * @return Lista de médicos
     */
    public List<Medico> listarPorEspecialidade(Long idEspecialidade) {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT m.ID_MEDICO, m.NM_COMPLETO, m.CRM, m.TELEFONE, m.EMAIL " +
                     "FROM T_EASEHC_MEDICO m " +
                     "INNER JOIN T_EASEHC_MED_ESP me ON m.ID_MEDICO = me.ID_MEDICO " +
                     "WHERE me.ID_ESP = ? " +
                     "ORDER BY m.NM_COMPLETO";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, idEspecialidade);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                medicos.add(extrairMedicoDoResultSet(rs));
            }
            
            return medicos;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar médicos por especialidade: " + e.getMessage(), e);
        }
    }
    
    /**
     * Método auxiliar para extrair objeto Medico do ResultSet
     */
    private Medico extrairMedicoDoResultSet(ResultSet rs) throws SQLException {
        Medico medico = new Medico();
        medico.setIdMedico(rs.getLong("ID_MEDICO"));
        medico.setNomeCompleto(rs.getString("NM_COMPLETO"));
        medico.setCrm(rs.getString("CRM"));
        medico.setTelefone(rs.getString("TELEFONE"));
        medico.setEmail(rs.getString("EMAIL"));
        
        return medico;
    }
}
