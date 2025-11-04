package br.com.fiap.model.dao;

import br.com.fiap.exception.DatabaseException;
import br.com.fiap.model.dto.Especialidade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para operações CRUD da entidade Especialidade
 */
public class EspecialidadeDAO {
    
    /**
     * Cria uma nova especialidade no banco de dados
     * @param especialidade objeto Especialidade a ser criado
     * @return Especialidade criada com ID gerado
     */
    public Especialidade criar(Especialidade especialidade) {
        String sql = "INSERT INTO T_EASEHC_ESPECIALIDADE (ID_ESP, NM_ESP, AREA_MED, TP_MED_CONS) " +
                     "VALUES (SEQ_EASEHC_ESPECIALIDADE.NEXTVAL, ?, ?, ?)";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_ESP"})) {
            
            stmt.setString(1, especialidade.getNomeEspecialidade());
            stmt.setString(2, especialidade.getAreaMedica());
            stmt.setInt(3, especialidade.getTempoMedioConsulta());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    especialidade.setIdEspecialidade(rs.getLong(1));
                }
                conn.commit();
                return especialidade;
            }
            
            throw new DatabaseException("Falha ao criar especialidade - nenhuma linha foi afetada");
            
        } catch (SQLException e) {
            ConexaoBD.rollback();
            throw new DatabaseException("Erro ao criar especialidade: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca uma especialidade pelo ID
     * @param id ID da especialidade
     * @return Especialidade encontrada ou null
     */
    public Especialidade buscarPorId(Long id) {
        String sql = "SELECT ID_ESP, NM_ESP, AREA_MED, TP_MED_CONS " +
                     "FROM T_EASEHC_ESPECIALIDADE WHERE ID_ESP = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extrairEspecialidadeDoResultSet(rs);
            }
            
            return null;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar especialidade por ID: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lista todas as especialidades
     * @return Lista de especialidades
     */
    public List<Especialidade> listarTodos() {
        List<Especialidade> especialidades = new ArrayList<>();
        String sql = "SELECT ID_ESP, NM_ESP, AREA_MED, TP_MED_CONS " +
                     "FROM T_EASEHC_ESPECIALIDADE ORDER BY NM_ESP";
        
        try (Connection conn = ConexaoBD.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                especialidades.add(extrairEspecialidadeDoResultSet(rs));
            }
            
            return especialidades;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar especialidades: " + e.getMessage(), e);
        }
    }
    
    /**
     * Atualiza uma especialidade existente
     * @param especialidade objeto Especialidade com dados atualizados
     * @return true se atualizado com sucesso
     */
    public boolean atualizar(Especialidade especialidade) {
        String sql = "UPDATE T_EASEHC_ESPECIALIDADE SET NM_ESP = ?, AREA_MED = ?, TP_MED_CONS = ? " +
                     "WHERE ID_ESP = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, especialidade.getNomeEspecialidade());
            stmt.setString(2, especialidade.getAreaMedica());
            stmt.setInt(3, especialidade.getTempoMedioConsulta());
            stmt.setLong(4, especialidade.getIdEspecialidade());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                conn.commit();
                return true;
            }
            
            return false;
            
        } catch (SQLException e) {
            ConexaoBD.rollback();
            throw new DatabaseException("Erro ao atualizar especialidade: " + e.getMessage(), e);
        }
    }
    
    /**
     * Deleta uma especialidade pelo ID
     * @param id ID da especialidade
     * @return true se deletado com sucesso
     */
    public boolean deletar(Long id) {
        String sql = "DELETE FROM T_EASEHC_ESPECIALIDADE WHERE ID_ESP = ?";
        
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
            throw new DatabaseException("Erro ao deletar especialidade: " + e.getMessage(), e);
        }
    }
    
    /**
     * Método auxiliar para extrair objeto Especialidade do ResultSet
     */
    private Especialidade extrairEspecialidadeDoResultSet(ResultSet rs) throws SQLException {
        Especialidade especialidade = new Especialidade();
        especialidade.setIdEspecialidade(rs.getLong("ID_ESP"));
        especialidade.setNomeEspecialidade(rs.getString("NM_ESP"));
        especialidade.setAreaMedica(rs.getString("AREA_MED"));
        especialidade.setTempoMedioConsulta(rs.getInt("TP_MED_CONS"));
        
        return especialidade;
    }
}

