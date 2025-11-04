package br.com.fiap.exception;

/**
 * Exceção personalizada para erros relacionados ao banco de dados
 */
public class DatabaseException extends RuntimeException {
    
    public DatabaseException(String message) {
        super(message);
    }
    
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

