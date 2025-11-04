package br.com.fiap.model.dao;

import br.com.fiap.exception.DatabaseException;
import br.com.fiap.model.dto.Localizacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para operações CRUD da entidade Localizacao
 */
public class LocalizacaoDAO {
    
    /**
     * Cria uma nova localização no banco de dados
     * @param localizacao objeto Localizacao a ser criado
     * @return Localizacao criada com ID gerado
     */
    public Localizacao criar(Localizacao localizacao) {
        String sql = "INSERT INTO T_EASEHC_LOCALIZACAO (ID_LOCAL, NM_UNIDADE, ENDERECO, ESTADO, CIDADE, PAIS, HR_FUNC, TELEFONE) " +
                     "VALUES (SEQ_EASEHC_LOCALIZACAO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_LOCAL"})) {
            
            stmt.setString(1, localizacao.getNomeUnidade());
            stmt.setString(2, localizacao.getEndereco());
            stmt.setString(3, localizacao.getEstado());
            stmt.setString(4, localizacao.getCidade());
            stmt.setString(5, localizacao.getPais());
            stmt.setString(6, localizacao.getHorarioFuncionamento());
            stmt.setString(7, localizacao.getTelefone());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    localizacao.setIdLocalizacao(rs.getLong(1));
                }
                conn.commit();
                return localizacao;
            }
            
            throw new DatabaseException("Falha ao criar localização - nenhuma linha foi afetada");
            
        } catch (SQLException e) {
            ConexaoBD.rollback();
            throw new DatabaseException("Erro ao criar localização: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca uma localização pelo ID
     * @param id ID da localização
     * @return Localizacao encontrada ou null
     */
    public Localizacao buscarPorId(Long id) {
        String sql = "SELECT ID_LOCAL, NM_UNIDADE, ENDERECO, ESTADO, CIDADE, PAIS, HR_FUNC, TELEFONE " +
                     "FROM T_EASEHC_LOCALIZACAO WHERE ID_LOCAL = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extrairLocalizacaoDoResultSet(rs);
            }
            
            return null;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar localização por ID: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lista todas as localizações
     * @return Lista de localizações
     */
    public List<Localizacao> listarTodos() {
        List<Localizacao> localizacoes = new ArrayList<>();
        String sql = "SELECT ID_LOCAL, NM_UNIDADE, ENDERECO, ESTADO, CIDADE, PAIS, HR_FUNC, TELEFONE " +
                     "FROM T_EASEHC_LOCALIZACAO ORDER BY NM_UNIDADE";
        
        try (Connection conn = ConexaoBD.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                localizacoes.add(extrairLocalizacaoDoResultSet(rs));
            }
            
            return localizacoes;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar localizações: " + e.getMessage(), e);
        }
    }
    
    /**
     * Atualiza uma localização existente
     * @param localizacao objeto Localizacao com dados atualizados
     * @return true se atualizado com sucesso
     */
    public boolean atualizar(Localizacao localizacao) {
        String sql = "UPDATE T_EASEHC_LOCALIZACAO SET NM_UNIDADE = ?, ENDERECO = ?, ESTADO = ?, " +
                     "CIDADE = ?, PAIS = ?, HR_FUNC = ?, TELEFONE = ? WHERE ID_LOCAL = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, localizacao.getNomeUnidade());
            stmt.setString(2, localizacao.getEndereco());
            stmt.setString(3, localizacao.getEstado());
            stmt.setString(4, localizacao.getCidade());
            stmt.setString(5, localizacao.getPais());
            stmt.setString(6, localizacao.getHorarioFuncionamento());
            stmt.setString(7, localizacao.getTelefone());
            stmt.setLong(8, localizacao.getIdLocalizacao());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                conn.commit();
                return true;
            }
            
            return false;
            
        } catch (SQLException e) {
            ConexaoBD.rollback();
            throw new DatabaseException("Erro ao atualizar localização: " + e.getMessage(), e);
        }
    }
    
    /**
     * Deleta uma localização pelo ID
     * @param id ID da localização
     * @return true se deletado com sucesso
     */
    public boolean deletar(Long id) {
        String sql = "DELETE FROM T_EASEHC_LOCALIZACAO WHERE ID_LOCAL = ?";
        
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
            throw new DatabaseException("Erro ao deletar localização: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lista localizações por cidade
     * @param cidade nome da cidade
     * @return Lista de localizações
     */
    public List<Localizacao> listarPorCidade(String cidade) {
        List<Localizacao> localizacoes = new ArrayList<>();
        String sql = "SELECT ID_LOCAL, NM_UNIDADE, ENDERECO, ESTADO, CIDADE, PAIS, HR_FUNC, TELEFONE " +
                     "FROM T_EASEHC_LOCALIZACAO WHERE UPPER(CIDADE) = UPPER(?) ORDER BY NM_UNIDADE";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cidade);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                localizacoes.add(extrairLocalizacaoDoResultSet(rs));
            }
            
            return localizacoes;
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar localizações por cidade: " + e.getMessage(), e);
        }
    }
    
    /**
     * Método auxiliar para extrair objeto Localizacao do ResultSet
     */
    private Localizacao extrairLocalizacaoDoResultSet(ResultSet rs) throws SQLException {
        Localizacao localizacao = new Localizacao();
        localizacao.setIdLocalizacao(rs.getLong("ID_LOCAL"));
        localizacao.setNomeUnidade(rs.getString("NM_UNIDADE"));
        localizacao.setEndereco(rs.getString("ENDERECO"));
        localizacao.setEstado(rs.getString("ESTADO"));
        localizacao.setCidade(rs.getString("CIDADE"));
        localizacao.setPais(rs.getString("PAIS"));
        localizacao.setHorarioFuncionamento(rs.getString("HR_FUNC"));
        localizacao.setTelefone(rs.getString("TELEFONE"));
        
        return localizacao;
    }
}

