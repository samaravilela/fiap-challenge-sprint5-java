package br.com.fiap.model.dao;

import br.com.fiap.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por gerenciar conexões com o banco de dados Oracle
 * Implementa padrão Singleton para garantir uma única instância de conexão
 */
public class ConexaoBD {
    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USUARIO = "rm566133";
    private static final String SENHA = "280201";

    private static Connection conexao;

    /**
     * Construtor privado para evitar instanciação
     */
    private ConexaoBD() {}

    /**
     * Obtém uma conexão com o banco de dados (padrão Singleton)
     * @return Connection - conexão ativa com o banco de dados
     * @throws DatabaseException se houver erro ao conectar
     */
    public static Connection getConexao() {
        try {
            if (conexao == null || conexao.isClosed()) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
                conexao.setAutoCommit(false); // Controle manual de transações
            }
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("Driver JDBC do Oracle não encontrado", e);
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao conectar com o banco de dados", e);
        }
        return conexao;
    }

    /**
     * Fecha a conexão com o banco de dados
     */
    public static void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                conexao = null;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao fechar conexão com o banco de dados", e);
        }
    }

    /**
     * Realiza commit da transação
     */
    public static void commit() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.commit();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao realizar commit da transação", e);
        }
    }

    /**
     * Realiza rollback da transação
     */
    public static void rollback() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.rollback();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao realizar rollback da transação", e);
        }
    }

    /**
     * Testa a conexão com o banco de dados
     * @return true se a conexão foi bem-sucedida, false caso contrário
     */
    public static boolean testarConexao() {
        try (Connection testConn = DriverManager.getConnection(URL, USUARIO, SENHA)) {
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
