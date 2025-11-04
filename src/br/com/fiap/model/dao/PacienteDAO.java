package br.com.fiap.model.dao;

import br.com.fiap.exception.DatabaseException;
import br.com.fiap.model.dto.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para operações CRUD da entidade Paciente
 */
public class PacienteDAO {
    
    /**
     * Cria um novo paciente no banco de dados
     * @param paciente objeto Paciente a ser criado
     * @return Paciente criado com ID gerado
     */
    public Paciente criar(Paciente paciente) {
        String sql = "INSERT INTO T_EASEHC_PACIENTE (ID_PACIENTE, NM_COMPLETO, DT_NASC, GENERO, TELEFONE, TP_SANGUINEO, ALERGIAS) " +
                     "VALUES (SEQ_EASEHC_PACIENTE.NEXTVAL, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_PACIENTE"})) {
            
            stmt.setString(1, paciente.getNomeCompleto());
            stmt.setDate(2, Date.valueOf(paciente.getDataNascimento()));
            stmt.setString(3, paciente.getGenero());
            stmt.setString(4, paciente.getTelefone());
            stmt.setString(5, paciente.getTipoSanguineo());
            stmt.setString(6, paciente.getAlergias());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    paciente.setIdPaciente(rs.getLong(1));
                }
                conn.commit();
                return paciente;
            }
            
            throw new DatabaseException("Falha ao criar paciente - nenhuma linha foi afetada");
            
        } catch (SQLException e) {
            ConexaoBD.rollback();
            throw new DatabaseException("Erro ao criar paciente: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca um paciente pelo ID
     * @param id ID do paciente
     * @return Paciente encontrado ou null
     */
    public Paciente buscarPorId(Long id) {
        String sql = "SELECT ID_PACIENTE, NM_COMPLETO, DT_NASC, GENERO, TELEFONE, TP_SANGUINEO, ALERGIAS " +
                     "FROM T_EASEHC_PACIENTE WHERE ID_PACIENTE = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extrairPacienteDoResultSet(rs);
            }
            
            return null;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar paciente por ID: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lista todos os pacientes
     * @return Lista de pacientes
     */
    public List<Paciente> listarTodos() {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT ID_PACIENTE, NM_COMPLETO, DT_NASC, GENERO, TELEFONE, TP_SANGUINEO, ALERGIAS " +
                     "FROM T_EASEHC_PACIENTE ORDER BY NM_COMPLETO";
        
        try (Connection conn = ConexaoBD.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                pacientes.add(extrairPacienteDoResultSet(rs));
            }
            
            return pacientes;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar pacientes: " + e.getMessage(), e);
        }
    }
    
    /**
     * Atualiza um paciente existente
     * @param paciente objeto Paciente com dados atualizados
     * @return true se atualizado com sucesso
     */
    public boolean atualizar(Paciente paciente) {
        String sql = "UPDATE T_EASEHC_PACIENTE SET NM_COMPLETO = ?, DT_NASC = ?, GENERO = ?, " +
                     "TELEFONE = ?, TP_SANGUINEO = ?, ALERGIAS = ? WHERE ID_PACIENTE = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, paciente.getNomeCompleto());
            stmt.setDate(2, Date.valueOf(paciente.getDataNascimento()));
            stmt.setString(3, paciente.getGenero());
            stmt.setString(4, paciente.getTelefone());
            stmt.setString(5, paciente.getTipoSanguineo());
            stmt.setString(6, paciente.getAlergias());
            stmt.setLong(7, paciente.getIdPaciente());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                conn.commit();
                return true;
            }
            
            return false;
            
        } catch (SQLException e) {
            ConexaoBD.rollback();
            throw new DatabaseException("Erro ao atualizar paciente: " + e.getMessage(), e);
        }
    }
    
    /**
     * Deleta um paciente pelo ID
     * @param id ID do paciente
     * @return true se deletado com sucesso
     */
    public boolean deletar(Long id) {
        String sql = "DELETE FROM T_EASEHC_PACIENTE WHERE ID_PACIENTE = ?";
        
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
            throw new DatabaseException("Erro ao deletar paciente: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca pacientes por nome (busca parcial)
     * @param nome nome ou parte do nome do paciente
     * @return Lista de pacientes encontrados
     */
    public List<Paciente> buscarPorNome(String nome) {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT ID_PACIENTE, NM_COMPLETO, DT_NASC, GENERO, TELEFONE, TP_SANGUINEO, ALERGIAS " +
                     "FROM T_EASEHC_PACIENTE WHERE UPPER(NM_COMPLETO) LIKE UPPER(?) ORDER BY NM_COMPLETO";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                pacientes.add(extrairPacienteDoResultSet(rs));
            }
            
            return pacientes;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar pacientes por nome: " + e.getMessage(), e);
        }
    }
    
    /**
     * Método auxiliar para extrair objeto Paciente do ResultSet
     */
    private Paciente extrairPacienteDoResultSet(ResultSet rs) throws SQLException {
        Paciente paciente = new Paciente();
        paciente.setIdPaciente(rs.getLong("ID_PACIENTE"));
        paciente.setNomeCompleto(rs.getString("NM_COMPLETO"));
        
        Date dataNasc = rs.getDate("DT_NASC");
        if (dataNasc != null) {
            paciente.setDataNascimento(dataNasc.toLocalDate());
        }
        
        paciente.setGenero(rs.getString("GENERO"));
        paciente.setTelefone(rs.getString("TELEFONE"));
        paciente.setTipoSanguineo(rs.getString("TP_SANGUINEO"));
        paciente.setAlergias(rs.getString("ALERGIAS"));
        
        return paciente;
    }
}
