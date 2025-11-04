package br.com.fiap.exception;

/**
 * Exceção personalizada para erros de validação de dados
 */
public class ValidationException extends RuntimeException {
    
    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

